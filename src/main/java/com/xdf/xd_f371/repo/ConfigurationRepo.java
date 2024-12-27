package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepo extends JpaRepository<Configuration, Integer> {
    @Query(value = "select * from configuration where parameter like :p", nativeQuery = true)
    Optional<Configuration> findByParameter(@Param("p") String p);
}
