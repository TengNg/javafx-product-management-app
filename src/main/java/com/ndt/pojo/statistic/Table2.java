package com.ndt.pojo.statistic;

import com.ndt.pojo.Customer;

public class Table2 {
    private Customer customer;
    private int nReceipts;

    public Table2(Customer customer, int nReceipts) {
        this.customer = customer;
        this.nReceipts = nReceipts;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getNReceipts() {
        return nReceipts;
    }

    public void setNReceipts(int nReceipts) {
        this.nReceipts = nReceipts;
    }

    public int getId() {
        return this.customer.getId();
    }

    public String getName() {
        return this.customer.getName();
    }

    public String getGender() {
        return this.customer.getGender();
    }

    public int getAge() {
        return this.customer.getAge();
    }

    @Override
    public String toString() {
        return "Table2{" +
                "customer=" + customer +
                ", nReceipts=" + nReceipts +
                '}';
    }
}
