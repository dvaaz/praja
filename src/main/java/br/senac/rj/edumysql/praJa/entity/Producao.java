package br.senac.rj.edumysql.praJa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "producao")
public class Producao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "producao_id")
	private Integer id;
	@Column(name = "producao_qtd")
	private Integer qtd;
	@Column(name = "producao_status")
	private Integer status;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "producao_dia_id", nullable = false)
	private ProducaoDia producaoDia;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "estoque_id", nullable = false)
	private Estoque estoque;
}
