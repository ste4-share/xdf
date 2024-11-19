package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuantityByTTDTO {
    private int title_id;
    private String ttname;
    private int sum;

    public QuantityByTTDTO(int title_id, String ttname, int sum) {
        this.title_id = title_id;
        this.ttname = ttname;
        this.sum = sum;
    }

}
