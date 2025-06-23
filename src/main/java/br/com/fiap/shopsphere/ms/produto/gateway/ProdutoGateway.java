package br.com.fiap.shopsphere.ms.produto.gateway;

import br.com.fiap.shopsphere.ms.produto.domain.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoGateway  {

    Optional<Produto> buscarPorSku(String sku);

    Optional<Produto> buscarPorId(UUID ID);

    List<Produto> buscarTodosProdutos();
}