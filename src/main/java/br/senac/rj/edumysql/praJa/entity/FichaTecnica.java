package br.senac.rj.edumysql.praJa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "ficha_tecnica")
public class FichaTecnica {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ficha_tecnica_id")
	private Integer id;
	@Column(name="ficha_tecnica_nome", nullable = false)
	private String nome;
	@Column(name="ficha_tecnica_descricao")
	private String descricao;
	@Column(name="ficha_tecnica_status")
	private Integer status;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "grupo_id", nullable = false)
	private Grupo grupo;

	@OneToMany(mappedBy = "fichaTecnica")
	private Set<IngredienteFichaTecnica> fichaTecnicaSet;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Set<IngredienteFichaTecnica> getFichaTecnicaSet() {
		return fichaTecnicaSet;
	}

	public void setFichaTecnicaSet(Set<IngredienteFichaTecnica> fichaTecnicaSet) {
		this.fichaTecnicaSet = fichaTecnicaSet;
	}
}
