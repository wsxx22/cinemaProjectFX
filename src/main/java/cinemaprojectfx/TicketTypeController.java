package cinemaprojectfx;

import cinemaprojectfx.hibernate.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class TicketTypeController implements Initializable {

    @FXML GridPane gridPaneTicketType;
    @FXML Button orderTicketButton;
    private Database database;
    private List<TextField> listTextFields = new ArrayList<>();

    //dataOrder
    private Seance seance;
    private User user;
    private Employee employee;
    private final LocalDateTime dateTime = LocalDateTime.now();
    private List<Ticket> tickets;


    //dataTicket
    private Order order;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();

        orderTicketButton.setDisable(true);

        var ticketTypes = database.getTicketTypes();

        for (int i=0; i< ticketTypes.size(); i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(30);
            rowConstraints.setVgrow(Priority.SOMETIMES);

            gridPaneTicketType.getRowConstraints().add(rowConstraints);
        }

            int j = 0;
            Label[] type_ticket = new Label[ticketTypes.size()];
            Label[] price_ticket = new Label[ticketTypes.size()];

            for (int i=0; i< ticketTypes.size(); i++) {

                listTextFields.add(new TextField(""));
                type_ticket[i] = new Label(ticketTypes.get(i).getTicketType());
                gridPaneTicketType.add(type_ticket[i], 0, j );
                price_ticket[i] = new Label(String.valueOf(ticketTypes.get(i).getPrice()));
                gridPaneTicketType.add(price_ticket[i], 1, j );
                j++;

                int finalI = i;
                listTextFields.get(i).textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        listTextFields.get(finalI).setText(newValue.replaceAll("[^\\d]", ""));
                    }

                    orderTicketButton.setDisable(order.getTickets().size() != countTickets());
                });

                GridPane.setConstraints(listTextFields.get(i), 2, i);
                listTextFields.get(i).setPrefSize(35,30);
                gridPaneTicketType.getChildren().add(listTextFields.get(i));
            }
    }

    private int countTickets() {
        int result = 0;
        for (TextField textField : listTextFields) {
            result += textField.getText().isEmpty() ? 0 : Integer.valueOf(textField.getText());
        }
        return result;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void onOrderTicketsClick(ActionEvent event) {
        order.setDateTime(LocalDateTime.now());
        order.setUser(database.getEntity(User.class, 3));
        order.setEmployee(database.getEntity(Employee.class, 0));

        var ticketTypes = database.getTicketTypes();

        int ticketIndex = 0;
        for (int j = 0; j < listTextFields.size(); j++) {
            for (int i = 0; i < Integer.valueOf(listTextFields.get(j).getText()); i++) {
                order.getTickets().get(ticketIndex).setTicketType(ticketTypes.get(j));
            }
        }

        database.saveEntity(order);
    }

}
