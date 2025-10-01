package br.senac.rj.edumysql.praJa.repository;

import br.senac.rj.edumysql.praJa.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
	@Modifying
	@Query("UPDATE Grupo g SET g.status = :status " +
			"WHERE g.id = :id")
	void updateStatus(@Param("id") Integer id, @Param("status") Integer status);

	@Query("SELECT g FROM Grupo g WHERE g.status >=0")
	List<Grupo> listar();

	@Query("SELECT g FROM Grupo g WHERE g.tipo=:tipo AND g.status>=0 ")
	List<Grupo> listarPorTipo(@Param("tipo") Integer tipo);

	@Query("SELECT g FROM Grupo g " +
			"WHERE g.status>=0 AND g.tipo=:tipo AND g.nome =:nome")
	Optional<Grupo> buscarGrupoPadrao(@Param("tipo") Integer tipo, @Param("nome") String nome);

	@Query("SELECT g FROM Grupo g WHERE g.id =:id AND g.status>=0")
	Optional<Grupo> buscarPorId(@Param("id") Integer id);

}
