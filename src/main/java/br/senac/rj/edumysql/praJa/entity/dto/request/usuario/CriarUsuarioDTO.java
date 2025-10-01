package br.senac.rj.edumysql.praJa.entity.dto.request.usuario;

import br.senac.rj.edumysql.praJa.Enum.RoleName;

public record CriarUsuarioDTO(
    String login,
    String senha,
    RoleName role
) {
}