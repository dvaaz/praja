package br.senac.rj.edumysql.praJa.service;

import br.senac.rj.edumysql.praJa.Enum.StatusEnum;
import br.senac.rj.edumysql.praJa.entity.FichaTecnica;
import br.senac.rj.edumysql.praJa.entity.Grupo;
import br.senac.rj.edumysql.praJa.entity.dto.request.fichaTecnica.FichaTecnicaDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.request.shared.AlterarStatusDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.request.shared.MudarDeGrupoDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.response.fichaTecnica.FichaTecnicaDTOResponse;
import br.senac.rj.edumysql.praJa.entity.dto.response.fichaTecnica.FichaTecnicaDTOSemGrupo;
import br.senac.rj.edumysql.praJa.entity.dto.response.fichaTecnica.ListaFichasDeGrupoDTO;
import br.senac.rj.edumysql.praJa.entity.dto.response.shared.AlterarStatusDTOResponse;
import br.senac.rj.edumysql.praJa.entity.dto.response.shared.MudarDeGrupoDTOResponse;
import br.senac.rj.edumysql.praJa.entity.dto.response.shared.MudarDeGrupoEmLoteDTOResponse;
import br.senac.rj.edumysql.praJa.exception.FichaTecnicaException;
import br.senac.rj.edumysql.praJa.exception.GrupoException;
import br.senac.rj.edumysql.praJa.repository.FichaTecnicaRepository;
import br.senac.rj.edumysql.praJa.repository.GrupoRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FichaTecnicaService {
    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final GrupoRepository grupoRepository;
    private GrupoService grupoService;

    public FichaTecnicaService(FichaTecnicaRepository fichaTecnicaRepository, GrupoRepository grupoRepository, GrupoService grupoService) {
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.grupoRepository = grupoRepository;
        this.grupoService = grupoService;
    }

    // Enuns a serem utilizados na classe
    private final Integer ativo = StatusEnum.ATIVO.getStatus(),
            inativo = StatusEnum.INATIVO.getStatus(),
            apagado = StatusEnum.APAGADO.getStatus();

    // Métodos

    /**
     * Criar nova Ficha Tecnica
     *
     * @param dtoRequest
     * @return
     */
    @Transactional
    public FichaTecnicaDTOResponse criar(FichaTecnicaDTORequest dtoRequest) {
        Grupo grupo = this.grupoRepository.buscarPorId(dtoRequest.getGrupo())
                .orElseGet(() -> this.grupoService.buscarOuCriarGrupoFichaTecnica());
        FichaTecnica fichaTecnica = new FichaTecnica();
        fichaTecnica.setNome(fichaTecnica.getNome());
        fichaTecnica.setDescricao(dtoRequest.getDescricao());
        fichaTecnica.setGrupo(grupo);
        fichaTecnica.setStatus(ativo);

        try {
            FichaTecnica salvo = this.fichaTecnicaRepository.save(fichaTecnica);

            FichaTecnicaDTOResponse dtoResponse = new FichaTecnicaDTOResponse();
            dtoResponse.setId(salvo.getId());
            dtoResponse.setNome(salvo.getNome());
            dtoResponse.setDescricao(salvo.getDescricao());
            dtoResponse.setIdGrupo(salvo.getGrupo().getId());
            dtoResponse.setNomeGrupo(salvo.getGrupo().getNome());
            dtoResponse.setStatus(salvo.getStatus());

            return dtoResponse;
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Erro ao criar Ficha Tecnica ", ex);
        }
    }

  /**
   * Lista Fichas Tecnicas
   * @return
   */
  public List<FichaTecnicaDTOResponse> listar(){
    List<FichaTecnica> fichasTecnicas = this.fichaTecnicaRepository.listar();

    if (fichasTecnicas.isEmpty()){
      throw new GrupoException("Não há nenhuma ficha tecnica criada.");
    }

    List<FichaTecnicaDTOResponse> dtoResponse = new ArrayList<>();
    for (FichaTecnica ficha: fichasTecnicas){
      FichaTecnicaDTOResponse temp = new FichaTecnicaDTOResponse();
      temp.setId(ficha.getId());
      temp.setNome(ficha.getNome());
      temp.setDescricao(ficha.getDescricao());
      temp.setIdGrupo(ficha.getGrupo().getId());
      temp.setNomeGrupo(ficha.getGrupo().getNome());
      temp.setStatus(ficha.getStatus());

      dtoResponse.add(temp);
    }
    return dtoResponse;
  }

    /**
     * Lista fichas tecnicas pertencentes a um grupo
     * @param grupoId
     * @return
     */
    public ListaFichasDeGrupoDTO listarFichasDeGrupo(Integer grupoId){
        Grupo grupo = this.grupoRepository.buscarPorId(grupoId)
                .orElseThrow(() -> new GrupoException("Grupo com o ID: "+grupoId +" não encontrado"));
        List<FichaTecnica> fichas = this.fichaTecnicaRepository.listarFichasTecnicasPorGrupo(grupo.getId());
        if (fichas.isEmpty()){
            throw new FichaTecnicaException("Não há fichas no grupo : ( "+grupo.getId()+" ) -> "+grupo.getNome());
        }
        // transforma as fichas em dto especifico
        List<FichaTecnicaDTOSemGrupo> fichasDto = new ArrayList<>();
        for (FichaTecnica ficha: fichas){
            FichaTecnicaDTOSemGrupo temp = new FichaTecnicaDTOSemGrupo();
            temp.setId(ficha.getId());
            temp.setNome(ficha.getNome());
            temp.setDescricao(ficha.getDescricao());
            fichasDto.add(temp);
        }

        ListaFichasDeGrupoDTO dtoResponse = new ListaFichasDeGrupoDTO();
        dtoResponse.setIdGrupo(grupo.getId());
        dtoResponse.setNomeGrupo(grupo.getNome());
        dtoResponse.setCorGrupo(grupo.getCor());
        dtoResponse.setFichasTecnicas(fichasDto);
        return dtoResponse;
    }

    /**
     * Buscar Ficha Tecnica por Id
     * @param fichaId
     * @return
     */
  public FichaTecnicaDTOResponse buscarPorId(Integer fichaId){
      FichaTecnica fichaTecnica = this.fichaTecnicaRepository.buscarPorID(fichaId)
              .orElseThrow(() -> new GrupoException("Ficha tecnica com o ID: " + fichaId + " não encontrada"));

      FichaTecnicaDTOResponse dtoResponse = new FichaTecnicaDTOResponse();
      dtoResponse.setId(fichaTecnica.getId());
      dtoResponse.setNome(fichaTecnica.getNome());
      dtoResponse.setDescricao(fichaTecnica.getDescricao());
      dtoResponse.setIdGrupo(fichaTecnica.getGrupo().getId());
      dtoResponse.setNomeGrupo(fichaTecnica.getGrupo().getNome());
      dtoResponse.setStatus(fichaTecnica.getStatus());
      return dtoResponse;
  }

    /**
     * Altera Status de Ficha Tecnica
     * @param fichaId
     * @param dtoRequest
     * @return
     */
  @Transactional
  public AlterarStatusDTOResponse alterarStatus(Integer fichaId, AlterarStatusDTORequest dtoRequest){
      FichaTecnica ficha = this.fichaTecnicaRepository.buscarPorID(fichaId) // busca por ficha tecnica
              .orElseThrow(() -> new FichaTecnicaException("Ficha Tecnica não encontrada"));
      ficha.setStatus(dtoRequest.getStatus());

      try{
          fichaTecnicaRepository.save(ficha);
          AlterarStatusDTOResponse dtoResponse = new AlterarStatusDTOResponse();
          dtoResponse.setId(ficha.getId());
          dtoResponse.setStatus(ficha.getStatus());
          return dtoResponse;
      } catch (DataIntegrityViolationException ex){
          throw new  RuntimeException("Erro ao salvar alteracões.", ex);
      }
  }

    /**
     * Altera Grupo de Ficha Tecnica
     * @param fichaId
     * @param dtoRequest
     * @return
     */
    @Transactional
    public MudarDeGrupoDTOResponse alterarGrupo(Integer fichaId, MudarDeGrupoDTORequest dtoRequest){
        Integer grupoId = dtoRequest.getGrupo();
        Grupo grupo = this.grupoRepository.buscarPorId(grupoId) // busca pelo grupo
                .orElseThrow(() -> new GrupoException("Grupo com o ID: "+grupoId +" não encontrado"));

        FichaTecnica ficha = this.fichaTecnicaRepository.buscarPorID(fichaId) // busca pela ficha tecnica
                .orElseThrow(() -> new FichaTecnicaException("Ficha tecnica com o ID: " + fichaId + " não encontrada"));

        ficha.setGrupo(grupo);

        try{
            FichaTecnica salvo = fichaTecnicaRepository.save(ficha);

            MudarDeGrupoDTOResponse dtoResponse = new MudarDeGrupoDTOResponse();
            dtoResponse.setId(salvo.getId());
            dtoResponse.setIdGrupo(salvo.getGrupo().getId());
            dtoResponse.setNomeGrupo(salvo.getGrupo().getNome());

            return dtoResponse;
        } catch (DataIntegrityViolationException ex){
            throw new RuntimeException("Erro ao salvar alterações", ex);
        }
    }

    /**
     * Alterar várias fichas tecnicas de um grupo para outro
     * @param grupoId
     * @param dtoRequest
     * @return
     */
  @Transactional
  public MudarDeGrupoEmLoteDTOResponse mudarDeGrupoEmLote(Integer grupoId, MudarDeGrupoDTORequest dtoRequest){
      Grupo grupo = this.grupoRepository.buscarPorId(grupoId)
              .orElseThrow(() -> new GrupoException("Grupo com o id: "+grupoId+" não encontrado"));
      List<FichaTecnica> fichas = this.fichaTecnicaRepository.listarFichasTecnicasPorGrupo(grupoId);

      if (fichas.isEmpty()){
          throw new FichaTecnicaException("Não há fichas tecnicas no grupo: ( "+grupoId+" )-> "+grupo.getNome());
      }
    try {
        for (FichaTecnica ficha : fichas) {
            ficha.setGrupo(grupo);
            FichaTecnica salvo = fichaTecnicaRepository.save(ficha);
        }

        MudarDeGrupoEmLoteDTOResponse dtoResponse = new MudarDeGrupoEmLoteDTOResponse();
        dtoResponse.setIdGrupo(grupo.getId());
        dtoResponse.setNomeDosItens(
                fichas.stream()
                .map(FichaTecnica::getNome)
                .collect(Collectors.toList()));
        return dtoResponse;

    } catch (DataIntegrityViolationException ex){
        throw new  RuntimeException("Erro ao salvar alteracões.", ex);
    }
  }

    @Transactional
    public boolean apagar(Integer fichaId){
      FichaTecnica ficha = this.fichaTecnicaRepository.buscarPorID(fichaId)
              .orElseThrow(() -> new FichaTecnicaException("Ficha tecnica com o ID: " + fichaId + " não encontrada"));
      boolean removido =this.fichaTecnicaRepository.updateStatus(fichaId, apagado);
      return removido;
    }

    /**
     * Destroi objeto que tenha sido apagado
     * @param fichaId
     * @return
     */
    @Transactional
    public boolean destroy(Integer fichaId) {
        FichaTecnica ficha = this.fichaTecnicaRepository.findById(fichaId)
                .orElseThrow(() -> new GrupoException("Ficha Tecnica com o ID: " + fichaId + " não encontrado"));

        if (ficha.getStatus() == apagado){
            fichaTecnicaRepository.delete(ficha);
            return true;
        }
        return false;
    }
}

