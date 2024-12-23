package com.xdf.xd_f371.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class TructhuocDto implements Serializable {
    private int nguonnx_id;
    private int tructhuoc_id;
    private String nguonnx_name;
    private String tentructhuoc;
    private String nhomtructhuoc;
    private String timestamp;
    private String code;
    private String status;

    public TructhuocDto() {
    }

}
