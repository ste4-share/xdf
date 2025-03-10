package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.DinhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DinhMucRepo extends JpaRepository<DinhMuc, Integer> {
    @Query("select d from PhuongTien p join p.dinhmuc d where d.years=:y and d.phuongtien_id=:pt_id")
    Optional<DinhMuc> findDinhmucByPhuongtien(@Param("pt_id") int pt_id, @Param("y") int y);
    @Query(value = "select years from dinhmuc group by 1",nativeQuery = true)
    List<Integer> findAllByYear();
}
