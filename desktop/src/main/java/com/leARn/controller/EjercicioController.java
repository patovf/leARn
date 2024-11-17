package main.java.com.leARn.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
import main.java.com.leARn.controller.dto.EjercicioDto;
import main.java.com.leARn.controller.dto.InstitucionDto;
import main.java.com.leARn.model.*;
import main.java.com.leARn.utils.AlertMessage;

import javax.xml.crypto.Data;


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

    List<Modulo> modulos = getModulos();
    ObservableList<Modulo> moduloObservableList = FXCollections.observableArrayList(modulos);
    private ObservableList<Dificultad> dificultadList = FXCollections.observableArrayList(Dificultad.values());
    private ObservableList<TipoDeEjercicio> tipoEjercicioList = FXCollections.observableArrayList(TipoDeEjercicio.values());


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
        System.out.println("ver Modulos 1");
        String query = "SELECT * FROM ejercicio";

        Institucion institucion;
        Curso curso = null;
        Modulo modulo;
        Ejercicio ejercicio;

        try {
            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            list.clear();

            if (result.next()) {
                while(result.next()) {
                    String query4 = "SELECT * FROM modulo WHERE id = ?";

                    PreparedStatement prepare4 = connect.prepareStatement(query4);
                    prepare4.setInt(1, result.getInt("modulo_id"));
                    ResultSet result4 = prepare4.executeQuery();

                    if (result4.next()) {

                    String query2 = "SELECT * FROM curso WHERE id = ?";

                    PreparedStatement prepare2 = connect.prepareStatement(query2);
                    prepare2.setInt(1, result4.getInt("curso_id"));
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

                            modulo = new Modulo(result.getInt("id"),
                                    result.getString("nombre"),
                                    result.getString("descripcion"),
                                    curso);

                            ejercicio = new Ejercicio(result.getInt("id"),
                                    result.getString("nombre"),
                                    result.getString("descripcion"),
                                    modulo,
                                    result.getString("tipo_de_ejercicio"),
                                    result.getString("dificultad"));

                            list.add(ejercicio);
                        }


                    }
                }
                }
            }

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

            String query = "INSERT INTO ejercicio (nombre, descripcion, dificultad, tipo_de_ejercicio, modulo_id) VALUES (?, ?, ?, ?, ?)";

            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);


System.out.println(nuevoEjercicio_modulo.getSelectionModel().getSelectedItem().getId());

            prepare.setString(1, nuevoEjercicio_nombre.getText());
            prepare.setString(2, nuevoEjercicio_descripcion.getText());
            prepare.setString(3, String.valueOf(nuevoEjercicio_dificultad.getSelectionModel().getSelectedItem()));
            prepare.setString(4, String.valueOf(nuevoEjercicio_tipoEjercicio.getSelectionModel().getSelectedItem()));
            prepare.setInt(5, nuevoEjercicio_modulo.getSelectionModel().getSelectedItem().getId());

            prepare.executeUpdate();

            this.getEjercicios();
            this.displayEjercicios();

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


                    EjercicioDto.id = ejercicio.getId();
                    EjercicioDto.nombre = ejercicio.getNombre();
                    EjercicioDto.descripcion = ejercicio.getDescripcion();
                    EjercicioDto.tipoDeEjercicio = ejercicio.getTipoDeEjercicio();
                    EjercicioDto.dificultad = ejercicio.getDificultad();
                    EjercicioDto.modulo = ejercicio.getModulo();

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

                String query = "UPDATE ejercicio SET nombre = '" + editarEjercicio_nombre.getText() + "', "
                        + "descripcion = '" + editarEjercicio_descripcion.getText() + "', "
                        + "dificultad = " + editarEjercicio_dificultad.getSelectionModel().getSelectedItem() + ", "
                        + "tipo_ejercicio = " + editarEjercicio_tipoEjercicio.getSelectionModel().getSelectedItem() + ", "
                        + "modulo_id = " + editarEjercicio_modulo.getSelectionModel().getSelectedItem().getId() + " "
                        + "WHERE id = " + EjercicioDto.id + "";

                System.out.println(query);

                connect = Database.connectDB();
                prepare = connect.prepareStatement(query);

                prepare.executeUpdate();

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

    public void setData() {
        try {
            String query = "SELECT * FROM ejercicio WHERE id = " + EjercicioDto.id + ";";

            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            if (result.next()) {
                editarEjercicio_nombre.setText(result.getString("nombre"));
                editarEjercicio_descripcion.setText(result.getString("descripcion"));
                editarEjercicio_dificultad.getSelectionModel().select(Dificultad.valueOf(result.getString("dificultad")));
                editarEjercicio_tipoEjercicio.getSelectionModel().select(TipoDeEjercicio.valueOf(result.getString("tipo_de_ejercicio")));
                //editarEjercicio_modulo.getSelectionModel().select(getModuloById(result.getInt("modulo_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (nuevoEjercicio_dificultad != null) {
            nuevoEjercicio_dificultad.setItems(dificultadList);
            nuevoEjercicio_dificultad.getSelectionModel().select(0);
        }

        if (editarEjercicio_dificultad != null) {
            editarEjercicio_dificultad.setItems(dificultadList);
            editarEjercicio_dificultad.getSelectionModel().select(0);
        }

        if (nuevoEjercicio_tipoEjercicio != null) {
            nuevoEjercicio_tipoEjercicio.setItems(tipoEjercicioList);
            nuevoEjercicio_tipoEjercicio.getSelectionModel().select(0);
        }

        if (editarEjercicio_tipoEjercicio != null) {
            editarEjercicio_tipoEjercicio.setItems(tipoEjercicioList);
            editarEjercicio_tipoEjercicio.getSelectionModel().select(0);
        }

        if (nuevoEjercicio_modulo != null) {
            nuevoEjercicio_modulo.setItems(moduloObservableList);
            nuevoEjercicio_modulo.getSelectionModel().select(0);
        }

        if (editarEjercicio_modulo != null) {
            editarEjercicio_modulo.setItems(moduloObservableList);
            editarEjercicio_modulo.getSelectionModel().select(0);
        }

        setData();
        displayEjercicios();
    }
}

