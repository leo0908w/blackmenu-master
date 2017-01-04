package com.org.iii.blackmenu;

/**
 * Created by user on 2017/1/4.
 */

public class Order {
    private String product;
    private String price;
    private String pathimg;
    private int number;

    public Order() {

    }

    public Order(String product, String price, String pathimg, int number) {
        this.product = product;
        this.price = price;
        this.pathimg = pathimg;
        this.number = number;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPathimg() {
        return pathimg;
    }

    public void setPathimg(String pathimg) {
        this.pathimg = pathimg;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}
