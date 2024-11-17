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
import main.java.com.leARn.config.Database;
import main.java.com.leARn.model.Institucion;
import main.java.com.leARn.model.Modulo;
import main.java.com.leARn.utils.AlertMessage;
import main.java.com.leARn.model.Curso;
import main.java.com.leARn.controller.dto.InstitucionDto;

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

    private ObservableList<String> provincias = FXCollections.observableArrayList("Cordoba", "Mendoza", "Santa Fe");
    private ObservableList<String> ciudades = FXCollections.observableArrayList("Cordoba", "Mendoza", "Rosario");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (nuevaInstitucion_provincia != null) {
            nuevaInstitucion_provincia.setItems(provincias);
            nuevaInstitucion_provincia.getSelectionModel().select(0);
        }

        if (editarInstitucion_provincia != null) {
            editarInstitucion_provincia.setItems(provincias);
            editarInstitucion_provincia.getSelectionModel().select(0);
        }

        if (nuevaInstitucion_ciudad != null) {
            nuevaInstitucion_ciudad.setItems(ciudades);
            nuevaInstitucion_ciudad.getSelectionModel().select(0);
        }

        if (editarInstitucion_ciudad != null) {
            editarInstitucion_ciudad.setItems(ciudades);
            editarInstitucion_ciudad.getSelectionModel().select(0);
        }

        setData();
        getInstituciones();
        displayInstituciones();

    }



    public void switchToInstituciones(boolean value) {
        try {
            System.out.println("ver instituciones");
            instituciones_panel.setVisible(value);
            instituciones_panel.setManaged(value);
            displayInstituciones();
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

            String query = "INSERT INTO institucion (nombre, provincia, ciudad, direccion) VALUES (?, ?, ?, ?)";

            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);

            prepare.setString(1, nuevaInstitucion_nombre.getText());
            prepare.setString(2, nuevaInstitucion_provincia.getSelectionModel().getSelectedItem());
            prepare.setString(3, nuevaInstitucion_ciudad.getSelectionModel().getSelectedItem());
            prepare.setString(4, nuevaInstitucion_direccion.getText());

            prepare.executeUpdate();

            this.getInstituciones();
            this.displayInstituciones();

            alert.successMessage("Institución creada correctamente!");
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

                    InstitucionDto.id = institucion.getId();
                    InstitucionDto.nombre = institucion.getNombre();
                    InstitucionDto.provincia = institucion.getProvincia();
                    InstitucionDto.ciudad = institucion.getCiudad();
                    InstitucionDto.direccion = institucion.getDireccion();

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

                String query = "UPDATE institucion SET nombre = '" + editarInstitucion_nombre.getText() + "', "
                        + "provincia = '" + editarInstitucion_provincia.getSelectionModel().getSelectedItem() + "', "
                        + "ciudad = " + editarInstitucion_ciudad.getSelectionModel().getSelectedItem() + ", "
                        + "direccion = " + editarInstitucion_direccion.getText() + " "
                        + "WHERE id = " + InstitucionDto.id + "";

                System.out.println(query);

                connect = Database.connectDB();
                prepare = connect.prepareStatement(query);

                prepare.executeUpdate();

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

    public void setData() {
        try {
            String query = "SELECT * FROM institucion WHERE id = " + InstitucionDto.id + ";";

            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            if (result.next()) {
                editarInstitucion_nombre.setText(result.getString("nombre"));
                editarInstitucion_provincia.getSelectionModel().select(result.getString("provincia"));
                editarInstitucion_ciudad.getSelectionModel().select(result.getString("ciudad"));
                editarInstitucion_direccion.setText(result.getString("direccion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

