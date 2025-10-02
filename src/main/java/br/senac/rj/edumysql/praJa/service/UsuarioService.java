package br.senac.rj.edumysql.praJa.service;

import br.senac.rj.edumysql.praJa.Enum.RoleName;
import br.senac.rj.edumysql.praJa.Enum.StatusEnum;
import br.senac.rj.edumysql.praJa.config.SecurityConfiguration;
import br.senac.rj.edumysql.praJa.entity.Role;
import br.senac.rj.edumysql.praJa.entity.Usuario;
import br.senac.rj.edumysql.praJa.entity.dto.request.usuario.UsuarioLoginDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.request.usuario.UsuarioDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.response.usuario.UsuarioLoginDTOResponse;
import br.senac.rj.edumysql.praJa.entity.dto.response.usuario.UsuarioDTOResponse;
import br.senac.rj.edumysql.praJa.repository.UsuarioRepository;
import br.senac.rj.edumysql.praJa.service.security.JwtTokenService;
import br.senac.rj.edumysql.praJa.service.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private SecurityConfiguration securityConfiguration;



  private final UsuarioRepository usuarioRepository;
  public UsuarioService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  private final Integer ativo = StatusEnum.ATIVO.getStatus(),
          inativo = StatusEnum.INATIVO.getStatus(),
          apagado = StatusEnum.APAGADO.getStatus();

  // Inicio dos métodos
  // Criacao
  public UsuarioDTOResponse criar(UsuarioDTORequest dtoRequest) {


    // Cria um novo usuário com os dados fornecidos
    Usuario usuario = new Usuario();
    usuario.setNome(dtoRequest.getNome());
    usuario.setTelefone(dtoRequest.getTelefone());
    usuario.setStatus(ativo);

    // Codifica a senha do usuário com o algoritmo bcrypt
    usuario.setSenha(securityConfiguration.passwordEncoder().encode(dtoRequest.getSenha()));

    // Atribui ao usuário um conjunto permissões específicas
    List<Role> roles = new ArrayList<>();
    List<String> dtoRoleList = dtoRequest.getRoleList();
    for (String role : dtoRoleList) {
      Role novaRole = new Role();
      novaRole.setName(RoleName.valueOf(role));
      roles.add(novaRole);
    }

    try {
      // Salva o novo usuário no banco de dados
      Usuario saveUsuario = usuarioRepository.save(usuario);
      UsuarioDTOResponse dtoResponse = new UsuarioDTOResponse();
      dtoResponse.setId(saveUsuario.getId());
      dtoResponse.setNome(saveUsuario.getNome());
      dtoResponse.setTelefone(saveUsuario.getTelefone());
      dtoResponse.setSenha(saveUsuario.getSenha());

      return dtoResponse;

    } catch (DataIntegrityViolationException ex) {
      throw new RuntimeException("Esse telefone ja consta no cadastro: " + dtoRequest.getTelefone(), ex);
    }
  }


  public UsuarioLoginDTOResponse login(UsuarioLoginDTORequest dtoRequest){
    // Cria um objeto de autenticação com o email e a senha do usuário
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(dtoRequest.getTelefone(), dtoRequest.getSenha());

    // Autentica o usuário com as credenciais fornecidas
    Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    // Obtém o objeto UserDetails do usuário autenticado
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    // Gera um token JWT para o usuário autenticado
    UsuarioLoginDTOResponse dtoLoginResponse = new UsuarioLoginDTOResponse();
    dtoLoginResponse.setId(userDetails.getIdUsuario());
    dtoLoginResponse.setNome(userDetails.getNomeUsuario());
    dtoLoginResponse.setToken(jwtTokenService.generateToken(userDetails));
    return dtoLoginResponse;

  }
}
