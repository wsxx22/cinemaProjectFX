package cinemaprojectfx.hibernate;


import javax.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_genre")
    private int id;

    @Column (name = "type_genre")
    private String type;

    public Genre() {
    }

    public Genre(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
