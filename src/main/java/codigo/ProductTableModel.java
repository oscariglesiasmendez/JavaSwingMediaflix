package codigo;

import entities.Client;
import entities.Product;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProductTableModel extends AbstractTableModel {

    private List<Product> products = new ArrayList<>();
    String[] nombreColumnas = {"Id", "Título", "Descripción", "Stock", "Idioma", "Tipo Producto", "Precio", "Rating", "Imagen", "Género", "Fecha salida", "Disponible"};

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String getColumnName(int column) {
        return nombreColumnas[column];
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return nombreColumnas.length;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Product p = products.get(fila);

        switch (columna) {
            case 0:
                return p.getProductId();
            case 1:
                return p.getTitle();
            case 2:
                return p.getDescription();
            case 3:
                return p.getStock();
            case 4:
                return p.getLanguage();
            case 5:
                return p.getProductType();
            case 6:
                return p.getPrice();
            case 7:
                return Math.round(p.getRating() * 100) / 100.0;
            case 8:
                return p.getUrl();
            case 9:
                return p.getGenre();
            case 10:
                // Formatear la fecha antes de devolverla
                return sdf.format(p.getReleaseDate());
            case 11:
                return p.getAvailable();
            default:
                return null;
        }
    }

    public void addProducts(List<Product> products) {
        this.products.addAll(products);
        fireTableDataChanged();
    }

    public void removeProducts() {
        this.products.clear();
        fireTableDataChanged();
    }

    public Product getProduct(int fila) {
        return products.get(fila);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == getColumnCount() - 1) {
            return Boolean.class; // La última columna será de tipo booleano para el checkbox
        }
        return super.getColumnClass(columnIndex);
    }

    public void setValue(Object value) {
        if (value instanceof Date) {
            value = sdf.format(value);
        }
        this.setValue(value);
    }

    public void mostrarTablaPor(String filtro) {
        if (filtro == null) {
            return; // Si el filtro es null, sale del método
        }

        List<Product> filterProducts = null;

        switch (filtro) {
            case "Todos":
                filterProducts = new ArrayList<>(products);
                break;
            case "Disponibles":
                filterProducts = new ArrayList<>();
                for (Product p : products) {
                    if (p.getAvailable()) {
                        filterProducts.add(p);
                    }
                }
                break;
            case "No Disponibles":
                filterProducts = new ArrayList<>();
                for (Product p : products) {
                    if (!p.getAvailable()) {
                        filterProducts.add(p);
                    }
                }
                break;
            case "Stock Ascendente":
                Collections.sort(products, Comparator.comparing(Product::getStock));
                filterProducts = new ArrayList<>(products);
                break;
            case "Stock Descendente":
                Collections.sort(products, Comparator.comparing(Product::getStock).reversed());
                filterProducts = new ArrayList<>(products);
                break;
            case "Precio Ascendente":
                Collections.sort(products, Comparator.comparing(Product::getPrice));
                filterProducts = new ArrayList<>(products);
                break;
            case "Precio Descendente":
                Collections.sort(products, Comparator.comparing(Product::getPrice).reversed());
                filterProducts = new ArrayList<>(products);
                break;
            case "Alfabéticamente Ascendente":
                Collections.sort(products, Comparator.comparing(Product::getTitle));
                filterProducts = new ArrayList<>(products);
                break;
            case "Alfabéticamente Descendente":
                Collections.sort(products, Comparator.comparing(Product::getTitle).reversed());
                filterProducts = new ArrayList<>(products);
                break;
        }

        this.products.clear();
        this.addProducts(filterProducts);
        this.fireTableDataChanged();
    }

}
