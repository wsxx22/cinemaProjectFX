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


}
