-- Seleccionar todos los cursos de una institución
SELECT * FROM learn.curso WHERE institucion_id = 1;

-- Seleccionar todas las respuestas correctas de un ejercicio
SELECT * FROM learn.respuesta WHERE ejercicio_id = 1 AND es_correcta = 1;

-- Eliminar un usuario
DELETE FROM learn.usuario WHERE id = 3;

-- Eliminar un ejercicio
DELETE FROM learn.ejercicio WHERE id = 2;

-- Seleccionar todos los ejercicios junto con sus módulos y cursos:

SELECT 
    e.id AS ejercicio_id, 
    e.nombre AS ejercicio_nombre, 
    e.descripcion AS ejercicio_descripcion, 
    m.nombre AS modulo_nombre, 
    c.nombre AS curso_nombre
FROM 
    learn.ejercicio e
JOIN 
    learn.modulo m ON e.modulo_id = m.id
JOIN 
    learn.curso c ON m.curso_id = c.id;

-- Seleccionar todas las respuestas de un usuario junto con los ejercicios:

SELECT 
    ru.id AS respuesta_usuario_id, 
    e.nombre AS nombre_ejercicio, 
    r.descripcion AS desc_respuesta, 
    r.es_correcta
FROM 
    learn.respuesta_usuario ru
JOIN 
    learn.respuesta r ON ru.respuesta_id = r.id
JOIN 
    learn.ejercicio e ON r.ejercicio_id = e.id
WHERE 
    ru.usuario_id = 2;

-- Seleccionar todos los cursos junto con las instituciones que los crearon:

SELECT 
    c.nombre AS nombre_curso, 
    c.descripcion AS desc_curso, 
    i.ciudad AS ciudad_institucion, 
    i.provincia AS provincia_institucion
FROM 
    learn.curso c
JOIN 
    learn.institucion i ON c.institucion_id = i.id;