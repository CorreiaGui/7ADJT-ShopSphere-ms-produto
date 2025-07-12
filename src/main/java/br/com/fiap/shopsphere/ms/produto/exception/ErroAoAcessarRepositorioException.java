package br.com.fiap.shopsphere.ms.produto.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ErroAoAcessarRepositorioException extends SystemBaseException {

    @Serial
    private static final long serialVersionUID = 1184512342688657535L;

    private final String code = "usuario.erroAcessarRepositorio";
    private final String message = "Erro ao acessar repositorio de dados.";
    private final Integer httpStatus = 500;
}