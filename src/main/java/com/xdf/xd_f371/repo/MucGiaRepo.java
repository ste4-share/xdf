package com.xdf.xd_f371.repo;

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

    @Query(value = "Select * from mucgia where quarter_id=:qId and item_id=:petroId and purpose=:pur", nativeQuery = true)
    List<Mucgia> findAllMucgiaByItemID(@Param("pur") String purpose,@Param("petroId") int itemID,@Param("qId") int quarter_id);

    @Query(value = "Select * from mucgia where quarter_id=:qId and item_id=:petroId and purpose like :pur and price=:price", nativeQuery = true)
    Mucgia findAllMucgiaUnique(@Param("pur") String purpose,@Param("petroId") int itemID,@Param("qId") int quarter_id, @Param("price") int price);
}
