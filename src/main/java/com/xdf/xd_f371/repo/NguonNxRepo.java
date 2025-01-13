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
    @Query(value = "select nnx.* from nguon_nx nnx left join donvi_tructhuoc dvtt on dvi_tructhuoc_id=nnx.id where dvtt.id is null",nativeQuery = true)
    List<NguonNx> findByStatusUnlessTructhuoc();
    List<NguonNx> findByStatus(String status);
    @Query("select n from NguonNx n join n.donViTrucThuocs dvtt order by dvtt.pr")
    List<NguonNx> findByAllBy();
}
