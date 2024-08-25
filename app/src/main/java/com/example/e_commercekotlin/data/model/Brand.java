package com.example.e_commercekotlin.data.model;

public class Brand {
    int imageBrand;
    String name;
    String discount;

    public String getOffer() {
        return discount;
    }

    public void setOffer(String discount) {
        this.discount = discount;
    }

    public Brand(int imageBrand, String name, String discount) {
        this.imageBrand = imageBrand;
        this.name = name;
        this.discount = discount;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageBrand() {
        return imageBrand;
    }

    public void setImageBrand(int imageBrand) {
        this.imageBrand = imageBrand;
    }
}
