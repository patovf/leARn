package main.java.com.leARn.service;

import main.java.com.leARn.config.Database;
import main.java.com.leARn.model.Curso;
import main.java.com.leARn.model.Institucion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CursoService {
    private Connection connect;
    private InstitucionService institucionService;

    public CursoService(Connection connect) {
        this.connect = connect;
    }

    public Curso getCursoById(int cursoId) {
        Curso curso = null;
        Institucion institucion = null;
        String query = "SELECT * FROM curso WHERE id = ?";

        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            prepare.setInt(1, cursoId);
            ResultSet result = prepare.executeQuery();
            System.out.println("rwrwerewr");
            if (result.next()) {

                int institucionId = result.getInt("institucion_id");

                institucion = institucionService.getInstitucionById(institucionId);


                curso = new Curso(result.getInt("id"),
                        result.getString("nombre"),
                        result.getString("descripcion"),
                        result.getBoolean("esPublico"),
                        result.getInt("institucion_id"),
                        result.getString("codigo"),
                        institucion);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return curso;
    }

//    public void loadCourses() {
//        try {
//            // Query to get the list of courses
//            String query = "SELECT * FROM curso";
//
//            PreparedStatement prepare = connect.prepareStatement(query);
//            ResultSet result = prepare.executeQuery();
//
//            // Loop through the result set and add courses to the ComboBox
//            while (result.next()) {
//                int courseId = result.getInt("id");
//                String courseName = result.getString("nombre");
//
//                Course course = new Course(courseId, courseName);
//                nuevoModulo_curso.getItems().add(course);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
