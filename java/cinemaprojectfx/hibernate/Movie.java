package cinemaprojectfx.hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "movies")
public class Movie implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_movie", nullable = false, length = 20)
    private int id;

    @Column (name = "title", nullable = false, length = 50)
    private String title;

    @Column (name = "description", nullable = false, length = 250)
    private String description;

    @Column (name = "duration", nullable = false, length = 10)
    private int duration;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movie_actors", joinColumns = {@JoinColumn(name = "id_movie")},
            inverseJoinColumns={@JoinColumn(name = "id_actor")})
    private List<Actor> actors;

    @OneToMany (cascade = CascadeType.ALL)
    @JoinTable (name = "movie_directors", joinColumns = {@JoinColumn(name = "id_movie")},
            inverseJoinColumns = {@JoinColumn(name = "id_director")})
    private List<Director> directors;

    @OneToMany (cascade = CascadeType.ALL)
    @JoinTable (name = "movie_genres", joinColumns = {@JoinColumn(name = "id_movie")},
            inverseJoinColumns = {@JoinColumn(name = "id_genre")})
    private List<Genre> genres;

    public Movie() {
    }

    public Movie(String title, String description, int duration) {
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
