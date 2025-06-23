package br.com.fiap.shopsphere.ms.produto.utils;

import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoJson;
import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;

public abstract class ProdutoUtils {

    private ProdutoUtils () {}

    public static Produto convertToProduto(ProdutoEntity e) {
        return Produto.builder()
                .id(e.getId())
                .sku(e.getSku())
                .preco(e.getPreco())
                .nome(e.getNome())
                .dataCriacao(e.getDataCriacao())
                .dataUltimaAlteracao(e.getDataUltimaAlteracao())
                .build();
    }

    public static ProdutoJson convertToProdutoJson(Produto produto) {
        return new ProdutoJson(
                produto.getId(),
                produto.getSku(),
                produto.getNome(),
                produto.getPreco(),
                produto.getDataCriacao(),
                produto.getDataUltimaAlteracao()
        );
    }
}
