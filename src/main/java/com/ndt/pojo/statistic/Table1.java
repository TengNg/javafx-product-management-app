package com.ndt.pojo.statistic;

import com.ndt.pojo.Product;

public class Table1 {
    private String productId;
    private String productName;
    private int categoryId;
    private int amountSold;

    public Table1(String productId, String productName, int categoryId, int amountSold) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.amountSold = amountSold;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(int amountSold) {
        this.amountSold = amountSold;
    }

    @Override
    public String toString() {
        return "Table1{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", amountSold=" + amountSold +
                '}';
    }
}
