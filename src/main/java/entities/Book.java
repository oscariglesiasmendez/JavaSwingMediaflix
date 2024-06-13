package entities;

import java.math.BigDecimal;
import java.sql.Date;

public class Book extends Product {

    private Long isbn;

    private String author;

    private String publisher;

    private Integer pageNumber;

    public Book(Long productId, String title, String description, Integer stock, String language, ProductType productType, Double price, Double rating, String url, String genre, Date releaseDate, Boolean available, Long isbn, String author, String publisher, Integer pageNumber) {
        super(productId, title, description, stock, language, productType, price, rating, url, genre, releaseDate, available);
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.pageNumber = pageNumber;
    }

    public Book(String title, String description, Integer stock, String language, ProductType productType, Double price, Double rating, String url, String genre, Date releaseDate, Boolean available, Long isbn, String author, String publisher, Integer pageNumber) {
        super(title, description, stock, language, productType, price, rating, url, genre, releaseDate, available);
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.pageNumber = pageNumber;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "Book{"
                + "productId=" + getProductId()
                + ", title='" + getTitle() + '\''
                + ", description='" + getDescription() + '\''
                + ", stock=" + getStock()
                + ", language='" + getLanguage() + '\''
                + ", productType='" + getProductType() + '\''
                + ", price=" + getPrice()
                + ", rating=" + getRating()
                + ", url='" + getUrl() + '\''
                + ", genre='" + getGenre() + '\''
                + ", releaseDate='" + getReleaseDate() + '\''
                + ", available=" + getAvailable()
                + ", isbn=" + isbn
                + ", author='" + author + '\''
                + ", publisher='" + publisher + '\''
                + ", pageNumber=" + pageNumber
                + '}';
    }

}
