package com.buildpc.models;

public class CPU extends Part {
    private String make;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public CPU (String name, double price, String make) {
        super(name, price);
        this.make = make;
    }
}
