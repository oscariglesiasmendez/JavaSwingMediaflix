package codigo;

import entities.Product;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class ProductStockModel extends AbstractTableModel{
    private List<Product> products = new ArrayList<>();
    String[] nombreColumnas = {"Id", "Título", "Tipo Producto", "Stock"};

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
                return p.getProductType();
            case 3:
                return p.getStock();
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

    
    public void mostrarTablaPor(String filtro) {
        if (filtro == null) {
            return; // Si el filtro es null, sale del método
        }

        List<Product> filterProducts = null;

        switch (filtro) {
            case "Stock Ascendente":
                Collections.sort(products, Comparator.comparing(Product::getStock));
                filterProducts = new ArrayList<>(products);
                break;
            case "Stock Descendente":
                Collections.sort(products, Comparator.comparing(Product::getStock).reversed());
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
