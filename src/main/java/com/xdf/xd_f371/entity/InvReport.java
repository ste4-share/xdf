package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvReport {
    private int id;
    private int petroleum_id;
    private int quarter_id;
    private int inventory_id;
    private int report_header;
    private int quantity;
    private int price_id;

    public InvReport() {
    }

    public InvReport(int id, int petroleum_id, int quarter_id, int inventory_id, int report_header, int quantity, int price_id) {
        this.id = id;
        this.petroleum_id = petroleum_id;
        this.quarter_id = quarter_id;
        this.inventory_id = inventory_id;
        this.report_header = report_header;
        this.quantity = quantity;
        this.price_id = price_id;
    }
}
