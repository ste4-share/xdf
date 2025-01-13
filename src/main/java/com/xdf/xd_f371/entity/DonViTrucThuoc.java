package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "donvi_tructhuoc")
@Getter
@Setter
@NoArgsConstructor
public class DonViTrucThuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "root_id")
    private int root_id;
    @Column(name = "dvi_tructhuoc_id")
    private int dvi_tructhuoc_id;
    @Column(name = "pr")
    private int pr;

    @ManyToOne
    @JoinColumn(name = "dvi_tructhuoc_id", referencedColumnName = "id", updatable = false, insertable = false)
    private NguonNx nguonNx;
}
