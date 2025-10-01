package br.senac.rj.edumysql.praJa.entity.dto.request.estoque;

//import gerenciamentorestaurante.projeto1.validation.anotation.OrdemDasDatas;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

public class EstoqueDTORequest {
   @NotEmpty
//   @OrdemDasDatas(primeiraData = "dia", segundaData = "validade", message = "Entrada não pode ser anterior à fabricação")
    private Date entrada;
    @NotEmpty
    @Future
    private Date validade;
    private Integer qtd;
    private Integer ingrediente;

	public Date getEntrada() {
		return entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
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
}
