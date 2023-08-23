package org.example;

import lombok.Data;
import lombok.Getter;

@Data
public class Doll {

    private String name;
    private double price;
    private int stock;

    public Doll(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
