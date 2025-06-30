package br.com.fiap.shopsphere.ms.produto.gateway.database.jpa;

import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.repository.ProdutoRepository;
import br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.PageRequest.of;

@Component
@AllArgsConstructor
public class ProdutoJpaGateway implements ProdutoGateway {

    @Autowired
    private final ProdutoRepository repository;

    @Override
    public Optional<Produto> buscarPorSku(String sku) {
        return repository.findById(sku)
                .map(ProdutoUtils::convertToProduto);
    }

    @Override
    public List<Produto> buscarProdutos(int page, int size) {
        Page<ProdutoEntity> clientesEntity = repository.findAll(of(page, size));
        return clientesEntity.map(ProdutoUtils::convertToProduto).getContent();
    }

    @Override
    public ProdutoEntity criarProduto(ProdutoEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void excluirProduto(String sku) {
        if (!repository.existsById(sku)) {
            throw new RuntimeException("Produto não encontrado - SKU: " + sku);
        }
        repository.deleteById(sku);
    }

    @Override
    public ProdutoEntity alterarProduto(ProdutoEntity entity) {
        return repository.save(entity);
    }
}
