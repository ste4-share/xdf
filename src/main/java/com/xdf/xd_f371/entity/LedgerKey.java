package com.xdf.xd_f371.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LedgerKey implements Serializable {
    private String lenh_so;
    private long root_id;
    private int year;
    public LedgerKey() {
    }
    public LedgerKey(String lenh_so, long root_id, int year) {
        this.lenh_so = lenh_so;
        this.root_id = root_id;
        this.year = year;
    }
    public String getLenh_so() {
        return lenh_so;
    }
    public void setLenh_so(String lenh_so) {
        this.lenh_so = lenh_so;
    }
    public long getRoot_id() {
        return root_id;
    }

    public void setRoot_id(long root_id) {
        this.root_id = root_id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LedgerKey ledgerKey = (LedgerKey) o;
        return root_id == ledgerKey.root_id && year == ledgerKey.year && Objects.equals(lenh_so, ledgerKey.lenh_so);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lenh_so, root_id, year);
    }
}
