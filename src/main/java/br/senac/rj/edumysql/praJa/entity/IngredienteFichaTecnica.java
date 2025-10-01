package br.senac.rj.edumysql.praJa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="ingrediente_ficha_tecnica")
public class IngredienteFichaTecnica {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ingrediente_ficha_tecnica_id")
	private Integer id;
	@Column(name= "ingrediente_ficha_tecnica_qtd")
	private Integer qtd;
	@Column(name="ingrediente_ficha_tecnica_unidade_medida")
	private Integer unidadeMedida;
	//  @Column(name="ingrediente_ficha_tecnica_observacao")
	//  private String observacao;
	@Column(name="ingrediente_ficha_tecnica_status")
	private Integer status;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ingrediente_id", nullable = false)
	private Ingrediente ingrediente;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ficha_tecnica_id", nullable = false)
	private FichaTecnica fichaTecnica;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public Integer getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(Integer unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public FichaTecnica getFichaTecnica() {
		return fichaTecnica;
	}

	public void setFichaTecnica(FichaTecnica fichaTecnica) {
		this.fichaTecnica = fichaTecnica;
	}
}
