package br.senac.rj.edumysql.praJa.entity.dto.response.shared;

import java.util.List;

public class MudarDeGrupoEmLoteDTOResponse {
    private Integer idGrupo;
    private List<String> nomeDosItens;

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public List<String> getNomeDosItens() {
        return nomeDosItens;
    }

    public void setNomeDosItens(List<String> idDoItem) {
        this.nomeDosItens = idDoItem;
    }
}