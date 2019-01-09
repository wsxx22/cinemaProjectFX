package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import cinemaprojectfx.hibernate.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class UserSettingsController implements Initializable {

    private Database database;
    private User user; // null

    @FXML Button changePasswordButton;
    @FXML Button changeEmailButton;
    @FXML TextField changeEmailTextField;
    @FXML Label mailLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();


//        Platform.runLater(() -> {
//            mailLabel.setText(user.getEmail());
//        });

    }

    @FXML
    public void onChangeEmailButtonClick(ActionEvent event) {


            if (database.isExistEmail(changeEmailTextField.getText())) {
                JOptionPane.showMessageDialog(null, "Ten email już istnieje, podaj inny",
                        "Zmiana email", JOptionPane.WARNING_MESSAGE);
            } else {
            database.changeClientEmail(changeEmailTextField.getText());
                user.setEmail(changeEmailTextField.getText());
                JOptionPane.showMessageDialog(null, "Email zmieniony", "Zmiana email",
                        JOptionPane.INFORMATION_MESSAGE);
            }

    }

    @FXML
    public void onDeleteAccountButtonClick(ActionEvent event) {

        int deleteAccount = JOptionPane.showConfirmDialog(null, "Czy napewno chcesz usunąć konto?", "Usuń konto",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (deleteAccount == JOptionPane.YES_OPTION) {
            String password = JOptionPane.showInputDialog(null, "Podaj hasło:",
                    "Potwierdzenie usunięcia konta", JOptionPane.INFORMATION_MESSAGE).trim();

        }

    }

    @FXML
    public void onChangePasswordButtonClick(ActionEvent event) {

    }

    public void setUser(User user) {
        this.user = user;
    }
}
