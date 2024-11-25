package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.GroupTitle;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.NguonnxTitle;

import java.util.List;

public interface NguonNXService {
    List<NguonNx> findNguonnxTructhuocF();
    List<GroupTitle> getAllGroup();
    List<NguonnxTitle> getAllNnxTitles(int groupId);
}
