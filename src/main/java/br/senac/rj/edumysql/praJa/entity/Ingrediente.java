package br.senac.rj.edumysql.praJa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

	@Entity
	@Table(name = "ingrediente")
	public class Ingrediente {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="ingrediente_id")
		private Integer id;
		@Column(name="ingrediente_nome", nullable = false)
		private String nome;
		@Column(name ="ingrediente_descricao")
		private String descricao;
		@Column(name="ingrediente_status")
		private Integer status;
		@JsonIgnore
		@ManyToOne
		@JoinColumn(name = "grupo_id", nullable = false)
		private Grupo grupo;

		@OneToMany(mappedBy = "ingrediente")
		private Set<Estoque> estoqueSet;
		@OneToMany(mappedBy = "ingrediente")
		private Set<IngredienteFichaTecnica> ingredienteFichaTecnicaSet;
		@OneToMany(mappedBy = "ingrediente")
		private Set<UtilizadoDia> utilizadoDiaSet;

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

		public Set<Estoque> getEstoqueSet() {
			return estoqueSet;
		}

		public void setEstoqueSet(Set<Estoque> estoqueSet) {
			this.estoqueSet = estoqueSet;
		}

		public Set<IngredienteFichaTecnica> getIngredienteFichaTecnicaSet() {
			return ingredienteFichaTecnicaSet;
		}

		public void setIngredienteFichaTecnicaSet(Set<IngredienteFichaTecnica> ingredienteFichaTecnicaSet) {
			this.ingredienteFichaTecnicaSet = ingredienteFichaTecnicaSet;
		}

		public Set<UtilizadoDia> getUtilizadoDiaSet() {
			return utilizadoDiaSet;
		}

		public void setUtilizadoDiaSet(Set<UtilizadoDia> utilizadoDiaSet) {
			this.utilizadoDiaSet = utilizadoDiaSet;
		}
	}
