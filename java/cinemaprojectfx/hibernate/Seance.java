package cinemaprojectfx.hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "seances")
public class Seance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "id_movie")
    private int movieId;

    @Column (name = "id_room")
    private int roomId;

    @Column (name = "datetime")
    private LocalDateTime dateTime;


}
