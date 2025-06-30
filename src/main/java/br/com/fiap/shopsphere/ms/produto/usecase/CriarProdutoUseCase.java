package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoBodyRequestJson;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;
import br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils.convertToProdutoEntity;
import static java.time.LocalDateTime.now;

@Service
public class CriarProdutoUseCase {

    @Autowired
    private ProdutoGateway gateway;

    public ProdutoEntity criarProduto(ProdutoBodyRequestJson json) {
        gateway.buscarPorSku(json.sku()).ifPresent(produto -> {
            throw new RuntimeException("O SKU do produto já está em uso");
        });
        ProdutoEntity entity = convertToProdutoEntity(json);
        return gateway.criarProduto(entity);
    }


}
