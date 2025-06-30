CREATE SCHEMA IF NOT EXISTS ms_produto;

SET search_path TO ms_produto;

CREATE TABLE produto (
    sku VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco MONEY NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_ultima_alteracao TIMESTAMP
);

CREATE INDEX idx_produto_nome ON produto (nome);
