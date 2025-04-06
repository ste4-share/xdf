package com.xdf.xd_f371.cons;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SubQuery {
    public static String bc_pttk_q(){
        return "select RANK() OVER (PARTITION BY loai ORDER BY tenxd DESC) AS ranks,loai,tenxd,\n" +
                "(case when NHAP_e916.tonkho is null then 0 else NHAP_e916.tonkho end)-(case when XUAT_e916.tonkho is null then 0 else XUAT_e916.tonkho end) as e916,\n" +
                "(case when NHAP_e921.tonkho is null then 0 else NHAP_e921.tonkho end)-(case when XUAT_e921.tonkho is null then 0 else XUAT_e921.tonkho end) as e921,\n" +
                "(case when NHAP_e923.tonkho is null then 0 else NHAP_e923.tonkho end)-(case when XUAT_e923.tonkho is null then 0 else XUAT_e923.tonkho end) as e923,\n" +
                "(case when NHAP_e927.tonkho is null then 0 else NHAP_e927.tonkho end)-(case when XUAT_e927.tonkho is null then 0 else XUAT_e927.tonkho end) as e927,\n" +
                "(case when NHAP_dnb.tonkho is null then 0 else NHAP_dnb.tonkho end)-(case when XUAT_dnb.tonkho is null then 0 else XUAT_dnb.tonkho end) as d_noibai,\n" +
                "(case when NHAP_dka.tonkho is null then 0 else NHAP_dka.tonkho end)-(case when XUAT_dka.tonkho is null then 0 else XUAT_dka.tonkho end) as d_kienan,\n" +
                "(case when NHAP_dvi.tonkho is null then 0 else NHAP_dvi.tonkho end)-(case when XUAT_dvi.tonkho is null then 0 else XUAT_dvi.tonkho end) as d_vinh,\n" +
                "(case when NHAP_dns.tonkho is null then 0 else NHAP_dns.tonkho end)-(case when XUAT_dns.tonkho is null then 0 else XUAT_dns.tonkho end) as d_nasan,\n" +
                "case when f_bo.tonkhotong is null then 0 else f_bo.tonkhotong end as fb\n" +
                "from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id\n" +
                "left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_nhan like 'e916' and loai_phieu like 'NHAP' group by 1) NHAP_e916 on lxd.id=NHAP_e916.loaixd_id\n" +
                "left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_xuat like 'e916' and loai_phieu like 'XUAT' group by 1) XUAT_e916 on lxd.id=XUAT_e916.loaixd_id\n" +
                "left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_nhan like 'e921' and loai_phieu like 'NHAP' group by 1) NHAP_e921 on lxd.id=NHAP_e921.loaixd_id\n" +
                "left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_xuat like 'e921' and loai_phieu like 'XUAT' group by 1) XUAT_e921 on lxd.id=XUAT_e921.loaixd_id\n" +
                "left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_nhan like 'e923' and loai_phieu like 'NHAP' group by 1) NHAP_e923 on lxd.id=NHAP_e923.loaixd_id\n" +
                "left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_xuat like 'e923' and loai_phieu like 'XUAT' group by 1) XUAT_e923 on lxd.id=XUAT_e923.loaixd_id\n" +
                "left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_nhan like 'e927' and loai_phieu like 'NHAP' group by 1) NHAP_e927 on lxd.id=NHAP_e927.loaixd_id\n" +
                "left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_xuat like 'e927' and loai_phieu like 'XUAT' group by 1) XUAT_e927 on lxd.id=XUAT_e927.loaixd_id\n" +
                "left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_nhan like 'd Nội Bài' and loai_phieu like 'NHAP' group by 1) NHAP_dnb on lxd.id=NHAP_dnb.loaixd_id\n" +
                "left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_xuat like 'd Nội Bài' and loai_phieu like 'XUAT' group by 1) XUAT_dnb on lxd.id=XUAT_dnb.loaixd_id\n" +
                "left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_nhan like 'd Kiến An' and loai_phieu like 'NHAP' group by 1) NHAP_dka on lxd.id=NHAP_dka.loaixd_id\n" +
                "left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_xuat like 'd Kiến An' and loai_phieu like 'XUAT' group by 1) XUAT_dka on lxd.id=XUAT_dka.loaixd_id\n" +
                "left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_nhan like 'd Vinh' and loai_phieu like 'NHAP' group by 1) NHAP_dvi on lxd.id=NHAP_dvi.loaixd_id\n" +
                "left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_xuat like 'd Vinh' and loai_phieu like 'XUAT' group by 1) XUAT_dvi on lxd.id=XUAT_dvi.loaixd_id\n" +
                "left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_nhan like 'd Nà Sản' and loai_phieu like 'NHAP' group by 1) NHAP_dns on lxd.id=NHAP_dns.loaixd_id\n" +
                "left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
                "where status like 'ACTIVE' and dvi_xuat like 'd Nà Sản' and loai_phieu like 'XUAT' group by 1) XUAT_dns on lxd.id=XUAT_dns.loaixd_id\n" +
                "left join (SELECT distinct on (xd_id) * FROM public.transaction_history order by xd_id,created_at desc) f_bo on lxd.id=f_bo.xd_id\n" +
                "order by priority_1,priority_2,priority_3";
    }
    public static String bc_ttxd_xmt_q(LocalDate sd,LocalDate ed,int rootid){
        return "select tc_gr,tinhchat,n_gr,case when tc_gr=1 and n_gr=1 then 0 else ranks end ranks,\n" +
                "case when tc_gr=1 and n_gr=1 then 'Cộng' when tc_gr=0 and n_gr=1 then tinhchat else name end tenxmt,soluong,km,giohd,thucchi,thucchi2,'','',dm,'','',''\n" +
                "from (select DENSE_RANK() OVER (PARTITION BY tinhchat ORDER BY name asc nulls first) as ranks,tinhchat,name,sum(soluong) as soluong,sum(x.km) as km,\n" +
                "EXTRACT(epoch FROM sum(giohd)) as giohd,sum(dmgio) as dmgio,sum(thucchi) as thucchi,sum(thucchi) as thucchi2,max(dmgio) as dm,grouping(tinhchat) as tc_gr,grouping(name) as n_gr\n" +
                "from (select pt.id as pt_id,name,tinhchat,count(u.id) as soluong,max(dm_hours) as dmgio,max(dm_km) as dmkm,max(dm_md) as dmmd,max(dm_tk) as dmtk,max(timestamp) as created_at\n" +
                "from phuongtien pt left join unit_xmt u on pt.id=u.xmt_id where unit_id="+rootid+" group by 1,2,3 order by max(timestamp) desc) pt\n" +
                "left join (select id,pt_id,sum(giohd_md::interval) as giohd,sum(so_km) as km\n" +
                "from ledgers l where l.status like 'ACTIVE' and root_id="+rootid+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) x on x.pt_id=pt.pt_id\n" +
                "left join (select l.id as lid,sum(thuc_xuat) as thucchi FROM ledgers l left join ledger_details ld on l.id=ld.ledger_id \n" +
                "where l.status like 'ACTIVE' and root_id="+rootid+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1) b\n" +
                "on x.id=b.lid\n" +
                "group by rollup(2,3)) a\n" +
                "order by tc_gr desc,tinhchat,n_gr desc, name";
    }
    public static String lcv_q(LocalDate sd,LocalDate ed){
        return "with shit as(\n" +
                "SELECT loaixd_id,don_gia,sum(nhap_nvdx)-sum(xuat_nvdx) as tdk_nvdx,sum(nhap_sscd)-sum(xuat_sscd) as tdk_sscd \n" +
                "FROM ledgers l join ledger_details ld on l.id=ld.ledger_id where from_date < '"+sd+"' group by 1,2 UNION ALL \n" +
                "SELECT loaixd_id,don_gia,0,0 FROM ledgers l join ledger_details ld on l.id=ld.ledger_id where from_date between '"+sd+"' and '"+ed+"' group by 1,2\n" +
                ")\n" +
                "select tinhchat,loai,case when tenxd is null then loai else tenxd end,case when price is null then 0 else price end,\n" +
                "sum(soluong) as tdk_sl,sum(thanhtien) as tdk_thanhtien,sum(cxd) as cxd,sum(qc) as qc,sum(pc) as pc,sum(ndvk) as ndvk,sum(nfnb) as nfnb,sum(nk) as nk,\n" +
                "(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk)) as sl_nhap,(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))*price as n_thanhtien,\n" +
                "sum(tt_xm) as tt_xm,sum(bq) as bq,sum(hh) as hh,sum(xdvk) as xdvk,sum(xfnb) as xfnb,sum(tt) as tt,sum(xk) as xk,(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk)) as sl_xuat,\n" +
                "(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk))*price as x_thanhtien,\n" +
                "(sum(soluong)+(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))-(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk))) as tck_sl,\n" +
                "(sum(soluong)+(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))-(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk)))*price as tck_thanhtien,\n" +
                "grouping(tinhchat) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as tenxd_gr,grouping(price) as price_gr\n" +
                "from (select lxd.id as lxd_id,tinhchat,cl.chungloai,loai,maxd,tenxd,case when s.don_gia is null then 0 else s.don_gia end as price,\n" +
                "case when tdk_sscd+tdk_nvdx is null then 0 else tdk_sscd+tdk_nvdx end as soluong,\n" +
                "s.don_gia*(tdk_sscd+tdk_nvdx) as thanhtien,\n" +
                "nCXD.soluong as CXD,nQC.soluong as QC,nPC.soluong as PC,nDVK.soluong as nDVK,nFNB.soluong as nFNB,nK.soluong as nK,TT_XM.soluong as TT_XM,BQ.soluong as BQ,\n" +
                "HH.soluong as HH,xDVK.soluong as xDVK,xFNB.soluong as xFNB,TT.soluong as TT,xK.soluong as xK\n" +
                "from loaixd2 lxd \n" +
                "left join chungloaixd cl on lxd.petroleum_type_id=cl.id \n" +
                "left join shit s on s.loaixd_id=lxd.id \n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'NHAP' and tructhuoc like 'CXD' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) nCXD on (lxd.id=nCXD.loaixd_id and nCXD.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'NHAP' and tructhuoc like 'QC' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) nQC on (lxd.id=nQC.loaixd_id and nQC.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'NHAP' and tructhuoc like 'PC' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) nPC on (lxd.id=nPC.loaixd_id and nPC.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'NHAP' and tructhuoc like 'DVK' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) nDVK on (lxd.id=nDVK.loaixd_id and nDVK.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'NHAP' and tructhuoc like 'FNB' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) nFNB on (lxd.id=nFNB.loaixd_id and nFNB.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'NHAP' and tructhuoc like 'K' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) nK on (lxd.id=nK.loaixd_id and nK.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'TT_XM' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) TT_XM on (lxd.id=TT_XM.loaixd_id and TT_XM.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'BQ' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) BQ on (lxd.id=BQ.loaixd_id and BQ.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'HH' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) HH on (lxd.id=HH.loaixd_id and HH.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'DVK' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) xDVK on (lxd.id=xDVK.loaixd_id and xDVK.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'FNB' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) xFNB on (lxd.id=xFNB.loaixd_id and xFNB.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'TT' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) TT on  (lxd.id=TT.loaixd_id and TT.don_gia=s.don_gia)\n" +
                "left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
                "where loai_phieu like 'XUAT' and tructhuoc like 'K' and status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"' group by 1,2) xK on (lxd.id=xK.loaixd_id and xK.don_gia=s.don_gia)) z\n" +
                "group by rollup(tinhchat,loai,tenxd,price)\n" +
                "order by tc_gr desc,tinhchat desc,loai_gr desc,loai,tenxd_gr desc,tenxd,price_gr desc";
    }
    public static String begin_q1(){
        return "select case when tinhchat is null then 'sum' else tinhchat end as tinhchat,case when chungloai is null then 'sum' else chungloai end as chungloai,rank_cl,case when (l=0 and xd=1) then chungloai when l=1 and xd=1 then tinhchat else tenxd end as tenxd,tdk_nvdx,tdk_sscd,cong_tdk,";
    }
    public static String end_q1(){
        return "tc,l,xd from (select RANK() OVER (PARTITION BY chungloai ORDER BY tenxd DESC) AS rank_cl,tinhchat,chungloai,tenxd,case when sum(tdk_nvdx) is null then 0 else sum(tdk_nvdx) end as tdk_nvdx,case when sum(tdk_sscd) is null then 0 else sum(tdk_sscd) end as tdk_sscd,case when sum(tdk_sscd)+sum(tdk_nvdx) is null then 0 else sum(tdk_sscd)+sum(tdk_nvdx) end as cong_tdk,";
    }
    public static String end_q1_1(int root_id,LocalDate sd){
        return "grouping(tinhchat) as tc,grouping(chungloai) as l,grouping(tenxd) as xd from loaixd2 adm left join (SELECT distinct on (xd_id) xd_id,tonkhotong as tdk_nvdx,tonkh_sscd as tdk_sscd FROM public.transaction_history \n" +
                "where root_id="+root_id+" and date <= '"+sd+"' order by xd_id,created_at desc) a on a.xd_id=adm.id";
    }
    public static String ttxd_nv(LocalDate sd,LocalDate ed,int r_id){
        return "select max(nv_gr),max(ten_nv) as ten_nv,max(ranks_1),case when t_gr=1 and tennv_gr=1 then 'Cộng'\n" +
                "when t_gr=0 and tennv_gr=1 then team_group when tennv_gr=0 and nv_gr=1 then ten_nv else nhiemvu end as nhiemvu,\n" +
                "max(xang) as xang,max(diezel) as diezel,max(daubay) as daubay,max(hacap) as hacap,max(cong) as cong,\n" +
                "max(xx_km) as xx_km,EXTRACT(epoch FROM max(xx_gio)) as xx_gio,max(xdo_km) as xdo_km,EXTRACT(epoch FROM max(xdo_gio)) as xdo_gio,max(nlpl) as nlpl,\n" +
                "max(case when xtt_xe=0 then null else xtt_xe end) as xtt_xe,max(case when xtt_may=0 then null else xtt_may end) as xtt_may,max(case when xtt_cong=0 then null else xtt_cong end) as xtt_cong,\n" +
                "max(case when dott_xe=0 then null else dott_xe end) as dott_xe,max(case when dott_may=0 then null else dott_may end) as dott_may,\n" +
                "max(case when dott_cong=0 then null else dott_cong end) as dott_cong,max(mbtt_daubay) as mbtt_daubay,'','',''\n" +
                "from (SELECT DENSE_RANK() OVER (order by team_group desc nulls last) as ranks_1,\n" +
                "DENSE_RANK() OVER (PARTITION BY team_group ORDER BY ten_nv asc nulls last) as ranks_2\n" +
                ",team_group,ten_nv,nhiemvu,sum(xang) as xang,sum(diezel) as diezel,sum(daubay) as daubay,sum(hacap) as hacap,(sum(xang)+sum(diezel)+sum(daubay)+sum(hacap)) as cong,\n" +
                "sum(a.km) as xx_km,sum(b.gio) as xx_gio,sum(c.km) as xdo_km,sum(d.gio) as xdo_gio,sum(e.gio) as nlpl,\n" +
                "sum(case when f.soluong is null then 0 else f.soluong end) as xtt_xe,sum(case when g.soluong is null then 0 else g.soluong end) as xtt_may,\n" +
                "sum(case when f.soluong is null then 0 else f.soluong end)+sum(case when g.soluong is null then 0 else g.soluong end) as xtt_cong,\n" +
                "sum(case when h.soluong is null then 0 else h.soluong end) as dott_xe,sum(case when i.soluong is null then 0 else i.soluong end) as dott_may,\n" +
                "sum(case when h.soluong is null then 0 else h.soluong end)+sum(case when i.soluong is null then 0 else i.soluong end) as dott_cong,\n" +
                "sum(j.soluong) as mbtt_daubay,grouping(team_group) as t_gr,grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nv_gr\n" +
                "FROM nhiemvu nv left join chitiet_nhiemvu ct on nv.id=ct.nhiemvu_id left join hanmuc_nhiemvu2 hmnv on ct.id=hmnv.nhiemvu_id \n" +
                "left join (select root_id,nhiemvu_id, sum(so_km) as km FROM ledgers l where \n" +
                "lpt like 'XE_CHAY_XANG' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) a on (a.nhiemvu_id=ct.id and a.root_id=hmnv.dvi_id)\n" +
                "left join (select root_id,nhiemvu_id, sum(giohd_md::interval) as gio FROM ledgers l where \n" +
                "lpt like 'MAY_CHAY_XANG' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) b on (b.nhiemvu_id=ct.id and b.root_id=hmnv.dvi_id)\n" +
                "left join (select root_id,nhiemvu_id, sum(so_km) as km FROM ledgers l where \n" +
                "lpt like 'XE_CHAY_DIEZEL' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) c on (c.nhiemvu_id=ct.id and c.root_id=hmnv.dvi_id)\n" +
                "left join (select root_id,nhiemvu_id, sum(giohd_md::interval) as gio FROM ledgers l where \n" +
                "lpt like 'MAY_CHAY_DIEZEL' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) d on (d.nhiemvu_id=ct.id and d.root_id=hmnv.dvi_id)\n" +
                "left join (select root_id,nhiemvu_id, sum(giohd_md::interval+giohd_tk::interval) as gio FROM ledgers l where \n" +
                "lpt_2 like '%MB-%' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) e on (e.nhiemvu_id=ct.id and e.root_id=hmnv.dvi_id)\n" +
                "left join (select root_id,nhiemvu_id, sum(so_luong) as soluong FROM ledgers l left join ledger_details ld on l.id=ld.ledger_id where \n" +
                "lpt_2 like 'XE' and chung_loai like 'Xăng' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) f on (f.nhiemvu_id=ct.id and f.root_id=hmnv.dvi_id)\n" +
                "left join (select root_id,nhiemvu_id, sum(so_luong) as soluong FROM ledgers l left join ledger_details ld on l.id=ld.ledger_id where \n" +
                "lpt_2 like 'MAY' and chung_loai like 'Xăng' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) g on(g.nhiemvu_id=ct.id and g.root_id=hmnv.dvi_id)\n" +
                "left join (select root_id,nhiemvu_id, sum(so_luong) as soluong FROM ledgers l left join ledger_details ld on l.id=ld.ledger_id where \n" +
                "lpt_2 like 'XE' and chung_loai like 'Diezel' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) h on (h.nhiemvu_id=ct.id and h.root_id=hmnv.dvi_id)\n" +
                "left join(select root_id,nhiemvu_id, sum(so_luong) as soluong FROM ledgers l left join ledger_details ld on l.id=ld.ledger_id where \n" +
                "lpt_2 like 'MAY' and chung_loai like 'Diezel' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) i on (i.nhiemvu_id=ct.id and i.root_id=hmnv.dvi_id)\n" +
                "left join (select root_id,nhiemvu_id, sum(so_luong) as soluong FROM ledgers l left join ledger_details ld on l.id=ld.ledger_id where \n" +
                "lpt_2 like '%MB-%' and chung_loai like 'Dầu bay' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) j on (j.nhiemvu_id=ct.id and j.root_id=hmnv.dvi_id)\n" +
                "group by rollup(team_group,ten_nv,nhiemvu)) k\n" +
                "group by 4\n" +
                "order by max(t_gr) desc,max(team_group) desc,max(tennv_gr) desc,ten_nv,max(nv_gr) desc";
    }
    public static String ttnlbtkh_for_mb(LocalDate sd,LocalDate ed,int r_id){
        return "select '1' as gr,'-','A' as sign,case when grouping(xmt_id)=1 then 'A' else cast(min(ranks) as text) end, \n" +
                "case when grouping(xmt_id)=1 then 'Máy bay' else xmt_id end,0 as tk,0 as mk,0 as sum_f,0 as nhienlieu,\n" +
                "EXTRACT(epoch FROM sum(giohd_md)) as giohd_md,\n" +
                "EXTRACT(epoch FROM sum(giohd_tk)) as giohd_tk,\n" +
                "EXTRACT(epoch FROM sum(tong_giohd)) as tong_giohd,\n" +
                "sum(nltt_md),sum(nltt_tk),sum(cong_nltt),sum(haohut),sum(tongcong)\n" +
                "from (SELECT RANK() OVER (ORDER BY xmt_id asc) AS ranks,xmt_id,0 as tk,0 as mk,0 as sum_f,0 as nhienlieu,\n" +
                "sum(giohd_md::interval) as giohd_md,sum(giohd_tk::interval) as giohd_tk,sum(giohd_md::interval)+sum(giohd_tk::interval) as tong_giohd,\n" +
                "sum(nltt_md) as nltt_md,sum(nltt_tk) as nltt_tk,sum(cong_nltt) as cong_nltt,\n" +
                "sum(haohut) as haohut,sum(tongcong) as tongcong from \n" +
                "(select xmt_id,nhiemvu_id as ctnv_id,0 as tk,0 as mk,0 as sum_f,0 as nhienlieu,\n" +
                "sum(giohd_md::interval) as giohd_md,sum(giohd_tk::interval) as giohd_tk,sum(tong_giohd::interval) as tong_giohd,\n" +
                "sum(nltt_md) as nltt_md,sum(nltt_tk) as nltt_tk,sum(cong_nltt) as cong_nltt,sum(haohut) as haohut,sum(tongcong) as tongcong\n" +
                "from (select id,xmt_id,nhiemvu_id,sum(giohd_md::interval) as giohd_md,sum(giohd_tk::interval) as giohd_tk,sum(giohd_md::interval)+sum(giohd_tk::interval) as tong_giohd \n" +
                "from ledgers l where lpt_2 like 'MAYBAY' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2,3) x\n" +
                "left join (select l.id as lid,sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,\n" +
                "max(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong\n" +
                "FROM ledgers l left join ledger_details ld on l.id=ld.ledger_id \n" +
                "where lpt_2 like 'MAYBAY' and l.status like 'ACTIVE' and root_id="+r_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1) b\n" +
                "on x.id=b.lid\n" +
                "group by 1,2) a_1 left join chitiet_nhiemvu ct on ct.id= a_1.ctnv_id group by 2) a\n" +
                "group by rollup(xmt_id)\n" +
                "order by grouping(xmt_id) desc";
    }
    public static String ttnlbtkh_for_all(LocalDate sd,LocalDate ed, int root_id){
        return "select nv_gr,tennv_gr,ten_nv,ranks,case when nhiemvu is null then 'Cộng nhiệm vụ' else nhiemvu end as nhiemvu,\n" +
                "tk,md,cong_giobay,nhienlieu,giohd_md,giohd_tk,tong_giohd,nltt_md,nltt_tk,cong_nltt,haohut,tongcong \n" +
                "from (select ten_nv,case when max(nv_gr)=0 then '-' when max(nv_gr)=1 and max(tennv_gr)=1 then 'B' else cast(max(ranks) as text) end ranks, \n" +
                "case when max(tennv_gr)=0 and max(nv_gr)=1 then ten_nv else nhiemvu end as nhiemvu, \n" +
                "EXTRACT(epoch FROM max(tk)) as tk,\n" +
                "EXTRACT(epoch FROM max(md)) as md,\n" +
                "EXTRACT(epoch FROM max(cong_giobay)) as cong_giobay,max(nhienlieu) as nhienlieu, \n" +
                "EXTRACT(epoch FROM max(giohd_md)) as giohd_md,\n" +
                "EXTRACT(epoch FROM max(giohd_tk)) as giohd_tk,\n" +
                "EXTRACT(epoch FROM max(tong_giohd)) as tong_giohd,\n" +
                "case when max(nltt_md) is null then 0 else max(nltt_md) end as nltt_md,\n" +
                "case when max(nltt_tk) is null then 0 else max(nltt_tk) end as nltt_tk, \n" +
                "case when max(cong_nltt) is null then 0 else max(cong_nltt) end as cong_nltt, \n" +
                "case when max(haohut) is null then 0 else max(haohut) end as haohut,\n" +
                "case when max(tongcong) is null then 0 else max(tongcong) end as tongcong,max(tennv_gr) as tennv_gr,max(nv_gr) as nv_gr\n" +
                "from (select ten_nv,nhiemvu,DENSE_RANK() OVER (ORDER BY ten_nv asc nulls last) as ranks,sum(hm_tk::interval) as tk,sum(hm_md::interval) as md,sum(cong_giobay) as cong_giobay,\n" +
                "sum(daubay) as nhienlieu,sum(giohd_md) as giohd_md,sum(giohd_tk) as giohd_tk,sum(tong_giohd) as tong_giohd,sum(nltt_md) as nltt_md,\n" +
                "sum(nltt_tk) as nltt_tk, sum(cong_nltt) as cong_nltt, sum(haohut) as haohut, sum(tongcong) as tongcong,grouping(ten_nv) as tennv_gr, grouping(nhiemvu) as nv_gr\n" +
                "from (SELECT ct.id as ctnv_id,ten_nv,nhiemvu,hm_tk,hm_md,\n" +
                "hm_tk::interval+hm_md::interval as cong_giobay,diezel,daubay,xang,hacap\n" +
                "FROM public.hanmuc_nhiemvu2 hm \n" +
                "right join chitiet_nhiemvu ct on hm.nhiemvu_id=ct.id\n" +
                "right join nhiemvu nv on ct.nhiemvu_id=nv.id\n" +
                "right join loai_nhiemvu lnv on lnv.id=nv.assignment_type_id\n" +
                "where dvi_id="+root_id+" and task_name like 'NV_BAY') a \n" +
                "left join (select id,nhiemvu_id,sum(giohd_md::interval) as giohd_md,sum(giohd_tk::interval) as giohd_tk,sum(giohd_md::interval)+sum(giohd_tk::interval) as tong_giohd \n" +
                "from ledgers l where lpt_2 like 'MAYBAY' and l.status like 'ACTIVE' and root_id="+root_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2) x on a.ctnv_id=x.nhiemvu_id \n" +
                "left join (select l.id as lid,sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,max(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong\n" +
                "FROM ledgers l left join ledger_details ld on l.id=ld.ledger_id where lpt_2 like 'MAYBAY' and l.status like 'ACTIVE' and root_id="+root_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1) b\n" +
                "on x.id=b.lid group by rollup(ten_nv,nhiemvu)) d\n" +
                "group by ten_nv,nhiemvu order by tennv_gr desc,ten_nv, nv_gr desc,nhiemvu) e";
    }
    public static String ttnlbtkh_for_tongmaybay(LocalDate sd,LocalDate ed, int root_id){
        return "select grouping(nhiemvu) as nv_gr,min(a.xmt_id) as xmt,ten_nv,\n" +
                "case when grouping(nhiemvu)=1 and grouping(ten_nv)=1 and grouping(a.xmt_id)=1 then 'C' \n" +
                "when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then cast(min(ranks) as text) else '-' end as ranks,\n" +
                "case when grouping(a.xmt_id)=1 then 'Cộng máy bay' \n" +
                "when grouping(nhiemvu)=1 and grouping(ten_nv)=1 and grouping(a.xmt_id)=0 then a.xmt_id \n" +
                "when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then ten_nv else nhiemvu end,\n" +
                "EXTRACT(epoch FROM sum(tk::interval)) as tk,\n" +
                "EXTRACT(epoch FROM sum(md::interval)) as md,\n" +
                "EXTRACT(epoch FROM sum(cong_giobay)) as cong_giobay,\n" +
                "sum(nhienlieu) as nhienlieu,\n" +
                "EXTRACT(epoch FROM sum(giohd_md)) as giohd_md,\n" +
                "EXTRACT(epoch FROM sum(giohd_tk)) as giohd_tk,\n" +
                "EXTRACT(epoch FROM sum(tong_giohd)) as tong_giohd,\n" +
                "sum(nltt_md) as nltt_md,\n" +
                "sum(nltt_tk) as nltt_tk, sum(cong_nltt) as cong_nltt, sum(haohut) as haohut, sum(tongcong) as tongcong\n" +
                "from (SELECT DENSE_RANK() OVER (ORDER BY ten_nv asc) AS ranks,\n" +
                "xmt_id,ct.id as ctnv_id,ten_nv,nhiemvu,tk,md,tk::interval+md::interval as cong_giobay,nhienlieu\n" +
                "FROM public.hanmuc_nhiemvu_taubay hm \n" +
                "left join chitiet_nhiemvu ct on hm.ctnv_id=ct.id\n" +
                "left join nhiemvu nv on ct.nhiemvu_id=nv.id\n" +
                "where dvi_xuat_id="+root_id+" and loainv like 'NV_BAY' order by xmt_id) a\n" +
                "left join (select id,xmt_id,nhiemvu_id,sum(giohd_md::interval) as giohd_md,sum(giohd_tk::interval) as giohd_tk,sum(giohd_md::interval)+sum(giohd_tk::interval) as tong_giohd \n" +
                "from ledgers l where lpt_2 like 'MAYBAY' and l.status like 'ACTIVE' and root_id="+root_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1,2,3) x on (a.ctnv_id=x.nhiemvu_id AND a.xmt_id=x.xmt_id)\n" +
                "left join (select l.id as lid,sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,max(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong\n" +
                "FROM ledgers l left join ledger_details ld on l.id=ld.ledger_id where lpt_2 like 'MAYBAY' and l.status like 'ACTIVE' and root_id="+root_id+" and l.from_date between '"+sd+"' and '"+ed+"' group by 1) b\n" +
                "on x.id=b.lid group by rollup(a.xmt_id,ten_nv,nhiemvu)\n" +
                "order by grouping(a.xmt_id) desc,xmt,grouping(ten_nv) desc,ten_nv,grouping(nhiemvu) desc,nhiemvu";
    }
    public static Map<String,String> lxdMap(){
        Map<String,String> map = new HashMap<>();
        map.put(LoaiXDCons.NHIENLIEU.getName(), "Nhiên liệu");
        map.put(LoaiXDCons.XANG.getName(), "Xăng ô tô");
        map.put(LoaiXDCons.DAUHACAP.getName(), "Hạ cấp");
        map.put(LoaiXDCons.DAUBAY.getName(), "Dầu bay");
        map.put(LoaiXDCons.DMN.getName(), "Dầu mỡ nhờn");
        map.put(LoaiXDCons.TK_MN.getName(), "Dầu mỡ H.Không");
        map.put(LoaiXDCons.TK_DTL.getName(), "Dầu thủy lực");
        map.put(LoaiXDCons.TK_DM.getName(), "Dung môi");
        map.put(LoaiXDCons.TK_DK.getName(), "Dầu khác");
        map.put(LoaiXDCons.MD_MGMS.getName(), "Mỡ giảm ma sát");
        map.put(LoaiXDCons.MD_DTD.getName(), "Dầu truyền động");
        map.put(LoaiXDCons.MD_DK.getName(), "Dầu M.Đất Dầu khác");
        map.put(LoaiXDCons.MD_DCOTO.getName(), "Dầu Đ.cơ Ô tô");
        return map;
    }
}
