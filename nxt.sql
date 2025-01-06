select tinhchat,chungloai,loai,case when grouping(tenxd)=1 and grouping(loai)=0 then loai else tenxd end,sum(tdk_nvdx) as tdk_nvdx,sum(tdk_sscd) as tdk_sscd,
sum(cong_tdk) as cong_tdk,sum(nPC) as PC,sum(nCXD) as CXD,sum(nTT_XM) as TT_XM,sum(nHH) as HH,sum(nQK2) as QK2,sum(nBQ) as BQ,sum(nN_QC) as N_QC,sum(nTT) as TT,
sum(nFNB) as FNB,sum(nQC) as QC,sum(nDVK) as DVK,sum(nK) as K,sum(nTQC) as TQC,sum(xPC) as PC,sum(xCXD) as CXD,sum(xTT_XM) as TT_XM,sum(xHH) as HH,sum(xQK2) as QK2,
sum(xBQ) as BQ,sum(xN_QC) as N_QC,sum(xTT) as TT,sum(xFNB) as FNB,sum(xQC) as QC,sum(xDVK) as DVK,sum(xK) as K,sum(xTQC) as TQC,max(p1) as p1, max(p2) as p2, max(p3) as p3,
grouping(tinhchat) as tc,grouping(loai) as l,grouping(tenxd) as xd 
from (select lxd.id,tinhchat,cl.chungloai,loai,maxd,tenxd,
case when tdk_nvdx is null then 0 else tdk_nvdx end,case when tdk_sscd is null then 0 else tdk_sscd end,case when tdk_sscd+tdk_nvdx is null then 0 else tdk_sscd+tdk_nvdx end as cong_tdk,
case when nPC.soluong is null then 0 else nPC.soluong end as nPC,case when nCXD.soluong is null then 0 else nCXD.soluong end as nCXD,
case when nTT_XM.soluong is null then 0 else nTT_XM.soluong end as nTT_XM,case when nHH.soluong is null then 0 else nHH.soluong end as nHH,
case when nQK2.soluong is null then 0 else nQK2.soluong end as nQK2,case when nBQ.soluong is null then 0 else nBQ.soluong end as nBQ,
case when nN_QC.soluong is null then 0 else nN_QC.soluong end as nN_QC,case when nTT.soluong is null then 0 else nTT.soluong end as nTT,
case when nFNB.soluong is null then 0 else nFNB.soluong end as nFNB,case when nQC.soluong is null then 0 else nQC.soluong end as nQC,
case when nDVK.soluong is null then 0 else nDVK.soluong end as nDVK,case when nK.soluong is null then 0 else nK.soluong end as nK,
case when nTQC.soluong is null then 0 else nTQC.soluong end as nTQC,case when xPC.soluong is null then 0 else xPC.soluong end as xPC,
case when xCXD.soluong is null then 0 else xCXD.soluong end as xCXD,case when xTT_XM.soluong is null then 0 else xTT_XM.soluong end as xTT_XM,case when xHH.soluong is null then 0 else xHH.soluong end as xHH,
case when xQK2.soluong is null then 0 else xQK2.soluong end as xQK2,case when xBQ.soluong is null then 0 else xBQ.soluong end as xBQ,
case when xN_QC.soluong is null then 0 else xN_QC.soluong end as xN_QC,case when xTT.soluong is null then 0 else xTT.soluong end as xTT,case when xFNB.soluong is null then 0 else xFNB.soluong end as xFNB,
case when xQC.soluong is null then 0 else xQC.soluong end as xQC,case when xDVK.soluong is null then 0 else xDVK.soluong end as xDVK,
case when xK.soluong is null then 0 else xK.soluong end as xK,case when xTQC.soluong is null then 0 else xTQC.soluong end 
as xTQC,cl.priority_1 as p1,cl.priority_2 as p2,cl.priority_3 as p3 
from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id 
left join (SELECT petro_id,sum(nhap_nvdx)-sum(xuat_nvdx) as tdk_nvdx,sum(nhap_sscd)-sum(xuat_sscd) as tdk_sscd FROM public.inventory group by 1) a on a.petro_id=lxd.id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'PC' and status like 'ACTIVE') nPC on lxd.id=nPC.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'CXD' and status like 'ACTIVE') nCXD on lxd.id=nCXD.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'TT_XM' and status like 'ACTIVE') nTT_XM on lxd.id=nTT_XM.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'HH' and status like 'ACTIVE') nHH on lxd.id=nHH.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'QK2' and status like 'ACTIVE') nQK2 on lxd.id=nQK2.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'BQ' and status like 'ACTIVE') nBQ on lxd.id=nBQ.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'N_QC' and status like 'ACTIVE') nN_QC on lxd.id=nN_QC.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'TT' and status like 'ACTIVE') nTT on lxd.id=nTT.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'FNB' and status like 'ACTIVE') nFNB on lxd.id=nFNB.loaixd_id 
left join (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'QC' and status like 'ACTIVE' group by 1) nQC on lxd.id=nQC.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'DVK' and status like 'ACTIVE') nDVK on lxd.id=nDVK.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'K' and status like 'ACTIVE') nK on lxd.id=nK.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'TQC' and status like 'ACTIVE') nTQC on lxd.id=nTQC.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'PC' and status like 'ACTIVE') xPC on lxd.id=xPC.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'CXD' and status like 'ACTIVE') xCXD on lxd.id=xCXD.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'TT_XM' and status like 'ACTIVE') xTT_XM on lxd.id=xTT_XM.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'HH' and status like 'ACTIVE') xHH on lxd.id=xHH.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'QK2' and status like 'ACTIVE') xQK2 on lxd.id=xQK2.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'BQ' and status like 'ACTIVE') xBQ on lxd.id=xBQ.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'N_QC' and status like 'ACTIVE') xN_QC on lxd.id=xN_QC.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'TT' and status like 'ACTIVE') xTT on lxd.id=xTT.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'FNB' and status like 'ACTIVE') xFNB on lxd.id=xFNB.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'QC' and status like 'ACTIVE') xQC on lxd.id=xQC.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'DVK' and status like 'ACTIVE') xDVK on lxd.id=xDVK.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'K' and status like 'ACTIVE') xK on lxd.id=xK.loaixd_id 
left join (select loaixd_id, so_luong as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'TQC' and status like 'ACTIVE') xTQC on lxd.id=xTQC.loaixd_id) z 
group by rollup(tinhchat,chungloai,loai,tenxd) order by tc desc,tinhchat desc,l desc,p3 asc,xd desc