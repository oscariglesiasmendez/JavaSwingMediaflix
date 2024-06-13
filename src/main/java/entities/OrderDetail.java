package entities;

public class OrderDetail {

    private Long orderId;

    private Long productId;

    private Integer quantity;

    private Double unitPrice;

    public OrderDetail() {
    }

    public OrderDetail(Long orderId, Long productId, Integer quantity, Double unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + " orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity + ", unitPrice=" + unitPrice + '}';
    }

    

}
