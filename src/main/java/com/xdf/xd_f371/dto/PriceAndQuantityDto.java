package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceAndQuantityDto {
    private int price;
    private int quantity;
    private String price_str;
    private String quantity_str;

    public PriceAndQuantityDto(int price, int quantity) {
        this.price = price;
        this.quantity = quantity;
        this.price_str = TextToNumber.textToNum(String.valueOf(price));
        this.quantity_str = TextToNumber.textToNum(String.valueOf(quantity));
    }
}
