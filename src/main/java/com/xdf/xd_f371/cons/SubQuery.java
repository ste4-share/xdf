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
    public static String bc_ttxd_xmt_q(LocalDate sd,LocalDate ed,int year){
        return "select pt_id,loai,ranks,case when xemay is null then loai else xemay end as xemay,soluong_xe,dm_km,dm_gio,km,case when EXTRACT(epoch FROM gio) is null then 0 else EXTRACT(epoch FROM gio) end as gio,dinh_muc,thuc_chi,name_gr \n" +
                "from (select RANK() OVER (ORDER BY name DESC) AS ranks,max(pt.id) as pt_id,max(type_name) as loai,name as xemay,sum(quantity) as soluong_xe,sum(dm_xm_km) as dm_km,sum(dm_xm_gio) as dm_gio,max(so_km) as km, max(giohd_md::interval) as gio,sum(so_luong) as dinh_muc,sum(so_luong) as thuc_chi,GROUPING(name) as name_gr \n" +
                "from phuongtien pt left join loai_phuongtien lpt on pt.loaiphuongtien_id=lpt.id left join dinhmuc dm on pt.id=dm.phuongtien_id\n" +
                "left join (select * from ledgers l left join ledger_details ld on l.id=ld.ledger_id where l.status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"') xcx on pt.id=xcx.pt_id\n" +
                "where type_name like 'XE_CHAY_XANG' and dm.years="+year+" group by rollup(4)) a\n" +
                "union \n" +
                "(select pt_id,loai,ranks,case when xemay is null then loai else xemay end as xemay,soluong_xe,dm_km,dm_gio,km,case when EXTRACT(epoch FROM gio) is null then 0 else EXTRACT(epoch FROM gio) end as gio,dinh_muc,thuc_chi,name_gr\n" +
                "from (select RANK() OVER (ORDER BY name DESC) AS ranks,max(pt.id) as pt_id,max(type_name) as loai,name as xemay,sum(quantity) as soluong_xe,sum(dm_xm_km) as dm_km,sum(dm_xm_gio) as dm_gio,max(so_km) as km, max(giohd_md::interval) as gio,sum(so_luong) as dinh_muc,sum(so_luong) as thuc_chi,GROUPING(name) as name_gr \n" +
                "from phuongtien pt left join loai_phuongtien lpt on pt.loaiphuongtien_id=lpt.id left join dinhmuc dm on pt.id=dm.phuongtien_id\n" +
                "left join (select * from ledgers l left join ledger_details ld on l.id=ld.ledger_id where l.status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"') xcx on pt.id=xcx.pt_id\n" +
                "where type_name like 'MAY_CHAY_XANG' and dm.years="+year+" group by rollup(4)) b)\n" +
                "union \n" +
                "(select pt_id,loai,ranks,case when xemay is null then loai else xemay end as xemay,soluong_xe,dm_km,dm_gio,km,case when EXTRACT(epoch FROM gio) is null then 0 else EXTRACT(epoch FROM gio) end as gio,dinh_muc,thuc_chi,name_gr\n" +
                "from (select RANK() OVER (ORDER BY name DESC) AS ranks,max(pt.id) as pt_id,max(type_name) as loai,name as xemay,sum(quantity) as soluong_xe,sum(dm_xm_km) as dm_km,sum(dm_xm_gio) as dm_gio,max(so_km) as km, max(giohd_md::interval) as gio,sum(so_luong) as dinh_muc,sum(so_luong) as thuc_chi,GROUPING(name) as name_gr \n" +
                "from phuongtien pt left join loai_phuongtien lpt on pt.loaiphuongtien_id=lpt.id left join dinhmuc dm on pt.id=dm.phuongtien_id\n" +
                "left join (select * from ledgers l left join ledger_details ld on l.id=ld.ledger_id where l.status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"') xcx on pt.id=xcx.pt_id\n" +
                "where type_name like 'XE_CHAY_DIEZEL' and dm.years="+year+" group by rollup(4)) d)\n" +
                "union \n" +
                "(select pt_id,loai,ranks,case when xemay is null then loai else xemay end as xemay,soluong_xe,dm_km,dm_gio,km,case when EXTRACT(epoch FROM gio) is null then 0 else EXTRACT(epoch FROM gio) end as gio,dinh_muc,thuc_chi,name_gr\n" +
                "from (select RANK() OVER (ORDER BY name DESC) AS ranks,max(pt.id) as pt_id,max(type_name) as loai,name as xemay,sum(quantity) as soluong_xe,sum(dm_xm_km) as dm_km,sum(dm_xm_gio) as dm_gio,sum(so_km) as km, max(giohd_md::interval) as gio,sum(so_luong) as dinh_muc,sum(so_luong) as thuc_chi,GROUPING(name) as name_gr \n" +
                "from phuongtien pt left join loai_phuongtien lpt on pt.loaiphuongtien_id=lpt.id left join dinhmuc dm on pt.id=dm.phuongtien_id\n" +
                "left join (select * from ledgers l left join ledger_details ld on l.id=ld.ledger_id where l.status like 'ACTIVE' and from_date between '"+sd+"' and '"+ed+"') xcx on pt.id=xcx.pt_id\n" +
                "where type_name like 'MAY_CHAY_DIEZEL' and dm.years="+year+" group by rollup(4)) e)\n" +
                "order by loai,name_gr desc,ranks";
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
    public static String end_q1_1(int root_id){
        return "grouping(tinhchat) as tc,grouping(chungloai) as l,grouping(tenxd) as xd from loaixd2 adm left join (SELECT petro_id,sum(nhap_nvdx)-sum(xuat_nvdx) as tdk_nvdx,sum(nhap_sscd)-sum(xuat_sscd) as tdk_sscd FROM public.inventory where dvi_id="+root_id+" group by 1) a on a.petro_id=adm.id";
    }
    public static String ttxd_nv(int y){
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
                "left join (select nhiemvu_id,root_id,max(so_km) as km, max(giohd_md::interval) as gio from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
                "where (l.lpt like 'XE_CHAY_XANG' OR l.lpt like 'MAY_CHAY_XANG') and l.status like 'ACTIVE' group by 1,2) a on (ct.id=a.nhiemvu_id and hm2.dvi_id=a.root_id)\n" +
                "left join (select nhiemvu_id,root_id,max(so_km) as km, max(giohd_md::interval) as gio from ledger_details ld join ledgers l on ld.ledger_id=l.id \n" +
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
                "where years="+y+") zz\n" +
                "group by n,ten_nv,nhiemvu) aaa\n" +
                "group by rollup(n,ten_nv,nhiemvu)\n" +
                "order by name_gr desc,tt,tennv_gr desc,pri,ten_nv,nv_gr desc\n" +
                "\n";
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
