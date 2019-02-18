package cinemaprojectfx.hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "tickets")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column (name = "id_ticket")
    private int id;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "id_order", referencedColumnName = "id_order")
    private Order order;

    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "id_ticket_type", referencedColumnName = "id_ticket_type")
    private TicketType ticketType;

    @Column (name = "row")
    private int row;

    @Column (name = "seat")
    private int seat;

    public Ticket() {
    }

    public Ticket(Order order, TicketType ticketType, int row, int seat) {
        this.order = order;
        this.ticketType = ticketType;
        this.row = row;
        this.seat = seat;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
