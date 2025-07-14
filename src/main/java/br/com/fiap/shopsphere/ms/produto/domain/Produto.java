package br.com.fiap.shopsphere.ms.produto.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private String sku;

    private String nome;

    private BigDecimal preco;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataUltimaAlteracao;
}
