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
    @Column(name = "index")
    private int index;

    public Quarter(String name, LocalDate start_date, LocalDate end_date, String year, int index) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.year = year;
        this.index = index;
    }

    @OneToMany(mappedBy = "quarter",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DinhMuc> dinhMucList;
    @OneToMany(mappedBy = "quarter",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NhiemvuTaubay> nhiemvuTaubays;
}
