package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.UnitXmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitXmtRepo extends JpaRepository<UnitXmt, String> {
    @Query(value = "select * from unit_xmt where unit_id=:uid",nativeQuery = true)
    List<UnitXmt> findByUnitId(@Param("uid") int id);
    @Query(value = "select ux.* from unit_xmt ux left join phuongtien p on ux.xmt_id=p.id where p.tinhchat like 'MAYBAY' and unit_id=:uid",nativeQuery = true)
    List<UnitXmt> findAllByMaybay(@Param("uid") int id);
    @Query(value = "select * from unit_xmt where unit_id=:uid and xmt_id=:pid",nativeQuery = true)
    List<UnitXmt> findByUnitIdPtId(@Param("uid") int id,@Param("pid") int ptid);
    @Query(value = "select * from unit_xmt where licence_plate_number like :l",nativeQuery = true)
    Optional<UnitXmt> findByLicensePlate(@Param("l") String license);
    @Query(value = "select u.id from unit_xmt u join phuongtien p on u.xmt_id=p.id where p.loaipt like 'MAYBAY'",nativeQuery = true)
    List<String> findXmtIdList();
    @Modifying
    @Query(value = "update unit_xmt set note=:note,dm_hours=:dmh,dm_km=:dmkm,dm_md=:dmmd,dm_tk=:dmtk,status=:s where unit_id=:uid and xmt_id=:pid and licence_plate_number like :license",nativeQuery = true)
    void updateUnitXmtByPtAndUnit(@Param("note") String note,@Param("uid") int uid,@Param("pid") int pid,@Param("dmh") double dm_hours,@Param("dmkm") double dm_km,
                                  @Param("dmmd") double dmmd,@Param("dmtk") double dmtk,@Param("license") String license,@Param("s") String status);
}
