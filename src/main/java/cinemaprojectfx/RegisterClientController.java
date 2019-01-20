package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterClientController implements Initializable {

    private Database database;

    @FXML TextField usernameTextField;
    @FXML PasswordField passwordField;
    @FXML TextField emailTextField;
    @FXML Button registerButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();

        registerButton.setDisable(true);

        registerButtonDisable(usernameTextField);
        registerButtonDisable(emailTextField);
        registerButtonDisable(passwordField);

    }

    private void registerButtonDisable (TextField actualTextField) {
        actualTextField.textProperty().addListener(((observable, oldValue, newValue) ->
                registerButton.setDisable(usernameTextField.getText().isEmpty()
                        || passwordField.getText().isEmpty()
                        || emailTextField.getText().isEmpty())));
    }

    public void onRegisterButtonClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.NONE);

        if (database.register(usernameTextField.getText(), passwordField.getText(), emailTextField.getText())) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Konto utworzone");
            alert.setContentText("Teraz możesz się zalogować");

            alert.showAndWait();

            backToClientMainMenu(actionEvent);
        } else {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Takie konto już istnieje");

            alert.showAndWait();
        }
    }

    public void backMenu(ActionEvent actionEvent) throws IOException {
        backToClientMainMenu(actionEvent);

    }

    private void backToClientMainMenu (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login_scene.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }


}
