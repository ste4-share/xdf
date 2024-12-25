package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "chitietNhiemVu", cascade = CascadeType.ALL)
    private List<NhiemvuTaubay> nhiemvuTaubays;

    @OneToMany(mappedBy = "chitietNhiemVu")
    private List<HanmucNhiemvu2> hanmucNhiemvu2 = new ArrayList<>();

    public ChitietNhiemVu(int id, int nhiemvu_id, String nhiemvu) {
        this.id = id;
        this.nhiemvu_id = nhiemvu_id;
        this.nhiemvu = nhiemvu;
    }
    public ChitietNhiemVu(int nhiemvu_id, String nhiemvu) {
        this.nhiemvu_id = nhiemvu_id;
        this.nhiemvu = nhiemvu;
    }
}
