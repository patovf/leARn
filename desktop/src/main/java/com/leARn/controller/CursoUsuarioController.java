package main.java.com.leARn.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
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
import main.java.com.leARn.model.Ejercicio;
import main.java.com.leARn.model.Institucion;
import main.java.com.leARn.model.Modulo;
import main.java.com.leARn.service.CursoService;
import main.java.com.leARn.service.InstitucionService;
import main.java.com.leARn.utils.AlertMessage;
import main.java.com.leARn.model.Curso;


public class CursoUsuarioController implements Initializable {

    @FXML
    private AnchorPane cursosUsuario_panel;

    @FXML
    private AnchorPane cursos_filtros;

    @FXML
    private TextField cursos_filtroNombre;

    @FXML
    private AnchorPane cursos_tablaVista;

    @FXML
    private TableView<Curso> cursos_tabla;

    @FXML
    private TableColumn<Curso, String> cursos_tablaInstitucion;

    @FXML
    private TableColumn<Curso, String> cursos_tablaNombre;

    @FXML
    private TableColumn<Curso, String> cursos_tablaDescripcion;

    @FXML
    private TableColumn<Curso, String> cursos_tablaPublico;

    @FXML
    private Button cursos_agregarBtn;

    @FXML
    private Button cursos_inscrBtn;


    // --- AGREGAR CURSO ---

    @FXML
    private TextField nuevoCurso_nombre;

    @FXML
    private TextField nuevoCurso_descripcion;

    @FXML
    private Button nuevoCurso_crearBtn;

    @FXML
    private Button nuevoCurso_cancelarBtn;

    @FXML
    private ComboBox<String> nuevoCurso_esPublico;

    @FXML
    private ComboBox<Institucion> nuevoCurso_institucion;

    // --- EDITAR CURSO ---

    @FXML
    private TextField editarCurso_nombre;

    @FXML
    private TextField editarCurso_descripcion;

    @FXML
    private Button editarCurso_editarBtn;

    @FXML
    private Button editarCurso_cancelarBtn;

    @FXML
    private ComboBox<String> editarCurso_esPublico;

    @FXML
    private ComboBox<Institucion> editarCurso_institucion;

    // --- ELIMINAR CURSO ---

    @FXML
    private Button cursos_eliminarBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private AlertMessage alert = new AlertMessage();

    private CursoService cursoService;
    private InstitucionService institucionService;

    public void switchToCursos(boolean value) {
        try {
            cursosUsuario_panel.setVisible(value);
            cursosUsuario_panel.setManaged(value);
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

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    private ObservableList<Curso> cursoList;

    public void displayCursos() {
        try {
            cursoList = getCursos();

            if (cursos_tablaNombre != null) {
                cursos_tablaInstitucion.setCellValueFactory(new PropertyValueFactory<>("institucionNombre"));
                cursos_tablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                cursos_tablaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                cursos_tablaPublico.setCellValueFactory(new PropertyValueFactory<>("cursoEsPublico"));

                cursos_tabla.setItems(cursoList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   CREAR CURSO
     *
     */

    public void displayFormCreateCurso() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/formCrearCurso.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createCurso() {
        try {

            alert.successMessage("Curso creado correctamente!");

//            cursos_tabla.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   EDITAR CURSO
     *
     */

    public void displayFormUpdateCurso() {
        try {
            Curso curso = cursos_tabla.getSelectionModel().getSelectedItem();
            int num = cursos_tabla.getSelectionModel().getSelectedIndex();

            if (curso != null) {
                if ((num - 1) < -1) {
                    alert.errorMessage("Debe seleccionar la fila a editar");
                    return;
                } else {

                    Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/formEditarCurso.fxml"));
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

    public void editCurso() {
        try {
            if (alert.confirmMessage("Está seguro de que desea editar este Curso?")) {

                alert.successMessage("Curso editado correctamente!");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   ELIMINAR CURSO
     *
     */

    public void displayInscrCurso() {
        try {
            Curso curso = cursos_tabla.getSelectionModel().getSelectedItem();
            int num = cursos_tabla.getSelectionModel().getSelectedIndex();

            if (curso != null) {
                if ((num - 1) < -1) {
                    alert.errorMessage("Debe seleccionar el curso");
                    return;
                } else {
                    System.out.println("Inscribiendo a curso");

                    System.out.println(curso.getNombre());
                    inscrCurso(curso.getCodigo());

                }
            } else {
                alert.errorMessage("Debe seleccionar el curso");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inscrCurso(String codigo) {
        try {
            if (alert.confirmMessage("Está seguro de que desea inscribirse a este curso?")) {
                String query = "SELECT * FROM curso WHERE codigo = ?";

                connect = Database.connectDB();
                prepare = connect.prepareStatement(query);

                prepare.setString(1, codigo);

                result = prepare.executeQuery();

                if (result.next()) {
                    int id = result.getInt("id");

                    System.out.println("curso id " + id);
                    String query2 = "INSERT INTO curso_usuario (id, curso_id, usuario_id) VALUES (?, ?, ?)";

                    PreparedStatement prepare2 = connect.prepareStatement(query2);
                    prepare2.setInt(1, 1);
                    prepare2.setInt(2, id);
                    prepare2.setInt(3, 1);

                    prepare2.executeUpdate();

                    alert.successMessage("Inscripción realizada!");
                }

            }

            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        displayCursos();
    }
}
