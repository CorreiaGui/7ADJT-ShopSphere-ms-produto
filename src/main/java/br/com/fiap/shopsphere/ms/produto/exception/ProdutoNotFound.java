package br.com.fiap.shopsphere.ms.produto.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ProdutoNotFound extends SystemBaseException {

    @Serial
    private static final long serialVersionUID = 6198217850168561979L;

    private final String code = "produto.produtoNaoEncontrado";

    private final String message = "Produto não encontrado.";

    private final Integer httpStatus = 404;
}
