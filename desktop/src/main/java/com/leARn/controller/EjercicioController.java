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
import main.java.com.leARn.model.*;
import main.java.com.leARn.utils.AlertMessage;


public class EjercicioController implements Initializable {

    @FXML
    private AnchorPane ejercicios_panel;

    @FXML
    private AnchorPane ejercicios_filtros;

    @FXML
    private TextField ejercicios_filtroNombre;

    @FXML
    private AnchorPane ejercicios_tablaVista;

    @FXML
    private TableView<Ejercicio> ejercicios_tabla;

    @FXML
    private TableColumn<Ejercicio, String> ejercicios_tablaModulo;

    @FXML
    private TableColumn<Ejercicio, String> ejercicios_tablaNombre;

    @FXML
    private TableColumn<Ejercicio, String> ejercicios_tablaDescripcion;

    @FXML
    private TableColumn<Ejercicio, String> ejercicios_tablaTipoEjercicio;

    @FXML
    private Button nuevoEjercicio_crearBtn;

    @FXML
    private Button nuevoEjercicio_cancelarBtn;

    // --- AGREGAR EJERCICIO ---

    @FXML
    private TextField nuevoEjercicio_nombre;

    @FXML
    private TextField nuevoEjercicio_descripcion;

    @FXML
    private ComboBox<Modulo> nuevoEjercicio_modulo;

    @FXML
    private ComboBox<TipoDeEjercicio> nuevoEjercicio_tipoEjercicio;

    @FXML
    private ComboBox<Dificultad> nuevoEjercicio_dificultad;

    // --- EDITAR EJERCICIO ---

    @FXML
    private TextField editarEjercicio_nombre;

    @FXML
    private TextField editarEjercicio_descripcion;

    @FXML
    private ComboBox<Modulo> editarEjercicio_modulo;

    @FXML
    private ComboBox<TipoDeEjercicio> editarEjercicio_tipoEjercicio;

    @FXML
    private ComboBox<Dificultad> editarEjercicio_dificultad;

    // --- ELIMINAR EJERCICIO ---

    @FXML
    private Button ejercicios_eliminarBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private AlertMessage alert = new AlertMessage();


    public void switchToEjercicios(boolean value) {
        try {
            System.out.println("ver Ejercicios");
            ejercicios_panel.setVisible(value);
            ejercicios_panel.setManaged(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Ejercicio> getEjercicios() {
        ObservableList<Ejercicio> list = FXCollections.observableArrayList();

        Institucion institucion;
        Curso curso;
        Modulo modulo;
        Ejercicio ejercicio;

        try {
            institucion = new Institucion("Institucion 1", "Cordoba", "Rio Cuarto", "Av. Libertador 1234");
            curso = new Curso("Curso 1", "Descripcion de Curso 1", true, institucion);
            modulo = new Modulo("Modulo 1", "Descripcion de Modulo 1", curso);
            ejercicio = new Ejercicio("Ejercicio 1", "Descripcion de Ejercicio 1", modulo, TipoDeEjercicio.SIMPLE, Dificultad.NORMAL);

            list.add(ejercicio);

            System.out.println("traigo Ejercicios");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    private ObservableList<Ejercicio> ejercicioList;

    public void displayEjercicios() {
        try {
            ejercicioList = getEjercicios();

            if (ejercicios_tablaNombre != null) {
                ejercicios_tablaModulo.setCellValueFactory(new PropertyValueFactory<>("moduloNombre"));
                ejercicios_tablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                ejercicios_tablaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                ejercicios_tablaTipoEjercicio.setCellValueFactory(new PropertyValueFactory<>("tipoEjercicioDisplayName"));

                ejercicios_tabla.setItems(ejercicioList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   CREAR EJERCICIO
     *
     */

    public void displayFormCreateEjercicio() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/formCrearEjercicio.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createEjercicio() {
        try {

            alert.successMessage("Ejercicio creado correctamente!");

//            ejercicios_tabla.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   EDITAR EJERCICIO
     *
     */

    public void displayFormUpdateEjercicio() {
        try {
            Ejercicio ejercicio = ejercicios_tabla.getSelectionModel().getSelectedItem();
            int num = ejercicios_tabla.getSelectionModel().getSelectedIndex();

            if (ejercicio != null) {
                if ((num - 1) < -1) {
                    alert.errorMessage("Debe seleccionar la fila a editar");
                    return;
                } else {

                    Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/formEditarEjercicio.fxml"));
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

    public void editEjercicio() {
        try {
            if (alert.confirmMessage("Está seguro de que desea editar este ejercicio?")) {

                alert.successMessage("Ejercicio editado correctamente!");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   ELIMINAR EJERCICIO
     *
     */

    public void displayDeleteEjercicio() {
        try {
            Ejercicio ejercicio = ejercicios_tabla.getSelectionModel().getSelectedItem();
            int num = ejercicios_tabla.getSelectionModel().getSelectedIndex();

            if (ejercicio != null) {
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

    public void deleteEjercicio() {
        try {
            if (alert.confirmMessage("Está seguro de que desea eliminar este ejercicio?")) {

                alert.successMessage("Ejercicio eliminado correctamente!");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayEjercicios();
    }
}

