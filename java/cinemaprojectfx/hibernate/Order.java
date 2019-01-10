package cinemaprojectfx.hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY);
    @Column (name = "id_order")
    private int id;



}
