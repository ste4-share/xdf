package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceAndQuantityDto {
    private double price;
    private double quantity_nvdx;
    private double quantity_sscd;
    private String price_str;
    private String quantitynvdx_str;
    private String quantitysscd_str;

    public PriceAndQuantityDto(double price, double quantity_nvdx,double quantity_sscd) {
        this.price = price;
        this.quantity_nvdx = quantity_nvdx;
        this.quantity_sscd = quantity_sscd;
        this.price_str = TextToNumber.textToNum_2digits(price);
        this.quantitynvdx_str = TextToNumber.textToNum_2digits(quantity_nvdx);
        this.quantitysscd_str = TextToNumber.textToNum_2digits(quantity_sscd);
    }
}
