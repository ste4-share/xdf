package com.xdf.xd_f371.cons;

public enum SubQueryEnum {
    nl_begin_q1("select max(stt_index) as stt,tinhchat,loai,CASE WHEN tenxd is null and grouping(tinhchat)=0 then loai else tenxd end,sum(tdk_sscd) as tdk_sscd,sum(tdk_nvdx) as tdk_nvdx,sum(cong) as cong,"),
    nl_end_q1("max(priority_3), grouping(tinhchat) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as xd_gr, max(priority_3) as pr from (select stt_index,tinhchat,loai,tenxd,tdk_sscd,tdk_nvdx,tdk_sscd+tdk_nvdx as cong,priority_3,"),
    nl_end("'ss' from inventory i join loaixd2 lxd on i.petro_id=lxd.id join chungloaixd cl on lxd.petroleum_type_id=cl.id where tinhchat like 'Nhiên liệu') s group by rollup(tinhchat,loai,tenxd) order by tc_gr desc,loai_gr desc,pr asc,xd_gr desc"),
    dmn_begin_q1("select max(stt_index) as stt,chungloai,loai,CASE WHEN tenxd is null and grouping(chungloai)=0 then loai else tenxd end,sum(tdk_sscd) as tdk_sscd,sum(tdk_nvdx) as tdk_nvdx,sum(cong) as cong,"),
    dmn_end_q1("max(priority_3),grouping(chungloai) as cl_gr,grouping(loai) as loai_gr,grouping(tenxd) as xd_gr, max(priority_3) as pr from (select stt_index,chungloai,loai,tenxd,tdk_sscd,tdk_nvdx,tdk_sscd+tdk_nvdx as cong,priority_3,"),
    dmn_end("'ss' from inventory i join loaixd2 lxd on i.petro_id=lxd.id join chungloaixd cl on lxd.petroleum_type_id=cl.id where tinhchat <> 'Nhiên liệu') s group by rollup(chungloai,loai,tenxd) order by chungloai desc,grouping(chungloai) desc,loai_gr desc,pr asc,xd_gr desc");


    public final String name;

    SubQueryEnum(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
