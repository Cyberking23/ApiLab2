CREATE DATABASE IF NOT EXISTS alfadb
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;
USE alfadb;

DROP TABLE IF EXISTS consultores;
CREATE TABLE consultores (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             nombre VARCHAR(150) NOT NULL,
                             especializacion VARCHAR(120) NOT NULL,
                             in_activo_actualizado BIT(1) DEFAULT 0
) ENGINE=InnoDB;

DROP TABLE IF EXISTS participantes;
CREATE TABLE participantes (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               nombre VARCHAR(150) NOT NULL,
                               email VARCHAR(180) NOT NULL UNIQUE,
                               genero VARCHAR(20) NOT NULL,
                               nivel_educativo VARCHAR(80),
                               ingreso_formal BIT(1) DEFAULT 0
) ENGINE=InnoDB;

DROP TABLE IF EXISTS programas;
CREATE TABLE programas (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           nombre VARCHAR(150) NOT NULL,
                           enfoque VARCHAR(80) NOT NULL,
                           duracion VARCHAR(50) NOT NULL,
                           institucion VARCHAR(120),
                           consultor_id BIGINT NOT NULL,
                           CONSTRAINT fk_prog_cons FOREIGN KEY (consultor_id)
                               REFERENCES consultores(id)
                               ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_programas_consultor_id ON programas(consultor_id);
CREATE INDEX idx_programas_enfoque ON programas(enfoque);

DROP TABLE IF EXISTS inscripciones;
CREATE TABLE inscripciones (
                               programa_id BIGINT NOT NULL,
                               participante_id BIGINT NOT NULL,
                               PRIMARY KEY (programa_id, participante_id),
                               CONSTRAINT fk_insc_prog FOREIGN KEY (programa_id)
                                   REFERENCES programas(id)
                                   ON DELETE CASCADE ON UPDATE CASCADE,
                               CONSTRAINT fk_insc_part FOREIGN KEY (participante_id)
                                   REFERENCES participantes(id)
                                   ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_insc_participante_id ON inscripciones(participante_id);

DROP TABLE IF EXISTS evaluaciones;
CREATE TABLE evaluaciones (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              fecha DATE NOT NULL,
                              puntaje DOUBLE NOT NULL,          -- 0..100
                              observaciones VARCHAR(500),
                              participante_id BIGINT NOT NULL,
                              programa_id BIGINT NOT NULL,
                              CONSTRAINT fk_eval_part FOREIGN KEY (participante_id)
                                  REFERENCES participantes(id)
                                  ON DELETE CASCADE ON UPDATE CASCADE,
                              CONSTRAINT fk_eval_prog FOREIGN KEY (programa_id)
                                  REFERENCES programas(id)
                                  ON DELETE CASCADE ON UPDATE CASCADE,
                              CONSTRAINT uk_eval_unique UNIQUE (programa_id, participante_id, fecha)
) ENGINE=InnoDB;

CREATE INDEX idx_eval_prog ON evaluaciones(programa_id);
CREATE INDEX idx_eval_part ON evaluaciones(participante_id);
CREATE INDEX idx_eval_fecha ON evaluaciones(fecha);

INSERT INTO consultores (nombre, especializacion, in_activo_actualizado)
VALUES ('Ana', 'Analítica', 1);

INSERT INTO participantes (nombre, email, genero, nivel_educativo, ingreso_formal)
VALUES ('Luis', 'luis@example.com', 'M', 'Bachillerato', 1);

INSERT INTO programas (nombre, enfoque, duracion, institucion, consultor_id)
VALUES ('Data 101', 'basico', '20h', 'MINED', 1);

INSERT INTO inscripciones (programa_id, participante_id) VALUES (1, 1);

INSERT INTO evaluaciones (fecha, puntaje, observaciones, participante_id, programa_id)
VALUES ('2025-05-01', 78, 'buen desempeño', 1, 1);


SELECT * FROM consultores;
SELECT * FROM participantes;
SELECT * FROM programas;
SELECT * FROM evaluaciones;
