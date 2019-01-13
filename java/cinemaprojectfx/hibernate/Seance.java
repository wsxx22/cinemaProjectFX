package cinemaprojectfx.hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "seances")
public class Seance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_seance")
    private int id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_movie", referencedColumnName = "id_movie")
    private Movie movie;

    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_room", referencedColumnName = "id_room")
    private Room room;

    @Column (name = "datetime")
    private LocalDateTime dateTime;

    public Seance() {
    }

    public Seance(Movie movie, Room room, LocalDateTime dateTime) {
        this.movie = movie;
        this.room = room;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
