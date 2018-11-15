package model;

import com.google.gson.Gson;

public class Customer {
    private String name;
    private String email;
    private String address;
    private int phoneNum;

    public Customer()
    {}
    public Customer(String name, String email, String address, int phoneNum) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
