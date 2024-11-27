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

}
