package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceAndQuantityDto {
    private double price;
    private double quantity;
    private String price_str;
    private String quantity_str;

    public PriceAndQuantityDto(double price, double quantity) {
        this.price = price;
        this.quantity = quantity;
        this.price_str = TextToNumber.textToNum(String.valueOf(price));
        this.quantity_str = TextToNumber.textToNum(String.valueOf(quantity));
    }
}
