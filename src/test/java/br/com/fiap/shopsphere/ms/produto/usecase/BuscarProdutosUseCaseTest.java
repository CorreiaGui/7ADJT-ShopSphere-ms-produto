package br.com.fiap.shopsphere.ms.produto.usecase;

import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.gateway.ProdutoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarProdutosUseCaseTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private BuscarProdutosUseCase buscarProdutosUseCase;

    @Test
    void deveRetornarListaDeProdutos() {
        List<Produto> produtosEsperados = List.of(new Produto(), new Produto());
        when(produtoGateway.buscarProdutos(0, 2)).thenReturn(produtosEsperados);

        List<Produto> produtos = buscarProdutosUseCase.buscarProdutos(0, 2);

        assertEquals(produtosEsperados, produtos);
        verify(produtoGateway, times(1)).buscarProdutos(0, 2);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremProdutos() {
        when(produtoGateway.buscarProdutos(0, 2)).thenReturn(Collections.emptyList());

        List<Produto> produtos = buscarProdutosUseCase.buscarProdutos(0, 2);

        assertEquals(Collections.emptyList(), produtos);
        verify(produtoGateway, times(1)).buscarProdutos(0, 2);
    }
}
