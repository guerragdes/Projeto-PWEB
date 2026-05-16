-- Pessoas Físicas (Clientes)
INSERT INTO tb_pessoa (tipo, nome, cpf, email, telefone) VALUES ('F', 'Caio Silva Santos', '123.456.789-00', 'joao.silva@email.com', '(11) 98765-4321');
INSERT INTO tb_pessoa (tipo, nome, cpf, email, telefone) VALUES ('F', 'Marina Oliveira Costa', '987.654.321-11', 'maria.costa@email.com', '(11) 99876-5432');
INSERT INTO tb_pessoa (tipo, nome, cpf, email, telefone) VALUES ('F', 'Carlos Eduardo Ferreira', '456.789.123-22', 'carlos.ferreira@email.com', '(11) 97654-3210');

-- Pessoas Jurídicas (Clientes Empresa)
INSERT INTO tb_pessoa (tipo, razao_social, cnpj, email, telefone) VALUES ('J', 'Tech Solutions Brasil LTDA', '12.345.678/0001-90', 'contato@techsolutions.com', '(11) 3456-7890');
INSERT INTO tb_pessoa (tipo, razao_social, cnpj, email, telefone) VALUES ('J', 'Consultoria Estratégica S.A.', '98.765.432/0001-11', 'vendas@consultoria.com', '(11) 3234-5678');

-- Produtos
INSERT INTO produto (descricao, valor) VALUES ('Notebook Dell', 3500.00);
INSERT INTO produto (descricao, valor) VALUES ('Mouse Logitech', 150.00);
INSERT INTO produto (descricao, valor) VALUES ('Teclado Mecânico', 450.00);
INSERT INTO produto (descricao, valor) VALUES ('Monitor LG 24"', 800.00);
INSERT INTO produto (descricao, valor) VALUES ('Webcam HD', 250.00);

-- Vendas associadas aos clientes
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-10 10:30:00', 1);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-12 14:15:00', 2);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-15 09:45:00', 4);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-22 12:35:00', 5);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-22 18:10:00', 3);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-29 15:10:00', 3);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-04-01 15:30:00', 3);

-- Itens das Vendas
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (1, 2, 1);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (2, 5, 1);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (3, 3, 2);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (4, 1, 2);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (5, 2, 3);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (1, 1, 4);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (2, 3, 5);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (3, 2, 5);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (3, 10, 6);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (2, 50, 7);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (1, 100, 7);
