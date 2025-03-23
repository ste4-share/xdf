package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
public class QuarterDto {
    private int year;
    private int quarter;
    private LocalDate start_date;
    private LocalDate end_date;

    public QuarterDto(int year, int quarter, Timestamp start_date, Timestamp end_date) {
        this.year = year;
        this.quarter = quarter;
        this.start_date = start_date.toLocalDateTime().toLocalDate();
        this.end_date = end_date.toLocalDateTime().toLocalDate();
    }

    @Override
    public String toString() {
        return "QuarterDto{" +
                "year=" + year +
                ", quarter=" + quarter +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
