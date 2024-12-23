package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.TructhuocDto;
import com.xdf.xd_f371.entity.TrucThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TructhuocRepo extends JpaRepository<TrucThuoc, Integer> {
    @Query("select new com.xdf.xd_f371.dto.TructhuocDto(tt.id, t.id, tt.ten,t.name, t.tennhom_tructhuoc,t.timestamp,tt.code, tt.status) from TrucThuoc t join t.nxList tt order by tt.id")
    List<TructhuocDto> findAllBy();
    @Query(value = "select * from tructhuoc where name like :n",nativeQuery = true)
    TrucThuoc findTructhuocByName(@Param("n") String name);
}
