package com.xdf.xd_f371.model;

public enum ChungLoaiModel {
    SSCD_a("SSCD"),
    NVDX_a("NVDX"),
    TDK_a("TDK"),
    TCK_a("TCK"),
    NL_XANG_OTO("NL-XANG-OTO"),
    XANG("XANG"),
    DIEZEL("DIEZEL-XANG-OTO"),
    DAUBAY("DAUBAY"),
    DAUHACAP("Dầu hạ cấp"),
    DMN_MD_DCOTO("Dầu mỡ nhờn mặt đất - Động cơ ô tô"),
    DMN_MD_DTD("Dầu mỡ nhờn mặt đất - Dầu truyền động"),
    DMN_MD_DK("Dầu mỡ nhờn mặt đất - Dầu khác"),
    DMN_MD_MGMS("Dầu mỡ nhờn mặt đất - Mỡ giảm ma sát"),
    DMN_HK_DM("Dầu mỡ nhờn hàng không - DUng môi"),
    DMN_HK_DDC("Dầu mỡ nhờn hàng không - Dầu động cơ"),
    DMN_HK_DTL("Dầu mỡ nhờn hàng không - Dầu thủy lực"),
    DMN_HK_DK("Dầu mỡ nhờn hàng không - Dầu khác"),
    NVDX("DT cho NV đột xuất"),
    SSCD("DT SSCD"),
    TDK("Tồn đầu kỳ"),
    TCK("Tồn cuối kỳ"),
    DMN_HK_MN("Dầu mỡ nhờn hàng không - Mỡ nhờn");

    public final String name;

    ChungLoaiModel(String name) {
        this.name = name;
    }

    public String getNameChungloai(){
        return name;
    }
}
