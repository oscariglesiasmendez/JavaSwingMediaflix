package entities;

import java.math.BigDecimal;
import java.sql.Date;

public class Movie extends Product {

    private String director;

    private Integer duration;

    private String studio;

    private String urlTrailer;

    public Movie(Long productId, String title, String description, Integer stock, String language, ProductType productType, Double price, Double rating, String url, String genre, Date releaseDate, Boolean available, String director, Integer duration, String studio, String urlTrailer) {
        super(productId, title, description, stock, language, productType, price, rating, url, genre, releaseDate, available);
        this.director = director;
        this.duration = duration;
        this.studio = studio;
        this.urlTrailer = urlTrailer;
    }
    
    public Movie(String title, String description, Integer stock, String language, ProductType productType, Double price, Double rating, String url, String genre, Date releaseDate, Boolean available, String director, Integer duration, String studio, String urlTrailer) {
        super(title, description, stock, language, productType, price, rating, url, genre, releaseDate, available);
        this.director = director;
        this.duration = duration;
        this.studio = studio;
        this.urlTrailer = urlTrailer;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }

    

}
