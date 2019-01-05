package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogClientController implements Initializable {

    private Database database;

    @FXML TextField loginTextField;
    @FXML PasswordField passwordField;
    @FXML CheckBox rememberMeCheckBox;
    @FXML Button loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();

        loginButton.setDisable(true);

        loginTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(loginTextField.getText().isEmpty() || passwordField.getText().isEmpty());
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(loginTextField.getText().isEmpty() || passwordField.getText().isEmpty());
        });
    }

    @FXML
    public void onLoginButtonClick(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if (database.login(loginTextField.getText(), passwordField.getText())) {
            alert.setTitle("Zalogowano");
            alert.setContentText("Poprawne dane logowania");
            onLoginButtonClickToClientMenu(event);
        } else {
            alert.setTitle("Error");
            alert.setContentText("Zle dane logowania");
        }

        alert.showAndWait();
    }

    @FXML
    private void onLoginButtonClickToClientMenu (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CinemaProject/client/ClientLoggedMenu.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        MainMenuController.backToClientMainMenu(event);
    }


}
