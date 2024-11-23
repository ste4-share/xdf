package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.GroupTitle;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.NguonnxTitle;

import java.util.List;

public interface NguonNXService {
    List<NguonNx> getAllAndNguonnx();
    List<NguonNx> getAllUnless(String ten_1);
    NguonNx create(NguonNx nguonNx);
    int delete(NguonNx nguonNx, int groupId);
    NguonNx update(NguonNx nguonNx);
    NguonNx findById(int id);
    NguonNx findNguonNXByName_NON(String name);
    List<NguonNx> findNguonnxTructhuocF();

    List<GroupTitle> getAllGroup();
    GroupTitle findGroupById(int grid);
    List<NguonnxTitle> getAllNnxTitles(int groupId);
    int createNew(NguonnxTitle nguonnxTitle);
    int updateNguonnxTitle(NguonnxTitle nguonnxTitle);
}
