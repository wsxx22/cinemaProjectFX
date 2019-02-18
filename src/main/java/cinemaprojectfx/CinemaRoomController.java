package cinemaprojectfx;

import cinemaprojectfx.hibernate.*;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CinemaRoomController implements Initializable {

    @FXML GridPane rowIdGridPane;
    @FXML GridPane columnIdGridPane;
    @FXML GridPane seatsGridPane;
    @FXML AnchorPane anchorPaneCinemaRoom;
    @FXML Button selectTypeTicketButton;

    private Database database;

    private int idSeance;

    private final Background seatTaken
            = new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));

    private final Background seatChecked
            = new Background(new BackgroundFill(Color.LIGHTSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY));

    private final Background seatUnchecked
            = new Background(new BackgroundFill(null, CornerRadii.EMPTY, Insets.EMPTY));

    private ObservableList<Pair<Integer, Integer>> seats;
    private List<Pair<Integer, Integer>> seatsTaken;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();
        Platform.runLater(() -> {
            seatsTaken = Database.getInstance().getTakenSeatsForSeance(idSeance);

//            var seance = database.getEntity(Seance.class, idSeance);
            var seance = database.getSeance(idSeance);
            if (seance.isPresent()) {
                var roomId = seance.get().getRoom().getId();

                showNameSeats(roomId);
                showSeats(roomId);
            }
//            var roomId = seance.getRoom().getId();


        });

        selectTypeTicketButton.setDisable(true);

        seats = FXCollections.observableArrayList();
        seats.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (seats.isEmpty()) {

                    selectTypeTicketButton.setDisable(true);

                } else {
                    selectTypeTicketButton.setDisable(false);
                }
            }
        });
    }

    private void showNameSeats (int idRoom) {

        Optional<Room> room = database.getRoom(idRoom);
        if (room.isPresent()) {

            for (int i = 0; i < room.get().getRows(); i++) {

                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setPrefHeight(19);
                rowConstraints.setVgrow(Priority.SOMETIMES);
                rowIdGridPane.getRowConstraints().add(rowConstraints);
            }

            for (int i =0; i < room.get().getColumns(); i++) {
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setPrefWidth(31);
                columnConstraints.setHgrow(Priority.SOMETIMES);
                columnIdGridPane.getColumnConstraints().add(columnConstraints);
            }
        }
    }

    private void showSeats (int idRoom) {

        Optional<Room> room = database.getRoom(idRoom);
        if (room.isPresent()) {

            for (int i=0; i < room.get().getColumns(); i++ ) {
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setPrefWidth(31);
                columnConstraints.setHgrow(Priority.SOMETIMES);
                seatsGridPane.getColumnConstraints().add(columnConstraints);
            }

            for (int i=0; i< room.get().getRows(); i++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setPrefHeight(19);
                rowConstraints.setVgrow(Priority.SOMETIMES);
                seatsGridPane.getRowConstraints().add(rowConstraints);
            }

            for (int i=0; i< room.get().getColumns(); i++) {
                for (int j=0; j< room.get().getRows(); j++) {
                    addPane(i,j);
                }
            }
        }
    }

    private void addPane (int column, int row) {

        Pane pane = new Pane();

        if (seatsTaken.stream().anyMatch(pair -> (pair.getKey() == column + 1) && (pair.getValue() == row + 1))) {
            pane.setBackground(seatTaken);
        } else {
            pane.setOnMouseClicked( e -> {
                if (pane.getBackground() == null || pane.getBackground().equals(seatUnchecked)) {
                    pane.setBackground(seatChecked);
                    seats.add(new Pair(column + 1, row + 1));
                } else {
                    pane.setBackground(seatUnchecked);
                    seats.removeIf(pair -> (pair.getKey() == column + 1) && (pair.getValue() == row + 1));
                }
            });

        }

        seatsGridPane.add(pane, column, row);

    }

    public void onSelectTypeTicketClick(ActionEvent event) throws IOException{

        var loader = new FXMLLoader(getClass().getResource("/fxml/show_ticket_type.fxml"));
        var anchorPane = (AnchorPane) loader.load();

        var order = new Order();
        order.setSeance(Database.getInstance().getSeance(idSeance).get());

        seats.forEach(pair -> {
            order.getTickets().add(new Ticket(order, null, pair.getKey(), pair.getValue()));
        });

        TicketTypeController ticketTypeController = loader.getController();
        ticketTypeController.setOrder(order);

        anchorPaneCinemaRoom.getChildren().clear();
        anchorPaneCinemaRoom.getChildren().add(anchorPane);
    }

    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;

        System.out.println(idSeance);
    }
}
