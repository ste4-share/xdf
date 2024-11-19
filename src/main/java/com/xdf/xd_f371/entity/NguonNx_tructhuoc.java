package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NguonNx_tructhuoc {
    private int id;
    private int nguonnx_id;
    private int tructhuoc_id;

    public NguonNx_tructhuoc() {
    }

    public NguonNx_tructhuoc(int id, int nguonnx_id, int tructhuoc_id) {
        this.id = id;
        this.nguonnx_id = nguonnx_id;
        this.tructhuoc_id = tructhuoc_id;
    }
}
