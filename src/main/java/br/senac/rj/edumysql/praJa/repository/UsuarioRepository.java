package br.senac.rj.edumysql.praJa.repository;

import br.senac.rj.edumysql.praJa.entity.Grupo;
import br.senac.rj.edumysql.praJa.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u " +
            "WHERE u.status>=0 AND u.telefone=:telefone")
    Optional<Grupo> buscarPorTelefone(@Param("telefone") String telefone);

    Optional<Usuario> findByTelefone(String login);

  }