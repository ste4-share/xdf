package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TructhuocLoaiphieuDTO {
    private int id;
    private int tructhuoc_id;
    private int loaiphieu_id;
    private String type;
    private String name;

    public TructhuocLoaiphieuDTO(int id, int tructhuoc_id, int loaiphieu_id, String type, String name) {
        this.id = id;
        this.tructhuoc_id = tructhuoc_id;
        this.loaiphieu_id = loaiphieu_id;
        this.type = type;
        this.name = name;
    }

    public TructhuocLoaiphieuDTO() {
    }
}
