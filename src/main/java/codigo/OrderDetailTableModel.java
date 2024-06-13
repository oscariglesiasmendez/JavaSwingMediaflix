package codigo;

import entities.OrderDetail;
import entities.Product;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import service.ProductApiCall;

public class OrderDetailTableModel extends AbstractTableModel {

    private List<OrderDetail> orderDetails = new ArrayList<>();
    String[] nombreColumnas = {"Uds.", "Producto", "Precio Unitario", "Total"};

    ProductApiCall productApiCall = new ProductApiCall();

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void addOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails.addAll(orderDetails);
        fireTableDataChanged();
    }

    public void addAnOrderDetail(OrderDetail orderDetail) {
        this.orderDetails.add(orderDetail);
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        return nombreColumnas[column];
    }

    @Override
    public int getRowCount() {
        return orderDetails.size();
    }

    @Override
    public int getColumnCount() {
        return nombreColumnas.length;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        OrderDetail o = orderDetails.get(fila);

        Product p = productApiCall.getById(o.getProductId());

        switch (columna) {
            case 0:
                return o.getQuantity();
            case 1:
                return p.getTitle();
            case 2:
                return p.getPrice();
            case 3:
                double total = o.getQuantity() * p.getPrice();
                return String.format("%.2f", total);
            default:
                return null;
        }
    }

    public void removeOrderDetails() {
        this.orderDetails.clear();
        fireTableDataChanged();
    }

    public OrderDetail getOrderDetail(int fila) {
        return orderDetails.get(fila);
    }

}
