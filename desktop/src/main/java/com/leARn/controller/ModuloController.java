package main.java.com.leARn.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.com.leARn.config.Database;
import main.java.com.leARn.controller.dto.CursoDto;
import main.java.com.leARn.controller.dto.InstitucionDto;
import main.java.com.leARn.controller.dto.ModuloDto;
import main.java.com.leARn.model.Curso;
import main.java.com.leARn.model.Modulo;
import main.java.com.leARn.model.Institucion;
import main.java.com.leARn.service.CursoService;
import main.java.com.leARn.service.InstitucionService;
import main.java.com.leARn.utils.AlertMessage;


public class ModuloController implements Initializable {

    @FXML
    private AnchorPane modulos_panel;

    @FXML
    private AnchorPane modulos_filtros;

    @FXML
    private TextField modulos_filtroNombre;

    @FXML
    private AnchorPane modulos_tablaVista;

    @FXML
    private TableView<Modulo> modulos_tabla;

    @FXML
    private TableColumn<Modulo, String> modulos_tablaCurso;

    @FXML
    private TableColumn<Modulo, String> modulos_tablaNombre;

    @FXML
    private TableColumn<Modulo, String> modulos_tablaDescripcion;

    @FXML
    private Button modulos_agregarBtn;

    @FXML
    private Button modulos_editarBtn;


    // --- AGREGAR MODULO ---

    @FXML
    private TextField nuevoModulo_nombre;

    @FXML
    private TextField nuevoModulo_descripcion;

    @FXML
    private Button nuevoModulo_crearBtn;

    @FXML
    private Button nuevoModulo_cancelarBtn;

    @FXML
    private ComboBox<Curso> nuevoModulo_curso;

    // --- EDITAR MODULO ---

    @FXML
    private TextField editarModulo_nombre;

    @FXML
    private TextField editarModulo_descripcion;

    @FXML
    private Button editarModulo_editarBtn;

    @FXML
    private Button editarModulo_cancelarBtn;

    @FXML
    private ComboBox<Curso> editarModulo_curso;

    // --- ELIMINAR MODULO ---

    @FXML
    private Button modulos_eliminarBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private CursoService cursoService;
    private InstitucionService institucionService;

    private AlertMessage alert = new AlertMessage();

    List<Curso> cursos = getCursos();
    ObservableList<Curso> cursoObservableList = FXCollections.observableArrayList(cursos);

    public void switchToModulos(boolean value) {
        try {
            System.out.println("ver Modulos");
            modulos_panel.setVisible(value);
            modulos_panel.setManaged(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Modulo> getModulos() {
        ObservableList<Modulo> list = FXCollections.observableArrayList();
        System.out.println("ver Modulos 1");
        String query = "SELECT * FROM modulo";

        Institucion institucion;
        Curso curso = null;
        Modulo modulo;

        try {
            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            list.clear();

            if (result.next()) {
                while(result.next()) {
                    
                String query2 = "SELECT * FROM curso WHERE id = ?";

                PreparedStatement prepare2 = connect.prepareStatement(query2);
                prepare2.setInt(1, result.getInt("curso_id"));
                ResultSet result2 = prepare2.executeQuery();
                if (result2.next()) {

                    int institucionId = result2.getInt("institucion_id");

                    String query3 = "SELECT * FROM institucion WHERE id = ?";

                    PreparedStatement prepare3 = connect.prepareStatement(query3);
                    prepare3.setInt(1, institucionId);
                    ResultSet result3 = prepare3.executeQuery();

                    if (result3.next()) {
                        institucion = new Institucion(result3.getInt("id"),
                                result3.getString("nombre"),
                                result3.getString("provincia"),
                                result3.getString("ciudad"),
                                result3.getString("direccion"));

                        curso = new Curso(result2.getInt("id"),
                                result2.getString("nombre"),
                                result2.getString("descripcion"),
                                result2.getBoolean("esPublico"),
                                result2.getInt("institucion_id"),
                                result2.getString("codigo"),
                                institucion);


                    }

                    modulo = new Modulo(result.getInt("id"),
                            result.getString("nombre"),
                            result.getString("descripcion"),
                            curso);

                    list.add(modulo);
                }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    private ObservableList<Modulo> moduloList;

    public void displayModulos() {
        try {
            moduloList = getModulos();

            if (modulos_tablaNombre != null) {
                modulos_tablaCurso.setCellValueFactory(new PropertyValueFactory<>("cursoNombre"));
                modulos_tablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                modulos_tablaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

                modulos_tabla.setItems(moduloList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   CREAR MODULO
     *
     */

    public void displayFormCreateModulo() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/formCrearModulo.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createModulo() {
        try {

            String query = "INSERT INTO modulo (nombre, descripcion, curso_id) VALUES (?, ?, ?)";

            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);

            prepare.setString(1, nuevoModulo_nombre.getText());
            prepare.setString(2, nuevoModulo_descripcion.getText());
            prepare.setString(3, String.valueOf(nuevoModulo_curso.getSelectionModel().getSelectedItem().getId()));
            prepare.executeUpdate();

            alert.successMessage("Modulo creado correctamente!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   EDITAR MODULO
     *
     */

    public void displayFormUpdateModulo() {
        try {
            Modulo modulo = modulos_tabla.getSelectionModel().getSelectedItem();
            int num = modulos_tabla.getSelectionModel().getSelectedIndex();

            if (modulo != null) {
                if ((num - 1) < -1) {
                    alert.errorMessage("Debe seleccionar la fila a editar");
                    return;
                } else {


                    ModuloDto.id = modulo.getId();
                    ModuloDto.nombre = modulo.getNombre();
                    ModuloDto.descripcion = modulo.getDescripcion();
                    ModuloDto.curso = modulo.getCurso();

                    Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/formEditarModulo.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));

                    stage.show();
                }
            } else {
                alert.errorMessage("Debe seleccionar la fila a editar");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editModulo() {
        try {
            if (alert.confirmMessage("Está seguro de que desea editar este Modulo?")) {

                String query = "UPDATE modulo SET nombre = '" + editarModulo_nombre.getText() + "', "
                        + "descripcion = '" + editarModulo_descripcion.getText() + "', "
                        + "curso_id = " + editarModulo_curso.getSelectionModel().getSelectedItem() + " "
                        + "WHERE id = " + ModuloDto.id + "";

                System.out.println(query);

                connect = Database.connectDB();
                prepare = connect.prepareStatement(query);

                prepare.executeUpdate();

                alert.successMessage("Modulo editado correctamente!");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   ELIMINAR MODULO
     *
     */

    public void displayDeleteModulo() {
        try {
            Modulo modulo = modulos_tabla.getSelectionModel().getSelectedItem();
            int num = modulos_tabla.getSelectionModel().getSelectedIndex();

            if (modulo != null) {
                if ((num - 1) < -1) {
                    alert.errorMessage("Debe seleccionar la fila a eliminar");
                    return;
                } else {

                }
            } else {
                alert.errorMessage("Debe seleccionar la fila a eliminar");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteModulo() {
        try {
            if (alert.confirmMessage("Está seguro de que desea eliminar este Modulo?")) {

                alert.successMessage("Modulo eliminado correctamente!");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Curso> getCursos() {
        ObservableList<Curso> list = FXCollections.observableArrayList();

        String query = "SELECT * FROM curso";

        Institucion institucion = null;
        Curso curso;
        Modulo modulo;

        try {
            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            list.clear();

            if (result.next()) {
                int institucionId = result.getInt("institucion_id");

                String query2 = "SELECT * FROM institucion WHERE id = ?";

                PreparedStatement prepare2 = connect.prepareStatement(query2);
                prepare2.setInt(1, institucionId);
                ResultSet result2 = prepare2.executeQuery();

                if (result2.next()) {
                    institucion = new Institucion(result2.getInt("id"),
                            result2.getString("nombre"),
                            result2.getString("provincia"),
                            result2.getString("ciudad"),
                            result2.getString("direccion"));
                }

                do {
                    curso = new Curso(result.getInt("id"),
                            result.getString("nombre"),
                            result.getString("descripcion"),
                            result.getBoolean("esPublico"),
                            result.getInt("institucion_id"),
                            result.getString("codigo"),
                            institucion);
                    list.add(curso);
                } while (result.next());
            }

            System.out.println("traigo Cursos");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public void setData() {
        try {
            String query = "SELECT * FROM modulo WHERE id = " + ModuloDto.id + ";";

            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            if (result.next()) {
                editarModulo_nombre.setText(result.getString("nombre"));
                editarModulo_descripcion.setText(result.getString("descripcion"));
                editarModulo_curso.getSelectionModel().select(cursoService.getCursoById(result.getInt("curso_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (nuevoModulo_curso != null) {
            nuevoModulo_curso.setItems(cursoObservableList);
            nuevoModulo_curso.getSelectionModel().select(0);
        }

        if (editarModulo_curso != null) {
            editarModulo_curso.setItems(cursoObservableList);
            editarModulo_curso.getSelectionModel().select(0);
        }
        setData();
        displayModulos();
    }
}
