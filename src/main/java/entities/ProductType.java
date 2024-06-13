package entities;

public enum ProductType {
    BOOK, MOVIE, GAME;

    @Override
    public String toString() {
        switch (this) {
            case BOOK:
                return "Libro";
            case GAME:
                return "Videojuego";
            case MOVIE:
                return "Película";
            default:
                break;
        }
        return null;
    }
    
    public static ProductType toEnum(String tipo){
        switch (tipo) {
            case "Libro":
                return BOOK;
            case "Videojuego":
                return GAME;
            case "Película":
                return MOVIE;
            default:
                break;
        }
        return null;
    }
    
}
