CREATE DATABASE cidades-db;

USE cidades-db;

CREATE TABLE estados (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(50) NOT NULL,
                        sigla CHAR(2) NOT NULL
);

CREATE TABLE cidades (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(80) NOT NULL,
                        id_estado INT NOT NULL,
                        FOREIGN KEY (id_estado) REFERENCES estados(id)
);

INSERT INTO estados (nome, sigla) VALUES ('Amazonas', 'AM');
INSERT INTO cidades (nome, id_estado) VALUES ('Manaus', 1), ('Manacapuru', 1), ('Parintins', 1);