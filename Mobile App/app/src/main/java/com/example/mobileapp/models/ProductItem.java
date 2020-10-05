package com.example.mobileapp.models;

public class ProductItem {
    private int Image;
    private String ProductName;

    public ProductItem(int image,String productName){
        this.Image=image;
        this.ProductName=productName;
    }

    public int getImage() {
        return Image;
    }

    public String getProductName() {
        return ProductName;
    }
}
