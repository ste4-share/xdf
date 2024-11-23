package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Mucgia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MucGiaRepo extends JpaRepository<Mucgia, Integer> {
    Optional<Mucgia> findMucGiaByIdAndStatus(int id, String status);
}
