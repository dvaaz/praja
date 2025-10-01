package br.senac.rj.edumysql.praJa.entity.dto.request.fichaTecnica;

public class FichaTecnicaDTORequest {
    private String nome;
    private String descricao;
    private Integer grupoId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }
}
