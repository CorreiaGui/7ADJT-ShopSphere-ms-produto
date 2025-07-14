package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.exception.ProdutoNotFound;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarProdutoUseCaseTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private BuscarProdutoUseCase buscarProdutoUseCase;

    @Test
    void deveRetornarProdutoQuandoSkuExistir() {
        // Arrange
        String sku = "SKU123";
        Produto produtoEsperado = new Produto();
        when(produtoGateway.buscarPorSku(sku)).thenReturn(Optional.of(produtoEsperado));

        // Act
        Produto produto = buscarProdutoUseCase.buscarPorSku(sku);

        // Assert
        assertEquals(produtoEsperado, produto);
        verify(produtoGateway, times(1)).buscarPorSku(sku);
    }

    @Test
    void deveLancarExcecaoQuandoSkuNaoExistir() {
        // Arrange
        String sku = "SKU404";
        when(produtoGateway.buscarPorSku(sku)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProdutoNotFound.class, () -> buscarProdutoUseCase.buscarPorSku(sku));

        verify(produtoGateway, times(1)).buscarPorSku(sku);
    }
}
