package cinemaprojectfx.model;

import java.time.LocalDateTime;

public class ShowRepertoireTableList {

    private String movieTitle;
    private String description;
    private int duration;
    private String dateTime;

    public ShowRepertoireTableList(String movieTitle, String description, int duration, String dateTime) {
        this.movieTitle = movieTitle;
        this.description = description;
        this.duration = duration;
        this.dateTime = dateTime;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public String getDateTime() {
        return dateTime;
    }
}
