-- Pessoas Físicas (Clientes)
INSERT INTO tb_pessoa (tipo, nome, cpf, email, telefone) VALUES ('F', 'Luiz Vitório Dias', '417.101.010-16', 'luizvitdg@gmail.com', '(31) 99632-1455');
INSERT INTO tb_pessoa (tipo, nome, cpf, email, telefone) VALUES ('F', 'Lourenço Sevieri', '297.762.590-90', 'lo.sevieri@email.com', '(63) 99876-5432');
INSERT INTO tb_pessoa (tipo, nome, cpf, email, telefone) VALUES ('F', 'Felipe Loppeux', '206.249.290-19', 'felipeloppeux@email.com', '(71) 99831-2468');

-- Pessoas Jurídicas (Clientes Empresa)
INSERT INTO tb_pessoa (tipo, razao_social, cnpj, email, telefone) VALUES ('J', 'Guerra Game Design', '28.491.746/0001-64', 'gamedesign@guerra.com', '(11) 3319-2236');
INSERT INTO tb_pessoa (tipo, razao_social, cnpj, email, telefone) VALUES ('J', 'Jotter Produtos Tecnológicos', '60.176.887/0001-03', 'jotter@tecnologicos.com', '(71) 3236-5798');
INSERT INTO tb_pessoa (tipo, razao_social, cnpj, email, telefone) VALUES ('J', 'Oeste Soluções em Tecnologia', '64.540.240/0001-61', 'oeste@tecnologia.com', '(21) 3579-5432');

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

-- Usuários (senhas criptografadas com BCrypt)
-- Senha do admin: admin | Senha do user: 123
INSERT INTO usuario (login, senha) VALUES ('admin', '$2a$10$uaPTPJQUXQk/KOyaNrQjP.5uXsXbV89BaIlK19R3aBU8gyTBncX3y');
INSERT INTO usuario (login, senha) VALUES ('user', '$2a$10$z3kW8ZjqBij7M.sgTSjlqOGJOxL55/2nA1uLgViMvWrEblKtV4vVu');

-- Associação de Roles aos Usuários (tabela de junção)
-- admin -> ROLE_ADMIN
INSERT INTO usuario_roles (usuario_id, role_id) VALUES (1, 1);
-- user -> ROLE_USER
INSERT INTO usuario_roles (usuario_id, role_id) VALUES (2, 2);
