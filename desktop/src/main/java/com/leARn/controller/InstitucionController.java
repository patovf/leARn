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
import main.java.com.leARn.model.Institucion;
import main.java.com.leARn.model.Modulo;
import main.java.com.leARn.utils.AlertMessage;
import main.java.com.leARn.model.Curso;


public class InstitucionController implements Initializable {

    @FXML
    private AnchorPane instituciones_panel;

    @FXML
    private AnchorPane instituciones_filtros;

    @FXML
    private TextField instituciones_filtroNombre;

    @FXML
    private AnchorPane instituciones_tablaVista;

    @FXML
    private TableView<Institucion> instituciones_tabla;

    @FXML
    private TableColumn<Institucion, String> instituciones_tablaNombre;

    @FXML
    private TableColumn<Institucion, String> instituciones_tablaProvincia;

    @FXML
    private TableColumn<Institucion, String> instituciones_tablaCiudad;

    @FXML
    private TableColumn<Institucion, String> instituciones_tablaDireccion;

    @FXML
    private Button instituciones_agregarBtn;

    @FXML
    private Button instituciones_editarBtn;


    // --- AGREGAR INSTITUCION ---

    @FXML
    private TextField nuevaInstitucion_nombre;

    @FXML
    private TextField nuevaInstitucion_direccion;

    @FXML
    private Button nuevaInstitucion_crearBtn;

    @FXML
    private Button nuevaInstitucion_cancelarBtn;

    @FXML
    private ComboBox<String> nuevaInstitucion_provincia;

    @FXML
    private ComboBox<String> nuevaInstitucion_ciudad;

    // --- EDITAR INSTITUCION ---

    @FXML
    private TextField editarInstitucion_nombre;

    @FXML
    private TextField editarInstitucion_direccion;

    @FXML
    private Button editarInstitucion_editarBtn;

    @FXML
    private Button editarInstitucion_cancelarBtn;

    @FXML
    private ComboBox<String> editarInstitucion_provincia;

    @FXML
    private ComboBox<String> editarInstitucion_ciudad;

    // --- ELIMINAR INSTITUCION ---

    @FXML
    private Button instituciones_eliminarBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private AlertMessage alert = new AlertMessage();


    public void switchToInstituciones(boolean value) {
        try {
            System.out.println("ver instituciones");
            instituciones_panel.setVisible(value);
            instituciones_panel.setManaged(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Institucion> getInstituciones() {
        ObservableList<Institucion> list = FXCollections.observableArrayList();

        Institucion institucion;

        try {
            institucion = new Institucion("Institucion 1", "Cordoba", "Rio Cuarto", "Av. Libertador 1234");

            list.add(institucion);

            System.out.println("traigo INSTITUCIONES");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    private ObservableList<Institucion> institucionList;

    public void displayInstituciones() {
        try {
            institucionList = getInstituciones();

            if (instituciones_tablaNombre != null) {
                instituciones_tablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                instituciones_tablaProvincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
                instituciones_tablaCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
                instituciones_tablaDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

                instituciones_tabla.setItems(institucionList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   CREAR INSTITUCION
     *
     */

    public void displayFormCreateInstitucion() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/formCrearInstitucion.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createInstitucion() {
        try {

            alert.successMessage("Institución creada correctamente!");

//            instituciones_tabla.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   EDITAR INSTITUCION
     *
     */

    public void displayFormUpdateInstitucion() {
        try {
            Institucion institucion = instituciones_tabla.getSelectionModel().getSelectedItem();
            int num = instituciones_tabla.getSelectionModel().getSelectedIndex();

            if (institucion != null) {
                if ((num - 1) < -1) {
                    alert.errorMessage("Debe seleccionar la fila a editar");
                    return;
                } else {

                    Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/formEditarInstitucion.fxml"));
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

    public void editInstitucion() {
        try {
            if (alert.confirmMessage("Está seguro de que desea editar esta institución?")) {

                alert.successMessage("Institución editada correctamente!");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     *   ELIMINAR INSTITUCION
     *
     */

    public void displayDeleteInstitucion() {
        try {
            Institucion institucion = instituciones_tabla.getSelectionModel().getSelectedItem();
            int num = instituciones_tabla.getSelectionModel().getSelectedIndex();

            if (institucion != null) {
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

    public void deleteInstitucion() {
        try {
            if (alert.confirmMessage("Está seguro de que desea eliminar esta institución?")) {

                alert.successMessage("Institución eliminada correctamente!");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayInstituciones();
    }
}

