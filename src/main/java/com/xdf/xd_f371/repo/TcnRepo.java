package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Tcn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TcnRepo extends JpaRepository<Tcn, Integer> {
    Optional<Tcn> findByName(String name);
    @Query(value = "select * from tcn where ma_tcn like :m",nativeQuery = true)
    Optional<Tcn> findByMaTcn(@Param("m") String mtc);
    List<Tcn> findByLoaiphieu(String loaiphieu);
}
