with shit as(
SELECT loaixd_id,don_gia,sum(nhap_nvdx)-sum(xuat_nvdx) as tdk_nvdx,sum(nhap_sscd)-sum(xuat_sscd) as tdk_sscd 
FROM ledgers l join ledger_details ld on l.id=ld.ledger_id where from_date < '2025-01-05' group by 1,2 UNION ALL 
SELECT loaixd_id,don_gia,0,0 FROM ledgers l join ledger_details ld on l.id=ld.ledger_id where from_date between '2025-01-05' and '2025-01-08' group by 1,2
)
select tinhchat,loai,case when tenxd is null then loai else tenxd end,case when price is null then 0 else price end,
sum(soluong) as tdk_sl,sum(thanhtien) as tdk_thanhtien,sum(cxd) as cxd,sum(qc) as qc,sum(pc) as pc,sum(ndvk) as ndvk,sum(nfnb) as nfnb,sum(nk) as nk,
(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk)) as sl_nhap,(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))*price as n_thanhtien,
sum(tt_xm) as tt_xm,sum(bq) as bq,sum(hh) as hh,sum(xdvk) as xdvk,sum(xfnb) as xfnb,sum(tt) as tt,sum(xk) as xk,(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk)) as sl_xuat,
(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk))*price as x_thanhtien,
(sum(soluong)+(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))-(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk))) as tck_sl,
(sum(soluong)+(sum(cxd)+sum(qc)+sum(pc)+sum(ndvk)+sum(nk))-(sum(tt_xm)+sum(bq)+sum(hh)+sum(xdvk)+sum(tt)+sum(xk)))*price as tck_thanhtien,
grouping(tinhchat) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as tenxd_gr,grouping(price) as price_gr
from (select lxd.id as lxd_id,tinhchat,cl.chungloai,loai,maxd,tenxd,case when s.don_gia is null then 0 else s.don_gia end as price,
case when tdk_sscd+tdk_nvdx is null then 0 else tdk_sscd+tdk_nvdx end as soluong,
s.don_gia*(tdk_sscd+tdk_nvdx) as thanhtien,
nCXD.soluong as CXD,nQC.soluong as QC,nPC.soluong as PC,nDVK.soluong as nDVK,nFNB.soluong as nFNB,nK.soluong as nK,TT_XM.soluong as TT_XM,BQ.soluong as BQ,
HH.soluong as HH,xDVK.soluong as xDVK,xFNB.soluong as xFNB,TT.soluong as TT,xK.soluong as xK
from loaixd2 lxd 
left join chungloaixd cl on lxd.petroleum_type_id=cl.id 
left join shit s on s.loaixd_id=lxd.id 
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'CXD' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-08' group by 1,2) nCXD on (lxd.id=nCXD.loaixd_id and nCXD.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'QC' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) nQC on (lxd.id=nQC.loaixd_id and nQC.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'PC' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) nPC on (lxd.id=nPC.loaixd_id and nPC.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'DVK' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) nDVK on (lxd.id=nDVK.loaixd_id and nDVK.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'FNB' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) nFNB on (lxd.id=nFNB.loaixd_id and nFNB.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'NHAP' and tructhuoc like 'K' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) nK on (lxd.id=nK.loaixd_id and nK.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'TT_XM' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) TT_XM on (lxd.id=TT_XM.loaixd_id and TT_XM.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'BQ' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) BQ on (lxd.id=BQ.loaixd_id and BQ.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'HH' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) HH on (lxd.id=HH.loaixd_id and HH.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'DVK' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) xDVK on (lxd.id=xDVK.loaixd_id and xDVK.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'FNB' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) xFNB on (lxd.id=xFNB.loaixd_id and xFNB.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'TT' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) TT on  (lxd.id=TT.loaixd_id and TT.don_gia=s.don_gia)
left join (select loaixd_id,don_gia, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id 
where loai_phieu like 'XUAT' and tructhuoc like 'K' and status like 'ACTIVE' and from_date between '2025-01-05' and '2025-01-07' group by 1,2) xK on (lxd.id=xK.loaixd_id and xK.don_gia=s.don_gia)) z
group by rollup(tinhchat,loai,tenxd,price)
order by tc_gr desc,tinhchat desc,loai_gr desc,loai,tenxd_gr desc,tenxd,price_gr desc