package br.com.fiap.shopsphere.ms.produto.gateway;

import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;

import java.util.List;
import java.util.Optional;

public interface ProdutoGateway  {

    Optional<Produto> buscarPorSku(String sku);

    List<Produto> buscarProdutos(int page, int size);

    ProdutoEntity criarProduto(ProdutoEntity entity);

    void excluirProduto(String sku);

    ProdutoEntity alterarProduto(ProdutoEntity sku);
}