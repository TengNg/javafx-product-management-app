package com.ndt.pojo;

import java.util.Objects;

public class Receipt {
    private int receiptId;
    private int customerId;
    private boolean isValid;

    private String status;

    public Receipt(int receiptId, int customerId) {
        this.receiptId = receiptId;
        this.customerId = customerId;
        this.isValid = true;
        this.status = "Unchecked";
    }

    public Receipt(int receiptId, int customerId, boolean isValid, String status) {
        this.receiptId = receiptId;
        this.customerId = customerId;
        this.isValid = isValid;
        this.status = status;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId=" + receiptId +
                ", customerId=" + customerId +
                ", status=" + status +
                ", isValid=" + isValid +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receipt receipt = (Receipt) o;

        if (receiptId != receipt.receiptId) return false;
        if (customerId != receipt.customerId) return false;
        if (isValid != receipt.isValid) return false;
        return Objects.equals(status, receipt.status);
    }

    @Override
    public int hashCode() {
        int result = receiptId;
        result = 31 * result + customerId;
        result = 31 * result + (isValid ? 1 : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
