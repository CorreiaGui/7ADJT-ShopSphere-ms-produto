package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.exception.ProdutoNotFound;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExcluirProdutoUseCaseTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private ExcluirProdutoUseCase excluirProdutoUseCase;

    @Test
    void deveExcluirProdutoQuandoSkuExistir() {
        String sku = "SKU123";
        doNothing().when(produtoGateway).excluirProduto(sku);

        excluirProdutoUseCase.excluirProduto(sku);

        verify(produtoGateway, times(1)).excluirProduto(sku);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        String sku = "SKU404";
        doThrow(new ProdutoNotFound()).when(produtoGateway).excluirProduto(sku);

        assertThrows(ProdutoNotFound.class, () -> excluirProdutoUseCase.excluirProduto(sku));
        verify(produtoGateway, times(1)).excluirProduto(sku);
    }
}
