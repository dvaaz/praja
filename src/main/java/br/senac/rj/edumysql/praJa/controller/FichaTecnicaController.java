package br.senac.rj.edumysql.praJa.controller;

import br.senac.rj.edumysql.praJa.entity.dto.request.fichaTecnica.FichaTecnicaDTORequest;
import br.senac.rj.edumysql.praJa.entity.dto.response.fichaTecnica.FichaTecnicaDTOResponse;
import br.senac.rj.edumysql.praJa.entity.dto.response.fichaTecnica.ListaFichasDeGrupoDTO;
import br.senac.rj.edumysql.praJa.service.FichaTecnicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ficha")
@Tag(name="Ficha Tecnica", description = "Api para gerenciamento de Fciha Tecnica")
public class FichaTecnicaController {
    private final FichaTecnicaService service;
    public FichaTecnicaController(FichaTecnicaService service) {
        this.service = service;
    }

    @PostMapping("/criar")
    @Operation(summary ="Criar nova Ficha Tecnica", description = "Endpoint para o registro de nova ficha tecnica")
    public ResponseEntity<FichaTecnicaDTOResponse> criar(
        @Valid @RequestBody FichaTecnicaDTORequest dtoRequest) {
        FichaTecnicaDTOResponse dtoResponse = this.service.criar(dtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    @GetMapping("/listar")
    @Operation(summary ="Listar Ficha Tecnica", description = "Endpoint para listar fichas tecnicas")
    public ResponseEntity<List<FichaTecnicaDTOResponse>> listar() {
        List<FichaTecnicaDTOResponse> dtoResponse = this.service.listar();
        return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
    }

    @GetMapping("/listar/{id}grupo/")
    @Operation(summary ="Listar Ficha Tecnica em um grupo", description = "Endpoint para listar fichas tecnicas de um grupo")
    public ResponseEntity<ListaFichasDeGrupoDTO> listarFichasPorGrupo(
        @PathVariable Integer id
    ){
        ListaFichasDeGrupoDTO dtoResponse = this.service.listarFichasDeGrupo(id);
        return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary ="Bustar Ficha Tecnica", description = "Endpoint para buscar ficha tecnica")
    public ResponseEntity<FichaTecnicaDTOResponse> buscarPorId(
        @PathVariable Integer id
    ){
        FichaTecnicaDTOResponse dtoResponse = this.service.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
    }

}
