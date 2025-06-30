package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoBodyRequestJson;
import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils.*;

@Service
public class AlterarProdutoUseCase {

    @Autowired
    private ProdutoGateway gateway;

    public void alterarProduto(ProdutoBodyRequestJson json) {
        Produto existente = gateway.buscarPorSku(json.sku())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado - SKU: " + json.sku()));
        ProdutoEntity atualizado = convertToProdutoEntity(json, existente);
        gateway.alterarProduto(atualizado);
    }

}
