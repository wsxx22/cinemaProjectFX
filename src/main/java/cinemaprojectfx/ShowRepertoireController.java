package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import cinemaprojectfx.hibernate.Seance;
import cinemaprojectfx.hibernate.User;
import cinemaprojectfx.model.ShowRepertoireTableList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
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

    private Database database;
    private User user;

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

    public void onRepertoireOnTommorowClick(ActionEvent event) {
        repertoireTable.getItems().clear();
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        showRepertoire(dateTime);
    }

    public void onRepertoireDateOnClick(ActionEvent event) {

        LocalDateTime dateTime = LocalDateTime.of(repertoireDate.getValue(), LocalTime.now());
        repertoireTable.getItems().clear();
        showRepertoire(dateTime);
    }

    public void onSelectMovieClick(MouseEvent mouseEvent) {



    }

    public void onOrderTicketClick(ActionEvent event) {

    }


    public void setUser(User user) {
        this.user = user;
    }

}
