package codigo;

import entities.Movie;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MovieTableModel extends AbstractTableModel {

    private List<Movie> movies = new ArrayList<>();
    String[] nombreColumnas = {"Id", "Título", "Descripción", "Stock", "Idioma", "Tipo Producto", "Precio", "Rating", "Imagen", "Género", "Fecha salida", "Director", "Estudio", "Duración (Minutos)", "URL Trailer", "Disponible"};

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
    }

    @Override
    public String getColumnName(int column) {
        return nombreColumnas[column];
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return nombreColumnas.length;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Movie m = movies.get(fila);

        switch (columna) {
            case 0:
                return m.getProductId();
            case 1:
                return m.getTitle();
            case 2:
                return m.getDescription();
            case 3:
                return m.getStock();
            case 4:
                return m.getLanguage();
            case 5:
                return m.getProductType();
            case 6:
                return m.getPrice();
            case 7:
                return Math.round(m.getRating() * 100) / 100.0;
            case 8:
                return m.getUrl();
            case 9:
                return m.getGenre();
            case 10:
                // Formatear la fecha antes de devolverla
                return sdf.format(m.getReleaseDate());
            case 11:
                return m.getDirector();
            case 12:
                return m.getStudio();
            case 13:
                return m.getDuration();
            case 14:
                return m.getUrlTrailer();
            case 15:
                return m.getAvailable();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == getColumnCount() - 1) {
            return Boolean.class; // La última columna será de tipo booleano para el checkbox
        }
        return super.getColumnClass(columnIndex);
    }

    public void removeMovies() {
        this.movies.clear();
        fireTableDataChanged();
    }

    public Movie getMovie(int fila) {
        return movies.get(fila);
    }

    public void mostrarTablaPor(String filtro) {
        if (filtro == null) {
            return; // Si el filtro es null, salir del método
        }

        List<Movie> filterMovies = null;

        switch (filtro) {
            case "Todos":
                filterMovies = new ArrayList<>(movies);
                break;
            case "Disponibles":
                filterMovies = new ArrayList<>();
                for (Movie m : movies) {
                    if (m.getAvailable()) {
                        filterMovies.add(m);
                    }
                }
                break;
            case "No Disponibles":
                filterMovies = new ArrayList<>();
                for (Movie m : movies) {
                    if (!m.getAvailable()) {
                        filterMovies.add(m);
                    }
                }
                break;
            case "Stock Ascendente":
                Collections.sort(movies, Comparator.comparing(Movie::getStock));
                filterMovies = new ArrayList<>(movies);
                break;
            case "Stock Descendente":
                Collections.sort(movies, Comparator.comparing(Movie::getStock).reversed());
                filterMovies = new ArrayList<>(movies);
                break;
            case "Precio Ascendente":
                Collections.sort(movies, Comparator.comparing(Movie::getPrice));
                filterMovies = new ArrayList<>(movies);
                break;
            case "Precio Descendente":
                Collections.sort(movies, Comparator.comparing(Movie::getPrice).reversed());
                filterMovies = new ArrayList<>(movies);
                break;
            case "Título Ascendente":
                Collections.sort(movies, Comparator.comparing(Movie::getTitle));
                filterMovies = new ArrayList<>(movies);
                break;
            case "Título Descendente":
                Collections.sort(movies, Comparator.comparing(Movie::getTitle).reversed());
                filterMovies = new ArrayList<>(movies);
                break;
            case "Director Ascendente":
                Collections.sort(movies, Comparator.comparing(Movie::getDirector));
                filterMovies = new ArrayList<>(movies);
                break;
            case "Director Descendente":
                Collections.sort(movies, Comparator.comparing(Movie::getDirector).reversed());
                filterMovies = new ArrayList<>(movies);
                break;
            case "Duración Ascendente":
                Collections.sort(movies, Comparator.comparing(Movie::getDuration));
                filterMovies = new ArrayList<>(movies);
                break;
            case "Duración Descendente":
                Collections.sort(movies, Comparator.comparing(Movie::getDuration).reversed());
                filterMovies = new ArrayList<>(movies);
                break;
        }

        this.movies.clear();
        this.addMovies(filterMovies);
        this.fireTableDataChanged();
    }

}
