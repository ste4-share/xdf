package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
    private String ten_nv;
    @Column(name = "status")
    private String status;
    @Column(name = "team_id")
    private int teamId;
    @Column(name = "assignment_type_id")
    private Integer assignmentTypeId;
    @Column(name = "priority")
    private Integer priority;
}
