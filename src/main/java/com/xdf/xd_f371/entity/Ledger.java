package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class Ledger {
    private int id;
    private int quarter_id;
    private int bill_id;
    private int billType_id;
    private int amount;
    private Date from_date;
    private Date end_date;
    private String status;

    public Ledger() {
    }

    public int getBillType_id() {
        return billType_id;
    }

    public void setBillType_id(int billType_id) {
        this.billType_id = billType_id;
    }

    public Ledger(int id, int quarter_id, int bill_id, int amount, Date from_date, Date end_date, String status) {
        this.id = id;
        this.quarter_id = quarter_id;
        this.bill_id = bill_id;
        this.amount = amount;
        this.from_date = from_date;
        this.end_date = end_date;
        this.status = status;
    }
}
