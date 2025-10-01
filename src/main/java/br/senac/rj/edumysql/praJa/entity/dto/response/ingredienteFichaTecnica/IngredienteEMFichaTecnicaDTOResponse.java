package br.senac.rj.edumysql.praJa.entity.dto.response.ingredienteFichaTecnica;

public class IngredienteEMFichaTecnicaDTOResponse {
    private Integer id;
//    private String observacao;
    private Integer unidadeMedida;
    private Integer qtd;
    private Integer idIngrediente;
    private String nomeIngrediente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public String getObservacao() {return observacao;}
//
//    public  void setObservacao(String observacao) {this.observacao = observacao;}

    public Integer getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(Integer unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    public void setNomeIngrediente(String nomeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
    }
}
