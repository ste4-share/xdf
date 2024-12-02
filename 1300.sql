select ten,tenxmt,ten_nv,nhiemvu,max(md) as md,max(tk) as tk,max(sum_ct) as sum_ct,max(nl) as nl,max(hdtk) as hdtk,
max(hdmd) as hdmd,max(sum_hd) as sum_hd,max(txmd) as txmd,max(txtk) as txtk,max(sum_tt) as sum_tt,max(sum_hh) as sum_hh,max(tongcong) as tongcong,max(ten_gr) as ten_gr,
max(xmt_gr) as xmt_gr,max(tennv_gr) as tennv_gr,max(nv_gr) as nv_gr,max(dm_tk) as dm_tk,max(dm_md) as dm_md,max(stt) as stt
from (select * from (select ten,nlb.name as tenxmt,ten_nv,
case when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then ten_nv 
when grouping(nhiemvu)=1 and grouping(ten_nv)=1 and grouping(nlb.name)=0 then concat(ten,'||',nlb.name)
when grouping(ten_nv)=1 and grouping(nlb.name)=1 and grouping(ten)=0 then ten else nhiemvu end,
concat(extract(hour from SUM(case when md is null then '00:00' else md::interval end)),':',extract(minute from SUM(case when md is null then '00:00' else md::interval end))) as md,
concat(extract(hour from SUM(case when tk is null then '00:00' else tk::interval end)),':',extract(minute from SUM(case when tk is null then '00:00' else tk::interval end))) as tk,
concat(extract(hour from SUM(case when (md::interval+tk::interval) is null then '00:00' else (md::interval+tk::interval) end)),':',extract(minute from SUM(case when (md::interval+tk::interval) is null then '00:00' else (md::interval+tk::interval) end))) as sum_ct,
sum(nl) as nl,
concat(extract(hour from SUM(case when hdtk is null then '00:00' else hdtk::interval end)),':',extract(minute from SUM(case when hdtk is null then '00:00' else hdtk::interval end))) as hdtk,
concat(extract(hour from SUM(case when hdmd is null then '00:00' else hdmd::interval end)),':',extract(minute from SUM(case when hdmd is null then '00:00' else hdmd::interval end))) as hdmd,
concat(extract(hour from SUM(case when sum_hd is null then '00:00' else sum_hd::interval end)),':',extract(minute from SUM(case when sum_hd is null then '00:00' else sum_hd::interval end))) as sum_hd,
sum(txmd) as txmd,
sum(txtk) as txtk,
sum(sum_tt) as sum_tt,
sum(haohut) as sum_hh,
sum(tongcong) as tongcong,
grouping(ten) as ten_gr,grouping(nlb.name) as xmt_gr, grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nv_gr,
max(dm_tk_gio) as dm_tk,max(dm_md_gio) as dm_md,max(nlb.priority_bc2) as stt
from (select * from (SELECT * FROM chitieu_pt ctpt
join nguon_nx nnx on ctpt.dvi_id=nnx.id
join chitiet_nhiemvu ct on ct.id=ctpt.ctnv_id
join nhiemvu n on n.id=ct.nhiemvu_id
left join phuongtien pt on pt.id=ctpt.pt_id 
left join dinhmuc dm on (dm.quarter_id=ctpt.quy_id and pt.id=dm.phuongtien_id)) chitieu
left join (
select l.quarter_id as q_id,dvi_xuat_id,phuongtien_id,nhiemvu_id, sum(giohd_tk::interval) as hdtk,sum(giohd_md::interval) as hdmd,
sum(giohd_tk::interval)+sum(giohd_md::interval) as sum_hd,sum(thuc_xuat) as txmd, sum(thuc_xuat_tk) as txtk,
sum(thuc_xuat)+sum(thuc_xuat_tk) as sum_tt, sum(haohut_sl) as haohut,sum(thuc_xuat)+sum(thuc_xuat_tk)+sum(haohut_sl) as tongcong from ledger_details ld
join ledgers l on ld.ledger_id=l.id
group by l.quarter_id,dvi_xuat_id,phuongtien_id,nhiemvu_id) socai 
on (chitieu.dvi_id=socai.dvi_xuat_id and chitieu.ctnv_id=socai.nhiemvu_id and chitieu.pt_id=socai.phuongtien_id)) nlb
group by rollup(ten,nlb.name,ten_nv,nhiemvu)) raw_1) raw_2
where ten_gr+xmt_gr<>1
group by 1,2,3,4
order by ten_gr desc,ten desc,tennv_gr desc,stt asc,ten_nv, nv_gr desc

