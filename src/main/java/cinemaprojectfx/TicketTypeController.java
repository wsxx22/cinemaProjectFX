package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TicketTypeController implements Initializable {


    @FXML GridPane gridPaneTicketType;
    private Database database;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();

        for (int i=0; i< database.getTicketTypes().size(); i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(30);
            rowConstraints.setVgrow(Priority.SOMETIMES);

            gridPaneTicketType.getRowConstraints().add(rowConstraints);


        }

            int j = 0;
            Label[] type_ticket = new Label[database.getTicketTypes().size()];
            Label[] price_ticket = new Label[database.getTicketTypes().size()];
            TextField[] textFields = new TextField[database.getTicketTypes().size()];
            for (int i=0; i< database.getTicketTypes().size(); i++) {
                textFields[i] = new TextField("");
                type_ticket[i] = new Label(database.getTicketTypes().get(i).getTicketType());
                gridPaneTicketType.add(type_ticket[i], 0, j );
                price_ticket[i] = new Label(String.valueOf(database.getTicketTypes().get(i).getPrice()));
                gridPaneTicketType.add(price_ticket[i], 1, j );
                j++;
                GridPane.setConstraints(textFields[i], 2, i);
                textFields[i].setPrefSize(35,30);
                gridPaneTicketType.getChildren().add(textFields[i]);
            }



    }




}
