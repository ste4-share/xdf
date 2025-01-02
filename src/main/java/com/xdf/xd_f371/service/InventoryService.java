package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.MucGiaEnum;
import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.InvDto;
import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.dto.LoaiXdLedgerDto;
import com.xdf.xd_f371.dto.TonkhoDto;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.entity.LoaiXangDau;
import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.repo.InventoryRepo;
import com.xdf.xd_f371.repo.LedgersRepo;
import com.xdf.xd_f371.repo.LoaiXangDauRepo;
import com.xdf.xd_f371.repo.QuarterRepository;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    private final LedgersRepo ledgersRepo;
    private final LoaiXangDauRepo loaiXangDauRepo;
    private final QuarterRepository quarterRepository;

    public List<Inventory> findByPetro_idAndDate(int petro_id, LocalDate sd, LocalDate ed){
        return inventoryRepo.findByPetro_idAndDate(petro_id, sd,ed);
    }
    public List<Inventory> findByPetro_idAndDateStatus(int petro_id,LocalDate sd, LocalDate ed,String status){
        return inventoryRepo.findByPetro_idAndDateStatus(petro_id, sd,ed,status);
    }
    public Optional<Inventory> findByUniqueGroupby(int xdid, LocalDate sd, LocalDate ed){
        return inventoryRepo.findByUniqueGroupby(xdid, sd,ed);
    }
    public Optional<Inventory> findByUnique(int petro_id, LocalDate sd, LocalDate ed,int p){
        return inventoryRepo.findByUnique(petro_id,sd,ed,p);
    }
    public Inventory save(Inventory inventory){
        return inventoryRepo.save(inventory);
    }
    public Inventory findById(int id){
        return inventoryRepo.findById(id).orElse(null);
    }
    public List<TonkhoDto> getAllTonkho(Quarter q){
        if (q!=null){
            return mapToTonkhoDto(inventoryRepo.getAllTonkho(q.getStart_date(),q.getEnd_date()));
        }
        return new ArrayList<>();
    }
    public List<TonkhoDto> mapToTonkhoDto(List<Object[]> results) {
        return results.stream()
                .map(row -> new TonkhoDto((int) row[0], (String) row[1], (String) row[2], (String) row[3],
                        (Long) row[4], (Long) row[5],  row[6].toString(),
                         row[7].toString(), row[8].toString(), row[9].toString(), row[10].toString(), row[11].toString()))
                .collect(Collectors.toList());
    }
    public List<InvDto> mapToInvDto(List<Object[]> results) {
        return results.stream()
                .map(row -> new InvDto((int) row[0],(String) row[1],(String) row[2],(int) row[3],(int) row[4],((BigDecimal) row[5]).intValue(),
                        ((BigDecimal) row[6]).intValue(),((BigDecimal) row[7]).intValue(),((BigDecimal) row[8]).intValue(), (LocalDate) row[9], (LocalDate) row[10]))
                .collect(Collectors.toList());
    }
    @Transactional
    public void saveInvWhenSwitchQuarter(Quarter q) {
        List<InvDto> previous_invs = mapToInvDto(ledgersRepo.findAllInvByQuarter(q.getStart_date(),q.getEnd_date()));
        if (!previous_invs.isEmpty()){
            previous_invs.forEach(x->{
                if (x.getNhap_nvdx()-x.getXuat_nvdx()<=0 && x.getNhap_sscd()-x.getXuat_sscd()<=0){
                    inventoryRepo.save(new Inventory(x.getPetro_id(),x.getNhap_nvdx()-x.getXuat_nvdx(),
                            x.getNhap_sscd()-x.getXuat_sscd(),x.getNhap_nvdx(), x.getNhap_sscd(),x.getXuat_nvdx(),x.getXuat_sscd(), MucGiaEnum.OUT_STOCK_ALL.getStatus(), x.getDon_gia(),x.getSd(),x.getEd()));
                } else {
                    inventoryRepo.save(new Inventory(x.getPetro_id(),x.getNhap_nvdx()-x.getXuat_nvdx(),
                            x.getNhap_sscd()-x.getXuat_sscd(),x.getNhap_nvdx(), x.getNhap_sscd(),x.getXuat_nvdx(),x.getXuat_sscd(), MucGiaEnum.IN_STOCK.getStatus(), x.getDon_gia(),x.getSd(),x.getEd()));
                }
            });
        }
    }
    @Transactional
    public void firstTimeSetup(Quarter q){
        Quarter quarter = quarterRepository.save(q);
        loaiXangDauRepo.findAll().forEach(x->{
            inventoryRepo.save(new Inventory(x.getId(),MucGiaEnum.OUT_STOCK_ALL.getStatus()));
        });
    }
    public List<LoaiXdLedgerDto> mapLoaixdLedger(List<Object[]> results) {
        List<LoaiXdLedgerDto> list = new ArrayList<>();

        results.forEach(x->{
            Date s = (Date) x[7];
            Date e = (Date) x[8];
            LocalDate seven = (s==null) ? LocalDate.now() : s.toLocalDate();
            LocalDate eight = (e==null) ? LocalDate.now() : e.toLocalDate();
            list.add(new LoaiXdLedgerDto((int) x[0],(String) x[1],
                    ((BigDecimal) x[2]).longValue(),((BigDecimal) x[3]).longValue(),
                    ((BigDecimal) x[4]).longValue(),((BigDecimal) x[5]).longValue(),
                    (int) x[6], seven, eight));
        });
        return list;
    }
    @Transactional
    public void changeRecordingRangeTime(Quarter q2){
        Quarter q = quarterRepository.save(q2);
        quarterRepository.updateStatus(StatusCons.DONE.getName());
        quarterRepository.updateStatusById(q.getId(),StatusCons.RECORDING.getName());
//        List<Ledger> ledgers = ledgersRepo.findAllByInDateRange(q.getStart_date(),q.getEnd_date());
        List<LoaiXdLedgerDto> beforeRangeLedger = mapLoaixdLedger(loaiXangDauRepo.findAllByLedgerBefore(q.getStart_date()));
//        Optional<Ledger> firstLedger = ledgersRepo.findFirstLedger();
        inventoryRepo.deleteAll();
        if (beforeRangeLedger.isEmpty()){
            List<LoaiXangDau> lxdList = loaiXangDauRepo.findAll();
            if (!lxdList.isEmpty()){
                lxdList.forEach(x->{
                    inventoryRepo.save(new Inventory(x.getId(),0,0,0,0,0,0,MucGiaEnum.OUT_STOCK_ALL.getStatus(), 0,
                            null,null));
                });
            }else{
                DialogMessage.errorShowing("Danh sách Xăng dầu trống. Vui lòng nhập thêm tại mục tồn kho.");
            }
        }else{
            beforeRangeLedger.forEach(x->{
                long tdk_nvdx = x.getNhap_nvdx()-x.getXuat_nvdx();
                long tdk_sscd = x.getNhap_sscd()-x.getXuat_sscd();
                if (tdk_sscd==0 && tdk_nvdx==0) {
                    inventoryRepo.save(new Inventory(x.getXd_id(), 0,0,
                            0,0,0,0,
                            MucGiaEnum.OUT_STOCK_ALL.getStatus(),Integer.parseInt(String.valueOf(x.getPrice())), x.getEd(),x.getEd()));
                } else {
                    inventoryRepo.save(new Inventory(x.getXd_id(), Integer.parseInt(String.valueOf(tdk_nvdx)),Integer.parseInt(String.valueOf(tdk_sscd)),
                            0,0,0,0,
                            MucGiaEnum.OUT_STOCK_ALL.getStatus(),Integer.parseInt(String.valueOf(x.getPrice())), x.getEd(),x.getEd()));
                }

            });
        }
    }
}
