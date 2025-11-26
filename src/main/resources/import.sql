-- Script de inicialización de datos para la tabla personas
-- Este script se ejecuta automáticamente al iniciar la aplicación

-- Insertar personas de ejemplo
INSERT INTO personas (id, nombre, apellido, edad, sexo) VALUES (1, 'Juan', 'Pérez', 30, 'M');
INSERT INTO personas (id, nombre, apellido, edad, sexo) VALUES (2, 'María', 'García', 25, 'F');
INSERT INTO personas (id, nombre, apellido, edad, sexo) VALUES (3, 'Carlos', 'López', 35, 'M');
INSERT INTO personas (id, nombre, apellido, edad, sexo) VALUES (4, 'Ana', 'Martínez', 28, 'F');
INSERT INTO personas (id, nombre, apellido, edad, sexo) VALUES (5, 'Roberto', 'Hernández', 42, 'M');
INSERT INTO personas (id, nombre, apellido, edad, sexo) VALUES (6, 'Laura', 'Ramírez', 31, 'F');
INSERT INTO personas (id, nombre, apellido, edad, sexo) VALUES (7, 'Diego', 'Torres', 38, 'M');
INSERT INTO personas (id, nombre, apellido, edad, sexo) VALUES (8, 'Sofía', 'Flores', 27, 'F');
INSERT INTO personas (id, nombre, apellido, edad, sexo) VALUES (9, 'Fernando', 'Cruz', 45, 'M');
INSERT INTO personas (id, nombre, apellido, edad, sexo) VALUES (10, 'Gabriela', 'Morales', 33, 'F');

-- Configurar la secuencia para el próximo ID
ALTER TABLE personas ALTER COLUMN id RESTART WITH 11;