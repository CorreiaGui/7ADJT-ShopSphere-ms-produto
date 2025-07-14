package br.com.fiap.shopsphere.ms.produto.utils;

import br.com.fiap.shopsphere.ms.produto.controller.json.AtualizarProdutoJson;
import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoBodyRequestJson;
import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoJson;
import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.exception.AtributoObrigatorioException;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoUtilsTest {

    @Test
    void testConvertToProduto() {
        ProdutoEntity entity = ProdutoEntity.builder()
                .sku("SKU123")
                .nome("Produto Teste")
                .preco(new BigDecimal("99.99"))
                .dataCriacao(LocalDateTime.of(2025, 1, 1, 1, 1))
                .dataUltimaAlteracao(LocalDateTime.of(2025, 2, 2, 2, 2))
                .build();

        Produto produto = ProdutoUtils.convertToProduto(entity);

        assertNotNull(produto);
        assertEquals("SKU123", produto.getSku());
        assertEquals("Produto Teste", produto.getNome());
        assertEquals(new BigDecimal("99.99"), produto.getPreco());
    }

    @Test
    void testConvertToProdutoJson() {
        Produto produto = Produto.builder()
                .sku("SKU456")
                .nome("Produto JSON")
                .preco(new BigDecimal("149.90"))
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        ProdutoJson json = ProdutoUtils.convertToProdutoJson(produto);

        assertNotNull(json);
        assertEquals("SKU456", json.sku());
        assertEquals("Produto JSON", json.nome());
        assertEquals(new BigDecimal("149.90"), json.preco());
    }

    @Test
    void testConvertToProdutoEntityFromBodyRequestJson() {
        ProdutoBodyRequestJson json = new ProdutoBodyRequestJson("SKU789", "Produto Novo", new BigDecimal("55.00"));

        ProdutoEntity entity = ProdutoUtils.convertToProdutoEntity(json);

        assertNotNull(entity);
        assertEquals("SKU789", entity.getSku());
        assertEquals("Produto Novo", entity.getNome());
        assertEquals(new BigDecimal("55.00"), entity.getPreco());
        assertNotNull(entity.getDataCriacao());
    }

    @Test
    void testConvertToProdutoEntityWithProdutoExistente() {
        ProdutoBodyRequestJson json = new ProdutoBodyRequestJson("SKU789", "Produto Atualizado", new BigDecimal("88.88"));

        Produto existente = Produto.builder()
                .sku("SKU789")
                .nome("Antigo")
                .preco(new BigDecimal("77.77"))
                .dataCriacao(LocalDateTime.of(2024, 1, 1, 10, 0))
                .build();

        ProdutoEntity entity = ProdutoUtils.convertToProdutoEntity(json, existente);

        assertNotNull(entity);
        assertEquals("SKU789", entity.getSku());
        assertEquals("Produto Atualizado", entity.getNome());
        assertEquals(new BigDecimal("88.88"), entity.getPreco());
        assertEquals(existente.getDataCriacao(), entity.getDataCriacao());
        assertNotNull(entity.getDataUltimaAlteracao());
    }

    @Test
    void testConvertToProdutoEntityFromAtualizarJson() {
        AtualizarProdutoJson json = new AtualizarProdutoJson("Produto Atualizado", new BigDecimal("99.99"));

        Produto existente = Produto.builder()
                .sku("SKU000")
                .nome("Antigo")
                .preco(new BigDecimal("10.00"))
                .dataCriacao(LocalDateTime.of(2023, 12, 25, 0, 0))
                .build();

        ProdutoEntity entity = ProdutoUtils.convertToProdutoEntity(json, existente);

        assertEquals("SKU000", entity.getSku());
        assertEquals("Produto Atualizado", entity.getNome());
        assertEquals(new BigDecimal("99.99"), entity.getPreco());
        assertEquals(existente.getDataCriacao(), entity.getDataCriacao());
        assertNotNull(entity.getDataUltimaAlteracao());
    }

    @Test
    void testCheckAtributoValido() {
        assertDoesNotThrow(() -> ProdutoUtils.checkAtributo("Algo", "Campo obrigatório"));
    }

    @Test
    void testCheckAtributoInvalido() {
        Exception ex = assertThrows(AtributoObrigatorioException.class,
                () -> ProdutoUtils.checkAtributo(null, "Campo obrigatório"));
        assertEquals("Campo obrigatório", ex.getMessage());
    }
}
