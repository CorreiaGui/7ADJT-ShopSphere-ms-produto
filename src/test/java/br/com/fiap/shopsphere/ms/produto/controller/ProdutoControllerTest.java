package br.com.fiap.shopsphere.ms.produto.controller;

import br.com.fiap.shopsphere.ms.produto.controller.json.AtualizarProdutoJson;
import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoBodyRequestJson;
import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoJson;
import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;
import br.com.fiap.shopsphere.ms.produto.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController controller;

    @Mock
    private BuscarProdutoUseCase buscar;

    @Mock
    private BuscarProdutosUseCase buscarProdutos;

    @Mock
    private CriarProdutoUseCase criar;

    @Mock
    private ExcluirProdutoUseCase excluir;

    @Mock
    private AlterarProdutoUseCase alterar;

    private Produto produtoMock;

    private ProdutoJson produtoJsonMock;

    private ProdutoBodyRequestJson produtoBodyRequestJsonMock;

    @BeforeEach
    void setup() {
        produtoMock = Produto.builder()
                .sku("SKU123")
                .nome("Produto Teste")
                .preco(new BigDecimal("99.90"))
                .dataCriacao(LocalDateTime.of(2025, 1, 1, 1, 1))
                .dataUltimaAlteracao(LocalDateTime.of(2025, 1, 1, 1, 1))
                .build();

        produtoJsonMock = new ProdutoJson(
                "SKU123",
                "Produto Teste",
                new BigDecimal("99.90"),
                produtoMock.getDataCriacao(),
                produtoMock.getDataUltimaAlteracao()
        );

        produtoBodyRequestJsonMock = new ProdutoBodyRequestJson(
                "SKU123",
                "Produto Teste",
                new BigDecimal("99.90")
        );
    }

    @Test
    void deveBuscarProdutoPorSku() {
        when(buscar.buscarPorSku("SKU123")).thenReturn(produtoMock);

        ResponseEntity<ProdutoJson> response = controller.buscarProduto("SKU123");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(produtoMock.getSku(), response.getBody().sku());
        verify(buscar, times(1)).buscarPorSku("SKU123");
    }

    @Test
    void deveBuscarProdutosComPaginacao() {
        when(buscarProdutos.buscarProdutos(0, 10)).thenReturn(List.of(produtoMock));

        ResponseEntity<List<ProdutoJson>> response = controller.buscarProdutos(0, 10);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("SKU123", response.getBody().get(0).sku());
        verify(buscarProdutos, times(1)).buscarProdutos(0, 10);
    }

    @Test
    void deveCriarProduto() {
        ProdutoEntity entityMock = ProdutoEntity.builder()
                .sku("SKU123")
                .nome("Produto Teste")
                .preco(new BigDecimal("99.90"))
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        when(criar.criarProduto(produtoBodyRequestJsonMock)).thenReturn(entityMock);

        ResponseEntity<String> response = controller.criarProduto(produtoBodyRequestJsonMock);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("SKU123", response.getBody());

    }

    @Test
    void deveExcluirProduto() {
        doNothing().when(excluir).excluirProduto("SKU123");

        ResponseEntity<String> response = controller.deletarProduto("SKU123");

        assertEquals(204, response.getStatusCodeValue());
        verify(excluir, times(1)).excluirProduto("SKU123");
    }

    @Test
    void deveAlterarProduto() {
        AtualizarProdutoJson request = new AtualizarProdutoJson("Produto Alterado", new BigDecimal("149.99"));
        when(alterar.alterarProduto(request, "SKU123")).thenReturn(produtoMock);

        ResponseEntity<Produto> response = controller.alterarProduto("SKU123", request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("SKU123", response.getBody().getSku());
        verify(alterar, times(1)).alterarProduto(request, "SKU123");
    }
}
