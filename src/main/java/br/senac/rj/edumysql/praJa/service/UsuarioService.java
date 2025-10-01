package br.senac.rj.edumysql.praJa.service;

import br.senac.rj.edumysql.praJa.Enum.RoleName;
import br.senac.rj.edumysql.praJa.Enum.Status;
import br.senac.rj.edumysql.praJa.config.SecurityConfiguration;
import br.senac.rj.edumysql.praJa.entity.Role;
import br.senac.rj.edumysql.praJa.entity.Usuario;
import br.senac.rj.edumysql.praJa.entity.dto.request.usuario.UsuarioDTOLoginRequest;
import br.senac.rj.edumysql.praJa.entity.dto.request.usuario.UsuarioDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.response.usuario.UsuarioDTOLoginResponse;
import br.senac.rj.edumysql.praJa.entity.dto.response.usuario.UsuarioDTOResponse;
import br.senac.rj.edumysql.praJa.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Integer ativo = Status.ATIVO.getStatus(),
        inativo = Status.INATIVO.getStatus(),
        apagado = Status.APAGADO.getStatus();


  private final UsuarioRepository usuarioRepository;

  public UsuarioService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  public UsuarioDTOResponse criar(UsuarioDTORequest dtoRequest) {


    // Cria um novo usuário com os dados fornecidos
    Usuario novoUsuario = new Usuario();
    novoUsuario.setNome(dtoRequest.getNome());
    novoUsuario.setTelefone(dtoRequest.getTelefone());
    novoUsuario.setStatus(ativo);

    // Codifica a senha do usuário com o algoritmo bcrypt
    novoUsuario.setSenha(securityConfiguration.passwordEncoder().encode(dtoRequest.getSenha()));

    // Atribui ao usuário um conjunto permissões específicas
    List<Role> roles = new ArrayList<>();
    List<String> dtoRoleList = dtoRequest.getRoleList();
    for (String role : dtoRoleList) {
      Role novaRole = new Role();
      novaRole.setName(RoleName.valueOf(role));
      roles.add(novaRole);
    }


    // Salva o novo usuário no banco de dados
    usuarioRepository.save(novoUsuario);

    UsuarioDTOResponse dtoResponse = new UsuarioDTOResponse();
    dtoResponse.setId(novoUsuario.getId());
    dtoResponse.setNome(novoUsuario.getNome());
    dtoResponse.setTelefone(novoUsuario.getTelefone());
    dtoResponse.setSenha(novoUsuario.getSenha());

    return dtoResponse;
  }


  public UsuarioDTOLoginResponse login(UsuarioDTOLoginRequest dtoRequest){
    // Cria um objeto de autenticação com o email e a senha do usuário
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(dtoRequest.getTelefone(), dtoRequest.getSenha());

    // Autentica o usuário com as credenciais fornecidas
    Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    // Obtém o objeto UserDetails do usuário autenticado
    UsuarioDetailsImpl userDetails = (UsuarioDetailsImpl) authentication.getPrincipal();

    // Gera um token JWT para o usuário autenticado
    UsuarioDTOLoginResponse dtoLoginResponse = new UsuarioDTOLoginResponse();
    dtoLoginResponse.setId(userDetails.getIdUsuario());
    dtoLoginResponse.setNome(userDetails.getNomeUsuario());
    dtoLoginResponse.setToken(jwtTokenService.generateToken(userDetails));
    return dtoLoginResponse;

  }
}
