package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import cinemaprojectfx.hibernate.Seance;
import cinemaprojectfx.hibernate.User;
import cinemaprojectfx.model.ShowRepertoireTableList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class ShowRepertoireController implements Initializable {

    @FXML DatePicker repertoireDate;
    @FXML Button repertoireOnTomorrowButton;
    @FXML Button orderTicketButton;
    @FXML TableView<ShowRepertoireTableList> repertoireTable;
    @FXML TableColumn<ShowRepertoireTableList, String> movieTitleCol;
    @FXML TableColumn<ShowRepertoireTableList, String> movieDescriptionCol;
    @FXML TableColumn<ShowRepertoireTableList, Integer> movieDurationCol;
    @FXML TableColumn<ShowRepertoireTableList, String> movieDateTimeCol;

    private ObservableList<ShowRepertoireTableList> observableList = FXCollections.observableArrayList();

    private LoggedController loggedController;
    private AnchorPane anchorPane;
    private Database database;
    @FXML AnchorPane anchorpaneShowTickets;

    private int idSeance = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();

        orderTicketButton.setDisable(true);

        Platform.runLater(() -> {
            showRepertoireToday();
        } );

    }

    private void showRepertoire(LocalDateTime dateTime) {

        List<Seance> seanceList = database.getReportoire();

        String dateTimeSeance = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        try {
            for (Seance s : seanceList) {
                if (s.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).
                        equals(dateTimeSeance)) {
                observableList.add(new ShowRepertoireTableList(
                        s.getId(),
                        s.getMovie().getTitle(),
                        s.getMovie().getDescription(),
                        s.getMovie().getDuration(),
                        s.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                );
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        movieDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        movieDurationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        movieDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        repertoireTable.setItems(observableList);
    }

    private void showRepertoireToday () {
        LocalDateTime dateTime = LocalDateTime.now();
        showRepertoire(dateTime);
    }

    public void onRepertoireOnTomorrowClick(ActionEvent event) {
        repertoireTable.getItems().clear();
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        showRepertoire(dateTime);
        orderTicketButton.setDisable(true);
    }

    public void onRepertoireDateOnClick(ActionEvent event) {

        LocalDateTime dateTime = LocalDateTime.of(repertoireDate.getValue(), LocalTime.now());
        if (dateTime.isAfter(LocalDateTime.now()) || dateTime.isEqual(LocalDateTime.now())){
            repertoireTable.getItems().clear();
            showRepertoire(dateTime);
        }
        orderTicketButton.setDisable(true);
    }

    public void onSelectMovieClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2 ) {
            if (!repertoireTable.getSelectionModel().isEmpty()) {
//            if (!repertoireTable.getItems().isEmpty()){
                idSeance = repertoireTable.getSelectionModel().getSelectedItem().getId();
                System.out.println("id " + idSeance );
            } else {
                idSeance = 0;
                System.out.println("id" + idSeance);
            }

        }
        if (idSeance == 0)
            orderTicketButton.setDisable(true);
        else if (mouseEvent.getClickCount() == 1)
            orderTicketButton.setDisable(true);
        else
            orderTicketButton.setDisable(false);
    }

    private int sum() {

        int sum =0;
        for (int i=0; i<999; i++) {
            int j = ThreadLocalRandom.current().nextInt(4,10);
            sum =+ j;
        }
        return sum;
    }


    public void onOrderTicketClick(ActionEvent event) {

        int optionPane = JOptionPane.showConfirmDialog(
                null,
                "Czy na pewno chcesz zamówić bilet na film:\n" + "tytul filmu",
                "Zamów bilet",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
                );
        if (optionPane == 0) {
            try {
                var loader = new FXMLLoader(getClass().getResource("/fxml/cinema_room.fxml"));
                var pane = (AnchorPane) loader.load();

                CinemaRoomController cinemaRoomController = loader.getController();
                cinemaRoomController.setIdSeance(repertoireTable.getSelectionModel().getSelectedItem().getId());

                anchorPane.getChildren().clear();
                anchorPane.getChildren().add(pane);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public void setPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }
}
