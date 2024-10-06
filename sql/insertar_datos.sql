-- Insertar instituciones
INSERT INTO learn.institucion (id, provincia, ciudad, direccion) VALUES
(1, 'Buenos Aires', 'La Plata', 'Independencia 123'),
(2, 'Córdoba', 'Córdoba', 'Av. Libertador 456');

-- Insertar usuarios
INSERT INTO learn.usuario (id, nombre, apellido, email, rol, institucion_id) VALUES
(1, 'Juan', 'Pérez', 'juan.perez@example.com', 'profesor', 1),
(2, 'María', 'Gómez', 'maria.gomez@example.com', 'estudiante', 1),
(3, 'Carlos', 'Sánchez', 'carlos.sanchez@example.com', 'estudiante', 2);

-- Insertar cursos
INSERT INTO learn.curso (id, nombre, descripcion, esPublico, institucion_id) VALUES
(1, 'Cálculo', 'Curso de Cálculo', 1, 1),
(2, 'Bases de datos', 'Curso de SQL', 1, 2);

-- Insertar módulos
INSERT INTO learn.modulo (id, nombre, descripcion, curso_id) VALUES
(1, 'Funciones', 'Módulo sobre Funciones', 1),
(2, 'SQL Básico', 'Módulo sobre SQL Básico', 2);

-- Insertar ejercicios
INSERT INTO learn.ejercicio (id, nombre, descripcion, modulo_id, tipo_de_ejercicio, dificultad) VALUES
(1, 'Valor de y cuando x cambie', '¿Qué valor toma y en la función y = x * 2 si x = 4?', 1, 'Selección única', 'fácil'),
(2, 'Consulta de valor en tabla', 'Si tuviéramos una tabla de empleados y quisiéramos conocer quiénes tienen sueldos mayores a 1000 ¿Qué sentencia SQL deberíamos usar?', 3, 'Selección única', 'difícil');

-- Insertar respuestas
-- Insertar respuestas para el ejercicio 1
INSERT INTO learn.respuesta (id, descripcion, es_correcta, ejercicio_id) VALUES
(1, 'y = 8', 1, 1),
(2, 'y = 6', 0, 1),
(3, 'y = 4', 0, 1),
(4, 'y = 10', 0, 1);

-- Insertar respuestas para el ejercicio 2
INSERT INTO learn.respuesta (id, descripcion, es_correcta, ejercicio_id) VALUES
(5, 'SELECT * FROM empleados WHERE sueldo > 1000;', 1, 2),
(6, 'SELECT nombre FROM empleados WHERE sueldo > 1000;', 0, 2),
(7, 'SELECT * FROM empleados WHERE sueldo >= 1000;', 0, 2),
(8, 'SELECT * FROM empleados WHERE sueldo < 1000;', 0, 2);