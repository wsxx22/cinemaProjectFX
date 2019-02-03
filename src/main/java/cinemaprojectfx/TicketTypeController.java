package cinemaprojectfx;

import cinemaprojectfx.hibernate.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketTypeController implements Initializable {

    @FXML GridPane gridPaneTicketType;
    private Database database;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Database.getInstance();

        int row = database.getTicketTypes().size();
        int col = 2;

        for (int i =0; i < row; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            gridPaneTicketType.getRowConstraints().add(rowConstraints);
        }

        for (int j =0; j < col; j++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.SOMETIMES);
            gridPaneTicketType.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0 ; i < col ; i++) {
            for (int j = 0; j < row; j++) {
                addPane(i, j);
            }
        }

    }

    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new Pane();
        pane.setOnMouseEntered(e -> {
            System.out.printf("Mouse enetered cell [%d, %d]%n", colIndex, rowIndex);
        });
        gridPaneTicketType.add(pane, colIndex, rowIndex);
    }

}
