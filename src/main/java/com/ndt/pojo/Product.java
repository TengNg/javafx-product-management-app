package com.ndt.pojo;

import com.ndt.services.CategoryService;
import com.ndt.services.ProductService;

import java.sql.SQLException;

public class Product {
    private String id; // UUID
    private String name;
    private double price;
    private int categoryId;
    private int quantity;

    public Product(Product other) {
        this.id = other.getId();
        this.name = other.getName();
        this.price = other.getPrice();
        this.categoryId = other.getCategoryId();
        this.quantity = other.getQuantity();
    }

    public Product(String id, String name, double price, int categoryId, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() throws SQLException {
        CategoryService categoryService = new CategoryService();
        return categoryService.getCategoryNameById(this.categoryId);
    }


    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Double.compare(product.price, price) != 0) return false;
        if (categoryId != product.categoryId) return false;
        if (quantity != product.quantity) return false;
        if (!id.equals(product.id)) return false;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + categoryId;
        result = 31 * result + quantity;
        return result;
    }
}
