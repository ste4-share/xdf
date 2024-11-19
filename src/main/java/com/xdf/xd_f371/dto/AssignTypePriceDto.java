package com.xdf.xd_f371.dto;

public class AssignTypePriceDto {
    private int id;
    private String price;
    private String soluong;

    public AssignTypePriceDto(int id, String price, String soluong) {
        this.id = id;
        this.price = price;
        this.soluong = soluong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }
}
