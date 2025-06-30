SET search_path TO ms_produto;

-- Inserir produtos
INSERT INTO produto (sku, nome, preco, data_criacao, data_ultima_alteracao) VALUES
    ('SKU-001', 'Camiseta Branca', 49.90, CURRENT_TIMESTAMP, NULL),
    ('SKU-002', 'Calça Jeans Azul', 139.90, CURRENT_TIMESTAMP, NULL),
    ('SKU-003', 'Tênis Casual', 219.99, CURRENT_TIMESTAMP, NULL),
    ('SKU-004', 'Jaqueta Corta-Vento', 189.00, CURRENT_TIMESTAMP, NULL),
    ('SKU-005', 'Meia Estampada (par)', 15.50, CURRENT_TIMESTAMP, NULL);
