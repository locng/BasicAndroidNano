package com.udacity.inventoryapp;

/**
 * Created by nloc on 8/11/2016.
 */
public class Product {
    private int productId;
    private String productName;
    private float productPrice;
    private int productQuantity;
    private int productRemaining;
    private String productSupplier;
    private String productImageLocation;

    public Product(int productId, String productImageLocation, String productName, float productPrice, int productQuantity, int productRemaining, String supplier) {
        this.productId = productId;
        this.productImageLocation = productImageLocation;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productRemaining = productRemaining;
        this.productSupplier = supplier;
    }

    public Product(String productName, float productPrice, int productQuantity, int productRemaining, String productSupplier, String productImageLocation) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productRemaining = productRemaining;
        this.productSupplier = productSupplier;
        this.productImageLocation = productImageLocation;
    }

    public Product() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductRemaining() {
        return productRemaining;
    }

    public void setProductRemaining(int productRemaining) {
        this.productRemaining = productRemaining;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(String supplier) {
        this.productSupplier = supplier;
    }

    public String getProductImageLocation() {
        return productImageLocation;
    }

    public void setProductImageLocation(String productImageLocation) {
        this.productImageLocation = productImageLocation;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                ", productRemaining=" + productRemaining +
                ", productSupplier='" + productSupplier + '\'' +
                ", productImageLocation='" + productImageLocation + '\'' +
                '}';
    }
}
