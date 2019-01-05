package cinemaprojectfx;

import cinemaprojectfx.hibernate.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientLoggedController implements Initializable {

    @FXML AnchorPane anchorPane;

    @FXML Label userLabel;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            userLabel.setText(user.getId() + " " + user.getUsername());
        });
    }

    @FXML
    public void onMainPageButtonClick(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/main_pane.fxml"));

        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(pane);
    }

    @FXML
    public void onBuyTicketWithAccButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(
                "/fxml/ShowRepertoire.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void onShowBoughtTicketsButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(
                "/fxml/ClientShowTicketsBought.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onSettingsButtonClick(ActionEvent event) throws IOException {

    }

    @FXML
    public void onLogOutButtonClick(ActionEvent event) throws IOException {
       backToClientLoggedMenu(event);
    }

    public void backToClientLoggedMenu (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(
                "/fxml/login_scene.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    public void setUser(User user) {
        this.user = user;
    }
}