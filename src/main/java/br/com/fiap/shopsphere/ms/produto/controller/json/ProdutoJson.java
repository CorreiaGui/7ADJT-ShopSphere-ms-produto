package br.com.fiap.shopsphere.ms.produto.controller.json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProdutoJson(
         String sku,
         String nome,
         BigDecimal preco,
         LocalDateTime dataCriacao,
         LocalDateTime dataUltimaAlteracao
) {}
