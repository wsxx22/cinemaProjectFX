package cinemaprojectfx.hibernate;


import java.time.LocalDateTime;

public class ModelTableList {

    private int ticketId;
    private String dateTime;
    private String movieTitle;
    private int ticketPrice;
    private String ticketType;


    public ModelTableList(int ticketId, String dateTime, String movieTitle, int ticketPrice, String ticketType) {
        this.ticketId = ticketId;
        this.dateTime = dateTime;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
