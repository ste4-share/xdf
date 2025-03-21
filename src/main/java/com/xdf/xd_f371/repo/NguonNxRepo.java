package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.NguonNx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NguonNxRepo extends JpaRepository<NguonNx,Integer> {
    Optional<NguonNx> findByTen(String ten);
    @Query("select n from NguonNx n join n.donViTrucThuocs dvtt order by dvtt.pr")
    List<NguonNx> findByAllBy();
    @Query(value = "select * from nguon_nx where id <> :nid",nativeQuery = true)
    List<NguonNx> findAllByDifrentId(@Param("nid") int id);
    @Query(value = "select * from nguon_nx where id=:nid",nativeQuery = true)
    List<NguonNx> findAllById(@Param("nid") int id);
}
