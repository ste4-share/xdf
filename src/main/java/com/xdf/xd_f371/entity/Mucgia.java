package com.xdf.xd_f371.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Mucgia {
    @Id
    private int id;
    private int price;
    private int amount;
    private int quarter_id;
    private int item_id;
    private int inventory_id;
    private int assign_type_id;
    private String status;
    private String timestamp;

}
