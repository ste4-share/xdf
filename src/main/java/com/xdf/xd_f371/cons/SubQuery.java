package com.xdf.xd_f371.cons;

public class SubQuery {
    public SubQuery() {
    }
    public static String nl_begin_q1(){
        return "select max(stt_index) as stt,tinhchat,loai,CASE WHEN tenxd is null and grouping(tinhchat)=0 then loai else tenxd end,sum(tdk_sscd) as tdk_sscd,sum(tdk_nvdx) as tdk_nvdx,sum(cong) as cong,";
    }
    public static String nl_end_q1(){
        return "max(priority_3), grouping(tinhchat) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as xd_gr, max(priority_3) as pr from (select stt_index,tinhchat,loai,tenxd,max(tdk_sscd) as tdk_sscd,max(tdk_nvdx) as tdk_nvdx,max(tdk_sscd+tdk_nvdx) as cong,max(priority_3) as priority_3,";
    }
    public static String nl_end(int quarter_id){
        return "'ss' from inventory i join loaixd2 lxd on i.petro_id=lxd.id join chungloaixd cl on lxd.petroleum_type_id=cl.id where tinhchat like 'Nhiên liệu' and i.quarter_id="+quarter_id+" group by 1,2,3,4) s group by rollup(tinhchat,loai,tenxd) order by tc_gr desc,loai_gr desc,pr asc,xd_gr desc";
    }
    public static String dmn_begin_q1(){
        return "select max(stt_index) as stt,chungloai,loai,CASE WHEN tenxd is null and grouping(chungloai)=0 then loai else tenxd end,sum(tdk_sscd) as tdk_sscd,sum(tdk_nvdx) as tdk_nvdx,sum(cong) as cong,";
    }
    public static String dmn_end_q1(){
        return "max(priority_3),grouping(chungloai) as cl_gr,grouping(loai) as loai_gr,grouping(tenxd) as xd_gr, max(priority_3) as pr from (select stt_index,chungloai,loai,tenxd,max(tdk_sscd) as tdk_sscd,max(tdk_nvdx) as tdk_nvdx,max(tdk_sscd+tdk_nvdx) as cong,max(priority_3) as priority_3,";
    }
    public static String dmn_end(int quarter_id){
        return "'ss' from inventory i join loaixd2 lxd on i.petro_id=lxd.id join chungloaixd cl on lxd.petroleum_type_id=cl.id where tinhchat <> 'Nhiên liệu' and i.quarter_id="+quarter_id+" group by 1,2,3,4) s group by rollup(chungloai,loai,tenxd) order by chungloai desc,grouping(chungloai) desc,loai_gr desc,pr asc,xd_gr desc";
    }
    public static String ttxd_nv(int quy_id,int dv_id){
        return "select max(tt) as tt,max(pri) as pri,n,\n" +
                "case when grouping(n)=0 and grouping(ten_nv)=1 then n else ten_nv end as ten_nv, \n" +
                "case when grouping(n)=0 and grouping(ten_nv)=1 then n when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then ten_nv else nhiemvu end as nhiemvu,\n" +
                "sum(xang) as xang, sum(diezel) as diezel,sum(daubay) as daubay,sum(hm_cong) as hm_cong, sum(xm_xang_km) as xm_xang_km,\n" +
                "sum(xm_xang_gio) as xm_xang_gio,\n" +
                "sum(xm_do_km) as xm_do_km,sum(xm_do_gio) as xm_do_gio,sum(nlpl) as nlpl,sum(x_choxe) as x_choxe,sum(x_chomay) as x_chomay,sum(x_cong) as x_cong,sum(do_choxe) as do_choxe,\n" +
                "sum(do_chomay) as do_chomay,sum(do_cong) as do_cong,sum(nltt_nlpl) as nltt_nlpl, sum(sd_tichluy) as sd_tichluy,\n" +
                "grouping(n) as name_gr, grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nv_gr\n" +
                "from (select max(tt) as tt,max(priority_bc2) as pri,n,ten_nv, \n" +
                "case when nhiemvu is null then ten_nv else nhiemvu end as nhiemvu,\n" +
                "sum(xang) as xang, sum(diezel) as diezel,sum(daubay) as daubay,sum(hm_cong) as hm_cong, sum(xm_xang_km) as xm_xang_km,sum(xm_xang_gio) as xm_xang_gio,\n" +
                "sum(xm_do_km) as xm_do_km,sum(xm_do_gio) as xm_do_gio,sum(nlpl) as nlpl,sum(x_choxe) as x_choxe,sum(x_chomay) as x_chomay,sum(x_cong) as x_cong,sum(do_choxe) as do_choxe,\n" +
                "sum(do_chomay) as do_chomay,sum(do_cong) as do_cong,sum(nltt_nlpl) as nltt_nlpl, sum(sd_tichluy) as sd_tichluy\n" +
                "from (select tt,priority_bc2,ct.id as ctnv_id,t.name as n,ten_nv,nhiemvu,\n" +
                "case when xang is null then 0 else xang end as xang, \n" +
                "case when diezel is null then 0 else diezel end as diezel,\n" +
                "case when daubay is null then 0 else daubay end as daubay,\n" +
                "case when xang is null then 0 else xang end+case when diezel is null then 0 else diezel end+case when daubay is null then 0 else daubay end as hm_cong,\n" +
                "case when a.km is null then 0 else a.km end as xm_xang_km,\n" +
                "case when EXTRACT(epoch FROM a.gio) is null then 0 else EXTRACT(epoch FROM a.gio) end as xm_xang_gio,\n" +
                "case when b.km is null then 0 else b.km end as xm_do_km,\n" +
                "case when EXTRACT(epoch FROM b.gio) is null then 0 else EXTRACT(epoch FROM b.gio) end as xm_do_gio,\n" +
                "case when EXTRACT(epoch FROM e.gio) is null then 0 else EXTRACT(epoch FROM e.gio) end as nlpl,\n" +
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
                "left join (select nhiemvu_id,root_id,sum(so_km) as km, max(giohd_md::interval) as gio from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
                "where (l.lpt like 'XE_CHAY_XANG' OR l.lpt like 'MAY_CHAY_XANG') and l.status like 'ACTIVE' group by 1,2) a on (ct.id=a.nhiemvu_id and hm2.dvi_id=a.root_id)\n" +
                "left join (select nhiemvu_id,root_id,sum(so_km) as km, max(giohd_md::interval) as gio from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
                "where (l.lpt like 'XE_CHAY_DIEZEL' OR l.lpt like 'MAY_CHAY_DIEZEL') and l.status like 'ACTIVE' group by 1,2) b on (ct.id=b.nhiemvu_id and hm2.dvi_id=b.root_id)\n" +
                "left join (select nhiemvu_id,root_id,max(giohd_md::interval + giohd_tk::interval) as gio from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
                "where l.lpt_2 like 'MAYBAY' and l.status like 'ACTIVE' group by 1,2) e on (ct.id=e.nhiemvu_id and hm2.dvi_id=e.root_id)\n" +
                "left join (select nhiemvu_id,root_id, sum(so_luong) as choxe from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
                "where l.lpt_2 like 'XE' and ld.chung_loai like 'Xăng' and l.status like 'ACTIVE' group by 1,2) c on (ct.id=c.nhiemvu_id and hm2.dvi_id=c.root_id) \n" +
                "left join (select nhiemvu_id,root_id, sum(so_luong) as chomay from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
                "where l.lpt_2 like 'MAY' and ld.chung_loai like 'Xăng' and l.status like 'ACTIVE' group by 1,2) d on (ct.id=d.nhiemvu_id and hm2.dvi_id=d.root_id)\n" +
                "left join (select nhiemvu_id,root_id, sum(so_luong) as choxe from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
                "where l.lpt_2 like 'XE' and ld.chung_loai like 'Diezel' and l.status like 'ACTIVE' group by 1,2) f on (ct.id=f.nhiemvu_id and hm2.dvi_id=f.root_id) \n" +
                "left join (select nhiemvu_id,root_id, sum(so_luong) as chomay from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
                "where l.lpt_2 like 'MAY' and ld.chung_loai like 'Diezel' and l.status like 'ACTIVE' group by 1,2) g on (ct.id=g.nhiemvu_id and hm2.dvi_id=g.root_id)\n" +
                "left join (select nhiemvu_id,root_id, sum(so_luong) as nlpl from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
                "where l.lpt_2 like 'MAYBAY' and (ld.chung_loai like 'Dầu bay' or ld.chung_loai like 'Dầu Hạ cấp') and l.status like 'ACTIVE' group by 1,2) h \n" +
                "on (ct.id=h.nhiemvu_id and hm2.dvi_id=h.root_id) \n" +
                "where quarter_id="+quy_id+" and dvi_id="+dv_id+") zz\n" +
                "group by n,ten_nv,nhiemvu) aaa\n" +
                "group by rollup(n,ten_nv,nhiemvu)\n" +
                "order by name_gr desc,tt,tennv_gr desc,pri,ten_nv,nv_gr desc\n" +
                "\n";
    }
    public static String ttnlbtkh_for_mb(int qid){
        return "select 'A' as stt,'maybay' as ten,'Cho máy bay' as ten_nv,name_pt as nhiemvu,\n" +
                "EXTRACT(epoch FROM sum(tk)) as tk,EXTRACT(epoch FROM sum(md)) as md, EXTRACT(epoch FROM sum(cong_giobay)) as cong_giobay,\n" +
                "sum(nhienlieu) as nhienlieu,\n" +
                "case when EXTRACT(epoch FROM sum(giohd_md)) is null then 0 else EXTRACT(epoch FROM sum(giohd_md)) end as giohd_md,\n" +
                "case when EXTRACT(epoch FROM sum(giohd_tk)) is null then 0 else EXTRACT(epoch FROM sum(giohd_tk)) end as giohd_tk, \n" +
                "case when EXTRACT(epoch FROM sum(tong_giohd)) is null then 0 else EXTRACT(epoch FROM sum(tong_giohd)) end as tong_giohd,\n" +
                "case when sum(nltt_md) is null then 0 else sum(nltt_md) end as nltt_md,\n" +
                "case when sum(nltt_tk) is null then 0 else sum(nltt_tk) end as nltt_tk,\n" +
                "case when sum(cong_nltt) is null then 0 else sum(cong_nltt) end as cong_nltt,\n" +
                "case when sum(haohut) is null then 0 else sum(haohut) end as haohut,\n" +
                "case when sum(tongcong) is null then 0 else sum(tongcong) end as tongcong,'1' as pri,\n" +
                "grouping(name_pt) as ten_gr,'0' as tennv_gr,'0' as nv_gr\n" +
                "from (select * from (SELECT quy_id as quy,pt_id,pt.name as name_pt,sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,sum(nhienlieu) as nhienlieu\n" +
                "FROM hanmuc_nhiemvu_taubay hmnvtb \n" +
                "left join phuongtien pt on pt.id=hmnvtb.pt_id \n" +
                "where quy_id="+qid+" and pt_id <> 0\n" +
                "group by quy_id,pt_id,name_pt) a\n" +
                "left join (SELECT quarter_id,phuongtien_id,max(giohd_md::interval) as giohd_md,max(giohd_tk::interval) as giohd_tk,max(giohd_md::interval)+max(giohd_tk::interval) as tong_giohd,\n" +
                "sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,\n" +
                "sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l \n" +
                "join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id \n" +
                "where lpt_2 like 'MAYBAY' and l.status like 'ACTIVE'\n" +
                "group by 1,2) b on (a.quy=b.quarter_id and a.pt_id=b.phuongtien_id)) c\n" +
                "group by rollup(name_pt) order by ten_gr desc,name_pt desc";
    }
    public static String ttnlbtkh_for_all(int qid){
        return "select 'B' as stt,'nhiemvu' as ten,ten_nv, nhiemvu, \n" +
                "case when EXTRACT(epoch FROM max(tk)) is null then 0 else EXTRACT(epoch FROM max(tk)) end as tk,\n" +
                "case when EXTRACT(epoch FROM max(md)) is null then 0 else EXTRACT(epoch FROM max(md)) end as md,\n" +
                "case when EXTRACT(epoch FROM max(cong_giobay)) is null then 0 else EXTRACT(epoch FROM max(cong_giobay)) end as cong_giobay,max(nhienlieu) as nhienlieu, \n" +
                "case when EXTRACT(epoch FROM max(giohd_md)) is null then 0 else EXTRACT(epoch FROM max(giohd_md)) end as giohd_md,\n" +
                "case when EXTRACT(epoch FROM max(giohd_tk)) is null then 0 else EXTRACT(epoch FROM max(giohd_tk)) end as giohd_tk,\n" +
                "case when EXTRACT(epoch FROM max(tong_giohd)) is null then 0 else EXTRACT(epoch FROM max(tong_giohd)) end as tong_giohd,\n" +
                "case when max(nltt_md) is null then 0 else max(nltt_md) end as nltt_md,\n" +
                "case when max(nltt_tk) is null then 0 else max(nltt_tk) end as nltt_tk, \n" +
                "case when max(cong_nltt) is null then 0 else max(cong_nltt) end as cong_nltt, \n" +
                "case when max(haohut) is null then 0 else max(haohut) end as haohut,\n" +
                "case when max(tongcong) is null then 0 else max(tongcong) end as tongcong,\n" +
                "'0' as ten_gr,max(tennv_gr) as tennv_gr,max(nv_gr) as nv_gr,max(pri) as pri\n" +
                "from (select max(ct_id) as ct_id,ten_nv,case when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then ten_nv else nhiemvu end as nhiemvu,\n" +
                "max(pri) as pri, sum(tk) as tk,sum(md) as md,sum(cong_giobay) as cong_giobay,\n" +
                "sum(nhienlieu) as nhienlieu,sum(giohd_md) as giohd_md,sum(giohd_tk) as giohd_tk,sum(tong_giohd) as tong_giohd,sum(nltt_md) as nltt_md,\n" +
                "sum(nltt_tk) as nltt_tk, sum(cong_nltt) as cong_nltt, sum(haohut) as haohut, sum(tongcong) as tongcong,\n" +
                "grouping(ten_nv) as tennv_gr, grouping(nhiemvu) as nv_gr\n" +
                "from (select * from (SELECT quy_id as quy,hmnvtb.ctnv_id as ct_id,ten_nv,nhiemvu,\n" +
                "max(priority_bc2) as pri,sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,sum(nhienlieu) as nhienlieu\n" +
                "FROM hanmuc_nhiemvu_taubay hmnvtb \n" +
                "left join phuongtien pt on pt.id=hmnvtb.pt_id \n" +
                "left join chitiet_nhiemvu ct on ct.id=hmnvtb.ctnv_id \n" +
                "left join nhiemvu nv on nv.id=ct.nhiemvu_id\n" +
                "where quy_id="+qid+" group by quy_id,ct_id,ten_nv,nhiemvu) rat2\n" +
                "left join (SELECT quarter_id,l.nhiemvu_id as ctnv_id,ct.nhiemvu as nv,max(giohd_md::interval) as giohd_md,max(giohd_tk::interval) as giohd_tk,\n" +
                "max(giohd_md::interval)+max(giohd_tk::interval) as tong_giohd,\n" +
                "sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,\n" +
                "sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l \n" +
                "join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id where l.status like 'ACTIVE'\n" +
                "group by quarter_id,l.nhiemvu_id,nv) c on (rat2.quy=c.quarter_id and c.ctnv_id=rat2.ct_id)) d\n" +
                "group by rollup(ten_nv,nhiemvu)) z\n" +
                "group by ten_nv, nhiemvu order by tennv_gr desc,pri,ten_nv, nv_gr desc,nhiemvu";
    }
    public static String ttnlbtkh_for_dv(int q){
        return "select 'C' as stt,ten,ten_nv,\n" +
                "case when nhiemvu is null then ten else nhiemvu end as nhiemvu,\n" +
                "case when EXTRACT(epoch FROM max(tk)) is null then 0 else EXTRACT(epoch FROM max(tk)) end as tk,\n" +
                "case when EXTRACT(epoch FROM max(md)) is null then 0 else EXTRACT(epoch FROM max(md)) end as md,\n" +
                "case when EXTRACT(epoch FROM max(congiobay)) is null then 0 else EXTRACT(epoch FROM max(congiobay)) end as congiobay,\n" +
                "max(nhienlieu) as nhienlieu,\n" +
                "case when EXTRACT(epoch FROM max(giohd_md)) is null then 0 else EXTRACT(epoch FROM max(giohd_md)) end as giohd_md,\n" +
                "case when EXTRACT(epoch FROM max(giohd_tk)) is null then 0 else EXTRACT(epoch FROM max(giohd_tk)) end as giohd_tk,\n" +
                "case when EXTRACT(epoch FROM max(tonggiohd)) is null then 0 else EXTRACT(epoch FROM max(tonggiohd)) end as tonggiohd,\n" +
                "case when max(nltt_md) is null then 0 else max(nltt_md) end as nltt_md, \n" +
                "case when max(nltt_tk) is null then 0 else max(nltt_tk) end as nltt_tk,\n" +
                "case when max(cong_nltt) is null then 0 else max(cong_nltt) end as cong_nltt,\n" +
                "case when max(haohut) is null then 0 else max(haohut) end as haohut,\n" +
                "case when max(tongcong) is null then 0 else max(tongcong) end as tongcong, \n" +
                "max(dm_tk) as dm_tk, max(dm_md) as dm_md, max(ten_gr) as ten_gr,max(tennv_gr) as tennv_gr,max(nv_gr) as nv_gr,pri\n" +
                "from (select max(nnx) as pt_id,max(ct_id) as ct_id,max(quy_id) as quy,max(pri) as pri,\n" +
                "ten,ten_nv,case when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then ten_nv else nhiemvu end as nhiemvu,sum(tk) as tk,sum(md) as md,sum(cong_giobay) as congiobay,sum(nhienlieu) as nhienlieu,\n" +
                "sum(giohd_md) as giohd_md,sum(giohd_tk) as giohd_tk,sum(tong_giohd) as tonggiohd,\n" +
                "sum(nltt_md) as nltt_md,sum(nltt_tk) as nltt_tk,sum(cong_nltt) as cong_nltt,sum(haohut) as haohut,sum(tongcong) as tongcong,\n" +
                "max(dm_tk) as dm_tk,max(dm_md) as dm_md,\n" +
                "grouping(ten) as ten_gr, grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nv_gr\n" +
                "from (SELECT hmnvtb.dvi_xuat_id as nnx,hmnvtb.ctnv_id as ct_id,quy_id,ten,ten_nv,nhiemvu,max(priority_bc2) as pri,\n" +
                "sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,sum(nhienlieu) as nhienlieu,max(dm_tk_gio) as dm_tk,max(dm_md_gio) as dm_md\n" +
                "FROM hanmuc_nhiemvu_taubay hmnvtb left join nguon_nx n on hmnvtb.dvi_xuat_id=n.id \n" +
                "left join phuongtien pt on pt.id=hmnvtb.pt_id \n" +
                "left join chitiet_nhiemvu ct on ct.id=hmnvtb.ctnv_id \n" +
                "left join nhiemvu nv on nv.id=ct.nhiemvu_id \n" +
                "left join dinhmuc dm on (dm.phuongtien_id=pt.id and dm.quarter_id=hmnvtb.quy_id)\n" +
                "where quy_id="+q+" group by hmnvtb.dvi_xuat_id,hmnvtb.ctnv_id,quy_id,ten,ten_nv,nhiemvu order by ten) rat \n" +
                "left join (SELECT quarter_id,dvi_xuat_id,l.nhiemvu_id as ctnv_id,max(giohd_md::interval) as giohd_md,max(giohd_tk::interval) as giohd_tk,\n" +
                "max(giohd_md::interval)+max(giohd_tk::interval) as tong_giohd,sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,\n" +
                "sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l \n" +
                "join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id where l.status like 'ACTIVE'\n" +
                "group by 1,2,3) a on (quy_id=a.quarter_id and a.dvi_xuat_id=rat.nnx and a.ctnv_id=rat.ct_id)\n" +
                "group by rollup(ten,ten_nv,nhiemvu)) b\n" +
                "group by pri,ten,ten_nv,nhiemvu\n" +
                "order by ten_gr desc, ten desc,tennv_gr desc,pri asc,ten_nv,nv_gr desc";
    }
    public static String ttnlbtkh_for_tongmaybay(int q){
        return "select 'D' as stt,name_pt as ten,ten_nv,case when nhiemvu is null then name_pt else nhiemvu end as nhiemvu,\n" +
                "case when EXTRACT(epoch FROM max(tk)) is null then 0 else EXTRACT(epoch FROM max(tk)) end as tk,\n" +
                "case when EXTRACT(epoch FROM max(md)) is null then 0 else EXTRACT(epoch FROM max(md)) end as md,\n" +
                "case when EXTRACT(epoch FROM max(congiobay)) is null then 0 else EXTRACT(epoch FROM max(congiobay)) end as congiobay,\n" +
                "max(nhienlieu) as nhienlieu,\n" +
                "case when EXTRACT(epoch FROM max(giohd_md)) is null then 0 else EXTRACT(epoch FROM max(giohd_md)) end as giohd_md,\n" +
                "case when EXTRACT(epoch FROM max(giohd_tk)) is null then 0 else EXTRACT(epoch FROM max(giohd_tk)) end as giohd_tk,\n" +
                "case when EXTRACT(epoch FROM max(tonggiohd)) is null then 0 else EXTRACT(epoch FROM max(tonggiohd)) end as tonggiohd,\n" +
                "case when max(nltt_md) is null then 0 else max(nltt_md) end as nltt_md, \n" +
                "case when max(nltt_tk) is null then 0 else max(nltt_tk) end as nltt_tk,\n" +
                "case when max(cong_nltt) is null then 0 else max(cong_nltt) end as cong_nltt,\n" +
                "case when max(haohut) is null then 0 else max(haohut) end as haohut,\n" +
                "case when max(tongcong) is null then 0 else max(tongcong) end as tongcong, \n" +
                "max(dm_tk) as dm_tk, \n" +
                "max(dm_md) as dm_md, \n" +
                "max(namept_gr) as ten_gr,max(tennv_gr) as tennv_gr,max(nv_gr) as nv_gr,pri\n" +
                "from (select max(pt_id) as pt_id,max(ct_id) as ct_id,max(quy_id) as quy,max(pri) as pri,\n" +
                "name_pt,ten_nv,\n" +
                "case when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then ten_nv else nhiemvu end as nhiemvu,sum(tk) as tk,sum(md) as md,sum(cong_giobay) as congiobay,sum(nhienlieu) as nhienlieu,\n" +
                "sum(giohd_md) as giohd_md,sum(giohd_tk) as giohd_tk,sum(tong_giohd) as tonggiohd,\n" +
                "sum(nltt_md) as nltt_md,sum(nltt_tk) as nltt_tk,sum(cong_nltt) as cong_nltt,sum(haohut) as haohut,sum(tongcong) as tongcong,\n" +
                "max(dm_tk) as dm_tk,max(dm_md) as dm_md,\n" +
                "grouping(name_pt) as namept_gr, grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nv_gr\n" +
                "from (SELECT hmnvtb.pt_id as pt_id,hmnvtb.ctnv_id as ct_id,quy_id,pt.name as name_pt,ten_nv,nhiemvu,max(priority_bc2) as pri,\n" +
                "sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,\n" +
                "sum(nhienlieu) as nhienlieu,max(dm_tk_gio) as dm_tk,max(dm_md_gio) as dm_md\n" +
                "FROM hanmuc_nhiemvu_taubay hmnvtb \n" +
                "left join phuongtien pt on pt.id=hmnvtb.pt_id \n" +
                "left join chitiet_nhiemvu ct on ct.id=hmnvtb.ctnv_id \n" +
                "left join nhiemvu nv on nv.id=ct.nhiemvu_id \n" +
                "left join dinhmuc dm on (dm.phuongtien_id=pt.id and dm.quarter_id=hmnvtb.quy_id)\n" +
                "where quy_id="+q+" group by hmnvtb.pt_id,hmnvtb.ctnv_id,quy_id,name_pt,ten_nv,nhiemvu order by name_pt) rat \n" +
                "left join (SELECT quarter_id,phuongtien_id,l.nhiemvu_id as ctnv_id,max(giohd_md::interval) as giohd_md,max(giohd_tk::interval) as giohd_tk,\n" +
                "max(giohd_md::interval)+max(giohd_tk::interval) as tong_giohd,sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,\n" +
                "sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l \n" +
                "join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id where l.status like 'ACTIVE'\n" +
                "group by 1,2,3) a on (quy_id=a.quarter_id and a.phuongtien_id=rat.pt_id and a.ctnv_id=rat.ct_id)\n" +
                "group by rollup(name_pt,ten_nv,nhiemvu)) b\n" +
                "where name_pt is not null\n" +
                "group by pri,name_pt,ten_nv,nhiemvu\n" +
                "order by ten_gr desc, name_pt desc,tennv_gr desc,pri asc,ten_nv,nv_gr desc";
    }
}
