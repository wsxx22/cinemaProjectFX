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
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();

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
    }

    public void onRepertoireDateOnClick(ActionEvent event) {

        LocalDateTime dateTime = LocalDateTime.of(repertoireDate.getValue(), LocalTime.now());
        repertoireTable.getItems().clear();
        showRepertoire(dateTime);
    }

    public ShowRepertoireTableList onSelectMovieClick(MouseEvent mouseEvent) {
        TableRow<ShowRepertoireTableList> tableRow = new TableRow<>();
        if (mouseEvent.getClickCount() == 2 && !tableRow.isEmpty()) {

//            System.out.println(tableRow.getItem().getMovieTitle() + " " + tableRow.getItem().getDateTime());
            return tableRow.getItem();
        }

//        TableRow<ShowRepertoireTableList> tableRow = new TableRow<>();
//        tableRow.setOnMouseClicked( event -> {
//            if (mouseEvent.getClickCount() == 2 && !tableRow.isEmpty()) {
//                ShowRepertoireTableList rowData = tableRow.getItem();
//                System.out.println(rowData.getMovieTitle() + " " + rowData.getDateTime());
//            }
//        });

        return null;
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

                anchorPane.getChildren().clear();
                anchorPane.getChildren().add(pane);

//                AnchorPane clearAnchorPane = new AnchorPane();
//                loggedController.setAnchorPane(clearAnchorPane);
//                loggedController.getAnchorPane().getChildren().add(pane);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public void setPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }
}
