package br.com.fiap.shopsphere.ms.produto.gateway.database.jpa.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto", schema = "ms_produto")
public class ProdutoEntity {

    @Id
    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = now();

    @Column(name = "data_ultima_alteracao")
    private LocalDateTime dataUltimaAlteracao;
}