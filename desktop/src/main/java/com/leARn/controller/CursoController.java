package main.java.com.leARn.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
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
import main.java.com.leARn.model.Ejercicio;
import main.java.com.leARn.model.Institucion;
import main.java.com.leARn.model.Modulo;
import main.java.com.leARn.service.CursoService;
import main.java.com.leARn.service.InstitucionService;
import main.java.com.leARn.utils.AlertMessage;
import main.java.com.leARn.model.Curso;


public class CursoController implements Initializable {

    @FXML
    private AnchorPane cursos_panel;

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
    private Button cursos_editarBtn;


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

    @FXML
    private TextField nuevoCurso_codigo;

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

    @FXML
    private TextField editarCurso_codigo;

    // --- ELIMINAR CURSO ---

    @FXML
    private Button cursos_eliminarBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private CursoService cursoService;
    private InstitucionService institucionService;

    private AlertMessage alert = new AlertMessage();

    List<Institucion> instituciones = getInstituciones();
    ObservableList<Institucion> institucionObservableList = FXCollections.observableArrayList(instituciones);
    private ObservableList<String> esPublico = FXCollections.observableArrayList("Si", "No");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (nuevoCurso_institucion != null) {
            nuevoCurso_institucion.setItems(institucionObservableList);
            nuevoCurso_institucion.getSelectionModel().select(0);
        }

        if (editarCurso_institucion != null) {
            editarCurso_institucion.setItems(institucionObservableList);
            editarCurso_institucion.getSelectionModel().select(0);
        }

        if (nuevoCurso_esPublico != null) {
            nuevoCurso_esPublico.setItems(esPublico);
            nuevoCurso_esPublico.getSelectionModel().select(0);
        }

        if (editarCurso_esPublico != null) {
            editarCurso_esPublico.setItems(esPublico);
            editarCurso_esPublico.getSelectionModel().select(0);
        }

        setData();
        displayCursos();
    }

    public void switchToCursos(boolean value) {
        try {
            System.out.println("ver Cursos");
            cursos_panel.setVisible(value);
            cursos_panel.setManaged(value);
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
            Institucion institucion;
            String query = "INSERT INTO curso (nombre, descripcion, institucion_id, esPublico, codigo) VALUES (?, ?, ?, ?, ?)";

            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);

            prepare.setString(1, nuevoCurso_nombre.getText());
            prepare.setString(2, nuevoCurso_descripcion.getText());
            prepare.setInt(3, nuevoCurso_institucion.getSelectionModel().getSelectedItem().getId());

            String esPublicoValue = nuevoCurso_esPublico.getSelectionModel().getSelectedItem();
            boolean esPublico = Objects.equals(esPublicoValue, "Si");

            prepare.setBoolean(4, esPublico);
            prepare.setString(5, nuevoCurso_codigo.getText());

            prepare.executeUpdate();

            this.getCursos();
            this.displayCursos();

            alert.successMessage("Curso creado correctamente!");
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

                    CursoDto.id = curso.getId();
                    CursoDto.nombre = curso.getNombre();
                    CursoDto.descripcion = curso.getDescripcion();
                    CursoDto.codigo = curso.getCodigo();
                    CursoDto.esPublico = curso.isEsPublico();
                    CursoDto.institucionId = curso.getInstitucionId();


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

                String query = "UPDATE curso SET nombre = '" + editarCurso_nombre.getText() + "', "
                        + "descripcion = '" + editarCurso_descripcion.getText() + "', "
                        + "esPublico = " + editarCurso_esPublico.getSelectionModel().getSelectedItem() + ", "
                        + "institucion_id = " + editarCurso_institucion.getSelectionModel().getSelectedItem().getId() + " "
                        + "WHERE id = " + CursoDto.id + "";

                System.out.println(query);

                connect = Database.connectDB();
                prepare = connect.prepareStatement(query);

                prepare.executeUpdate();

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

    public void displayDeleteCurso() {
        try {
            Curso curso = cursos_tabla.getSelectionModel().getSelectedItem();
            int num = cursos_tabla.getSelectionModel().getSelectedIndex();

            if (curso != null) {
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

    public void deleteCurso() {
        try {
            if (alert.confirmMessage("Está seguro de que desea eliminar este Curso?")) {

                alert.successMessage("Curso eliminado correctamente!");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ObservableList<Institucion> getInstituciones() {
        ObservableList<Institucion> list = FXCollections.observableArrayList();

        String query = "SELECT * FROM institucion";

        Institucion institucion;

        try {
            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            list.clear();


            if (result.next()) {
                do {
                    institucion = new Institucion(result.getInt("id"),
                            result.getString("nombre"),
                            result.getString("provincia"),
                            result.getString("ciudad"),
                            result.getString("direccion"));
                    list.add(institucion);
                } while (result.next());

            }
            System.out.println("traigo inst");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public void setData() {
        try {
            String query = "SELECT * FROM curso WHERE id = " + CursoDto.id + ";";

            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            if (result.next()) {
                editarCurso_nombre.setText(result.getString("nombre"));
                editarCurso_descripcion.setText(result.getString("descripcion"));
                editarCurso_esPublico.getSelectionModel().select(String.valueOf(result.getBoolean("esPublico")));
                //editarCurso_institucion.getSelectionModel().select(getInstitucionById(result.getInt("institucion_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
