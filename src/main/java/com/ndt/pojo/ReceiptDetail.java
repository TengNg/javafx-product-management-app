package com.ndt.pojo;

import java.util.Objects;

public class ReceiptDetail {
    private int receiptId;
    private Product product;
    private int quantity;
    private boolean isOk;
    private boolean isChecked;

    public ReceiptDetail(int receiptId, Product product, int quantity, boolean isOk) {
        this.receiptId = receiptId;
        this.product = product;
        this.quantity = quantity;
        this.isOk = isOk;
        this.isChecked = false;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getIsOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "ReceiptDetail{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", is_ok=" + isOk +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReceiptDetail that = (ReceiptDetail) o;

        if (receiptId != that.receiptId) return false;
        if (quantity != that.quantity) return false;
        if (isOk != that.isOk) return false;
        if (isChecked != that.isChecked) return false;
        return product.equals(that.product);
    }

    @Override
    public int hashCode() {
        int result = receiptId;
        result = 31 * result + product.hashCode();
        result = 31 * result + quantity;
        result = 31 * result + (isOk ? 1 : 0);
        result = 31 * result + (isChecked ? 1 : 0);
        return result;
    }
}
