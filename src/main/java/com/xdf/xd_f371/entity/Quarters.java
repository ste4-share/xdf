package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name = "quarters")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Quarters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "quarter_name")
    private String quarter_name;
    @Column(name = "year")
    private int year;
    @Column(name = "start")
    private Date start;
    @Column(name = "end")
    private Date end;
}
