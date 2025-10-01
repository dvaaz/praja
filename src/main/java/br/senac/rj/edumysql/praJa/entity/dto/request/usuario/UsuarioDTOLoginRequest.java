package br.senac.rj.edumysql.praJa.entity.dto.request.usuario;

public class UsuarioDTOLoginRequest {
  private String telefone;
  private String senha;

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }
}