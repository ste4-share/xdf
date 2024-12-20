package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.Mucgia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MucGiaRepo extends JpaRepository<Mucgia, Integer> {
    Optional<Mucgia> findMucGiaByIdAndStatus(int id, String status);

    @Query(value = "Select * from mucgia where quarter_id=:qId and item_id=:petroId and status like :s", nativeQuery = true)
    List<Mucgia> findAllMucgiaByItemID(@Param("petroId") int itemID,@Param("qId") int quarter_id,@Param("s") String s);

    @Query(value = "Select * from mucgia where quarter_id=:qId and item_id=:petroId and price=:price", nativeQuery = true)
    Optional<Mucgia> findAllMucgiaUnique(@Param("petroId") int itemID,@Param("qId") int quarter_id, @Param("price") int price);
}
