package com.xdf.xd_f371.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TTPhieuModel {
    private final StringProperty so = new SimpleStringProperty();
    private final StringProperty loai_phieu= new SimpleStringProperty();
    private final StringProperty dvn= new SimpleStringProperty();
    private final StringProperty dvvc= new SimpleStringProperty();
    private final StringProperty tcn= new SimpleStringProperty();
    private final StringProperty hang_hoa= new SimpleStringProperty();
    private final StringProperty tong= new SimpleStringProperty();
    private final StringProperty ngaytao= new SimpleStringProperty();

    public TTPhieuModel(String so2,String ngaytao2, String lp, String dvn1, String dvvc1, String tcn1, String hanghoa1, String tong1){
        so.set(so2);
        ngaytao.set(ngaytao2);
        loai_phieu.set(lp);
        dvn.set(dvn1);
        dvvc.set(dvvc1);
        tcn.set(tcn1);
        hang_hoa.set(hanghoa1);
        tong.set(tong1);
    }

    public TTPhieuModel() {
    }

    public String getSo() {
        return so.get();
    }

    public StringProperty soProperty() {
        return so;
    }

    public String getNgaytao() {
        return ngaytao.get();
    }
    public StringProperty ngaytaoProperty() {
        return ngaytao;
    }

    public String getLoai_phieu() {
        return loai_phieu.get();
    }

    public StringProperty loai_phieuProperty() {
        return loai_phieu;
    }

    public String getDvn() {
        return dvn.get();
    }

    public StringProperty dvnProperty() {
        return dvn;
    }

    public String getDvvc() {
        return dvvc.get();
    }

    public StringProperty dvvcProperty() {
        return dvvc;
    }

    public String getTcn() {
        return tcn.get();
    }

    public StringProperty tcnProperty() {
        return tcn;
    }

    public String getHang_hoa() {
        return hang_hoa.get();
    }

    public StringProperty hang_hoaProperty() {
        return hang_hoa;
    }

    public String getTong() {
        return tong.get();
    }

    public StringProperty tongProperty() {
        return tong;
    }

    public final void setSo(String sso){
        so.set(sso);
    }
    public final void setLoai_phieu(String lp){
        loai_phieu.set(lp);
    }
    public final void setDvvc(String dvvc1){
        dvvc.set(dvvc1);
    }
    public final void setDvn(String dvn1){
        dvn.set(dvn1);
    }
    public final void setTcn(String tcn1){
        tcn.set(tcn1);
    }
    public final void setHang_hoa(String hang_hoa1){
        hang_hoa.set(hang_hoa1);
    }
    public final void setTong(String tong1){
        tong.set(tong1);
    }
}
