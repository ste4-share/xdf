package com.xdf.xd_f371.dto;

import java.io.Serializable;

public class TructhuocDto implements Serializable {
    private int nguonnx_id;
    private int tructhuoc_id;
    private String nguonnx_name;
    private String tentructhuoc;
    private String nhomtructhuoc;
    private String timestamp;

    public TructhuocDto() {
    }

    public TructhuocDto(int nguonnx_id, int tructhuoc_id, String nguonnx_name, String tentructhuoc, String nhomtructhuoc, String timestamp) {
        this.nguonnx_id = nguonnx_id;
        this.tructhuoc_id = tructhuoc_id;
        this.nguonnx_name = nguonnx_name;
        this.tentructhuoc = tentructhuoc;
        this.nhomtructhuoc = nhomtructhuoc;
        this.timestamp = timestamp;
    }

    public int getNguonnx_id() {
        return nguonnx_id;
    }

    public void setNguonnx_id(int nguonnx_id) {
        this.nguonnx_id = nguonnx_id;
    }

    public int getTructhuoc_id() {
        return tructhuoc_id;
    }

    public void setTructhuoc_id(int tructhuoc_id) {
        this.tructhuoc_id = tructhuoc_id;
    }

    public String getNguonnx_name() {
        return nguonnx_name;
    }

    public void setNguonnx_name(String nguonnx_name) {
        this.nguonnx_name = nguonnx_name;
    }

    public String getTentructhuoc() {
        return tentructhuoc;
    }

    public void setTentructhuoc(String tentructhuoc) {
        this.tentructhuoc = tentructhuoc;
    }

    public String getNhomtructhuoc() {
        return nhomtructhuoc;
    }

    public void setNhomtructhuoc(String nhomtructhuoc) {
        this.nhomtructhuoc = nhomtructhuoc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
