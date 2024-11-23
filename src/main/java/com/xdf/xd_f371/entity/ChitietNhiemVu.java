package com.xdf.xd_f371.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class ChitietNhiemVu {
    @Id
    private int id;
    private int nhiemvu_id;
    private String nhiemvu;
}
