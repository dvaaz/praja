package br.senac.rj.edumysql.praJa.controller;

import br.senac.rj.edumysql.praJa.entity.dto.request.usuario.UsuarioDTOLoginRequest;
import br.senac.rj.edumysql.praJa.entity.dto.request.usuario.UsuarioDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.response.usuario.UsuarioDTOLoginResponse;
import br.senac.rj.edumysql.praJa.entity.dto.response.usuario.UsuarioDTOResponse;
import br.senac.rj.edumysql.praJa.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
  private final UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @GetMapping("/login")
  public ResponseEntity<UsuarioDTOLoginResponse> login(@RequestBody UsuarioDTOLoginRequest dtoLoginRequest) {
    return ResponseEntity.ok(usuarioService.login(dtoLoginRequest));
  }

  @PostMapping("/criar")
  public ResponseEntity<UsuarioDTOResponse> criar(
      @RequestBody UsuarioDTORequest dtoRequest){
   return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criar(dtoRequest));
  }
}