package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import cinemaprojectfx.hibernate.Room;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CinemaRoomController implements Initializable {

    @FXML GridPane rowIdGridPane;
    @FXML GridPane columnIdGridPane;
    @FXML GridPane seatsGridPane;
    private Database database;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();

        showNameSeats(1);
        showSeats(1);

//        Platform.runLater(() -> {
//
//        });

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
            for (int i=0; i< room.get().getRows(); i++) {
                for (int j=0; j< room.get().getColumns(); j++) {
                    addPane(i,j);
                }
            }

        }
    }

    private void addPane (int row, int column) {

        Background backgroundClicked = new Background(new BackgroundFill(Color.LIGHTSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY));
        Background backgroundUnClicked = new Background(new BackgroundFill(null, CornerRadii.EMPTY, Insets.EMPTY));

        Pane pane = new Pane();
//        pane.backgroundProperty().bind(Bindings
//                .when(pane.focusedProperty()).then(backgroundClicked).otherwise(backgroundUnClicked));
        pane.setOnMouseClicked( e -> {

            System.out.println("nacisnieto na pole: " + row + ", " + column);
        });
        seatsGridPane.add(pane, row, column);
    }

}
