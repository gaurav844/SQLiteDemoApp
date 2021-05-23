package com.taskom.sqlitedemoapp;

public class CustomerModel {

    int customer_id,customer_age;
    String customer_name;

    public CustomerModel(int customer_id, int customer_age, String customer_name) {
        this.customer_id = customer_id;
        this.customer_age = customer_age;
        this.customer_name = customer_name;
    }

    @Override
    public String toString() {
        return "customer_id=" + customer_id +
                ",\ncustomer_age=" + customer_age +
                ",\ncustomer_name='" + customer_name;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setCustomer_age(int customer_age) {
        this.customer_age = customer_age;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public int getCustomer_age() {
        return customer_age;
    }

    public String getCustomer_name() {
        return customer_name;
    }
}
