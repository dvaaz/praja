package br.senac.rj.edumysql.praJa.entity.dto.response.fichaTecnica;

import java.util.List;

public class IngredientesEmFichaDTOResponse {
	private Integer idFicha;
	private String nomeFicha;
	private String descricaoFicha;
	private List<IngredientesEmFichaDTOResponse> ingredientes;
}
