package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoBodyRequestJson;
import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.exception.AtributoObrigatorioException;
import br.com.fiap.shopsphere.ms.produto.exception.ProdutoExistenteException;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarProdutoUseCaseTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private CriarProdutoUseCase criarProdutoUseCase;

    @Test
    void deveCriarProdutoComSucesso() {
        // Arrange
        ProdutoBodyRequestJson json = new ProdutoBodyRequestJson("SKU123", "Produto Teste", BigDecimal.valueOf(50.0));
        ProdutoEntity entityCriado = new ProdutoEntity();

        when(produtoGateway.buscarPorSku("SKU123")).thenReturn(Optional.empty());
        when(produtoGateway.criarProduto(any(ProdutoEntity.class))).thenReturn(entityCriado);

        // Act
        ProdutoEntity resultado = criarProdutoUseCase.criarProduto(json);

        // Assert
        assertNotNull(resultado);
        assertEquals(entityCriado, resultado);
        verify(produtoGateway).buscarPorSku("SKU123");
        verify(produtoGateway).criarProduto(any(ProdutoEntity.class));
    }

    @Test
    void deveLancarExcecaoQuandoProdutoJaExistir() {
        // Arrange
        ProdutoBodyRequestJson json = new ProdutoBodyRequestJson("SKU123", "Produto Existente", BigDecimal.valueOf(100.0));
        Produto produtoExistente = new Produto();

        when(produtoGateway.buscarPorSku("SKU123")).thenReturn(Optional.of(produtoExistente));

        // Act & Assert
        assertThrows(ProdutoExistenteException.class, () -> criarProdutoUseCase.criarProduto(json));

        verify(produtoGateway).buscarPorSku("SKU123");
        verify(produtoGateway, never()).criarProduto(any());
    }

    @Test
    void deveLancarExcecaoQuandoSkuForNulo() {
        // Arrange
        ProdutoBodyRequestJson json = new ProdutoBodyRequestJson(null, "Produto Teste", BigDecimal.valueOf(49.99));

        // Act & Assert
        var ex = assertThrows(AtributoObrigatorioException.class, () -> criarProdutoUseCase.criarProduto(json));
        assertEquals("O campo sku é de preenchimento obrigatório", ex.getMessage());

        verify(produtoGateway, never()).buscarPorSku(anyString());
        verify(produtoGateway, never()).criarProduto(any());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNulo() {
        // Arrange
        ProdutoBodyRequestJson json = new ProdutoBodyRequestJson("SKU001", null, BigDecimal.valueOf(49.99));

        // Act & Assert
        var ex = assertThrows(AtributoObrigatorioException.class, () -> criarProdutoUseCase.criarProduto(json));
        assertEquals("O campo nome é de preenchimento obrigatório", ex.getMessage());

        verify(produtoGateway, never()).buscarPorSku(anyString());
        verify(produtoGateway, never()).criarProduto(any());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoForNulo() {
        // Arrange
        ProdutoBodyRequestJson json = new ProdutoBodyRequestJson("SKU002", "Produto sem preço", null);

        // Act & Assert
        var ex = assertThrows(AtributoObrigatorioException.class, () -> criarProdutoUseCase.criarProduto(json));
        assertEquals("O campo preco é de preenchimento obrigatório", ex.getMessage());

        verify(produtoGateway, never()).buscarPorSku(anyString());
        verify(produtoGateway, never()).criarProduto(any());
    }
}
