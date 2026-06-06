-- Pessoas Físicas (Clientes)
INSERT INTO tb_pessoa (tipo, nome, cpf, email, telefone) VALUES ('F', 'Luiz Dias Gonçalves', '417.101.010-16', 'luizdg@gmail.com', '(31) 99632-1455');
INSERT INTO tb_pessoa (tipo, nome, cpf, email, telefone) VALUES ('F', 'Fernando Sevieri Muniz', '297.762.590-90', 'fsevieri@email.com', '(63) 99876-5432');
INSERT INTO tb_pessoa (tipo, nome, cpf, email, telefone) VALUES ('F', 'Felipe Cavalcante Menezes', '206.249.290-19', 'felipecm@email.com', '(71) 99831-2468');

-- Pessoas Jurídicas (Clientes Empresa)
INSERT INTO tb_pessoa (tipo, razao_social, cnpj, email, telefone) VALUES ('J', 'Guerra Game Design', '28.491.746/0001-64', 'gamedesign@guerra.com', '(11) 3319-2236');
INSERT INTO tb_pessoa (tipo, razao_social, cnpj, email, telefone) VALUES ('J', 'Jotter Produtos Tecnológicos', '60.176.887/0001-03', 'jotter@tecnologicos.com', '(71) 3236-5798');
INSERT INTO tb_pessoa (tipo, razao_social, cnpj, email, telefone) VALUES ('J', 'Oeste Soluções em Tecnologia', '64.540.240/0001-61', 'oeste@tecnologia.com', '(21) 3579-5432');

-- Usuários (Clientes com Login)
-- Senha do admin: admin | Senha do user: 123
INSERT INTO tb_pessoa (tipo, login, senha, nome, email) VALUES ('U', 'admin', '$2a$10$UaCicdIjoORu0DZwEh3kleX5oK2LZb5Y9dxQPnCXyw8JFHCXNvJNq', 'Administrador', 'admin@loja.com');
INSERT INTO tb_pessoa (tipo, login, senha, nome, email) VALUES ('U', 'user', '$2a$10$lDqnpLLDVAqjJHhp21mHXOZV8naFc5Bx4XAR0zIhHlVVCCWDp8lTW', 'Usuário Comum', 'user@email.com');

-- Produtos
INSERT INTO produto (descricao, valor) VALUES ('Notebook Dell', 3500.00);
INSERT INTO produto (descricao, valor) VALUES ('Mouse Logitech', 150.00);
INSERT INTO produto (descricao, valor) VALUES ('Teclado Mecânico', 450.00);
INSERT INTO produto (descricao, valor) VALUES ('Monitor LG 24"', 800.00);
INSERT INTO produto (descricao, valor) VALUES ('Webcam HD', 250.00);
INSERT INTO produto (descricao, valor) VALUES ('Processador AMD Ryzen 9', 2200.00);
INSERT INTO produto (descricao, valor) VALUES ('Placa de Vídeo RTX 3080', 5000.00);
INSERT INTO produto (descricao, valor) VALUES ('Memória RAM 32GB', 600.00);

-- Vendas associadas aos clientes
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-10 10:30:00', 1);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-12 14:15:00', 2);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-15 09:45:00', 4);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-22 12:35:00', 5);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-22 18:10:00', 6);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-03-29 15:10:00', 2);
INSERT INTO venda (data, pessoa_id) VALUES ('2026-04-01 15:30:00', 1);

-- Itens das Vendas
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (1, 1, 1);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (2, 1, 2);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (3, 3, 3);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (4, 1, 2);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (5, 2, 6);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (1, 1, 4);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (2, 3, 5);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (3, 2, 5);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (3, 1, 6);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (2, 1, 7);
INSERT INTO item (produto_id, quantidade, venda_id) VALUES (1, 3, 5);

-- Roles (Papéis de Usuário)
INSERT INTO role (nome) VALUES ('ROLE_ADMIN');
INSERT INTO role (nome) VALUES ('ROLE_USER');

-- Associação de Roles aos Usuários (cada INSERT deve ficar em UMA ÚNICA LINHA - exigência do parser do Hibernate para import.sql)
INSERT INTO usuario_roles (usuario_id, role_id) SELECT p.id, r.id FROM tb_pessoa p, role r WHERE p.login = 'admin' AND r.nome = 'ROLE_ADMIN';
INSERT INTO usuario_roles (usuario_id, role_id) SELECT p.id, r.id FROM tb_pessoa p, role r WHERE p.login = 'user' AND r.nome = 'ROLE_USER';
