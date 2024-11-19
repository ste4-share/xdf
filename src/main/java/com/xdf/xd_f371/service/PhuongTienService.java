package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.ChitieuDTO;
import com.xdf.xd_f371.dto.NormDto;
import com.xdf.xd_f371.dto.StatusActive;
import com.xdf.xd_f371.entity.*;

import java.util.List;

public interface PhuongTienService {
    List<PhuongTien> getAll();
    List<NormDto> getAllPt(String typeName);
    List<LoaiPhuongTien> getLoaiPt(String typeName);
    List<LoaiPhuongTien> getAllLoaiPt();
    ChitieuDTO getChitieuDtoById(int pt_id, int quarterId);
    int createNewChiTieu(ChitieuDTO chitieuDTO);
    int updateChiTieu(int lxd_id, int quarterId);
    int createNewNorm(Norm norm);
    int updateNewNorm(Norm norm);
    LoaiPhuongTien findPtById(int id);
    List<NguonNx> getIdNguonnx();
    List<String> getTypeName();
    int createNew(PhuongTien phuongTien);
    int updateNew(PhuongTien phuongTien);
    int createNewPt(NormDto normDto);
    PhuongTien udpateObj(PhuongTien phuongTien) ;
    int createPt(PhuongTien phuongTien) ;
    PhuongTien findPhuongTienById(int id);
    List<PhuongTien> findPhuongTienByType(String type);
    List<NormDto> findListPhuongTienByType(int lpt_id);
    Integer findNnxByPt(String pt);
    List<StatusActive> getAllStatus();
    StatusActive findStatusByName(String status);


}
