package br.senac.rj.edumysql.praJa.entity.dto.response.producaoDia;

import java.util.Date;

public class ProducaoDiaDTOResponse {
    private Integer id;
    private Date data;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}