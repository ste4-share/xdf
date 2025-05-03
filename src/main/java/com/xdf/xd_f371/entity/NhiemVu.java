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
    @Column(name = "priority")
    private Integer priority;
    @Column(name = "priority_bc2")
    private Integer priorityBc2;
    @Column(name = "loainv")
    private String loainv;
    @Column(name = "team_group")
    private String team_group;

    @OneToMany(mappedBy = "nhiemVu", cascade = CascadeType.ALL)
    private List<ChitietNhiemVu> details = new ArrayList<>();

    public NhiemVu(String tenNv, String status, Integer priority, Integer priorityBc2,String loainv,String team_group) {
        this.tenNv = tenNv;
        this.status = status;
        this.priority = priority;
        this.priorityBc2 = priorityBc2;
        this.loainv = loainv;
        this.team_group = team_group;
    }
}
