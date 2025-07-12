package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarProdutoUseCase {

    @Autowired
    private ProdutoGateway gateway;

    public Produto buscarPorSku(String sku) {
        return gateway.buscarPorSku(sku).orElseThrow(() -> new RuntimeException("Produto não encontrado - SKU: " + sku));
    }

}
