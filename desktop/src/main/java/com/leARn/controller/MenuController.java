package main.java.com.leARn.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    @FXML
    private AnchorPane ventanaApp;

    // EMPIEZAN SECCIONES

    @FXML
    private AnchorPane inicio_panel;

    @FXML
    private AnchorPane instituciones_panel;

    @FXML
    private AnchorPane cursos_panel;

    @FXML
    private AnchorPane modulos_panel;

    @FXML
    private AnchorPane ejercicios_panel;

    // TERMINAN SECCIONES

    @FXML
    private AnchorPane menu_barraSuperior;

    @FXML
    private ImageView menu_barraLogo;

    @FXML
    private Button menu_cerrarApp;

    @FXML
    private Button menu_minimizarApp;

    @FXML
    private Label menu_barraTitulo;

    @FXML
    private AnchorPane menu_panel;

    @FXML
    private ImageView menu_logo;

    @FXML
    private Label menu_bienvenido;

    @FXML
    private Label menu_nombreUsuario;

    @FXML
    private Button menu_cerrarSesion;

    @FXML
    private Line menu_separador;

    @FXML
    private Button menu_inicioBtn;

    @FXML
    private Button menu_institucionesBtn;

    @FXML
    private Button menu_cursosBtn;

    @FXML
    private Button menu_modulosBtn;

    @FXML
    private Button menu_ejerciciosBtn;

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) ventanaApp.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void switchForm(ActionEvent event) {

        // PANEL INICIO

        FXMLLoader inicioLoader = new FXMLLoader();
        inicioLoader.setLocation(getClass().getResource("/main/resources/view/inicio.fxml"));
        try {
            Parent inicioRoot = inicioLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        InicioController InicioController = inicioLoader.getController();

        // PANEL INSTITUCIONES

        FXMLLoader institucionLoader = new FXMLLoader();
        institucionLoader.setLocation(getClass().getResource("/main/resources/view/instituciones.fxml"));
        try {
            Parent institucionRoot = institucionLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        InstitucionController institucionController = institucionLoader.getController();

        // PANEL CURSOS

        FXMLLoader cursosLoader = new FXMLLoader();
        cursosLoader.setLocation(getClass().getResource("/main/resources/view/cursos.fxml"));
        try {
            Parent cursosRoot = cursosLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        CursoController cursoController = cursosLoader.getController();

        // PANEL MODULOS

        FXMLLoader modulosLoader = new FXMLLoader();
        modulosLoader.setLocation(getClass().getResource("/main/resources/view/modulos.fxml"));
        try {
            Parent modulosRoot = modulosLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModuloController moduloController = modulosLoader.getController();

        // PANEL EJERCICIOS

        FXMLLoader ejerciciosLoader = new FXMLLoader();
        ejerciciosLoader.setLocation(getClass().getResource("/main/resources/view/ejercicios.fxml"));
        try {
            Parent ejercicioRoot = ejerciciosLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        EjercicioController ejercicioController = ejerciciosLoader.getController();

        // MUESTRA DE PANELES

        if (event.getSource() == menu_inicioBtn) {
            inicio_panel.setVisible(true);
            instituciones_panel.setVisible(false);
            cursos_panel.setVisible(false);
            modulos_panel.setVisible(false);
            ejercicios_panel.setVisible(false);
        } else if (event.getSource() == menu_institucionesBtn) {
            inicio_panel.setVisible(false);
            instituciones_panel.setVisible(true);
            cursos_panel.setVisible(false);
            modulos_panel.setVisible(false);
            ejercicios_panel.setVisible(false);
        } else if (event.getSource() == menu_cursosBtn) {
            inicio_panel.setVisible(false);
            instituciones_panel.setVisible(false);
            cursos_panel.setVisible(true);
            modulos_panel.setVisible(false);
            ejercicios_panel.setVisible(false);
        } else if (event.getSource() == menu_modulosBtn) {
            inicio_panel.setVisible(false);
            instituciones_panel.setVisible(false);
            cursos_panel.setVisible(false);
            modulos_panel.setVisible(true);
            ejercicios_panel.setVisible(false);
        } else if (event.getSource() == menu_ejerciciosBtn) {
            inicio_panel.setVisible(false);
            instituciones_panel.setVisible(false);
            cursos_panel.setVisible(false);
            modulos_panel.setVisible(false);
            ejercicios_panel.setVisible(true);
        }
    }
}