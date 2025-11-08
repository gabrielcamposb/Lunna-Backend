CREATE TABLE IF NOT EXISTS tb_dados_menstruais (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_nascimento DATE,
    usa_metodo_contraceptivo BOOLEAN,
    metodo_contraceptivo VARCHAR(50),
    intervalo_pilula VARCHAR(50),
    data_inicio_pilula DATE,
    data_ultima_menstruacao DATE,
    duracao_ciclo_em_dias INT
);

CREATE TABLE IF NOT EXISTS tb_usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    apelido VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    dados_menstruais_id BIGINT NULL,
    CONSTRAINT fk_usuario_dados FOREIGN KEY (dados_menstruais_id)
        REFERENCES tb_dados_menstruais (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS tb_ciclo_menstrual (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_inicio DATE NOT NULL,
    duracao_dias INT NOT NULL,
    fase_atual VARCHAR(50),
    dados_menstruais_id BIGINT,
    CONSTRAINT fk_ciclo_dados FOREIGN KEY (dados_menstruais_id)
        REFERENCES tb_dados_menstruais (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tb_sintoma (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_registro DATE NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    intensidade VARCHAR(50),
    ciclo_menstrual_id BIGINT,
    CONSTRAINT fk_sintoma_ciclo FOREIGN KEY (ciclo_menstrual_id)
        REFERENCES tb_ciclo_menstrual (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tb_notificacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    icone VARCHAR(255),
    data_envio DATETIME NOT NULL,
    tipo VARCHAR(50) NOT NULL
);
