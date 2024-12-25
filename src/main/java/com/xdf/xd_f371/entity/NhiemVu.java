package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nhiemvu")
@Getter
@Setter
@NoArgsConstructor
public class NhiemVu implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ten_nv")
    private String tenNv;
    @Column(name = "status")
    private String status;
    @Column(name = "team_id")
    private int teamId;
    @Column(name = "assignment_type_id")
    private Integer assignmentTypeId;
    @Column(name = "priority")
    private Integer priority;
    @Column(name = "priority_bc2")
    private Integer priorityBc2;

    @OneToMany(mappedBy = "nhiemVu", cascade = CascadeType.ALL)
    private List<ChitietNhiemVu> details = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Team team;
    @ManyToOne
    @JoinColumn(name = "assignment_type_id", referencedColumnName = "id" , insertable = false, updatable = false)
    private LoaiNhiemVu loaiNhiemVu;

    public NhiemVu(String tenNv, String status, int teamId, Integer assignmentTypeId, Integer priority, Integer priorityBc2) {
        this.tenNv = tenNv;
        this.status = status;
        this.teamId = teamId;
        this.assignmentTypeId = assignmentTypeId;
        this.priority = priority;
        this.priorityBc2 = priorityBc2;
    }
}
