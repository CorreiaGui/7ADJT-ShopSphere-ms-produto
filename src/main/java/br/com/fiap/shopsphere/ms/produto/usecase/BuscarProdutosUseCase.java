package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarProdutosUseCase {

    @Autowired
    private ProdutoGateway gateway;

    public List<Produto> buscarProdutos(int page, int size) {
        return gateway.buscarProdutos(page, size);
    }
}
