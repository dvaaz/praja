package br.senac.rj.edumysql.praJa.repository;

import br.senac.rj.edumysql.praJa.entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
	@Modifying
	@Transactional
	@Query("UPDATE Ingrediente i SET i.status = :status " +
			"WHERE i.id = :id")
	void updateStatus(@Param("id") Integer ingredieneId, @Param("status") int status);

	@Query("SELECT i FROM Ingrediente i WHERE i.status>=0")
	List<Ingrediente> listarIngredientes();

	@Query("SELECT i FROM Ingrediente i WHERE i.id = :id AND i.status>=0")
	Optional<Ingrediente> buscarIngredientePorId(@Param("id") Integer ingredienteId);

	@Query("SELECT i FROM Ingrediente i WHERE i.grupo.id = :grupoId")
	List<Ingrediente> listarIngredientesPorGrupo(@Param("grupoId") Integer grupo);
}