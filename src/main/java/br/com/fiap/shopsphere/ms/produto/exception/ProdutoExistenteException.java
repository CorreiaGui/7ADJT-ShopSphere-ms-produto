package br.com.fiap.shopsphere.ms.produto.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ProdutoExistenteException  extends SystemBaseException {

    @Serial
    private static final long serialVersionUID = 6198217850168561979L;

    private final String code = "produto.produtoJaExiste";

    private final String message = "Produto com SKU já cadastrado.";

    private final Integer httpStatus = 422;
}
