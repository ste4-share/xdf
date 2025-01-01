package com.xdf.xd_f371.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "quarter")
@Getter
@Setter
@NoArgsConstructor
public class Quarter{
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
    @Column(name = "status")
    private String status;

    public Quarter(LocalDate start_date, LocalDate end_date, String year, String index,String status) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.start_date = LocalDate.parse(start_date.toString(), formatter) ;
        this.end_date = LocalDate.parse(end_date.toString(), formatter) ;;
        this.year = year;
        this.index = index;
        this.status = status;
    }

    public Quarter(int id, LocalDate start_date, LocalDate end_date, String year,String index,String status) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.id = id;
        this.start_date = LocalDate.parse(start_date.toString(), formatter) ;
        this.end_date = LocalDate.parse(end_date.toString(), formatter) ;
        this.year = year;
        this.index = index;
        this.status = status;
    }
}
