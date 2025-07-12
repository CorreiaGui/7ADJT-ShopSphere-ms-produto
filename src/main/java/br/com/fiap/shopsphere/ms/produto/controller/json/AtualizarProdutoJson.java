package br.com.fiap.shopsphere.ms.produto.controller.json;


import java.math.BigDecimal;

public record AtualizarProdutoJson (
        String nome,
        BigDecimal preco
){ }
