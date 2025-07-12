package br.com.fiap.shopsphere.ms.produto.controller;

import br.com.fiap.shopsphere.ms.produto.controller.json.AtualizarProdutoJson;
import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoBodyRequestJson;
import br.com.fiap.shopsphere.ms.produto.controller.json.ProdutoJson;
import br.com.fiap.shopsphere.ms.produto.domain.Produto;
import br.com.fiap.shopsphere.ms.produto.usecase.*;
import br.com.fiap.shopsphere.ms.produto.utils.ProdutoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        log.info("GET | {} | Iniciada busca de produto | SKU: {}", V1_PRODUTOS, sku);
        Produto produto = buscar.buscarPorSku(sku);
        ProdutoJson produtoJson = convertToProdutoJson(produto);
        log.info("GET | {} | Finalizada busca de produto  | SKU: {} {}", V1_PRODUTOS, sku, produtoJson);
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
        log.info("DELETE | {} | Iniciado exclusao de Produto | id: {}", V1_PRODUTOS, sku);
        excluir.excluirProduto(sku);
        log.info("DELETE | {} | Finalizada exclusao de Produto | Id: {}", V1_PRODUTOS, sku);
        return status(NO_CONTENT).build();
    }

    @PutMapping("/{sku}")
    public ResponseEntity<Produto> alterarProduto(@PathVariable("sku") String sku,
                                                 @RequestBody AtualizarProdutoJson atualizarProdutoJson) {
        log.info("PUT | {} | Iniciado alterarProduto | sku: {}", V1_PRODUTOS, sku);
        var atualizado = alterar.alterarProduto(atualizarProdutoJson, sku);
        log.info("PUT | {} | Finalizado alterarProduto", V1_PRODUTOS);
        return status(OK).body(atualizado);
    }
}
