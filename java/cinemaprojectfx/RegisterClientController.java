package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import cinemaprojectfx.hibernate.User;
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

    @FXML TextField usernameRegisterTextField;
    @FXML PasswordField passwordRegisterField;
    @FXML TextField emailRegisterField;
    @FXML Button registerButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();

        registerButton.setDisable(true);

        registerButtonDisable(usernameRegisterTextField);
        registerButtonDisable(emailRegisterField);
        registerButtonDisable(passwordRegisterField);

    }

    private void registerButtonDisable (TextField actualTextField) {

        actualTextField.textProperty().addListener(((observable, oldValue, newValue) ->
                registerButton.setDisable(usernameRegisterTextField.getText().isEmpty()
                        | passwordRegisterField.getText().isEmpty()
                        | emailRegisterField.getText().isEmpty())));
    }

    public void onRegisterButtonClick(ActionEvent actionEvent) throws IOException {

        Alert loginAlert = new Alert(Alert.AlertType.NONE);

        if (database.registerNewClient(
                usernameRegisterTextField.getText(),
                passwordRegisterField.getText(),
                emailRegisterField.getText())) {
            loginAlert.setAlertType(Alert.AlertType.WARNING);
            loginAlert.setTitle("Error");
            loginAlert.setContentText("Takie konto już istnieje");
        } else {
            loginAlert.setAlertType(Alert.AlertType.INFORMATION);
            loginAlert.setTitle("Konto utworzone");
            loginAlert.setContentText("Teraz możesz się zalogować");
        }

        backToClientMainMenu(actionEvent);

    }

    public void backMenu(ActionEvent actionEvent) throws IOException {
        backToClientMainMenu(actionEvent);

    }

    private void backToClientMainMenu (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CinemaProject/client/ClientMainMenu.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }


}
