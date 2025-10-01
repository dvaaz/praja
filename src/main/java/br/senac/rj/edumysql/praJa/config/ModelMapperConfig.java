package br.senac.rj.edumysql.praJa.config;

import br.senac.rj.edumysql.praJa.entity.Grupo;
import br.senac.rj.edumysql.praJa.entity.dto.response.fichaTecnica.FichaTecnicaDTOSemGrupo;
import br.senac.rj.edumysql.praJa.entity.dto.response.grupo.ListaFichasDeGrupoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}
}
