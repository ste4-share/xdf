package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.NhiemvuTeamDto;
import com.xdf.xd_f371.entity.NhiemVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhiemvuRepository extends JpaRepository<NhiemVu,Integer> {
    @Query("select new com.xdf.xd_f371.dto.NhiemvuTeamDto(data1.nvid, data1.tennv,data1.teamId,data1.p2,data1.p1,data1.ctnv,data1.tt) ta from (select nv.id as nvid,nv.tenNv as tennv,nv.teamId as teamId,nv.priorityBc2 as p2, t.priority as p1,t.name as ctnv,t.tt as tt from NhiemVu nv join nv.team t where nv.priorityBc2 is not null order by t.priority,nv.priorityBc2) data1 join ChitietNhiemVu ct on data1.nvid=ct.nhiemvu_id")
    List<NhiemvuTeamDto> findByTeam();
    @Query(value = "select * from nhiemvu where ten_nv like :n and status like :st",nativeQuery = true)
    Optional<NhiemVu> findByName(@Param("n") String n,@Param("st") String st);
}
