package entities;

import java.util.List;

public class ProductPage {

    private List<Product> products;
    private int total;
    private int skip;
    private int limit;

    public List<Product> getProducts() {
        return products;
    }

    public int getTotal() {
        return total;
    }

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return limit;
    }

}
