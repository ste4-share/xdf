package com.xdf.xd_f371.cons;

public enum MessageCons {
    TITLE_PRINT("Xác nhận"),
    HEADER_PRINT("Xác nhận"),
    CONTENT("Bạn có muốn in phiếu không ?");

    public final String name;

    MessageCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
