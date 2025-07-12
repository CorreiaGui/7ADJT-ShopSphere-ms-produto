package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoBodyRequestJson;
import br.com.fiap.shopsphere.ms.produto.exception.ProdutoExistenteException;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoConstants.*;
import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils.checkAtributo;
import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils.convertToProdutoEntity;
import static java.time.LocalDateTime.now;

@Service
public class CriarProdutoUseCase {

    @Autowired
    private ProdutoGateway gateway;

    public ProdutoEntity criarProduto(ProdutoBodyRequestJson json) {
        checkAtributo(json.sku(), ERROR_MESSAGE_SKU_PRODUTO_OBRIGATORIO);
        checkAtributo(json.nome(), ERROR_MESSAGE_NOME_PRODUTO_OBRIGATORIO);
        checkAtributo(json.preco(), ERROR_MESSAGE_VALOR_PRODUTO_OBRIGATORIO);
        checarSeProdutoExiste(json);
        ProdutoEntity entity = convertToProdutoEntity(json);
        return gateway.criarProduto(entity);
    }

    private void checarSeProdutoExiste(ProdutoBodyRequestJson json) {
        gateway.buscarPorSku(json.sku()).ifPresent(produto -> {
            throw new ProdutoExistenteException();
        });
    }
}
