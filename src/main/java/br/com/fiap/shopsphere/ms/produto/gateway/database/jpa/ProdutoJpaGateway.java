package br.com.fiap.shopsphere.ms.produto.gateway.database.jpa;

import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.repository.ProdutoRepository;
import br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class ProdutoJpaGateway implements ProdutoGateway {

    @Autowired
    private final ProdutoRepository repository;

    @Override
    public Optional<Produto> buscarPorSku(String sku) {
        var produtoEntity = repository.findBySku(sku).orElseThrow(() -> new RuntimeException("Produto não encontrado - SKU: " + sku));
        return ofNullable(ProdutoUtils.convertToProduto(produtoEntity));
    }

    @Override
    public Optional<Produto> buscarPorId(UUID id) {
        var produtoEntity = repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado - id: " + id));
        return ofNullable(ProdutoUtils.convertToProduto(produtoEntity));
    }

    @Override
    public List<Produto> buscarTodosProdutos() {
        return repository.findAll().stream()
                .map(ProdutoUtils::convertToProduto)
                .collect(toList());
    }

}
