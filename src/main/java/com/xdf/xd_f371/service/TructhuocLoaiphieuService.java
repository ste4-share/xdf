package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.TructhuocLoaiphieu;

public interface TructhuocLoaiphieuService {
    TructhuocLoaiphieu findByTructhuocId(int tt_id);
    TructhuocLoaiphieu findByTTLPId(int tt_id, int lp_id);
}
