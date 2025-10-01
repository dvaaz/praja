package br.senac.rj.edumysql.praJa.repository;

import br.senac.rj.edumysql.praJa.entity.FichaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Integer> {
	@Modifying
	@Transactional
	@Query("UPDATE FichaTecnica f SET f.status = :status " +
			"WHERE f.id = :id")
	boolean updateStatus(@Param("id") Integer id, @Param("status") int status) ;

	@Query("SELECT f FROM FichaTecnica f WHERE f.status>=0")
	List<FichaTecnica> listar();

	@Query("SELECT f FROM FichaTecnica f WHERE f.id = :id AND f.status>=0")
	Optional<FichaTecnica> buscarPorID(@Param("id") Integer fichaTecnicaId);

	@Query("SELECT f FROM FichaTecnica f WHERE f.grupo.id = :grupoId")
	List<FichaTecnica> listarFichasTecnicasPorGrupo(@Param("grupoId") Integer grupo);
}
