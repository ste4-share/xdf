package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "surname")
    private String surname;
    @Column(name = "roles")
    private String roles;
    @Column(name = "passwd")
    private String passwd;
    @Column(name = "color")
    private String color;
    @Column(name = "status")
    private String status;
    @Column(name = "create_at")
    private LocalDate create_at;
}
