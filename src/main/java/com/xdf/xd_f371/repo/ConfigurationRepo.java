package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigurationRepo extends JpaRepository<Configuration, Integer> {
    @Query(value = "select * from configuration where parameter like :p", nativeQuery = true)
    Optional<Configuration> findByParameter(@Param("p") String p);
    @Modifying
    @Query(value = "update configuration set value=:val where parameter like :p",nativeQuery = true)
    void updateConfigByParam(@Param("p") String p,@Param("val") String val);
    @Query(value = "SELECT year,quarter,('01-01'::text || '-' || year)::date + (quarter - 1) * INTERVAL '3 months' AS start_date,('01-01'::text || '-' || year)::date + quarter * INTERVAL '3 months' - INTERVAL '1 day' AS end_date " +
            "FROM (SELECT :y AS year, generate_series(1, 4) AS quarter) q;",nativeQuery = true)
    List<Object[]> getListQuarter(@Param("y") int y);
}
