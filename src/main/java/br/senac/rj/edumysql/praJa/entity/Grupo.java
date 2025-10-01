package br.senac.rj.edumysql.praJa.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name= "grupo")
public class Grupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="grup_id")
	private Integer id;
	@Column (name="grupo_nome", unique=true, nullable=false)
	private String nome;
	@Column(name = "grupo_cor")
	private String cor;
	@Column(name = "grupo_tipo")
	private Integer tipo;
	@Column(name = "grupo_status")
	private Integer status;

	@OneToMany(mappedBy = "grupo")
	private Set<Ingrediente> ingredienteSet;

	@OneToMany(mappedBy = "grupo")
	private Set<FichaTecnica> fichaTecnicaSet;

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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<Ingrediente> getIngredienteSet() {
		return ingredienteSet;
	}

	public void setIngredienteSet(Set<Ingrediente> ingredienteSet) {
		this.ingredienteSet = ingredienteSet;
	}

	public Set<FichaTecnica> getFichaTecnicaSet() {
		return fichaTecnicaSet;
	}

	public void setFichaTecnicaSet(Set<FichaTecnica> fichaTecnicaSet) {
		this.fichaTecnicaSet = fichaTecnicaSet;
	}
}
