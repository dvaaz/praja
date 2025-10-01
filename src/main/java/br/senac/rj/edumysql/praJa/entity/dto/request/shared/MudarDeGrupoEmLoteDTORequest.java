package br.senac.rj.edumysql.praJa.entity.dto.request.shared;

import java.util.List;

public class MudarDeGrupoEmLoteDTORequest {
  private List<Integer> idDosItens;

  public List<Integer> getIdDosItens() {
    return idDosItens;
  }

  public void setIdDosItens(List<Integer> idDoItem) {
    this.idDosItens = idDoItem;
  }

}
