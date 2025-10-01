package br.senac.rj.edumysql.praJa.service;

import br.senac.rj.edumysql.praJa.Enum.StatusEnum;
import br.senac.rj.edumysql.praJa.entity.FichaTecnica;
import br.senac.rj.edumysql.praJa.entity.Grupo;
import br.senac.rj.edumysql.praJa.entity.Ingrediente;
import br.senac.rj.edumysql.praJa.entity.dto.request.fichaTecnica.FichaTecnicaDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.response.fichaTecnica.FichaTecnicaDTOResponse;
import br.senac.rj.edumysql.praJa.exception.GrupoNotFoundException;
import br.senac.rj.edumysql.praJa.repository.FichaTecnicaRepository;
import br.senac.rj.edumysql.praJa.repository.GrupoRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
   * @param dtoRequest
   * @return
   */
  @Transactional
  public FichaTecnicaDTOResponse criar(FichaTecnicaDTORequest dtoRequest){
    Grupo grupo = this.grupoRepository.buscarPorId(dtoRequest.getGrupo())
            .orElseGet(() -> this.grupoService.buscarOuCriarGrupoFichaTecnica());
    FichaTecnica fichaTecnica = new FichaTecnica();
    fichaTecnica.setNome(fichaTecnica.getNome());
    fichaTecnica.setDescricao(dtoRequest.getDescricao());
    fichaTecnica.setGrupo(grupo);
    fichaTecnica.setStatus(ativo);

    try{
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
      throw new GrupoNotFoundException("Não há nenhuma ficha tecnica criada.");
    }

    List<FichaTecnicaDTOResponse> = new ArrayList<>();
    for (FichaTecnica ficha: fichasTecnicas){
      FichaTecnicaDTOResponse dtoResponse = new FichaTecnicaDTOResponse();
      dtoResponse.setNome(ficha.getNome());

    }

  }
}

