package com.xdf.xd_f371.cons;

public enum MessageCons {
    TITLE_PRINT("Xác nhận"),
    HEADER_PRINT("Xác nhận"),
    CONTENT("Bạn có muốn in phiếu không ?"),
    CO_LOI_XAY_RA("Có lỗi xảy ra, vui lòng thử lại sau."),
    NOT_SELECT_END_TIME("Chưa chọn thời gian kết thúc."),
    NOT_SELECT_START_TIME("Chưa chọn thời gian kết thúc.");

    public final String name;

    MessageCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
