package com.xdf.xd_f371.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
public class Quarter {
    @Id
    private int id;
    private String name;
    private LocalDate start_date;
    private LocalDate end_date;
    private String year;
    private String status;
    private String convey;

    public Quarter() {
    }

}
