package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.NguonNx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NguonNxRepo extends JpaRepository<NguonNx,Integer> {
    Optional<NguonNx> findByTen(String ten);
    List<NguonNx> findByStatus(String status);
    @Query("select n from NguonNx n join n.donViTrucThuocs dvtt")
    List<NguonNx> findByAllBy();
}
