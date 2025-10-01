package br.senac.rj.edumysql.praJa.entity.dto.request.fichaTecnica;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FichaTecnicaDTORequest {
    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;
    private String descricao;
    @NotNull(message = "É necessário que haja um grupo")
    private Integer grupo;

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

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }
}
