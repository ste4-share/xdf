package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    @Column(name = "create_at",insertable = false,updatable = false)
    private LocalDate create_at;
    @Column(name = "sd")
    private LocalDate sd;
    @Column(name = "ed")
    private LocalDate ed;

    @OneToMany(mappedBy = "accounts")
    List<Ledger> ledgerList;

    public Accounts(String username, String surname, String roles, String passwd, String color, String status) {
        this.username = username;
        this.surname = surname;
        this.roles = roles;
        this.passwd = passwd;
        this.color = color;
        this.status = status;
    }
}
