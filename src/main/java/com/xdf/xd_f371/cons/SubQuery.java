package com.xdf.xd_f371.cons;

import java.time.LocalDate;

public class SubQuery {
    public static String lcv_q(LocalDate sd,LocalDate ed){
        return "select max(ranks) as ranks,tinhchat,loai,tenxd,case when price is null then tenxd else '-' end as txd,\n" +
                "case when price is null then 0 else price end,\n" +
                "sum(soluong) as tdk_sl,sum(thanhtien) as tdk_thanhtien,sum(cxd) as cxd,sum(qc) as qc,sum(pc) as ps,sum(ndvk) as ndvk,sum(nk) as nk,\n" +
                "(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk)) as sl_nhap,(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))*(case when price is null then 0 else price end) as n_thanhtien,\n" +
                "sum(tt_xm) as tt_xm,sum(bq) as bq,sum(hh) as hh,sum(xdvk) as xdvk,sum(tt) as tt,sum(xk) as xk,(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk)) as sl_xuat,\n" +
                "(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk))*(case when price is null then 0 else price end) as x_thanhtien,\n" +
                "(sum(soluong)+(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))-(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk))) as tck_sl,\n" +
                "(sum(soluong)+(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))-(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk)))*(case when price is null then 0 else price end) as tck_thanhtien,\n" +
                "grouping(tinhchat) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as tenxd_gr,grouping(price) as price_gr\n" +
                "from (select RANK() OVER (ORDER BY lxd.id ASC) AS ranks,lxd.id,tinhchat,cl.chungloai,loai,maxd,tenxd,a.don_gia as price,\n" +
                "case when max(tdk_sscd)+max(tdk_nvdx) is null then 0 else max(tdk_sscd)+max(tdk_nvdx) end as soluong,a.don_gia*(max(tdk_sscd)+max(tdk_nvdx)) as thanhtien,\n" +
                "case when max(nCXD.soluong) is null then 0 else max(nCXD.soluong) end as CXD,\n" +
                "case when max(nQC.soluong) is null then 0 else max(nQC.soluong) end as QC,\n" +
                "case when max(nPC.soluong) is null then 0 else max(nPC.soluong) end as PC,\n" +
                "case when max(nDVK.soluong) is null then 0 else max(nDVK.soluong) end as nDVK,\n" +
                "case when max(nK.soluong) is null then 0 else max(nK.soluong) end as nK,\n" +
                "case when max(TT_XM.soluong) is null then 0 else max(TT_XM.soluong) end as TT_XM,\n" +
                "case when max(BQ.soluong) is null then 0 else max(BQ.soluong) end as BQ,\n" +
                "case when max(HH.soluong) is null then 0 else max(HH.soluong) end as HH,\n" +
                "case when max(xDVK.soluong) is null then 0 else max(xDVK.soluong) end as xDVK,\n" +
                "case when max(TT.soluong) is null then 0 else max(TT.soluong) end as TT,\n" +
                "case when max(xK.soluong) is null then 0 else max(xK.soluong) end as xK\n" +
                "from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id \n" +
                "left join (SELECT loaixd_id,don_gia,sum(nhap_nvdx)-sum(xuat_nvdx) as tdk_nvdx,sum(nhap_sscd)-sum(xuat_sscd) as tdk_sscd \n" +
                "FROM ledgers l join ledger_details ld on l.id=ld.ledger_id where from_date < '"+sd+"' group by 1,2) a on a.loaixd_id=lxd.id \n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'NHAP' and tructhuoc like 'CXD' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) nCXD on (lxd.id=nCXD.loaixd_id and a.don_gia=nCXD.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'NHAP' and tructhuoc like 'QC' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) nQC on (lxd.id=nQC.loaixd_id and a.don_gia=nQC.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'NHAP' and tructhuoc like 'PC' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) nPC on (lxd.id=nPC.loaixd_id and a.don_gia=nPC.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'NHAP' and tructhuoc like 'DVK' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) nDVK on (lxd.id=nDVK.loaixd_id and a.don_gia=nDVK.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'NHAP' and tructhuoc like 'K' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) nK on (lxd.id=nK.loaixd_id and a.don_gia=nK.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'TT_XM' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) TT_XM on (lxd.id=TT_XM.loaixd_id and a.don_gia=TT_XM.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'BQ' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) BQ on (lxd.id=BQ.loaixd_id and a.don_gia=BQ.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'HH' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) HH on (lxd.id=HH.loaixd_id and a.don_gia=HH.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'DVK' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) xDVK on (lxd.id=xDVK.loaixd_id and a.don_gia=xDVK.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'TT' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) TT on  (lxd.id=TT.loaixd_id and a.don_gia=TT.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'K' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) xK on (lxd.id=xK.loaixd_id and a.don_gia=xK.don_gia)\n" +
                "group by 2,3,4,5,6,7,8) z\n" +
                "group by rollup(tinhchat,loai,tenxd,price)\n" +
                "order by tc_gr desc,tinhchat desc,loai_gr desc,loai,tenxd_gr desc,tenxd,price_gr desc";
    }
    public static String begin_q1(){
        return "select tinhchat,chungloai,loai,case when grouping(tenxd)=1 and grouping(loai)=0 then loai else tenxd end,sum(tdk_nvdx) as tdk_nvdx,sum(tdk_sscd) as tdk_sscd,sum(cong_tdk) as cong_tdk,";
    }
    public static String end_q1(){
        return "max(p1) as p1, max(p2) as p2, max(p3) as p3,grouping(tinhchat) as tc,grouping(loai) as l,grouping(tenxd) as xd from (select lxd.id,tinhchat,cl.chungloai,loai,maxd,tenxd,case when max(tdk_nvdx) is null then 0 else max(tdk_nvdx) end as tdk_nvdx,case when max(tdk_sscd) is null then 0 else max(tdk_sscd) end as tdk_sscd,case when max(tdk_sscd)+max(tdk_nvdx) is null then 0 else max(tdk_sscd)+max(tdk_nvdx) end as cong_tdk,";
    }
    public static String end_q1_1(){
        return "max(cl.priority_1) as p1,max(cl.priority_2) as p2,max(cl.priority_3) as p3 from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id left join (SELECT petro_id,sum(nhap_nvdx)-sum(xuat_nvdx) as tdk_nvdx,sum(nhap_sscd)-sum(xuat_sscd) as tdk_sscd FROM public.inventory group by 1) a on a.petro_id=lxd.id";
    }
    public static String ttxd_nv(int y,int dv_id){
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
                "where years="+y+" and dvi_id="+dv_id+") zz\n" +
                "group by n,ten_nv,nhiemvu) aaa\n" +
                "group by rollup(n,ten_nv,nhiemvu)\n" +
                "order by name_gr desc,tt,tennv_gr desc,pri,ten_nv,nv_gr desc\n" +
                "\n";
    }
    public static String ttnlbtkh_for_mb(LocalDate sd,LocalDate ed){
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
                "from (select * from (SELECT years,pt_id,pt.name as name_pt,sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,sum(nhienlieu) as nhienlieu\n" +
                "FROM hanmuc_nhiemvu_taubay hmnvtb \n" +
                "left join phuongtien pt on pt.id=hmnvtb.pt_id \n" +
                "where pt_id <> 0\n" +
                "group by years,pt_id,name_pt) a\n" +
                "left join (SELECT phuongtien_id,max(giohd_md::interval) as giohd_md,max(giohd_tk::interval) as giohd_tk,max(giohd_md::interval)+max(giohd_tk::interval) as tong_giohd,\n" +
                "sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,\n" +
                "sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l \n" +
                "join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id \n" +
                "where lpt_2 like 'MAYBAY' and l.status like 'ACTIVE' and l.from_date between '"+sd+"' and '"+ed+"' \n" +
                "group by 1) b on a.pt_id=b.phuongtien_id) c\n" +
                "group by rollup(name_pt) order by ten_gr desc,name_pt desc";
    }
    public static String ttnlbtkh_for_all(LocalDate sd,LocalDate ed){
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
                "from (select * from (SELECT years,hmnvtb.ctnv_id as ct_id,ten_nv,nhiemvu,\n" +
                "max(priority_bc2) as pri,sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,sum(nhienlieu) as nhienlieu\n" +
                "FROM hanmuc_nhiemvu_taubay hmnvtb \n" +
                "left join phuongtien pt on pt.id=hmnvtb.pt_id \n" +
                "left join chitiet_nhiemvu ct on ct.id=hmnvtb.ctnv_id \n" +
                "left join nhiemvu nv on nv.id=ct.nhiemvu_id \n" +
                "group by years,ct_id,ten_nv,nhiemvu) rat2 \n" +
                "left join (SELECT l.nhiemvu_id as ctnv_id,ct.nhiemvu as nv,max(giohd_md::interval) as giohd_md,max(giohd_tk::interval) as giohd_tk,\n" +
                "max(giohd_md::interval)+max(giohd_tk::interval) as tong_giohd,\n" +
                "sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,\n" +
                "sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l \n" +
                "join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id where l.status like 'ACTIVE' and l.from_date between '"+sd+"' and '"+ed+"' \n" +
                "group by 1,2) c on c.ctnv_id=rat2.ct_id) d\n" +
                "group by rollup(ten_nv,nhiemvu)) z\n" +
                "group by ten_nv, nhiemvu order by tennv_gr desc,pri,ten_nv, nv_gr desc,nhiemvu";
    }
    public static String ttnlbtkh_for_dv(LocalDate sd,LocalDate ed){
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
                "from (select max(nnx) as pt_id,max(ct_id) as ct_id,max(years) as years,max(pri) as pri,\n" +
                "ten,ten_nv,case when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then ten_nv else nhiemvu end as nhiemvu,sum(tk) as tk,sum(md) as md,sum(cong_giobay) as congiobay,sum(nhienlieu) as nhienlieu,\n" +
                "sum(giohd_md) as giohd_md,sum(giohd_tk) as giohd_tk,sum(tong_giohd) as tonggiohd,\n" +
                "sum(nltt_md) as nltt_md,sum(nltt_tk) as nltt_tk,sum(cong_nltt) as cong_nltt,sum(haohut) as haohut,sum(tongcong) as tongcong,\n" +
                "max(dm_tk) as dm_tk,max(dm_md) as dm_md,\n" +
                "grouping(ten) as ten_gr, grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nv_gr\n" +
                "from (SELECT hmnvtb.dvi_xuat_id as nnx,hmnvtb.ctnv_id as ct_id,hmnvtb.years as years,ten,ten_nv,nhiemvu,max(priority_bc2) as pri,\n" +
                "sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,sum(nhienlieu) as nhienlieu,max(dm_tk_gio) as dm_tk,max(dm_md_gio) as dm_md\n" +
                "FROM hanmuc_nhiemvu_taubay hmnvtb left join nguon_nx n on hmnvtb.dvi_xuat_id=n.id \n" +
                "left join phuongtien pt on pt.id=hmnvtb.pt_id \n" +
                "left join chitiet_nhiemvu ct on ct.id=hmnvtb.ctnv_id \n" +
                "left join nhiemvu nv on nv.id=ct.nhiemvu_id \n" +
                "left join dinhmuc dm on (dm.phuongtien_id=pt.id and dm.years=hmnvtb.years) \n" +
                "group by hmnvtb.dvi_xuat_id,hmnvtb.ctnv_id,hmnvtb.years,ten,ten_nv,nhiemvu order by ten) rat \n" +
                "left join (SELECT dvi_xuat_id,l.nhiemvu_id as ctnv_id,max(giohd_md::interval) as giohd_md,max(giohd_tk::interval) as giohd_tk,\n" +
                "max(giohd_md::interval)+max(giohd_tk::interval) as tong_giohd,sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,\n" +
                "sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l \n" +
                "join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id where l.status like 'ACTIVE' and l.from_date between '"+sd+"' and '"+ed+"' \n" +
                "group by 1,2) a on (a.dvi_xuat_id=rat.nnx and a.ctnv_id=rat.ct_id)\n" +
                "group by rollup(ten,ten_nv,nhiemvu)) b\n" +
                "group by pri,ten,ten_nv,nhiemvu\n" +
                "order by ten_gr desc, ten desc,tennv_gr desc,pri asc,ten_nv,nv_gr desc";
    }
    public static String ttnlbtkh_for_tongmaybay(LocalDate sd,LocalDate ed){
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
                "from (select max(pt_id) as pt_id,max(ct_id) as ct_id,max(years) as years,max(pri) as pri,\n" +
                "name_pt,ten_nv,\n" +
                "case when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then ten_nv else nhiemvu end as nhiemvu,sum(tk) as tk,sum(md) as md,sum(cong_giobay) as congiobay,sum(nhienlieu) as nhienlieu,\n" +
                "sum(giohd_md) as giohd_md,sum(giohd_tk) as giohd_tk,sum(tong_giohd) as tonggiohd,\n" +
                "sum(nltt_md) as nltt_md,sum(nltt_tk) as nltt_tk,sum(cong_nltt) as cong_nltt,sum(haohut) as haohut,sum(tongcong) as tongcong,\n" +
                "max(dm_tk) as dm_tk,max(dm_md) as dm_md,\n" +
                "grouping(name_pt) as namept_gr, grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nv_gr\n" +
                "from (SELECT hmnvtb.pt_id as pt_id,hmnvtb.ctnv_id as ct_id,hmnvtb.years as years,pt.name as name_pt,ten_nv,nhiemvu,max(priority_bc2) as pri,\n" +
                "sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,\n" +
                "sum(nhienlieu) as nhienlieu,max(dm_tk_gio) as dm_tk,max(dm_md_gio) as dm_md\n" +
                "FROM hanmuc_nhiemvu_taubay hmnvtb \n" +
                "left join phuongtien pt on pt.id=hmnvtb.pt_id \n" +
                "left join chitiet_nhiemvu ct on ct.id=hmnvtb.ctnv_id \n" +
                "left join nhiemvu nv on nv.id=ct.nhiemvu_id \n" +
                "left join dinhmuc dm on (dm.phuongtien_id=pt.id and dm.years=hmnvtb.years) \n" +
                "group by hmnvtb.pt_id,hmnvtb.ctnv_id,hmnvtb.years,name_pt,ten_nv,nhiemvu order by name_pt) rat \n" +
                "left join (SELECT phuongtien_id,l.nhiemvu_id as ctnv_id,max(giohd_md::interval) as giohd_md,max(giohd_tk::interval) as giohd_tk,\n" +
                "max(giohd_md::interval)+max(giohd_tk::interval) as tong_giohd,sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,\n" +
                "sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l \n" +
                "join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id where l.status like 'ACTIVE' and l.from_date between '"+sd+"' and '"+ed+"' \n" +
                "group by 1,2) a on (a.phuongtien_id=rat.pt_id and a.ctnv_id=rat.ct_id)\n" +
                "group by rollup(name_pt,ten_nv,nhiemvu)) b\n" +
                "where name_pt is not null\n" +
                "group by pri,name_pt,ten_nv,nhiemvu\n" +
                "order by ten_gr desc, name_pt desc,tennv_gr desc,pri asc,ten_nv,nv_gr desc";
    }
}
