-- Criação do schema
CREATE SCHEMA IF NOT EXISTS ms_produto;

-- Garante que tudo será criado dentro do schema ms_produto
SET search_path TO ms_produto;

-- Criação da extensão para UUID (se ainda não existir)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criação da tabela produto
CREATE TABLE produto (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sku VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    preco MONEY NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_ultima_alteracao TIMESTAMP,

    CONSTRAINT uk_produto_sku UNIQUE (sku)
);

-- Índice adicional para otimizar buscas por SKU
CREATE INDEX idx_produto_sku ON produto (sku);
