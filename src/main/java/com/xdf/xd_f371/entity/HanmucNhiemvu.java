package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.postgresql.util.PGInterval;

import java.io.Serializable;
import java.time.Duration;

@Entity
@Table(name = "hanmuc_nhiemvu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HanmucNhiemvu implements Serializable {

    private static final long serialVersionUID = -7941769011539363185L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "quarter_id")
    private int quarter_id;
    @Column(name = "unit_id")
    private int unit_id;
    @Column(name = "nhiemvu_id")
    private int nhiemvu_id;
    @Column(name = "ct_tk")
    private String ct_tk;
    @Column(name = "ct_md")
    private String ct_md;
    @Column(name = "consumpt")
    private int consumpt;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    private NguonNx nguonNx;

    @ManyToOne
    @JoinColumn(name = "nhiemvu_id", referencedColumnName = "id", insertable = false, updatable = false)
    private NhiemVu nhiemVu;

}
