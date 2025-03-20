package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.InventoryUnits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryUnitsRepo extends JpaRepository<InventoryUnits,Long> {
    @Query(value = "select * from inventory_units where status like 'ACTIVE' and root_unit_id=:root_id",nativeQuery = true)
    List<InventoryUnits> getInventoryByUnit(@Param("root_id")Long dvn_id);
    @Query(value = "select * from inventory_units where status like 'ACTIVE' and root_unit_id=:root_id and petro_id=:pid and year=:y",nativeQuery = true)
    List<InventoryUnits> getInventoryByUnitByPetro(@Param("root_id")Long dvn_id,@Param("pid")int p_id,@Param("y") int year);
    @Query(value = "select * from inventory_units where status like 'ACTIVE' and root_unit_id=:root_id and petro_id=:pid and price=:pric",nativeQuery = true)
    Optional<InventoryUnits> getInventoryByUnitByPetroByPrice(@Param("root_id")Long dvn_id, @Param("pid")int p_id, @Param("pric")double price);
    @Modifying
    @Query(value = "update inventory_units set nvdx_quantity=:n,sscd_quantity=:s where status like 'ACTIVE' and root_unit_id=:root_id and petro_id=:p_id and price=:pric",nativeQuery = true)
    void updateQuantityForPetro(@Param("n") double nvdx,@Param("s") double sscd,@Param("root_id") Long root_id,@Param("p_id") int petro_id,@Param("pric") double price);
}
