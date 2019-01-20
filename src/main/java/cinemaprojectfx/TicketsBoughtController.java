package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import cinemaprojectfx.hibernate.Order;
import cinemaprojectfx.hibernate.Ticket;
import cinemaprojectfx.hibernate.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class TicketsBoughtController implements Initializable {

    private Database database;
    private User user;

    @FXML TableView tableView;
    @FXML TableColumn ticketIdCol;
    @FXML TableColumn datetimeCol;
    @FXML TableColumn movieTitleCol;
    @FXML TableColumn ticketPriceCol;
    @FXML TableColumn ticketTypeCol;

    ObservableList<TicketBought> tickets = FXCollections.emptyObservableList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = Database.getInstance();

        ticketIdCol.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        datetimeCol.setCellValueFactory(new PropertyValueFactory<>("datetime"));
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        ticketPriceCol.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        ticketTypeCol.setCellValueFactory(new PropertyValueFactory<>("ticketType"));

        tableView.setItems(tickets);
    }

    public void onBackMenuClient(ActionEvent event) throws IOException {
    }

    public void setUser(User user) {
        this.user = user;

        tickets.add(new TicketBought(1, LocalDateTime.now(), "Szklana pulapka", 15, "NORMALNY"));

//        for (Order o : user.getOrders()) {
//            for (Ticket t : o.getTickets()) {
//                tickets.add(new TicketBought(t.getId(), o.getDateTime(), o.getSeance().getMovie().getTitle(),
//                        t.getTicketType().getPrice(), t.getTicketType().getTicketType()));
//            }
//        }
    }

    public static class TicketBought {
        private final SimpleStringProperty ticketId;
        private final SimpleStringProperty datetime;
        private final SimpleStringProperty movieTitle;
        private final SimpleStringProperty ticketPrice;
        private final SimpleStringProperty ticketType;

        public TicketBought(int ticketId, LocalDateTime dateTime, String movieTitle, int ticketPrice, String ticketType) {
            this.ticketId = new SimpleStringProperty(String.valueOf(ticketId));
            this.datetime = new SimpleStringProperty(dateTime.toString());
            this.movieTitle = new SimpleStringProperty(movieTitle);
            this.ticketPrice = new SimpleStringProperty(String.valueOf(ticketPrice));
            this.ticketType = new SimpleStringProperty(ticketType);
        }

        public String getTicketId() {
            return ticketId.get();
        }

        public SimpleStringProperty ticketIdProperty() {
            return ticketId;
        }

        public String getDatetime() {
            return datetime.get();
        }

        public SimpleStringProperty datetimeProperty() {
            return datetime;
        }

        public String getMovieTitle() {
            return movieTitle.get();
        }

        public SimpleStringProperty movieTitleProperty() {
            return movieTitle;
        }

        public String getTicketPrice() {
            return ticketPrice.get();
        }

        public SimpleStringProperty ticketPriceProperty() {
            return ticketPrice;
        }

        public String getTicketType() {
            return ticketType.get();
        }

        public SimpleStringProperty ticketTypeProperty() {
            return ticketType;
        }
    }
}