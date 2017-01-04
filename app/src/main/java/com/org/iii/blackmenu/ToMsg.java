package com.org.iii.blackmenu;

/**
 * Created by W10User on 2017/1/3.
 */

public class ToMsg {
    private String product;
    private String price;
    private String pathimg;

    public ToMsg() {

    }

    public ToMsg(String product, String price, String pathimg) {
        this.product = product;
        this.price = price;
        this.pathimg = pathimg;
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
}
