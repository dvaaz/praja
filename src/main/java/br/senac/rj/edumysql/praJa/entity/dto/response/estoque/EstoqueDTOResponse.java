package br.senac.rj.edumysql.praJa.entity.dto.response.estoque;

//import gerenciamentorestaurante.projeto1.validation.anotation.OrdemDasDatas;

import java.util.Date;

public class EstoqueDTOResponse {
	private Integer id;
	private Date dia;
	private Date validade;
	private Integer qtd;
	private Integer ingrediente;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public Date getValidade() {
		return validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public Integer getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Integer ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
