package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.MucGiaEnum;
import com.xdf.xd_f371.dto.InvDto;
import com.xdf.xd_f371.dto.TonkhoDto;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.repo.InventoryRepo;
import com.xdf.xd_f371.repo.LedgersRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    private final LedgersRepo ledgersRepo;

    public List<Inventory> findByQuarter_id(int quarter_id){
        return inventoryRepo.findByQuarter_id(quarter_id);
    }
    public List<Inventory> findByPetro_idAndQuarter_id(int petro_id, int quarter_id,String st){
        return inventoryRepo.findByPetro_idAndQuarter_id(petro_id, quarter_id,st);
    }
    public Optional<Inventory> findByUniqueGroupby(int xdid, int qid){
        return inventoryRepo.findByUniqueGroupby(xdid, qid);
    }
    public Optional<Inventory> findByUnique(int petro_id, int quarter_id,int p){
        return inventoryRepo.findByUnique(petro_id,quarter_id,p);
    }
    public Inventory save(Inventory inventory){
        return inventoryRepo.save(inventory);
    }
    public Inventory findById(int id){
        return inventoryRepo.findById(id).orElse(null);
    }
    public List<TonkhoDto> getAllTonkho(int quarter_id){
        return mapToTonkhoDto(inventoryRepo.getAllTonkho(quarter_id));
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
                        ((BigDecimal) row[6]).intValue(),((BigDecimal) row[7]).intValue(),((BigDecimal) row[8]).intValue()))
                .collect(Collectors.toList());
    }
    @Transactional
    public void saveInvWhenSwitchQuarter(Quarter q, int pre_q_id){
        List<InvDto> previous_invs = mapToInvDto(ledgersRepo.findAllInvByQuarter(q.getStart_date(),q.getEnd_date()));
        if (!previous_invs.isEmpty()){
            previous_invs.forEach(x->{
                if (x.getNhap_nvdx()-x.getXuat_nvdx()<=0 && x.getNhap_sscd()-x.getXuat_sscd()<=0){
                    inventoryRepo.save(new Inventory(x.getPetro_id(),pre_q_id,x.getNhap_nvdx()-x.getXuat_nvdx(),
                            x.getNhap_sscd()-x.getXuat_sscd(),x.getNhap_nvdx(), x.getNhap_sscd(),x.getXuat_nvdx(),x.getXuat_sscd(), MucGiaEnum.OUT_STOCK_ALL.getStatus(), x.getDon_gia()));
                } else {
                    inventoryRepo.save(new Inventory(x.getPetro_id(),pre_q_id,x.getNhap_nvdx()-x.getXuat_nvdx(),
                            x.getNhap_sscd()-x.getXuat_sscd(),x.getNhap_nvdx(), x.getNhap_sscd(),x.getXuat_nvdx(),x.getXuat_sscd(), MucGiaEnum.IN_STOCK.getStatus(), x.getDon_gia()));
                }
            });
        }
    }
}
