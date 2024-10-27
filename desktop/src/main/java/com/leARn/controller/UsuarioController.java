package main.java.com.leARn.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.com.leARn.config.Database;

public class UsuarioController {

    @FXML
    private AnchorPane si_panel;

    // todo: averiguar sobre ruta de imagen
    @FXML
    private ImageView si_logo;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private TextField si_username;

    @FXML
    private Button si_loginBtn;

    @FXML
    private PasswordField si_password;

    // database
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    private double x = 0;
    private double y = 0;

    public void showAlert(AlertType type, String title, String headerText, String contentText) {
        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error", null, "Usuario/Contraseña incorrecto");
        } else {
            // TODO: descomentar para TP4
//            String selectUser = "SELECT * FROM usuario WHERE username = ? AND password = ?";

            // busco usuario
            try {

//                connect = Database.iniciarDatabase();
//
//                prepare = connect.prepareStatement(selectUser);
//                prepare.setString(1, si_username.getText());
//                prepare.setString(2, si_password.getText());
//
//                result = prepare.executeQuery();
//
//                if (result.next()) {

                List<String> usuariosHabilitados = new ArrayList<>();

                usuariosHabilitados.add("fernando.alonso");
                usuariosHabilitados.add("max.verstappen");
                usuariosHabilitados.add("franco.colapinto");

                System.out.println(si_username.getText());

                if (usuariosHabilitados.contains(si_username.getText())) {
                    showAlert(AlertType.INFORMATION, "Éxito", null, "Inicio de sesión exitoso!");

                    si_loginBtn.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/menu.fxml"));


                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();
                } else {
                    showAlert(AlertType.ERROR, "Error", null, "Usuario/Contraseña incorrecto");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}