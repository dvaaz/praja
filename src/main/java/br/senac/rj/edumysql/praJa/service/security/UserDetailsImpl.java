package br.senac.rj.edumysql.praJa.service.security;

import br.senac.rj.edumysql.praJa.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
  private Usuario usuario;

  public UserDetailsImpl(Usuario usuario){
    this.usuario = usuario;
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return usuario.getRoles()
        .stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());
  }
  @Override
  public String getPassword() {
    return usuario.getSenha();
  }
  @Override
  public String getUsername() {
    return usuario.getTelefone();
  }

  public Integer getIdUsuario() {
    return usuario.getId();
  }

  public String getNomeUsuario() {
    return usuario.getNome();
  }

}