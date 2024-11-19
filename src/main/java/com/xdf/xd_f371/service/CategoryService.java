package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.AssignmentCategory;
import com.xdf.xd_f371.dto.TitleDto;
import com.xdf.xd_f371.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    List<AssignmentCategory> getAllAssCategory();
    List<String> getAllTypeTitle();
    List<String> getAllCode();
    List<String> getAll_Header1();
    List<String> getAll_Header2();
    List<String> getAll_Header3();
    int create(Category category);
    int updateAndDoNotConflic(Category category);
    int update(Category category);
    int updateType(Category category);
    int delete(int category_id);
    TitleDto getTitleById(int catalog);
    Category getTitleByttLpId(int tructhuocId, String type);
    Category findByCode(String code, String type);
    List<Category> getCategoryByTructhuocId(int tructhuocId);
    int deleteBytructhuocId(int tructhuocId);
}
