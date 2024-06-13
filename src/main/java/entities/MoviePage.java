package entities;

import java.util.List;

public class MoviePage {

    private List<Movie> movies;
    private int total;
    private int skip;
    private int limit;

    public List<Movie> getMovies() {
        return movies;
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

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
