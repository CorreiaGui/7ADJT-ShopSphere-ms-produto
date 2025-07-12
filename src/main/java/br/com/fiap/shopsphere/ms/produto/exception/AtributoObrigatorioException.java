package br.com.fiap.shopsphere.ms.produto.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class AtributoObrigatorioException extends SystemBaseException {


    @Serial
    private static final long serialVersionUID = 6198217850168561979L;

    private final String code = "produto.atributoObrigatorio";

    private final String message;

    private final Integer httpStatus = 400;

    public AtributoObrigatorioException(String message) {
        this.message = message;
    }
}
