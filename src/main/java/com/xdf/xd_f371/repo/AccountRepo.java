package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Accounts, Integer> {
    @Query(value = "select * from accounts where username like :u", nativeQuery = true)
    Accounts findByUsername(@Param("u") String username);
}
