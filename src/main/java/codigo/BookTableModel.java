package codigo;

import entities.Book;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class BookTableModel extends AbstractTableModel {
     
    private List<Book> books = new ArrayList<>();
    String[] nombreColumnas = {"Id", "Título", "Descripción", "Stock", "Idioma", "Tipo Producto", "Precio", "Rating", "Imagen", "Género", "Fecha salida", "Autor", "ISBN", "Número de Páginas", "Editorial", "Disponible"};

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public List<Book> getBooks() {
        return books;
    }

    public void addBooks(List<Book> books) {
        this.books.addAll(books);
    }

    @Override
    public String getColumnName(int column) {
        return nombreColumnas[column];
    }

    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return nombreColumnas.length;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Book b = books.get(fila);

        switch (columna) {
            case 0:
                return b.getProductId();
            case 1:
                return b.getTitle();
            case 2:
                return b.getDescription();
            case 3:
                return b.getStock();
            case 4:
                return b.getLanguage();
            case 5:
                return b.getProductType();
            case 6:
                return b.getPrice();
            case 7:
                return Math.round(b.getRating() * 100) / 100.0;
            case 8:
                return b.getUrl();
            case 9:
                return b.getGenre();
            case 10:
                // Formatear la fecha antes de devolverla
                return sdf.format(b.getReleaseDate());
            case 11:
                return b.getAuthor();
            case 12:
                return b.getIsbn();
            case 13:
                return b.getPageNumber();
            case 14:
                return b.getPublisher();
            case 15:
                return b.getAvailable();
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

    public void removeBooks() {
        this.books.clear();
        fireTableDataChanged();
    }

    public Book getBook(int fila) {
        return books.get(fila);
    }

    public void mostrarTablaPor(String filtro) {
        if (filtro == null) {
            return; // Si el filtro es null, salir del método
        }

        List<Book> filterBooks = null;

        switch (filtro) {
            case "Todos":
                filterBooks = new ArrayList<>(books);
                break;
            case "Disponibles":
                filterBooks = new ArrayList<>();
                for (Book b : books) {
                    if (b.getAvailable()) {
                        filterBooks.add(b);
                    }
                }
                break;
            case "No Disponibles":
                filterBooks = new ArrayList<>();
                for (Book b : books) {
                    if (!b.getAvailable()) {
                        filterBooks.add(b);
                    }
                }
                break;
            case "Stock Ascendente":
                Collections.sort(books, Comparator.comparing(Book::getStock));
                filterBooks = new ArrayList<>(books);
                break;
            case "Stock Descendente":
                Collections.sort(books, Comparator.comparing(Book::getStock).reversed());
                filterBooks = new ArrayList<>(books);
                break;
            case "Precio Ascendente":
                Collections.sort(books, Comparator.comparing(Book::getPrice));
                filterBooks = new ArrayList<>(books);
                break;
            case "Precio Descendente":
                Collections.sort(books, Comparator.comparing(Book::getPrice).reversed());
                filterBooks = new ArrayList<>(books);
                break;
            case "Título Ascendente":
                Collections.sort(books, Comparator.comparing(Book::getTitle));
                filterBooks = new ArrayList<>(books);
                break;
            case "Título Descendente":
                Collections.sort(books, Comparator.comparing(Book::getTitle).reversed());
                filterBooks = new ArrayList<>(books);
                break;
            case "Autor Ascendente":
                Collections.sort(books, Comparator.comparing(Book::getAuthor));
                filterBooks = new ArrayList<>(books);
                break;
            case "Autor Descendente":
                Collections.sort(books, Comparator.comparing(Book::getAuthor).reversed());
                filterBooks = new ArrayList<>(books);
                break;
            case "Número Páginas Ascendente":
                Collections.sort(books, Comparator.comparingInt(Book::getPageNumber));
                filterBooks = new ArrayList<>(books);
                break;
            case "Número Páginas Descendente":
                Collections.sort(books, Comparator.comparingInt(Book::getPageNumber).reversed());
                filterBooks = new ArrayList<>(books);
                break;
        }

        this.books.clear();
        this.addBooks(filterBooks);
        this.fireTableDataChanged();
    }

}
