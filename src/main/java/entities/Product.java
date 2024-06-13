package entities;

import java.math.BigDecimal;
import java.sql.Date;

public class Product {

    private Long productId;

    private String title;

    private String description;

    private Integer stock;

    private String language;

    private ProductType productType;

    private Double price;

    private Double rating;

    private String url;

    private String genre;

    private Date releaseDate;

    private Boolean available;

    public Product(Long productId, String title, String description, Integer stock, String language, ProductType productType, Double price, Double rating, String url, String genre, Date releaseDate, Boolean available) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.stock = stock;
        this.language = language;
        this.productType = productType;
        this.price = price;
        this.rating = rating;
        this.url = url;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.available = available;
    }
    
    public Product(String title, String description, Integer stock, String language, ProductType productType, Double price, Double rating, String url, String genre, Date releaseDate, Boolean available) {
        this.title = title;
        this.description = description;
        this.stock = stock;
        this.language = language;
        this.productType = productType;
        this.price = price;
        this.rating = rating;
        this.url = url;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.available = available;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", title=" + title + ", description=" + description + ", stock=" + stock + ", language=" + language + ", productType=" + productType + ", price=" + price + ", rating=" + rating + ", url=" + url + ", genre=" + genre + ", releaseDate=" + releaseDate + ", available=" + available + '}';
    }

}
