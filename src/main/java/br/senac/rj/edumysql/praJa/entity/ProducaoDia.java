package br.senac.rj.edumysql.praJa.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "producao_dia")
public class ProducaoDia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "producao_dia_id")
	private Integer id;
	@Column(name = "producao_dia_data")
	private Date data;
	@Column(name="producao_dia_status")
	private Integer status;
	@OneToMany(mappedBy = "producaoDia")
	private Set<Producao> producaoDia;
}
