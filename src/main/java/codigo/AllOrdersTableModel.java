package codigo;

import entities.Order;
import entities.OrderDetail;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import service.ProductApiCall;

public class AllOrdersTableModel extends AbstractTableModel {

    private List<Order> order = new ArrayList<>();
    String[] nombreColumnas = {"Id Venta", "Fecha de pedido", "Id Cliente", "Nombre Cliente", "Email", "Total", "Número de productos", "Estado", "Método de pago"};

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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                String formattedDateTime = o.getCreationDate().format(formatter);

                return formattedDateTime;
            case 2:
                return o.getClient().getClientId();
            case 3:
                return o.getClient().getFirstName();
            case 4:
                return o.getClient().getEmail();
            case 5:
                return Math.round(o.getTotal() * 100) / 100.0;
            case 6:
                return totalProductos;
            case 7:
                return o.getStatus();
            case 8:
                return o.getPaymentMethod();
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
