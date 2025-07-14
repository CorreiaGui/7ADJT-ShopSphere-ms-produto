package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.controller.json.AtualizarProdutoJson;
import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.exception.AtributoObrigatorioException;
import br.com.fiap.shopsphere.ms.produto.exception.ProdutoNotFound;
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
class AlterarProdutoUseCaseTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private AlterarProdutoUseCase alterarProdutoUseCase;

    @Test
    void deveAlterarProdutoQuandoSkuExistir() {

        String sku = "ABC123";
        AtualizarProdutoJson json = new AtualizarProdutoJson("Produto Teste", BigDecimal.valueOf(99.99));
        ProdutoEntity atualizadoEntity = new ProdutoEntity();
        Produto produtoExistente = new Produto();

        when(produtoGateway.buscarPorSku(sku))
                .thenReturn(Optional.of(produtoExistente));

        when(produtoGateway.alterarProduto(any())).thenReturn(atualizadoEntity);

        Produto resultado = alterarProdutoUseCase.alterarProduto(json, sku);

        assertNotNull(resultado);
        verify(produtoGateway).buscarPorSku(sku);
        verify(produtoGateway).alterarProduto(any());
    }

    @Test
    void deveLancarExcecaoQuandoSkuNaoExistir() {
        String sku = "SKU_INEXISTENTE";
        AtualizarProdutoJson json = new AtualizarProdutoJson("Produto Teste", BigDecimal.valueOf(49.99));

        when(produtoGateway.buscarPorSku(sku)).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFound.class, () -> alterarProdutoUseCase.alterarProduto(json, sku));

        verify(produtoGateway).buscarPorSku(sku);
        verify(produtoGateway, never()).alterarProduto(any());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNulo() {
        String sku = "SKU001";
        AtualizarProdutoJson json = new AtualizarProdutoJson(null, BigDecimal.valueOf(99.99));

        var ex = assertThrows(AtributoObrigatorioException.class,
                () -> alterarProdutoUseCase.alterarProduto(json, sku));

        assertEquals("O campo nome é de preenchimento obrigatório", ex.getMessage());

        verify(produtoGateway, never()).buscarPorSku(anyString());
        verify(produtoGateway, never()).alterarProduto(any());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoForNulo() {
        String sku = "SKU002";
        AtualizarProdutoJson json = new AtualizarProdutoJson("Produto sem preço", null);

        var ex = assertThrows(AtributoObrigatorioException.class,
                () -> alterarProdutoUseCase.alterarProduto(json, sku));

        assertEquals("O campo preco é de preenchimento obrigatório", ex.getMessage());

        verify(produtoGateway, never()).buscarPorSku(anyString());
        verify(produtoGateway, never()).alterarProduto(any());
    }

}
