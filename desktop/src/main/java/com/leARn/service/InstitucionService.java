package main.java.com.leARn.service;

import main.java.com.leARn.config.Database;
import main.java.com.leARn.model.Curso;
import main.java.com.leARn.model.Institucion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InstitucionService {
    private Connection connect;

    public InstitucionService(Connection connect) {
        this.connect = connect;
    }

    public Institucion getInstitucionById(int institucionId) {
        Institucion institucion = null;

        System.out.println("traigo Cursos 3");

        System.out.println("traigo Cursosssss" + institucionId);
        String query = "SELECT * FROM institucion WHERE id = ?";

        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            prepare.setInt(1, institucionId);
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                institucion = new Institucion(result.getInt("id"),
                        result.getString("nombre"),
                        result.getString("provincia"),
                        result.getString("ciudad"),
                        result.getString("direccion"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return institucion;
    }
}
