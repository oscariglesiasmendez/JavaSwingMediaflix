package entities;

import java.util.List;

public class GamePage {

    private List<Game> games;
    private int total;
    private int skip;
    private int limit;

    public List<Game> getGames() {
        return games;
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

    public void setGames(List<Game> games) {
        this.games = games;
    }
    
    

}
