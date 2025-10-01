package br.senac.rj.edumysql.praJa.entity.dto.request.shared;

public class AlterarUnidadeMedidasDTORequest {
  private Integer qtd;
  private Integer unidadeMedida;

  public Integer getQtd() {
    return qtd;
  }

  public void setQtd(Integer qtd) {
    this.qtd = qtd;
  }

  public Integer getUnidadeMedida() {
    return unidadeMedida;
  }

  public void setUnidadeMedida(Integer unidadeMedida) {
    this.unidadeMedida = unidadeMedida;
  }
}
