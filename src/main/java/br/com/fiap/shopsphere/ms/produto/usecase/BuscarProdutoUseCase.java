package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BuscarProdutoUseCase {

    @Autowired
    private ProdutoGateway gateway;

    public Produto buscarPorSku(String sku) {
        return gateway.buscarPorSku(sku).orElseThrow(() -> new RuntimeException("Produto não encontrado - SKU: " + sku));
    }

    public Produto buscarPorId(UUID id) {
        return gateway.buscarPorId(id).orElseThrow(() -> new RuntimeException("Produto não encontrado - id: " + id));
    }

    public List<Produto> buscarTodosProdutos() {
        return gateway.buscarTodosProdutos();
    }
}
