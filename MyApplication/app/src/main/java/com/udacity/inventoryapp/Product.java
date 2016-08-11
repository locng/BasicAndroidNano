package com.udacity.inventoryapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nloc on 8/11/2016.
 */
public class Product implements Parcelable {
    private int productId;
    private String productName;
    private double productPrice;
    private int productQuantity;
    private int productRemaining;
    private String productSupplier;
    private String productImageLocation;

    public Product(String productName, double productPrice, int productQuantity, int productRemaining, String productSupplier, String productImageLocation) {
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

    public void setProductPrice(double productPrice) {
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

    protected Product(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        productPrice = in.readFloat();
        productQuantity = in.readInt();
        productRemaining = in.readInt();
        productSupplier = in.readString();
        productImageLocation = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(productName);
        dest.writeDouble(productPrice);
        dest.writeInt(productQuantity);
        dest.writeInt(productRemaining);
        dest.writeString(productSupplier);
        dest.writeString(productImageLocation);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}