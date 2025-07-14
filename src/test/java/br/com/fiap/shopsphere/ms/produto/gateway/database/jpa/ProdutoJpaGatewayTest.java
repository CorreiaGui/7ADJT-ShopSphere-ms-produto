package br.com.fiap.shopsphere.ms.produto.gateway.database.jpa;

import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.exception.ErroAoAcessarRepositorioException;
import br.com.fiap.shopsphere.ms.produto.exception.ProdutoNotFound;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity.ProdutoEntity;
import br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoJpaGatewayTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoJpaGateway gateway;

    @Test
    void deveBuscarProdutoPorSkuQuandoExistir() {
        ProdutoEntity entity = criarMockProdutoEntity();
        when(repository.findById("SKU123")).thenReturn(Optional.of(entity));

        Optional<Produto> resultado = gateway.buscarPorSku("SKU123");

        assertTrue(resultado.isPresent());
        assertEquals("SKU123", resultado.get().getSku());
        verify(repository, times(1)).findById("SKU123");
    }

    @Test
    void deveRetornarVazioQuandoProdutoNaoExistirPorSku() {
        when(repository.findById("SKU123")).thenReturn(Optional.empty());

        Optional<Produto> resultado = gateway.buscarPorSku("SKU123");

        assertTrue(resultado.isEmpty());
        verify(repository).findById("SKU123");
    }

    @Test
    void deveBuscarProdutosComPaginacao() {
        ProdutoEntity entity = criarMockProdutoEntity();
        Page<ProdutoEntity> page = new PageImpl<>(List.of(entity));
        when(repository.findAll(PageRequest.of(0, 2))).thenReturn(page);

        List<Produto> produtos = gateway.buscarProdutos(0, 2);

        assertEquals(1, produtos.size());
        assertEquals("Produto Teste", produtos.get(0).getNome());
        verify(repository).findAll(PageRequest.of(0, 2));
    }

    @Test
    void deveCriarProdutoComSucesso() {
        ProdutoEntity entity = criarMockProdutoEntity();
        when(repository.save(entity)).thenReturn(entity);

        ProdutoEntity salvo = gateway.criarProduto(entity);

        assertNotNull(salvo);
        assertEquals("SKU123", salvo.getSku());
        verify(repository).save(entity);
    }

    @Test
    void deveLancarErroAoAcessarRepositorioAoCriarProduto() {
        ProdutoEntity entity = criarMockProdutoEntity();
        when(repository.save(entity)).thenThrow(new RuntimeException("Erro banco"));

        assertThrows(ErroAoAcessarRepositorioException.class, () -> gateway.criarProduto(entity));
        verify(repository).save(entity);
    }

    @Test
    void deveExcluirProdutoQuandoSkuExistir() {
        when(repository.existsById("SKU123")).thenReturn(true);
        doNothing().when(repository).deleteById("SKU123");

        assertDoesNotThrow(() -> gateway.excluirProduto("SKU123"));
        verify(repository).existsById("SKU123");
        verify(repository).deleteById("SKU123");
    }

    @Test
    void deveLancarExcecaoAoExcluirProdutoInexistente() {
        when(repository.existsById("SKU123")).thenReturn(false);

        assertThrows(ProdutoNotFound.class, () -> gateway.excluirProduto("SKU123"));
        verify(repository).existsById("SKU123");
        verify(repository, never()).deleteById("SKU123");
    }

    @Test
    void deveAlterarProdutoComSucesso() {
        ProdutoEntity entity = criarMockProdutoEntity();
        when(repository.save(entity)).thenReturn(entity);

        ProdutoEntity alterado = gateway.alterarProduto(entity);

        assertNotNull(alterado);
        verify(repository).save(entity);
    }

    @Test
    void deveLancarErroAoAcessarRepositorioAoAlterarProduto() {
        ProdutoEntity entity = criarMockProdutoEntity();
        when(repository.save(entity)).thenThrow(new RuntimeException("Falha no update"));

        assertThrows(ErroAoAcessarRepositorioException.class, () -> gateway.alterarProduto(entity));
        verify(repository).save(entity);
    }

    private ProdutoEntity criarMockProdutoEntity() {
        return ProdutoEntity.builder()
                .sku("SKU123")
                .nome("Produto Teste")
                .preco(new BigDecimal("99.90"))
                .dataCriacao(LocalDateTime.now())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
    }
}
