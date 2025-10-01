package br.senac.rj.edumysql.praJa.config;

import br.senac.rj.edumysql.praJa.exception.GrupoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe de configuracao para tratar a Exceptions personalizados
 * no RestController
 */
@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(GrupoNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleGrupoNaoEncontrado(GrupoNotFoundException ex){
		return new ApiError("Grupo_nao_encontrado", ex.getMessage());
	}
	public record ApiError(String code, String message) {}

}
