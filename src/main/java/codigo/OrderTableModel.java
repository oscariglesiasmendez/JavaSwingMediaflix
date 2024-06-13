package codigo;

import entities.Order;
import entities.OrderDetail;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import service.ProductApiCall;

public class OrderTableModel extends AbstractTableModel {

    private List<Order> order = new ArrayList<>();
    String[] nombreColumnas = {"Id Venta", "Cliente", "Fecha de pedido", "Total", "Número de productos", "Estado"};

    ProductApiCall productApiCall = new ProductApiCall();

    public List<Order> getOrders() {
        return order;
    }

    public void addOrders(List<Order> order) {
        this.order.addAll(order);
        fireTableDataChanged();
    }

    public void addAnOrder(Order order) {
        this.order.add(order);
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        return nombreColumnas[column];
    }

    @Override
    public int getRowCount() {
        return order.size();
    }

    @Override
    public int getColumnCount() {
        return nombreColumnas.length;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Order o = order.get(fila);

        // Calcular el total de productos fuera del switch
        int totalProductos = 0;
        for (OrderDetail details : o.getDetails()) {
            totalProductos += details.getQuantity();
        }

        switch (columna) {
            case 0:
                return o.getOrderId();
            case 1:
                return o.getClient().getEmail();
            case 2:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                String formattedDateTime = o.getCreationDate().format(formatter);

                return formattedDateTime;
            case 3:
                return Math.round(o.getTotal() * 100) / 100.0;
            case 4:
                return totalProductos;
            case 5:
                return o.getStatus();
            default:
                return null;
        }
    }

    public void removeOrders() {
        this.order.clear();
        fireTableDataChanged();
    }

    public Order getOrder(int fila) {
        return order.get(fila);
    }

}
