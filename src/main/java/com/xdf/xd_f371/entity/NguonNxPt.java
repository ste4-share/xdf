package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nguonnx_pt")
@Getter
@Setter
@NoArgsConstructor
public class NguonNxPt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nguonnx_id")
    private Long nguonnx_id;
    @Column(name = "pt_id")
    private Long pt_id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "note")
    private String note;
}
