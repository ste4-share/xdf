package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "phuongtien")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhuongTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "nguonnx_id")
    private int nguonnx_id;
    @Column(name = "loaiphuongtien_id")
    private int loaiphuongtien_id;
    @Column(name = "status")
    private String status;
}
