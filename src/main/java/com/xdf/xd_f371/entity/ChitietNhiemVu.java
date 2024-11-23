package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chitiet_nhiemvu")
@Setter
@Getter
@NoArgsConstructor
public class ChitietNhiemVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nhiemvu_id")
    private int nhiemvu_id;
    @Column(name = "nhiemvu")
    private String nhiemvu;

    @ManyToOne
    @JoinColumn(name = "nhiemvu_id", referencedColumnName = "id", insertable = false, updatable = false)
    private NhiemVu nhiemVu;
}
