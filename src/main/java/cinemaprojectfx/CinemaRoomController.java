package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import cinemaprojectfx.hibernate.Room;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CinemaRoomController implements Initializable {

    @FXML GridPane rowIdGridPane;
    @FXML GridPane columnIdGridPane;
    @FXML GridPane seatsGridPane;
    @FXML AnchorPane anchorPaneCinemaRoom;

    private Database database;

    private final Background seatTaken
            = new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));

    private final Background seatChecked
            = new Background(new BackgroundFill(Color.LIGHTSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY));

    private final Background seatUnchecked
            = new Background(new BackgroundFill(null, CornerRadii.EMPTY, Insets.EMPTY));

    private List<Pair<Integer, Integer>> seats;
    private List<Pair<Integer, Integer>> seatsTaken = Database.getInstance().getTakenSeatsForSeance(4);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();

        showNameSeats(1);
        showSeats(1);

        seats = new ArrayList<>();
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

    public void onOrderTicketClick(ActionEvent event) throws IOException {

        var loader = new FXMLLoader(getClass().getResource("/fxml/show_ticket_type.fxml"));
        AnchorPane anchorPane = (AnchorPane) loader.load();

        anchorPaneCinemaRoom.getChildren().clear();
        anchorPaneCinemaRoom.getChildren().add(anchorPane);

    }

}
