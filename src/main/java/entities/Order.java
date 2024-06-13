package entities;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private Long orderId;

    public LocalDateTime creationDate;

    private Double total;

    private String paymentMethod;

    private Client client;

    private OrderStatus status;

    private List<OrderDetail> details;

    public Order() {
    }
    
    
    /*
    public Order(Long orderId, LocalDateTime creationDate, Double total, String paymentMethod, Client client, OrderStatus status, List<OrderDetail> details) {
        this.orderId = orderId;
        this.creationDate = creationDate;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.client = client;
        this.status = status;
        this.details = details;
    }

    public Order(LocalDateTime creationDate, Double total, String paymentMethod, Client client, OrderStatus status, List<OrderDetail> details) {
        this.creationDate = creationDate;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.client = client;
        this.status = status;
        this.details = details;
    }
    */

    public Order(Long orderId, LocalDateTime creationDate, Double total, String paymentMethod, OrderStatus status, List<OrderDetail> details) {
        this.orderId = orderId;
        this.creationDate = creationDate;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.details = details;
    }

    public Order(LocalDateTime creationDate, Double total, String paymentMethod, OrderStatus status, List<OrderDetail> details) {
        this.creationDate = creationDate;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.details = details;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client clientId) {
        this.client = clientId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", creationDate=" + creationDate + ", total=" + total + ", paymentMethod=" + paymentMethod + ", client=" + client + ", status=" + status + ", details=" + details + '}';
    }

}
