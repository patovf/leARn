package main.java.com.leARn.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
import main.java.com.leARn.model.Curso;
import main.java.com.leARn.model.Modulo;
import main.java.com.leARn.model.Institucion;
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

    private AlertMessage alert = new AlertMessage();


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

        Institucion institucion;
        Curso curso;
        Modulo modulo;

        try {
            institucion = new Institucion("Institucion 1", "Cordoba", "Rio Cuarto", "Av. Libertador 1234");
            curso = new Curso("Curso 1", "Descripcion de Curso 1", true, institucion);
            modulo = new Modulo("Modulo 1", "Descripcion de Modulo 1", curso);
            list.add(modulo);

            System.out.println("traigo Modulos");

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

            alert.successMessage("Modulo creado correctamente!");

//            modulos_tabla.refresh();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayModulos();
    }
}
