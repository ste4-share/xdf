package com.xdf.xd_f371.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "quarter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quarter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "start_date")
    private LocalDate start_date;
    @Column(name = "end_date")
    private LocalDate end_date;
    @Column(name = "year")
    private String year;
    @Column(name = "status")
    private String status;
    @Column(name = "convey")
    private String convey;

    @OneToMany(mappedBy = "quarter",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DinhMuc> dinhMucList;
}
