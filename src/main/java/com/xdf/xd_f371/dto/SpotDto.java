package com.xdf.xd_f371.dto;

public class SpotDto {
    private int stt;
    private int lxd_id;
    private String maxd;
    private String tenxd;
    private String nvdx_total;
    private String sscd_total;
    private String total;

    public SpotDto() {
    }

    public SpotDto(int stt, int lxd_id, String maxd, String tenxd, String nvdx_total, String sscd_total, String total) {
        this.stt = stt;
        this.lxd_id = lxd_id;
        this.maxd = maxd;
        this.tenxd = tenxd;
        this.nvdx_total = nvdx_total;
        this.sscd_total = sscd_total;
        this.total = total;
    }

    public int getLxd_id() {
        return lxd_id;
    }

    public void setLxd_id(int lxd_id) {
        this.lxd_id = lxd_id;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getMaxd() {
        return maxd;
    }

    public void setMaxd(String maxd) {
        this.maxd = maxd;
    }

    public String getTenxd() {
        return tenxd;
    }

    public void setTenxd(String tenxd) {
        this.tenxd = tenxd;
    }

    public String getNvdx_total() {
        return nvdx_total;
    }

    public void setNvdx_total(String nvdx_total) {
        this.nvdx_total = nvdx_total;
    }

    public String getSscd_total() {
        return sscd_total;
    }

    public void setSscd_total(String sscd_total) {
        this.sscd_total = sscd_total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
