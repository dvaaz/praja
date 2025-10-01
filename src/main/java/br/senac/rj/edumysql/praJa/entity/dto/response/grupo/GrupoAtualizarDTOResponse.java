package br.senac.rj.edumysql.praJa.entity.dto.response.grupo;

public class GrupoAtualizarDTOResponse {
    private Integer id;
    private String nome;
    private String cor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
