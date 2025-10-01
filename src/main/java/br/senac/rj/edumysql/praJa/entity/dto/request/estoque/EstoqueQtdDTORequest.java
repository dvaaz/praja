package br.senac.rj.edumysql.praJa.entity.dto.request.estoque;

import jakarta.validation.constraints.Min;

public class EstoqueQtdDTORequest {
  @Min(1)
  private Integer qtd;

  public Integer getQtd() {
    return qtd;
  }

  public void setQtd(Integer qtd) {
    this.qtd = qtd;
  }
}
