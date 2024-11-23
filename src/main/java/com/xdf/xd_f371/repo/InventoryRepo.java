package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {
    @Query(value = "select * from inventory where quarter_id=:quarter_id",nativeQuery = true)
    List<Inventory> findByQuarter_id(@Param("quarter_id") int quarter_id);
    @Query(value = "select * from inventory where petro_id=:petro_id and quarter_id=:quarter_id",nativeQuery = true)
    Optional<Inventory> findByPetro_idAndQuarter_id(@Param("petro_id") int petro_id,@Param("quarter_id") int quarter_id);
}
