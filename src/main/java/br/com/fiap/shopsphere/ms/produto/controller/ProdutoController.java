package br.com.fiap.shopsphere.ms.produto.controller;

import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoJson;
import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.usecase.*;
import br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils.convertToProdutoJson;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProdutoController.V1_PRODUTOS, produces = "application/json")
public class ProdutoController {

    public static final String V1_PRODUTOS = "/api/v1/produtos";

    private final BuscarProdutoUseCase buscar;

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProdutoJson> buscarProdutoPorSku(@PathVariable("sku") String sku) {
        log.info("Buscando produto com SKU: {}", sku);
        Produto produto = buscar.buscarPorSku(sku);
        if (produto == null) {
            log.error("Produto não encontrado com SKU: {}", sku);
            return notFound().build();
        }
        log.info("Produto encontrado: {}", produto);
        ProdutoJson produtoJson = convertToProdutoJson(produto);
        log.info("produtoJson encontrado: {}", produtoJson);
        return ok(produtoJson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoJson> buscarProdutoPorId(@PathVariable("id") UUID id) {
        log.info("Buscando produto com ID: {}", id);
        Produto produto = buscar.buscarPorId(id);
        if (produto == null) {
            log.error("Produto não encontrado com ID: {}", id);
            return notFound().build();
        }
        log.info("Produto encontrado: {}", produto);
        ProdutoJson produtoJson = convertToProdutoJson(produto);
        log.info("produtoJson encontrado: {}", produtoJson);
        return ok(produtoJson);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoJson>> buscarTodosProdutos() {
        log.info("Buscando produtos");
        List<Produto> produtos = buscar.buscarTodosProdutos();
        List<ProdutoJson> produtosJson = produtos.stream().map(ProdutoUtils::convertToProdutoJson).collect(toList());
        log.info("produtosJson encontrado: {}", produtosJson);
        return ok(produtosJson);
    }
}
