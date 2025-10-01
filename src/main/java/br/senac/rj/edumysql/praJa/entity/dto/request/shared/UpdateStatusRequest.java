package br.senac.rj.edumysql.praJa.entity.dto.request.shared;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class UpdateStatusRequest {
    @Min(0)
    @Max(1)
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
