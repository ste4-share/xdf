package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Tcn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TcnRepo extends JpaRepository<Tcn, Integer> {
    String findByName(String name);
}
