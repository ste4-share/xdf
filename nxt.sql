select max(ranks) as ranks,tinhchat,loai,tenxd,price, case when gia is null then 0 else gia end,
sum(soluong) as tdk_sl,sum(thanhtien) as tdk_thanhtien,sum(cxd) as cxd,sum(qc) as qc,sum(pc) as pc,sum(ndvk) as ndvk,sum(nfnb) as nfnb,sum(nk) as nk,
(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk)) as sl_nhap,(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))*(case when gia is null then 0 else gia end) as n_thanhtien,
sum(tt_xm) as tt_xm,sum(bq) as bq,sum(hh) as hh,sum(xdvk) as xdvk,sum(xfnb) as xfnb,sum(tt) as tt,sum(xk) as xk,(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk)) as sl_xuat,
(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk))*(case when gia is null then 0 else gia end) as x_thanhtien,
(sum(soluong)+(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))-(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk))) as tck_sl,
(sum(soluong)+(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))-(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk)))*(case when gia is null then 0 else gia end) as tck_thanhtien,
grouping(tinhchat) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as tenxd_gr,grouping(gia) as price_gr
from (select ranks,lxd_id,tinhchat,chungloai,loai,maxd,tenxd,price,max(b.soluong) as soluong,max(b.thanhtien) as thanhtien,
unnest(ARRAY[price,max(nCXD.don_gia), max(nQC.don_gia), max(nPC.don_gia),max(nDVK.don_gia),max(nFNB.don_gia),max(nK.don_gia),max(TT_XM.don_gia),
max(BQ.don_gia),max(HH.don_gia),max(xDVK.don_gia),max(xFNB.don_gia),max(TT.don_gia),max(xK.don_gia)]) as gia,
case when max(nCXD.soluong) is null then 0 else max(nCXD.soluong) end as CXD,
case when max(nQC.soluong) is null then 0 else max(nQC.soluong) end as QC,
case when max(nPC.soluong) is null then 0 else max(nPC.soluong) end as PC,
case when max(nDVK.soluong) is null then 0 else max(nDVK.soluong) end as nDVK,
case when max(nFNB.soluong) is null then 0 else max(nFNB.soluong) end as nFNB,
case when max(nK.soluong) is null then 0 else max(nK.soluong) end as nK,
case when max(TT_XM.soluong) is null then 0 else max(TT_XM.soluong) end as TT_XM,
case when max(BQ.soluong) is null then 0 else max(BQ.soluong) end as BQ,
case when max(HH.soluong) is null then 0 else max(HH.soluong) end as HH,
case when max(xDVK.soluong) is null then 0 else max(xDVK.soluong) end as xDVK,
case when max(xFNB.soluong) is null then 0 else max(xFNB.soluong) end as xFNB,
case when max(TT.soluong) is null then 0 else max(TT.soluong) end as TT,
case when max(xK.soluong) is null then 0 else max(xK.soluong) end as xK
from (select RANK() OVER (ORDER BY lxd.id ASC) AS ranks,lxd.id as lxd_id,tinhchat,cl.chungloai,loai,maxd,tenxd,a.don_gia as price,
case when max(tdk_sscd)+max(tdk_nvdx) is null then 0 else max(tdk_sscd)+max(tdk_nvdx) end as soluong,a.don_gia*(max(tdk_sscd)+max(tdk_nvdx)) as thanhtien
from loaixd2 lxd 
left join chungloaixd cl on lxd.petroleum_type_id=cl.id 
left join (SELECT loaixd_id,don_gia,sum(nhap_nvdx)-sum(xuat_nvdx) as tdk_nvdx,sum(nhap_sscd)-sum(xuat_sscd) as tdk_sscd 
FROM ledgers l join ledger_details ld on l.id=ld.ledger_id where from_date < '2025-01-05' group by 1,2) a on a.loaixd_id=lxd.id
group by 2,3,4,5,6,7,8) b
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'CXD' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-08' group by 1,2) nCXD on (lxd_id=nCXD.loaixd_id and nCXD.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'QC' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) nQC on (lxd_id=nQC.loaixd_id and nQC.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'PC' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) nPC on (lxd_id=nPC.loaixd_id and nPC.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'DVK' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) nDVK on (lxd_id=nDVK.loaixd_id and nDVK.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'FNB' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) nFNB on (lxd_id=nFNB.loaixd_id and nFNB.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'K' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) nK on (lxd_id=nK.loaixd_id and nK.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'TT_XM' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) TT_XM on (lxd_id=TT_XM.loaixd_id and TT_XM.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'BQ' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) BQ on (lxd_id=BQ.loaixd_id and BQ.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'HH' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) HH on (lxd_id=HH.loaixd_id and HH.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'DVK' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) xDVK on (lxd_id=xDVK.loaixd_id and xDVK.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'FNB' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) xFNB on (lxd_id=xFNB.loaixd_id and xFNB.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'TT' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) TT on  (lxd_id=TT.loaixd_id and TT.don_gia=b.price)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'K' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) xK on 
(lxd_id=xK.loaixd_id and xK.don_gia=b.price) group by 1,2,3,4,5,6,7,8) z
group by rollup(tinhchat,loai,tenxd,price,gia)
order by tc_gr desc,tinhchat desc,loai_gr desc,loai,tenxd_gr desc,tenxd,price_gr desc