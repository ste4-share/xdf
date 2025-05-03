package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.LedgerDto;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import com.xdf.xd_f371.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LedgersRepo extends JpaRepository<Ledger, Long> {
    @Query(value = "select new com.xdf.xd_f371.dto.LedgerDto(ld.ledger_id,ld.id, l.bill_id,l.amount,l.from_date,l.end_date,l.status,l.so_km,l.giohd_md,l.giohd_tk," +
            "l.dvi_nhan_id,l.dvi_xuat_id,l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.loaigiobay," +
            "l.nguoi_nhan,l.so_xe,l.lenh_so,l.nhiemvu,l.nhiemvu_id,l.tcn_id,ld.ma_xd,ld.ten_xd,ld.chung_loai,ld.chat_luong,ld.phai_xuat,ld.nhiet_do_tt,ld.ty_trong," +
            "ld.he_so_vcf,ld.don_gia,ld.loaixd_id,ld.phuongtien_id,ld.thuc_xuat, ld.thuc_xuat_tk,ld.soluong,ld.thuc_nhap," +
            "ld.phai_nhap,ld.haohut_sl,l.loainv,ld.nl_gio,ld.nl_km,ld.soluong_px) from Ledger l join l.ledgerDetails ld where l.id=:i")
    List<LedgerDto> findLedgerByID(@Param("i") String billId);
    @Query("select new com.xdf.xd_f371.dto.MiniLedgerDto(l.id,l.bill_id, l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.timestamp,l.nhiemvu," +
            "a.username, count(ld.ten_xd), sum(ld.soluong*ld.don_gia)) from Ledger l join " +
            "l.ledgerDetails ld join l.accounts a where l.status like :s " +
            "group by 1,2,3,4,5,6,7,8 order by l.timestamp desc")
    List<MiniLedgerDto> findAllInterfaceLedger(@Param("s") String s);
    @Modifying
    @Query(value = "update ledgers l set status='IN_ACTIVE' where l.id=:i", nativeQuery = true)
    void inactiveLedgers(@Param("i") String id);

    @Query("SELECT l FROM Ledger l LEFT JOIN FETCH l.ledgerDetails WHERE l.status like 'ACTIVE' and l.from_date between :sd and :ed and l.root_id=:dvid order by l.bill_id desc")
    List<Ledger> findAllLedgerDtoByTime(@Param("sd") LocalDate sd,@Param("ed") LocalDate ed,@Param("dvid") int dvid);
    @Query("SELECT l FROM Ledger l LEFT JOIN FETCH l.ledgerDetails WHERE l.status like 'ACTIVE' and l.from_date between :sd and :ed")
    List<Ledger> findAllLedgerDtoByTime(@Param("sd") LocalDate sd,@Param("ed") LocalDate ed);
    @Query(value = "select * from ledgers l where status like 'ACTIVE' and l.root_id=:dvid and year=:y order by bill_id desc",nativeQuery = true)
    List<Ledger> findAllLedgerByUnit(@Param("dvid") int dvid,@Param("y") int y);
    @Query(value = "select * from ledgers l where status like 'ACTIVE'",nativeQuery = true)
    List<Ledger> findAllLedgerActive();
    @Query(value = "SELECT column_name FROM information_schema.columns WHERE table_schema = 'public' AND table_name ='ledgers';",nativeQuery = true)
    List<String> getColumnNames_LEDGER();
    @Query(value = "SELECT column_name FROM information_schema.columns WHERE table_schema = 'public' AND table_name ='ledger_details';",nativeQuery = true)
    List<String> getColumnNames_LEDGER_DETAIL();
    @Query(value = "SELECT column_name FROM information_schema.columns WHERE table_schema = 'public' AND table_name ='transaction_history';",nativeQuery = true)
    List<String> getColumnNames_TRANSACTION_HISTORY();
    @Query(value = "SELECT column_name FROM information_schema.columns WHERE table_schema = 'public' AND table_name ='hanmuc_nhiemvu2';",nativeQuery = true)
    List<String> getColumnNames_HANMUCNHIEMVU();
    @Query(value = "SELECT column_name FROM information_schema.columns WHERE table_schema = 'public' AND table_name ='hanmuc_nhiemvu_taubay';",nativeQuery = true)
    List<String> getColumnNames_HANMUCNHIEMVU_TAUBAY();
    @Modifying
    @Query(value = "UPDATE ledgers SET bill_id = :b1 WHERE id like :id",nativeQuery = true)
    void updateLedger(@Param("id") String id,@Param("b1") String billId);
}
