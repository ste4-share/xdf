package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {
    List<Inventory> findByQuarter_id(int quarter_id);
    Optional<Inventory> findByPetro_idAndQuarter_id(int petro_id, int quarter_id);
}
