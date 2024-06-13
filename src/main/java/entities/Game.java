package entities;

import java.sql.Date;

public class Game extends Product {

    private String developer;

    private String platform;

    private Integer duration;

    private String urlTrailer;

    public Game(Long productId, String title, String description, Integer stock, String language, ProductType productType, Double price, Double rating, String url, String genre, Date releaseDate, Boolean available, String developer, String platform, Integer duration, String urlTrailer) {
        super(productId, title, description, stock, language, productType, price, rating, url, genre, releaseDate, available);
        this.developer = developer;
        this.platform = platform;
        this.duration = duration;
        this.urlTrailer = urlTrailer;
    }
    
    public Game(String title, String description, Integer stock, String language, ProductType productType, Double price, Double rating, String url, String genre, Date releaseDate, Boolean available, String developer, String platform, Integer duration, String urlTrailer) {
        super(title, description, stock, language, productType, price, rating, url, genre, releaseDate, available);
        this.developer = developer;
        this.platform = platform;
        this.duration = duration;
        this.urlTrailer = urlTrailer;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }
    
    
    
}
