package cinemaprojectfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientMainMenuController implements Initializable {

    @FXML
    public void onLoginClientButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CinemaProject/client/LoginClient.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onRegisterClientButtonClick(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("/fxml/CinemaProject/client/RegisterClient.fxml"));
        Scene scene = new Scene(loader);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onBuyTicketWithoutAccountButtonClick(ActionEvent event) {

    }

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CinemaProject/MenuCinemaProject.fxml"));
        Scene scene = new Scene(root);


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
