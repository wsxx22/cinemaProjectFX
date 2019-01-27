package cinemaprojectfx.hibernate;

import java.time.LocalDateTime;

public class ModelTableList {

    private int ticketId;
    private LocalDateTime localDateTime;
    private String movieTitle;
    private int ticketPrice;
    private String ticketType;

    public ModelTableList(int ticketId, LocalDateTime localDateTime, String movieTitle, int ticketPrice, String ticketType) {
        this.ticketId = ticketId;
        this.localDateTime = localDateTime;
        this.movieTitle = movieTitle;
        this.ticketPrice = ticketPrice;
        this.ticketType = ticketType;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
}
