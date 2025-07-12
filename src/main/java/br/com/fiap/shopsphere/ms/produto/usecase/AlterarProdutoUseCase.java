package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.controller.json.AtualizarProdutoJson;
import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoBodyRequestJson;
import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.exception.ProdutoNotFound;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;
import br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoConstants.*;
import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils.*;
import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils.convertToProduto;

@Service
public class AlterarProdutoUseCase {

    @Autowired
    private ProdutoGateway gateway;

    public Produto alterarProduto(AtualizarProdutoJson json, String sku) {
        checkAtributo(json.nome(), ERROR_MESSAGE_NOME_PRODUTO_OBRIGATORIO);
        checkAtributo(json.preco(), ERROR_MESSAGE_VALOR_PRODUTO_OBRIGATORIO);
        Produto existente = gateway.buscarPorSku(sku)
                .orElseThrow(ProdutoNotFound::new);
        var atualizado = gateway.alterarProduto(convertToProdutoEntity(json, existente));
        return convertToProduto(atualizado);
    }

}
