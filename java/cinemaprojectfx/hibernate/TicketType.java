package cinemaprojectfx.hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "ticket_types")
public class TicketType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_ticket_type", nullable = false, length = 20)
    private int id;

    @Column (name = "ticket_type", nullable = false, length = 50)
    private String ticketType;

    @Column (name = "price", nullable = false, length = 20)
    private int price;

    public TicketType() {

    }

    public TicketType(String ticketType, int price) {
        this.ticketType = ticketType;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTicketType() {
        return ticketType;
    }

    public int getPrice() {
        return price;
    }
}
