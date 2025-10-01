package br.senac.rj.edumysql.praJa.service;

import br.senac.rj.edumysql.praJa.Enum.GrupoEnum;
import br.senac.rj.edumysql.praJa.Enum.Status;
import br.senac.rj.edumysql.praJa.entity.Grupo;
import br.senac.rj.edumysql.praJa.entity.dto.request.grupo.GrupoDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.response.grupo.GrupoDTOResponse;
import br.senac.rj.edumysql.praJa.entity.dto.response.grupo.ListaIngredienteDeGrupoDTO;
import br.senac.rj.edumysql.praJa.exception.GrupoNotFoundException;
import br.senac.rj.edumysql.praJa.repository.FichaTecnicaRepository;
import br.senac.rj.edumysql.praJa.repository.GrupoRepository;
import br.senac.rj.edumysql.praJa.repository.IngredienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

		private final GrupoEnum grupoEnumIngr = GrupoEnum.ingrediente,
				grupoEnumFicha = GrupoEnum.fichaTecnica;
		private final Integer ativo = Status.ATIVO.getStatus(),
				inativo = Status.INATIVO.getStatus(),
				apagado = Status.APAGADO.getStatus();

		@Autowired
		public GrupoService(GrupoRepository grupoRepository, IngredienteRepository ingredienteRepository,
		                  FichaTecnicaRepository fichaTecnicaRepository, ModelMapper modelMapper) {
		this.grupoRepository = grupoRepository;
		this.ingredienteRepository = ingredienteRepository;
		this.fichaTecnicaRepository = fichaTecnicaRepository;
		this.modelMapper = modelMapper;
		}

		@Transactional
		public GrupoDTOResponse criarGrupo(GrupoDTORequest dtoRequest) {
		Grupo grupo = modelMapper.map(dtoRequest, Grupo.class);
		grupo.setStatus(ativo);

		if (grupo.getTipo() == grupoEnumIngr.getNumber() || grupo.getTipo() == grupoEnumFicha.getNumber()) {
			Grupo grupoSave = this.grupoRepository.save(grupo);
			return modelMapper.map(grupoSave, GrupoDTOResponse.class);
		} else {
			return null;
		}
		}

		/**
		 * Cria o grupo padrao de ingrediente
		 * @return Grupo
		 */
		@Transactional(propagation = Propagation.REQUIRES_NEW)
		protected Grupo criarGrupoPadraoIngrediente() {
				Grupo grupoDefault = new Grupo();

				grupoDefault.setNome(grupoEnumIngr.getText());
				grupoDefault.setStatus(ativo);
				grupoDefault.setCor("90EE90");
				grupoDefault.setTipo(grupoEnumIngr.getNumber());
				Grupo save = grupoRepository.save(grupoDefault);

				return save;
		}

		/**
		 * Cria o grupo padrao de ficha tecnica
		 * @return Grupo
		 */
		@Transactional(propagation = Propagation.REQUIRES_NEW)
		protected Grupo criarGrupoPadraoFichaTecnica() {
				Grupo grupoDefault = new Grupo();
				grupoDefault.setNome(grupoEnumFicha.getText());
				grupoDefault.setStatus(ativo);
				grupoDefault.setCor("FA8907");
				grupoDefault.setTipo(grupoEnumFicha.getNumber());
				Grupo save = grupoRepository.save(grupoDefault);

				return save;
		}

		/**
		 * Busca grupo padrao ou, caso não haja, cria o grupo padrao de ingredientes
		 * @return Grupo
		 */
		@Transactional
		public Grupo buscarOuCriarGrupoPadraoIngrediente() {
				return grupoRepository.buscarGrupoPadrao(
								grupoEnumIngr.getNumber(), grupoEnumIngr.getText())
						.orElseGet(() -> criarGrupoPadraoIngrediente());
		}

		public GrupoDTOResponse buscarGrupoPorID(Integer id) {
		Grupo grupo = this.grupoRepository.buscarGrupoPorId(id)
				.orElseThrow(() -> new GrupoNotFoundException("Grupo com o ID: "+id +" não encontrado"));

		GrupoDTOResponse dtoResponse = this.modelMapper.map(grupo, GrupoDTOResponse.class);

		return dtoResponse;
		}

		/**
		 * Lista grupos com status >=0
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
		 * @return listDTOResponse
		 */
		public List<GrupoDTOResponse> listarGruposDoTipo(Integer tipo) {
				List<Grupo> grupos = this.grupoRepository.listarPorTipo(tipo);

				if (grupos.isEmpty()) {
						GrupoEnum grupoEnum = GrupoEnum.values()[tipo];
						throw new GrupoNotFoundException("Não há nenhum grupo de "+ grupoEnum +" criado.");
				}

				return grupos.stream()
						.map(grupo -> modelMapper.map(grupo, GrupoDTOResponse.class))
						.toList();
		}

}
