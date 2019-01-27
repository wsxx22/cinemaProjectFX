package cinemaprojectfx.hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table (name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_order")
    private int id;

    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "id_seance", referencedColumnName = "id_seance")
    private Seance seance;

    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "id_user", referencedColumnName = "id_user")
    private User user;

    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "id_employee", referencedColumnName = "id_employee")
    private Employee employee;

    @Column (name = "datetime")
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "order")
    private List<Ticket> tickets;

    public Order() {
    }

    public Order(Seance seance, User user, Employee employee, LocalDateTime dateTime, List<Ticket> tickets) {
        this.seance = seance;
        this.user = user;
        this.employee = employee;
        this.dateTime = dateTime;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public Seance getSeance() {
        return seance;
    }

    public User getUser() {
        return user;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

}
