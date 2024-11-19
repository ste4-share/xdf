package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.PhuongTienNhiemVu;

import java.util.List;

public interface PhuongTienNhiemVuService {
    List<PhuongTienNhiemVu> getAll();
    PhuongTienNhiemVu createNew(PhuongTienNhiemVu phuongTienNhiemVu);
    PhuongTienNhiemVu update(PhuongTienNhiemVu phuongTienNhiemVu);
    List<PhuongTienNhiemVu> findByPhuongtienIdAndNvu_id(int phuongtien_id, int nvu_id);
    PhuongTienNhiemVu delete(PhuongTienNhiemVu phuongTienNhiemVu);
}
