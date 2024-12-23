package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loai_nhiemvu")
@Getter
@Setter
@NoArgsConstructor
public class LoaiNhiemVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "task_name")
    private String task_name;

    @OneToMany(mappedBy = "loaiNhiemVu", cascade = CascadeType.ALL, orphanRemoval = true)
    List<NhiemVu> nhiemVuList = new ArrayList<>();

    public LoaiNhiemVu(String task_name) {
        this.task_name = task_name;
    }
}
