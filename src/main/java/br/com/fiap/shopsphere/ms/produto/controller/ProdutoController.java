package br.com.fiap.shopsphere.ms.produto.controller;

import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoBodyRequestJson;
import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoJson;
import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.usecase.*;
import br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoConstants.PRODUCES;
import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoConstants.V1_PRODUTOS;
import static br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils.convertToProdutoJson;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = V1_PRODUTOS, produces = PRODUCES)
public class ProdutoController {

    private final BuscarProdutoUseCase buscar;

    private final BuscarProdutosUseCase buscarProdutos;

    private final CriarProdutoUseCase criar;

    private final ExcluirProdutoUseCase excluir;

    private final AlterarProdutoUseCase alterar;

    @GetMapping("/{sku}")
    public ResponseEntity<ProdutoJson> buscarProduto(@PathVariable("sku") String sku) {
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

    @GetMapping
    public ResponseEntity<List<ProdutoJson>> buscarProdutos(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("GET | {} | Iniciado busca de produtos com paginacao | page: {} size: {} ", V1_PRODUTOS, page, size);
        List<Produto> clientes = buscarProdutos.buscarProdutos(page, size);
        log.info("GET | {} | Finalizada busca de produtos com paginacao | page: {} size: {} produtos: {}", V1_PRODUTOS, page, size, clientes);
        List<ProdutoJson> clientesJson = clientes.stream().map(ProdutoUtils::convertToProdutoJson).toList();
        return ok(clientesJson);
    }

    @PostMapping
    public ResponseEntity<String> criarProduto(@RequestBody ProdutoBodyRequestJson produtoBodyRequestJson) {
        log.info("POST | {} | Iniciado criação de produto | ", V1_PRODUTOS);
        var produto = criar.criarProduto(produtoBodyRequestJson);
        log.info("POST | {} | Finalizada criação de produto | ", V1_PRODUTOS);
        return status(CREATED).body(produto.getSku());
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<String> deletarProduto(@PathVariable("sku") String sku) {
        log.info("DELETE | {} | Iniciado deleção de Produto | id: {}", V1_PRODUTOS, sku);
        try {
            excluir.excluirProduto(sku);
            log.info("DELETE | {} | Produto deletado com sucesso | Id: {}", V1_PRODUTOS, sku);
            return status(NO_CONTENT).body("Produto excluído com sucesso!");
        } catch (RuntimeException e) {
            log.error("DELETE | {} | Erro ao deletar Produto | Id: {} | Erro: {}", V1_PRODUTOS, sku, e.getMessage());
            return status(NOT_FOUND).body("Produto não encontrado");
        }
    }

    @PutMapping("/{sku}")
    public ResponseEntity<String> alterarProduto(@PathVariable("sku") String sku,
                                                 @RequestBody ProdutoBodyRequestJson produtoBodyRequestJson) {
        log.info("PUT | {} | Iniciado alterarProduto | sku: {}", V1_PRODUTOS, produtoBodyRequestJson.sku());
        alterar.alterarProduto(produtoBodyRequestJson, sku);
        log.info("PUT | {} | Finalizado alterarProduto", V1_PRODUTOS);
        return ok("Produto atualizado com sucesso!");
    }
}
