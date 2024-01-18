package com.ayman.simulation.models;

import java.util.HashMap;
import java.util.Map;

public class ProductQueue {

    long queueId;
    Map<Long, Product> products;

    public ProductQueue(long id) {
        this.queueId = id;
        products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public boolean containsProductById(long id) {
        return products.containsKey(id);
    }

    public Product getProductById(long id) {
        return products.get(id);
    }

    public void removeProductById(long id) {
        products.remove(id);
    }

    public void removeProduct(Product product) {
        products.remove(product.getId());
    }

}
