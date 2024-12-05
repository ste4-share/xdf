package com.xdf.xd_f371.cons;

public enum SubQueryEnum {
    nl_begin_q1("select max(stt_index) as stt,tinhchat,loai,CASE WHEN tenxd is null and grouping(tinhchat)=0 then loai else tenxd end,sum(tdk_sscd) as tdk_sscd,sum(tdk_nvdx) as tdk_nvdx,sum(cong) as cong,"),
    nl_end_q1("max(priority_3), grouping(tinhchat) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as xd_gr, max(priority_3) as pr from (select stt_index,tinhchat,loai,tenxd,tdk_sscd,tdk_nvdx,tdk_sscd+tdk_nvdx as cong,priority_3,"),
    nl_end("'ss' from inventory i join loaixd2 lxd on i.petro_id=lxd.id join chungloaixd cl on lxd.petroleum_type_id=cl.id where tinhchat like 'Nhiên liệu') s group by rollup(tinhchat,loai,tenxd) order by tc_gr desc,loai_gr desc,pr asc,xd_gr desc"),
    dmn_begin_q1("select max(stt_index) as stt,chungloai,loai,CASE WHEN tenxd is null and grouping(chungloai)=0 then loai else tenxd end,sum(tdk_sscd) as tdk_sscd,sum(tdk_nvdx) as tdk_nvdx,sum(cong) as cong,"),
    dmn_end_q1("max(priority_3),grouping(chungloai) as cl_gr,grouping(loai) as loai_gr,grouping(tenxd) as xd_gr, max(priority_3) as pr from (select stt_index,chungloai,loai,tenxd,tdk_sscd,tdk_nvdx,tdk_sscd+tdk_nvdx as cong,priority_3,"),
    dmn_end("'ss' from inventory i join loaixd2 lxd on i.petro_id=lxd.id join chungloaixd cl on lxd.petroleum_type_id=cl.id where tinhchat <> 'Nhiên liệu') s group by rollup(chungloai,loai,tenxd) order by chungloai desc,grouping(chungloai) desc,loai_gr desc,pr asc,xd_gr desc"),
    ttxd_nv("select n, ten_nv,nhiemvu,xang,diezel,daubay,hm_cong,xm_xang_km,xm_xang_gio::text,xm_do_km,xm_do_gio::text,nlpl::text,x_choxe,x_chomay,x_cong,do_choxe,do_chomay,do_cong,nltt_nlpl,sd_tichluy,name_gr,tennv_gr,nv_gr\n" +
            "from (select max(tt) as tt,max(pri) as pri,n,\n" +
            "case when max(name_gr)=0 and max(tennv_gr)=1 then n else ten_nv end as ten_nv, \n" +
            "case when max(name_gr)=0 and max(tennv_gr)=1 then n else nhiemvu end as nhiemvu,\n" +
            "sum(xang) as xang, sum(diezel) as diezel,sum(daubay) as daubay,sum(hm_cong) as hm_cong, sum(xm_xang_km) as xm_xang_km,\n" +
            "sum(xm_xang_gio) as xm_xang_gio,\n" +
            "sum(xm_do_km) as xm_do_km,sum(xm_do_gio) as xm_do_gio,sum(nlpl) as nlpl,sum(x_choxe) as x_choxe,sum(x_chomay) as x_chomay,sum(x_cong) as x_cong,sum(do_choxe) as do_choxe,\n" +
            "sum(do_chomay) as do_chomay,sum(do_cong) as do_cong,sum(nltt_nlpl) as nltt_nlpl, sum(sd_tichluy) as sd_tichluy,\n" +
            "max(name_gr) as name_gr,max(tennv_gr) as tennv_gr,max(nv_gr) as nv_gr\n" +
            "from (select max(tt) as tt,max(priority_bc2) as pri,n,ten_nv, \n" +
            "case when nhiemvu is null then ten_nv else nhiemvu end as nhiemvu,\n" +
            "sum(xang) as xang, sum(diezel) as diezel,sum(daubay) as daubay,sum(hm_cong) as hm_cong, sum(xm_xang_km) as xm_xang_km,sum(xm_xang_gio) as xm_xang_gio,\n" +
            "sum(xm_do_km) as xm_do_km,sum(xm_do_gio) as xm_do_gio,sum(nlpl) as nlpl,sum(x_choxe) as x_choxe,sum(x_chomay) as x_chomay,sum(x_cong) as x_cong,sum(do_choxe) as do_choxe,\n" +
            "sum(do_chomay) as do_chomay,sum(do_cong) as do_cong,sum(nltt_nlpl) as nltt_nlpl, sum(sd_tichluy) as sd_tichluy,\n" +
            "grouping(n) as name_gr, grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nv_gr\n" +
            "from (select tt,priority_bc2,ct.id as ctnv_id,t.name as n,ten_nv,nhiemvu,\n" +
            "case when xang is null then 0 else xang end as xang, \n" +
            "case when diezel is null then 0 else diezel end as diezel,\n" +
            "case when daubay is null then 0 else daubay end as daubay,\n" +
            "case when xang is null then 0 else xang end+case when diezel is null then 0 else diezel end+case when daubay is null then 0 else daubay end as hm_cong,\n" +
            "case when a.km is null then 0 else a.km end as xm_xang_km,\n" +
            "case when a.gio is null then '00:00' else a.gio end as xm_xang_gio,\n" +
            "case when b.km is null then 0 else b.km end as xm_do_km,\n" +
            "case when b.gio is null then '00:00' else b.gio end as xm_do_gio,\n" +
            "case when e.gio is null then '00:00' else e.gio end as nlpl,\n" +
            "case when c.choxe is null then 0 else c.choxe end as x_choxe,\n" +
            "case when d.chomay is null then 0 else d.chomay end as x_chomay,\n" +
            "case when c.choxe is null then 0 else c.choxe end+case when d.chomay is null then 0 else d.chomay end as x_cong,\n" +
            "case when f.choxe is null then 0 else f.choxe end as do_choxe,\n" +
            "case when g.chomay is null then 0 else g.chomay end as do_chomay,\n" +
            "case when f.choxe is null then 0 else f.choxe end+case when g.chomay is null then 0 else g.chomay end as do_cong,\n" +
            "case when h.nlpl is null then 0 else h.nlpl end as nltt_nlpl,\n" +
            "case when c.choxe is null then 0 else c.choxe end+case when d.chomay is null then 0 else d.chomay end+case when f.choxe is null then 0 else f.choxe end+case when g.chomay is null then 0 else g.chomay end+case when h.nlpl is null then 0 else h.nlpl end as sd_tichluy\n" +
            "from hanmuc_nhiemvu2 hm2 right join chitiet_nhiemvu ct on hm2.nhiemvu_id=ct.id right join nhiemvu n on ct.nhiemvu_id=n.id\n" +
            "join team t on n.team_id=t.id\n" +
            "left join (select nhiemvu_id,dvi_xuat_id,sum(so_km) as km, sum(giohd_md::interval) as gio from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
            "where l.lpt like 'XE_CHAY_XANG' OR l.lpt like 'MAY_CHAY_XANG' group by 1,2) a on (ct.id=a.nhiemvu_id and hm2.dvi_id=a.dvi_xuat_id)\n" +
            "left join (select nhiemvu_id,dvi_xuat_id,sum(so_km) as km, sum(giohd_md::interval) as gio from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
            "where l.lpt like 'XE_CHAY_DIEZEL' OR l.lpt like 'MAY_CHAY_DIEZEL' group by 1,2) b on (ct.id=b.nhiemvu_id and hm2.dvi_id=b.dvi_xuat_id)\n" +
            "left join (select nhiemvu_id,dvi_xuat_id,sum(giohd_md::interval + giohd_tk::interval) as gio from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
            "where l.lpt_2 like 'MAYBAY' group by 1,2) e on (ct.id=e.nhiemvu_id and hm2.dvi_id=e.dvi_xuat_id)\n" +
            "left join (select nhiemvu_id,dvi_xuat_id, sum(so_luong) as choxe from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
            "where l.lpt_2 like 'XE' and chung_loai like 'Xăng' group by 1,2) c on (ct.id=c.nhiemvu_id and hm2.dvi_id=c.dvi_xuat_id) \n" +
            "left join (select nhiemvu_id,dvi_xuat_id, sum(so_luong) as chomay from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
            "where l.lpt_2 like 'MAY' and chung_loai like 'Xăng' group by 1,2) d on (ct.id=d.nhiemvu_id and hm2.dvi_id=d.dvi_xuat_id)\n" +
            "left join (select nhiemvu_id,dvi_xuat_id, sum(so_luong) as choxe from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
            "where l.lpt_2 like 'XE' and chung_loai like 'Diezel' group by 1,2) f on (ct.id=f.nhiemvu_id and hm2.dvi_id=f.dvi_xuat_id) \n" +
            "left join (select nhiemvu_id,dvi_xuat_id, sum(so_luong) as chomay from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
            "where l.lpt_2 like 'MAY' and chung_loai like 'Diezel' group by 1,2) g on (ct.id=g.nhiemvu_id and hm2.dvi_id=g.dvi_xuat_id)\n" +
            "left join (select nhiemvu_id,dvi_xuat_id, sum(so_luong) as nlpl from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
            "where l.lpt_2 like 'MAYBAY' and (chung_loai like 'Dầu bay' or chung_loai like 'Dầu Hạ cấp') group by 1,2) h on (ct.id=h.nhiemvu_id and hm2.dvi_id=h.dvi_xuat_id)) zz\n" +
            "group by rollup(n,ten_nv,nhiemvu)) zxy\n" +
            "group by n,ten_nv,nhiemvu\n" +
            "order by name_gr desc,tt,tennv_gr desc,pri,ten_nv,nv_gr desc) xxx\n");


    public final String name;

    SubQueryEnum(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
