package com.xdf.xd_f371.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "quarter")
@Getter
@Setter
@NoArgsConstructor
public class Quarter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "start_date")
    private LocalDate start_date;
    @Column(name = "end_date")
    private LocalDate end_date;
    @Column(name = "year")
    private String year;
    @Column(name = "index")
    private String index;

    @Transient
    private String s;
    @Transient
    private String e;

    public Quarter(LocalDate start_date, LocalDate end_date, String year, String index) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.year = year;
        this.index = index;
        this.s = start_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.e = end_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public Quarter(int id, LocalDate start_date, LocalDate end_date, String year,String index) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.year = year;
        this.index = index;
        this.s = start_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.e = end_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
