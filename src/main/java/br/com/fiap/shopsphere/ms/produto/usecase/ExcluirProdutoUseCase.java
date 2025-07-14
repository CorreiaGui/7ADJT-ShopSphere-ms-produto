package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcluirProdutoUseCase {

    @Autowired
    private ProdutoGateway gateway;

    public void excluirProduto(String sku) {
        gateway.excluirProduto(sku);
    }
}
