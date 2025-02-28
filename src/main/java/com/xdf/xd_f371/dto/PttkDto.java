package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PttkDto {
    private String loai;
    private String tenxd;
    private String e916;
    private String e921;
    private String e923;
    private String e927;
    private String dnb;
    private String dka;
    private String dvi;
    private String dns;
    private String fb;
    private String tdv;

    public PttkDto(String loai, String tenxd, double e916, double e921, double e923, double e927, double dnb, double dka, double dvi, double dns, double fb) {
        this.loai = loai;
        this.tenxd = tenxd;
        this.e916 = TextToNumber.textToNum(String.valueOf(e916));
        this.e921 = TextToNumber.textToNum(String.valueOf(e921));
        this.e923 = TextToNumber.textToNum(String.valueOf(e923));
        this.e927 = TextToNumber.textToNum(String.valueOf(e927));
        this.dnb = TextToNumber.textToNum(String.valueOf(dnb));
        this.dka = TextToNumber.textToNum(String.valueOf(dka));
        this.dvi = TextToNumber.textToNum(String.valueOf(dvi));
        this.dns = TextToNumber.textToNum(String.valueOf(dns));
        this.fb = TextToNumber.textToNum(String.valueOf(fb));
        this.tdv = TextToNumber.textToNum(String.valueOf((e916+e921+e923+e927+dnb+dka+dvi+dns+fb)));
    }
}
