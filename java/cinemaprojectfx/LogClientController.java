package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    public void onLoginButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if (database.login(loginTextField.getText(), passwordField.getText())) {
            alert.setTitle("Zalogowano");
            alert.setContentText("Poprawne dane logowania");

        } else {
            alert.setTitle("Error");
            alert.setContentText("Zle dane logowania");
        }

        alert.showAndWait();
    }

    @FXML
    public void onBackButtonClick(ActionEvent event) {

    }


}
