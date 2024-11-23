package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface QuarterRepository extends JpaRepository<Quarter, Integer> {
    @Query(value = "select * from quarter where :current_time between start_date and end_date",nativeQuery = true)
    Optional<Quarter> findByCurrentTime(@Param("current_time") LocalDate currentTime);

    @Query(value = "select * from quarter where name=:name",nativeQuery = true)
    Optional<Quarter> findByName(@Param("name") String name);
}
