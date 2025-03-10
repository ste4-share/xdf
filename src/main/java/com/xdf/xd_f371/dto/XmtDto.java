package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class XmtDto {
    private int xmtId;
    private String name;
    private String loaiXmt;
    private Timestamp timestamp;
    private int quantity;

    public XmtDto(int xmtId, String name, String loaiXmt, Timestamp timestamp, Integer quantity) {
        this.xmtId = xmtId;
        this.name = name;
        this.loaiXmt = loaiXmt;
        this.timestamp = timestamp;
        this.quantity = quantity==null ? 0 : quantity;
    }
}
