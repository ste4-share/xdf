package com.xdf.xd_f371.model;

import java.util.*;

public class ChungloaiMap {
    public static Map<String, String> getMapChungloai(){
        Map<String, String> map = new HashMap<>();
        map.put("NL", "Nhiên liệu");
        map.put("DMN-MD", "DMN nhờn MĐ");
        map.put("DMN", "DM nhờn");
        map.put("DMN-HK", "DMN nhờn HK");
        map.put("NL-XANG-OTO", "Xăng ô tô");
        map.put("XANG-OTO", "Xăng ô tô");
        map.put("DIEZEL", "Diezel");
        map.put("DAUHACAP", "Dầu hạ cấp");
        map.put("DAUBAY", "Dầu bay");
        map.put("DMN-MD-DCOTO", "Dầu đ.cơ ô tô");
        map.put("DMN-MD-DTD", "Dầu truyền động");
        map.put("DMN-MD-DK", "Dầu khác");
        map.put("DMN-MD-MGMS", "Mỡ giảm ma sát");
        map.put("DMN-HK-DM", "Dung môi");
        map.put("DMN-HK-MN", "Mỡ nhờn");
        map.put("DMN-HK-DK", "Dầu khác");
        map.put("DMN-HK-DTL", "Dầu thủy lực");
        map.put("DMN-HK-DDC", "Dầu động cơ");
        return map;
    }
    public static List<String> getMapChungloaiTitle(){
        List<String> list = new ArrayList<>();
        list.add("NL-XANG-OTO");
        list.add("DIEZEL-XANG-OTO");
        list.add("DAUHACAP");
        list.add("DAUBAY");
        list.add("DMN-MD-DCOTO");
        list.add( "DMN-MD-DTD");
        list.add("DMN-MD-DK");
        list.add( "DMN-MD-MGMS");
        list.add("DMN-HK-DM");
        list.add("DMN-HK-MN");
        list.add("DMN-HK-DK");
        list.add("DMN-HK-DTL");
        list.add("DMN-HK-DDC");
        return list;
    }

    public static Map<String, String> titleMap(){
        Map<String, String> map = new HashMap<>();
        map.put("TDK-NVDX", "DT cho NV đột xuất");
        map.put("TDK-SSCD", "DT SSCĐ");
        map.put("TDK-CONG", "Cộng");
        map.put("CXD", "CXD");
        map.put("QC", "QC");
        map.put("PC", "Mua P/cấp");
        map.put("NHAP-TRONGQC", "Trong QC");
        map.put("NHAP-KHAC", "Khác");
        map.put("NHAP-CONG", "Cộng");
        map.put("TT-XE-MAY", "TT Xe máy");
        map.put("BQ", "BQ");
        map.put("HAO-HUT", "Hao hụt");
        map.put("XUAT-TRONGQC", "Trong QC");
        map.put("XUAT-NGOAIQC", "Ngoài QC");
        map.put("TONTHAT", "Tổn thất");
        map.put("XUAT-KHAC", "Khác");
        map.put("XUAT-CONG", "Cộng");
        map.put("TCK-NVDX", "DT cho NV đột xuất");
        map.put("TCK-SSCD", "DT SSCĐ");
        map.put("TCK-CONG", "Cộng");
        return map;
    }

    public static Map<String, String> type_Str_detail(){
        Map<String,String> map = new LinkedHashMap<>();
        map.put("NL", "Nhiên liệu");
        map.put("DMN-MD", "DMN nhờn MĐ");
        map.put("DMN-HK", "DMN nhờn HK");
        return map;
    }


}
