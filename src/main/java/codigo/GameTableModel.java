package codigo;

import entities.Game;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class GameTableModel extends AbstractTableModel {

    private List<Game> games = new ArrayList<>();
    String[] nombreColumnas = {"Id", "Título", "Descripción", "Stock", "Idioma", "Tipo Producto", "Precio", "Rating", "Imagen", "Género", "Fecha salida", "Desarrollador", "Plataforma", "Duración (Horas)", "URL Trailer", "Disponible"};

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public List<Game> getGames() {
        return games;
    }

    public void addGames(List<Game> games) {
        this.games.addAll(games);
    }

    @Override
    public String getColumnName(int column) {
        return nombreColumnas[column];
    }

    @Override
    public int getRowCount() {
        return games.size();
    }

    @Override
    public int getColumnCount() {
        return nombreColumnas.length;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Game g = games.get(fila);

        switch (columna) {
            case 0:
                return g.getProductId();
            case 1:
                return g.getTitle();
            case 2:
                return g.getDescription();
            case 3:
                return g.getStock();
            case 4:
                return g.getLanguage();
            case 5:
                return g.getProductType();
            case 6:
                return g.getPrice();
            case 7:
                return Math.round(g.getRating() * 100) / 100.0;
            case 8:
                return g.getUrl();
            case 9:
                return g.getGenre();
            case 10:
                // Formatear la fecha antes de devolverla
                return sdf.format(g.getReleaseDate());
            case 11:
                return g.getDeveloper();
            case 12:
                return g.getPlatform();
            case 13:
                return g.getDuration();
            case 14:
                return g.getUrlTrailer();
            case 15:
                return g.getAvailable();
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

    public void removeGames() {
        this.games.clear();
        fireTableDataChanged();
    }

    public Game getGame(int fila) {
        return games.get(fila);
    }

    public void mostrarTablaPor(String filtro) {
        if (filtro == null) {
            return; // Si el filtro es null, salir del método
        }

        List<Game> filterGames = null;

        switch (filtro) {
            case "Todos":
                filterGames = new ArrayList<>(games);
                break;
            case "Disponibles":
                filterGames = new ArrayList<>();
                for (Game g : games) {
                    if (g.getAvailable()) {
                        filterGames.add(g);
                    }
                }
                break;
            case "No Disponibles":
                filterGames = new ArrayList<>();
                for (Game g : games) {
                    if (!g.getAvailable()) {
                        filterGames.add(g);
                    }
                }
                break;
            case "Stock Ascendente":
                Collections.sort(games, Comparator.comparing(Game::getStock));
                filterGames = new ArrayList<>(games);
                break;
            case "Stock Descendente":
                Collections.sort(games, Comparator.comparing(Game::getStock).reversed());
                filterGames = new ArrayList<>(games);
                break;
            case "Precio Ascendente":
                Collections.sort(games, Comparator.comparing(Game::getPrice));
                filterGames = new ArrayList<>(games);
                break;
            case "Precio Descendente":
                Collections.sort(games, Comparator.comparing(Game::getPrice).reversed());
                filterGames = new ArrayList<>(games);
                break;
            case "Título Ascendente":
                Collections.sort(games, Comparator.comparing(Game::getTitle));
                filterGames = new ArrayList<>(games);
                break;
            case "Título Descendente":
                Collections.sort(games, Comparator.comparing(Game::getTitle).reversed());
                filterGames = new ArrayList<>(games);
                break;
            case "Desarrollador Ascendente":
                Collections.sort(games, Comparator.comparing(Game::getDeveloper));
                filterGames = new ArrayList<>(games);
                break;
            case "Desarrollador Descendente":
                Collections.sort(games, Comparator.comparing(Game::getDeveloper).reversed());
                filterGames = new ArrayList<>(games);
                break;
            case "Duración Ascendente":
                Collections.sort(games, Comparator.comparing(Game::getDuration));
                filterGames = new ArrayList<>(games);
                break;
            case "Duración Descendente":
                Collections.sort(games, Comparator.comparing(Game::getDuration).reversed());
                filterGames = new ArrayList<>(games);
                break;
        }

        this.games.clear();
        this.addGames(filterGames);
        this.fireTableDataChanged();
    }

}
