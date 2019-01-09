package cinemaprojectfx.hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "rooms")

public class Room implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_room", nullable = false, length = 20)
    private int id;

    @Column (name = "name", nullable = false, length = 20)
    private String name;

    @Column (name = "seats", nullable = false, length = 20)
    private int seats;

    @Column (name = "rows", nullable = false, length = 11)
    private int rows;

    public Room() {
    }

    public Room(String name, int seats, int rows) {
        this.name = name;
        this.seats = seats;
        this.rows = rows;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
