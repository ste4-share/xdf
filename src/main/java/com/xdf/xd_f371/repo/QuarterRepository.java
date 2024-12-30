package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuarterRepository extends JpaRepository<Quarter, Integer> {
    @Query(value = "select * from quarter where :current_time between start_date and end_date",nativeQuery = true)
    Optional<Quarter> findByCurrentTime(@Param("current_time") LocalDate currentTime);
    @Query(value = "select * from quarter where index like :in",nativeQuery = true)
    Optional<Quarter> findByIndex(@Param("in") String index);
    @Query(value = "select * from quarter where year like :y",nativeQuery = true)
    List<Quarter> findByYear(@Param("y") String year);
    @Query(value = "select * from quarter where year like :y and index like :i",nativeQuery = true)
    Optional<Quarter> findByUnique(@Param("y") String year,@Param("i") String i);
    @Query(value = "select * from quarter order by end_date desc limit 1",nativeQuery = true)
    Optional<Quarter> findPreviousTime();
}
