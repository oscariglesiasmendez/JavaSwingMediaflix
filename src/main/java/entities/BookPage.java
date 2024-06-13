package entities;

import java.util.List;

public class BookPage {

    private List<Book> books;
    private int total;
    private int skip;
    private int limit;

    public List<Book> getBooks() {
        return books;
    }

    public int getTotal() {
        return total;
    }

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
}
