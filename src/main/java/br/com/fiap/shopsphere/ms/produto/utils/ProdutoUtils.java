package br.com.fiap.shopsphere.ms.produto.utils;

import br.com.fiap.shopsphere.ms.produto.controller.json.AtualizarProdutoJson;
import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoBodyRequestJson;
import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoJson;
import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.exception.AtributoObrigatorioException;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;

import static java.time.LocalDateTime.now;

public final class ProdutoUtils {

    private ProdutoUtils () {}

    public static Produto convertToProduto(ProdutoEntity e) {
        return Produto.builder()
                .sku(e.getSku())
                .preco(e.getPreco())
                .nome(e.getNome())
                .dataCriacao(e.getDataCriacao())
                .dataUltimaAlteracao(e.getDataUltimaAlteracao())
                .build();
    }

    public static ProdutoJson convertToProdutoJson(Produto produto) {
        return new ProdutoJson(
                produto.getSku(),
                produto.getNome(),
                produto.getPreco(),
                produto.getDataCriacao(),
                produto.getDataUltimaAlteracao()
        );
    }

    public static ProdutoEntity convertToProdutoEntity(ProdutoBodyRequestJson json) {
        return ProdutoEntity.builder()
                .sku(json.sku())
                .preco(json.preco())
                .nome(json.nome())
                .dataCriacao(now())
                .build();
    }

    public static ProdutoEntity convertToProdutoEntity(ProdutoBodyRequestJson json, Produto existente) {
        return ProdutoEntity.builder()
                .sku(json.sku())
                .nome(json.nome())
                .preco(json.preco())
                .dataCriacao(existente.getDataCriacao())
                .dataUltimaAlteracao(now())
                .build();
    }

    public static ProdutoEntity convertToProdutoEntity(AtualizarProdutoJson json, Produto existente) {
        return ProdutoEntity.builder()
                .sku(existente.getSku())
                .nome(json.nome())
                .preco(json.preco())
                .dataCriacao(existente.getDataCriacao())
                .dataUltimaAlteracao(now())
                .build();
    }

    public static void checkAtributo(Object obj, String errorMessage) {
        if(obj == null){
            throw new AtributoObrigatorioException(errorMessage);
        }
    }
}
