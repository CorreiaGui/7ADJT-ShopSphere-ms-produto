package br.com.fiap.shopsphere.ms.produto.controller.json;

import java.math.BigDecimal;

public record ProdutoBodyRequestJson (
        String sku,
        String nome,
        BigDecimal preco
){ }