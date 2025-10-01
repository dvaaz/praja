package br.senac.rj.edumysql.praJa.repository;

import br.senac.rj.edumysql.praJa.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByTelefone(String login);

  }