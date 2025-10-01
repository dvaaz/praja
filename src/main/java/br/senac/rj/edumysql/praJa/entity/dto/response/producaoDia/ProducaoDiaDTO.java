package br.senac.rj.edumysql.praJa.entity.dto.response.producaoDia;

import java.util.Date;

public class ProducaoDiaDTO {
	private Integer id;
	private Date data;

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
}
