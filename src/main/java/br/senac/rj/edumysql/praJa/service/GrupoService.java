package br.senac.rj.edumysql.praJa.service;

import br.senac.rj.edumysql.praJa.Enum.GrupoEnum;
import br.senac.rj.edumysql.praJa.Enum.Status;
import br.senac.rj.edumysql.praJa.entity.FichaTecnica;
import br.senac.rj.edumysql.praJa.entity.Grupo;
import br.senac.rj.edumysql.praJa.entity.Ingrediente;
import br.senac.rj.edumysql.praJa.entity.dto.request.grupo.GrupoDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.request.shared.UpdateGrupoDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.request.shared.UpdateStatusRequest;
import br.senac.rj.edumysql.praJa.entity.dto.response.grupo.GrupoAtualizarDTOResponse;
import br.senac.rj.edumysql.praJa.entity.dto.response.grupo.GrupoDTOResponse;
import br.senac.rj.edumysql.praJa.entity.dto.response.grupo.ListaIngredienteDeGrupoDTO;
import br.senac.rj.edumysql.praJa.entity.dto.response.shared.UpdateStatusResponse;
import br.senac.rj.edumysql.praJa.exception.GrupoNotFoundException;
import br.senac.rj.edumysql.praJa.repository.FichaTecnicaRepository;
import br.senac.rj.edumysql.praJa.repository.GrupoRepository;
import br.senac.rj.edumysql.praJa.repository.IngredienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {
    private final GrupoRepository grupoRepository;
    private final IngredienteRepository ingredienteRepository;
    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final ModelMapper modelMapper;

    // Enums
    private final String grupoIngrNome = GrupoEnum.ingrediente.getText(),
                        grupoFichNome = GrupoEnum.fichaTecnica.getText();

    private final Integer grupoIngrNum = GrupoEnum.ingrediente.getNumber(),
        grupoFichaNum = GrupoEnum.fichaTecnica.getNumber();
    private final Integer ativo = Status.ATIVO.getStatus(),
        inativo = Status.INATIVO.getStatus(),
        apagado = Status.APAGADO.getStatus();

    @Autowired
    public GrupoService(GrupoRepository grupoRepository,
                        IngredienteRepository ingredienteRepository,
                        FichaTecnicaRepository fichaTecnicaRepository,
                        ModelMapper modelMapper) {
        this.grupoRepository = grupoRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public GrupoDTOResponse criarGrupo(GrupoDTORequest dtoRequest) {
        Grupo grupo = modelMapper.map(dtoRequest, Grupo.class);
        grupo.setStatus(ativo);

        if (grupo.getTipo() != grupoIngrNum || grupo.getTipo() != grupoFichaNum) {
            return null;
        }
            try {
                Grupo save = grupoRepository.save(grupo);
                return modelMapper.map(save, GrupoDTOResponse.class);
            } catch (DataIntegrityViolationException ex) {
                throw new RuntimeException("Um objeto com este nome já existe: " + dtoRequest.getNome(), ex);
            }
        }

    /**
     * Cria o grupo padrao de ingrediente
     *
     * @return Grupo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected Grupo criarGrupoPadraoIngrediente() {
        Grupo grupoDefault = new Grupo();

        grupoDefault.setNome(grupoIngrNome);
        grupoDefault.setStatus(ativo);
        grupoDefault.setCor("90EE90");
        grupoDefault.setTipo(grupoIngrNum);
        Grupo save = grupoRepository.save(grupoDefault);

        return save;
    }

    /**
     * Cria o grupo padrao de ficha tecnica
     *
     * @return Grupo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected Grupo criarGrupoPadraoFichaTecnica() {
        Grupo grupoDefault = new Grupo();
        grupoDefault.setNome(grupoFichNome);
        grupoDefault.setStatus(ativo);
        grupoDefault.setCor("FA8907");
        grupoDefault.setTipo(grupoFichaNum);
        Grupo save = grupoRepository.save(grupoDefault);

        return save;
    }

    /**
     * Busca grupo padrao ou, caso não haja, cria o grupo padrao de ingredientes
     *
     * @return Grupo
     */
    @Transactional
    public Grupo buscarOuCriarGrupoPadraoIngrediente() {
        return grupoRepository.buscarGrupoPadrao(
                grupoIngrNum, grupoIngrNome)
            .orElseGet(() -> criarGrupoPadraoIngrediente());
    }

    public GrupoDTOResponse buscarGrupoPorID(Integer id) {
        Grupo grupo = this.grupoRepository.buscarGrupoPorId(id)
            .orElseThrow(() -> new GrupoNotFoundException("Grupo com o ID: " + id + " não encontrado"));

        GrupoDTOResponse dtoResponse = this.modelMapper.map(grupo, GrupoDTOResponse.class);

        return dtoResponse;
    }

    /**
     * Lista grupos com status >=0
     *
     * @return listDTOResponse
     */
    public List<GrupoDTOResponse> listarGrupos() {
        List<Grupo> grupos = this.grupoRepository.findAll();

        if (grupos.isEmpty()) {
            throw new GrupoNotFoundException("Não há nenhum grupo criado.");
        }

        return grupos.stream()
            .map(grupo -> modelMapper.map(grupo, GrupoDTOResponse.class))
            .toList();
    }

    /**
     * Lista grupos de fichas tecnicas ou ingredientes com status >=0
     *
     * @return listDTOResponse
     */
    public List<GrupoDTOResponse> listarGruposDoTipo(Integer tipo) {
        List<Grupo> grupos = this.grupoRepository.listarPorTipo(tipo);

        if (grupos.isEmpty()) {
            GrupoEnum grupoEnum = GrupoEnum.values()[tipo];
            throw new GrupoNotFoundException("Não há nenhum grupo de " + grupoEnum + " criado.");
        }

        return grupos.stream()
            .map(grupo -> modelMapper.map(grupo, GrupoDTOResponse.class))
            .toList();
    }

    @Transactional
    public GrupoAtualizarDTOResponse atualizarGrupo(Integer grupoId, UpdateGrupoDTORequest dtoRequest) {
        Grupo grupo = grupoRepository.buscarGrupoPorId(grupoId)
            .orElseThrow(() -> new GrupoNotFoundException("Grupo com o ID: "+grupoId +" não encontrado"));;

        if (dtoRequest.getCor() != null) {
            grupo.setCor(dtoRequest.getCor());
        }
        if (dtoRequest.getNome()!= null) {
            grupo.setNome(dtoRequest.getNome());
        }
        try {
            Grupo save = grupoRepository.save(grupo);
            return modelMapper.map(save, GrupoAtualizarDTOResponse.class);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Um objeto com este nome já existe: " + dtoRequest.getNome(), ex);
        }


    }

    /**
     * Apaga o grupo. Caso haja elementos no grupo eles serão alocados para o grupo padrao devido.
     *
     * @param grupoId
     */
    @Transactional
    public void apagarGrupo(Integer grupoId) {
        Grupo grupo = this.grupoRepository.buscarGrupoPorId(grupoId)
            .orElseThrow(() -> new GrupoNotFoundException("Grupo com o Id:" + grupoId + " não encontrado"));

        // Remaneja os itens presentes nos grupos para seus devidos grupos padrões.
        if (grupo.getTipo() == grupoIngrNum) {
            List<Ingrediente> listaIngredientes = this.ingredienteRepository.listarIngredientesPorGrupo(grupoId);
            if (!listaIngredientes.isEmpty()) {
                Grupo grupoPadrao = this.grupoRepository.buscarGrupoPadrao(grupoIngrNum, grupoIngrNome)
                    .orElseGet(this::criarGrupoPadraoIngrediente);

                for (Ingrediente ingrediente : listaIngredientes) {
                    ingrediente.setGrupo(grupoPadrao);
                }
            }
        } else if (grupo.getTipo() == grupoIngrNum) {
            List<FichaTecnica> listaFichasTecnicas = this.fichaTecnicaRepository.listarFichasTecnicasPorGrupo(grupoId);
            if (!listaFichasTecnicas.isEmpty()) {
                Grupo grupoPadrao = this.grupoRepository.buscarGrupoPadrao(grupoFichaNum, grupoIngrNome)
                    .orElseGet(this::criarGrupoPadraoFichaTecnica);

                for (FichaTecnica fichaTecnica : listaFichasTecnicas) {
                    fichaTecnica.setGrupo(grupoPadrao);
                }
            }
        }
    }

    @Transactional
    public UpdateStatusResponse atualizarStatus(Integer id, UpdateStatusRequest dtoRequest) {
        Grupo  grupo = this.grupoRepository.buscarGrupoPorId(id)
            .orElseThrow(() -> new GrupoNotFoundException("Grupo com o id: "+id+" não encontrado"));
        Integer novoStatus = dtoRequest.getStatus();

        grupo.setStatus(dtoRequest.getStatus());
        Grupo tempResponse = grupoRepository.save(grupo);
        return modelMapper.map(tempResponse, UpdateStatusResponse.class);
    }

    /**
     * Destroi objeto que tenha sido apagado
     * @param id
     * @return
     */
    @Transactional
    public boolean destroyGrupoRepository(Integer id) {
        Grupo grupo = this.grupoRepository.findById(id)
            .orElseThrow(() -> new GrupoNotFoundException("Grupo com o ID: " + id + " não encontrado"));

        if (grupo.getStatus() == apagado){
            grupoRepository.delete(grupo);
            return true;
        }
        return false;

    }
}