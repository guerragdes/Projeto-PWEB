-- Pessoas Físicas (Clientes)
-- Senha: 123
INSERT INTO tb_pessoa (nome, documento, tipo_pessoa, email, telefone, login, senha) VALUES ('Luiz Victor Gonçalves', '417.101.010-16', 'F', 'lvdg@gmail.com', '(31) 99632-1455', 'lvdg', '$2a$10$lDqnpLLDVAqjJHhp21mHXOZV8naFc5Bx4XAR0zIhHlVVCCWDp8lTW');
INSERT INTO tb_pessoa (nome, documento, tipo_pessoa, email, telefone, login, senha) VALUES ('Lorenzo Sevieri', '297.762.590-90', 'F', 'lsevieri@email.com', '(63) 99876-5432', 'lsevieri', '$2a$10$lDqnpLLDVAqjJHhp21mHXOZV8naFc5Bx4XAR0zIhHlVVCCWDp8lTW');
INSERT INTO tb_pessoa (nome, documento, tipo_pessoa, email, telefone, login, senha) VALUES ('Felipe Cavalcante', '206.249.290-19', 'F', 'felipelc@email.com', '(71) 99831-2468', 'felipelc', '$2a$10$lDqnpLLDVAqjJHhp21mHXOZV8naFc5Bx4XAR0zIhHlVVCCWDp8lTW');

-- Pessoas Jurídicas (Clientes Empresa)
-- Senha: 123
INSERT INTO tb_pessoa (nome, documento, tipo_pessoa, email, telefone, login, senha) VALUES ('Guerra Game Design', '28.491.746/0001-64', 'J', 'gamedesign@guerra.com', '(11) 3319-2236', 'guerra', '$2a$10$lDqnpLLDVAqjJHhp21mHXOZV8naFc5Bx4XAR0zIhHlVVCCWDp8lTW');
INSERT INTO tb_pessoa (nome, documento, tipo_pessoa, email, telefone, login, senha) VALUES ('Jotter Produtos Tecnológicos', '60.176.887/0001-03', 'J', 'jotter@tecnologicos.com', '(71) 3236-5798', 'jotter', '$2a$10$lDqnpLLDVAqjJHhp21mHXOZV8naFc5Bx4XAR0zIhHlVVCCWDp8lTW');
INSERT INTO tb_pessoa (nome, documento, tipo_pessoa, email, telefone, login, senha) VALUES ('Oeste Soluções em Tecnologia', '64.540.240/0001-61', 'J', 'oeste@tecnologia.com', '(21) 3579-5432', 'oeste', '$2a$10$lDqnpLLDVAqjJHhp21mHXOZV8naFc5Bx4XAR0zIhHlVVCCWDp8lTW');

-- Admin
-- Senha do admin: admin
INSERT INTO tb_pessoa (login, senha, nome, email) VALUES ('admin', '$2a$10$UaCicdIjoORu0DZwEh3kleX5oK2LZb5Y9dxQPnCXyw8JFHCXNvJNq', 'Administrador', 'admin@loja.com');

-- Produtos
INSERT INTO produto (descricao, valor, imagem_url) VALUES ('Notebook Dell', 3500.00, 'notebook-dell.jpg');
INSERT INTO produto (descricao, valor, imagem_url) VALUES ('Mouse Logitech', 150.00, 'mouse-logitech.png');
INSERT INTO produto (descricao, valor, imagem_url) VALUES ('Teclado Mecânico', 450.00, 'teclado-mecanico.jpg');
INSERT INTO produto (descricao, valor, imagem_url) VALUES ('Monitor LG 24"', 800.00, 'monitor-lg.png');
INSERT INTO produto (descricao, valor, imagem_url) VALUES ('Webcam HD', 250.00, 'webcam-hd.jpg');
INSERT INTO produto (descricao, valor, imagem_url) VALUES ('Processador AMD Ryzen 9', 2200.00, 'processador-ryzen.jpg');
INSERT INTO produto (descricao, valor, imagem_url) VALUES ('Placa de Vídeo RTX 3080', 5000.00, 'placa-video-rtx.jpg');

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

-- Associação de Roles aos Usuários (cada INSERT deve ficar em UMA ÚNICA LINHA)
INSERT INTO usuario_roles (usuario_id, role_id) SELECT p.id, r.id FROM tb_pessoa p, role r WHERE p.login = 'admin' AND r.nome = 'ROLE_ADMIN';
INSERT INTO usuario_roles (usuario_id, role_id) SELECT p.id, r.id FROM tb_pessoa p, role r WHERE p.login = 'lvdg' AND r.nome = 'ROLE_USER';
INSERT INTO usuario_roles (usuario_id, role_id) SELECT p.id, r.id FROM tb_pessoa p, role r WHERE p.login = 'guerra' AND r.nome = 'ROLE_USER';
INSERT INTO usuario_roles (usuario_id, role_id) SELECT p.id, r.id FROM tb_pessoa p, role r WHERE p.login = 'lsevieri' AND r.nome = 'ROLE_USER';
INSERT INTO usuario_roles (usuario_id, role_id) SELECT p.id, r.id FROM tb_pessoa p, role r WHERE p.login = 'felipelc' AND r.nome = 'ROLE_USER';
INSERT INTO usuario_roles (usuario_id, role_id) SELECT p.id, r.id FROM tb_pessoa p, role r WHERE p.login = 'jotter' AND r.nome = 'ROLE_USER';
INSERT INTO usuario_roles (usuario_id, role_id) SELECT p.id, r.id FROM tb_pessoa p, role r WHERE p.login = 'oeste' AND r.nome = 'ROLE_USER';
