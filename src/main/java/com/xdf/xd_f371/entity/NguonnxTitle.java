package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NguonnxTitle {
    private int id;
    private int nguonnx_id;
    private int title_id;
    private int group_id;

    public NguonnxTitle() {
    }

    public NguonnxTitle(int id, int nguonnx_id, int title_id, int group_id) {
        this.id = id;
        this.nguonnx_id = nguonnx_id;
        this.title_id = title_id;
        this.group_id = group_id;
    }

    public NguonnxTitle(int nguonnx_id, int title_id, int group_id) {
        this.nguonnx_id = nguonnx_id;
        this.title_id = title_id;
        this.group_id = group_id;
    }

}
