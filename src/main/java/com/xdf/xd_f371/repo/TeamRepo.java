package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepo extends JpaRepository<Team,Integer> {
    @Query(value = "select * from team where team_code like :tc",nativeQuery = true)
    Team findByTeam_code(@Param("tc") String tc);
}
