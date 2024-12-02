select ten,ten_nv11,ten_nv,max(sum_cttk) as sum_cttk,max(sum_ctmd) as sum_ctmd,max(cong_ct_gio) as cong_ct_gio,max(nhienlieu) as nhienlieu,
max(hdtk) as hdtk,max(hdmd) as hdmd,max(sum_hd) as sum_hd,max(sumtxmd) as sumtxmd,max(sum_txtk) as sum_txtk,max(sum_tt) as sum_tt,max(sum_hh) as sum_hh,
max(sum_tongcong) as sum_tongcong,max(ten_gr) as ten_gr,max(tennv_gr) as tennv_gr,max(nhiemvu_gr) as nhiemvu_gr,max(seq_num) as seq_num from (select 'ten' as ten,case when grouping(ten_nv)=1 then 'B' when ten_nv is null then ten_nv else ten_nv end as ten_nv11,
case when nhiemvu is null and grouping(ten_nv)=1 then 'Cộng nhiệm vụ' 
when nhiemvu is not null then nhiemvu else ten_nv end,
concat(extract(hour from SUM(case when ct_tk is null then '00:00' else ct_tk end)),':',extract(minute from SUM(case when ct_tk is null then '00:00' else ct_tk end))) as sum_cttk,
concat(extract(hour from SUM(case when ct_md is null then '00:00' else ct_md end)),':',extract(minute from SUM(case when ct_md is null then '00:00' else ct_md end))) as sum_ctmd,
concat(extract(hour from SUM(case when cong_ct_gio is null then '00:00' else cong_ct_gio end)),':',extract(minute from SUM(case when cong_ct_gio is null then '00:00' else cong_ct_gio end))) as cong_ct_gio,
sum(consumpt) as nhienlieu,
concat(extract(hour from SUM(case when hdtk is null then '00:00' else hdtk end)),':',extract(minute from SUM(case when hdtk is null then '00:00' else hdtk end))) as hdtk,
concat(extract(hour from SUM(case when hdmd is null then '00:00' else hdmd end)),':',extract(minute from SUM(case when hdmd is null then '00:00' else hdmd end))) as hdmd,
concat(extract(hour from SUM(case when sum_hd is null then '00:00' else sum_hd end)),':',extract(minute from SUM(case when sum_hd is null then '00:00' else sum_hd end))) as sum_hd,
sum(txmd) as sumtxmd,
sum(txtk) as sum_txtk,
sum(sum_tt) as sum_tt,sum(haohut) as sum_hh,sum(tongcong) as sum_tongcong,
1 as ten_gr,grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nhiemvu_gr,max(stt) as seq_num
from (select ten,ten_nv,nhiemvu,ct_tk,ct_md,cong_ct_gio,consumpt,hdtk,hdmd,sum_hd,txmd,txtk,sum_tt,haohut,tongcong,sour.priority_bc2 as stt from (select nxx.id as nguonnx_id,ct.id as ctnv_id,ten,ten_nv,nhiemvu,ct_tk::interval,ct_md::interval,ct_tk::interval+ct_md::interval as cong_ct_gio,consumpt,priority, priority_bc2 
from hanmuc_nhiemvu2 hmnv 
join nguon_nx nxx on hmnv.unit_id=nxx.id 
join chitiet_nhiemvu ct on ct.id=hmnv.nhiemvu_id
join nhiemvu n on n.id=ct.nhiemvu_id
order by ten) sour
left join (select l.quarter_id as q_id,dvi_xuat_id,nhiemvu_id, sum(giohd_tk::interval) as hdtk,sum(giohd_md::interval) as hdmd,sum(giohd_tk::interval)+sum(giohd_md::interval) as sum_hd,sum(thuc_xuat) as txmd, sum(thuc_xuat_tk) as txtk,sum(thuc_xuat)+sum(thuc_xuat_tk) as sum_tt, sum(haohut_sl) as haohut,sum(thuc_xuat)+sum(thuc_xuat_tk)+sum(haohut_sl) as tongcong from ledger_details ld
join ledgers l on ld.ledger_id=l.id
group by l.quarter_id,dvi_xuat_id,nhiemvu_id) dic
on (sour.nguonnx_id=dic.dvi_xuat_id and sour.ctnv_id=dic.nhiemvu_id)) raw_dat
group by rollup(ten_nv,nhiemvu)
order by tennv_gr desc,seq_num asc,ten_nv11) dat_4
group by 1,2,3
