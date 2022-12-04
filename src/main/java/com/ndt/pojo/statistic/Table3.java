package com.ndt.pojo.statistic;

public class Table3 {
    private int categoryId;
    private String productId;
    private String productName;
    private int frequency;

    public Table3(int categoryId, String productId, String productName, int frequency) {
        this.categoryId = categoryId;
        this.productId = productId;
        this.productName = productName;
        this.frequency = frequency;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "NProductsByCategory{" +
                "categoryId=" + categoryId +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", frequency=" + frequency +
                '}';
    }
}
