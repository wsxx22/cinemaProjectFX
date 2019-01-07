package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import cinemaprojectfx.hibernate.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

public class UserSettings {

    private Database database;

    @FXML Button changePasswordButton;
    @FXML Button changeEmailButton;
    @FXML TextField changeEmailTextField;

    @FXML
    public void onChangeEmailButtonClick(ActionEvent event) throws IOException {


        System.out.println(changeEmailTextField.getText());

        if (database.isExistEmail(changeEmailTextField.getText())) {
            JOptionPane.showMessageDialog(null,"Ten email już istnieje, podaj inny",
                    "Zmiana email", JOptionPane.WARNING_MESSAGE);
        } else {
            System.out.println("email zmieniony");
        }

    }

    @FXML
    public void onDeleteAccountButtonClick(ActionEvent event) {
    }

    @FXML
    public void onChangePasswordButtonClick(ActionEvent event) {
    }
}
