package com.xdf.xd_f371.util;


import com.xdf.xd_f371.entity.Category;
import com.xdf.xd_f371.entity.InvReportDetail;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.model.ChungLoaiModel;

public class Common {

    public static boolean getInvCatalogField(Category category, Inventory invetory, InvReportDetail invReportDetail){
        if (category.getType_title().equals(ChungLoaiModel.TDK_a.getNameChungloai()) && category.getCode().equals(ChungLoaiModel.NVDX_a.getNameChungloai())){
            invReportDetail.setSoluong(invetory.getTdk_nvdx());
            return true;
        } else if (category.getType_title().equals(ChungLoaiModel.TDK_a.getNameChungloai()) && category.getCode().equals(ChungLoaiModel.SSCD_a.getNameChungloai())) {
            invReportDetail.setSoluong(invetory.getTdk_sscd());
            return true;
        }
        return false;
    }
}
