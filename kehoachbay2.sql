(select charseq,case when ten is not null then 'ten' else 'ten' end as ten, seqnum,nhiem_vu,priority,sum_cttk,sum_ctmd,sum_dinhmuc,sum_csump,sum_durmd,sum_durtk,sum_giobay,sum_txmd,sum_txtk,sum_tieuthu,haohut,ten_gr,tennv_gr,nhiemvu_gr,nv_count,seqnum2
from (select 'B' as charseq,* from (select *,rank() over (partition by ten order by nhiem_vu desc) as seqnum2
from (select CASE WHEN ten_nv is null then 'Cộng Nhiệm vụ' else ten_nv end as ten,dense_rank() over (order by ten_nv asc) as seqnum,
CASE WHEN GROUPING(nhiemvu)=1 and GROUPING(ten_nv)=1 then 'Cộng Nhiệm vụ' 
WHEN GROUPING(nhiemvu)=1 and GROUPING(ten_nv)=0 then ten_nv else nhiemvu end as nhiem_vu,
CASE WHEN GROUPING(ten_nv)=1 then 0 else priority end,
concat(extract(hour from SUM(case when ct_tk is null then '00:00' else ct_tk end)),':',extract(minute from SUM(case when ct_tk is null then '00:00' else ct_tk end))) as sum_cttk,
concat(extract(hour from SUM(case when ct_md is null then '00:00' else ct_md end)),':',extract(minute from SUM(case when ct_md is null then '00:00' else ct_md end))) as sum_ctmd,
concat(extract(hour from SUM(case when ct_md+ct_tk is null then '00:00' else ct_md+ct_tk end)),':',extract(minute from SUM(case when ct_md+ct_tk is null then '00:00' else ct_md+ct_tk end))) as sum_dinhmuc,
SUM(consumpt) AS sum_csump, 
concat(extract(hour from SUM(case when dur_md is null then '00:00' else dur_md end)),':',extract(minute from SUM(case when dur_md is null then '00:00' else dur_md end))) as sum_durmd,
concat(extract(hour from SUM(case when dur_tk is null then '00:00' else dur_tk end)),':',extract(minute from SUM(case when dur_tk is null then '00:00' else dur_tk end))) as sum_durtk,
concat(extract(hour from SUM(case when dur_tk+dur_md is null then '00:00' else dur_tk+dur_md end)),':',extract(minute from SUM(case when dur_tk+dur_md is null then '00:00' else dur_tk+dur_md end))) as sum_giobay,
SUM(case when tx_md is null then 0 else tx_md end) sum_txmd,
SUM(case when tx_tk is null then 0 else tx_tk end) as sum_txtk,
SUM(case when tx_md+tx_tk is null then 0 else tx_md+tx_tk end) as sum_tieuthu,
SUM(case when haohut is null then 0 else haohut end) as haohut,
0 as ten_gr,grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nhiemvu_gr, count(nhiemvu) as nv_count
from hanmuc_nhiemvu
join chitiet_nhiemvu on hanmuc_nhiemvu.nhiemvu_id=chitiet_nhiemvu.id
join nhiemvu on nhiemvu.id= chitiet_nhiemvu.nhiemvu_id
left join (select nhiemvu_hanmuc_id, sum(l.giohd_md::interval) as dur_md, sum(l.giohd_tk::interval) as dur_tk, sum(ld.thuc_xuat) as tx_md,sum(ld.thuc_xuat_tk) as tx_tk,sum(haohut_sl) as haohut 
from ledger_details ld join ledgers l on ld.ledger_id=l.id 
where l.quarter_id=20 group by nhiemvu_hanmuc_id) lsd on lsd.nhiemvu_hanmuc_id=hanmuc_nhiemvu.id
where nhiemvu.assignment_type_id=1 or nhiemvu.assignment_type_id=3 
GROUP BY ROLLUP(ten_nv,priority,nhiemvu)
order by ten desc, grouping(priority) asc, priority asc,grouping(nhiemvu) desc, nhiemvu desc) abss 
where (abss.nv_count + abss.nhiemvu_gr<>2 and priority is not null)
order by priority asc, abss.nv_count desc) ssg
where (nhiemvu_gr+seqnum2<>2) or tennv_gr=1 or seqnum2=2) sxd)
union (select 'A' as charseq,*, dense_rank() over (order by priority asc) as seqnum2 
from (select pts.name as ten,dense_rank() over (order by pts.name) as seqnum,
CASE WHEN GROUPING(nhiemvu)=1 and GROUPING(ten_nv)=1 then pts.name
WHEN GROUPING(nhiemvu)=1 and GROUPING(ten_nv)=0 then ten_nv else nhiemvu end as nhiem_vu,
CASE WHEN GROUPING(ten_nv)=1 then 0 else priority end,
concat(extract(hour from SUM(case when ct_tk is null then '00:00' else ct_tk end)),':',extract(minute from SUM(case when ct_tk is null then '00:00' else ct_tk end))) as sum_cttk,
concat(extract(hour from SUM(case when ct_md is null then '00:00' else ct_md end)),':',extract(minute from SUM(case when ct_md is null then '00:00' else ct_md end))) as sum_ctmd,
concat(extract(hour from SUM(case when ct_md+ct_tk is null then '00:00' else ct_md+ct_tk end)),':',extract(minute from SUM(case when ct_md+ct_tk is null then '00:00' else ct_md+ct_tk end))) as sum_dinhmuc,
SUM(consumpt) AS sum_csump, 
concat(extract(hour from SUM(case when dur_md is null then '00:00' else dur_md end)),':',extract(minute from SUM(case when dur_md is null then '00:00' else dur_md end))) as sum_durmd,
concat(extract(hour from SUM(case when dur_tk is null then '00:00' else dur_tk end)),':',extract(minute from SUM(case when dur_tk is null then '00:00' else dur_tk end))) as sum_durtk,
concat(extract(hour from SUM(case when dur_tk+dur_md is null then '00:00' else dur_tk+dur_md end)),':',extract(minute from SUM(case when dur_tk+dur_md is null then '00:00' else dur_tk+dur_md end))) as sum_giobay,
SUM(case when tx_md is null then 0 else tx_md end) sum_txmd,
SUM(case when tx_tk is null then 0 else tx_tk end) as sum_txtk,
SUM(case when tx_md+tx_tk is null then 0 else tx_md+tx_tk end) as sum_tieuthu,
SUM(case when haohut is null then 0 else haohut end) as haohut,
grouping(pts.name) as ten_gr,grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nhiemvu_gr, count(nhiemvu) as nv_count
from hanmuc_nhiemvu hmnv
join chitiet_nhiemvu ctnv on hmnv.nhiemvu_id=ctnv.id
join nhiemvu nv on nv.id= ctnv.nhiemvu_id
join nguon_nx u on u.id = hmnv.unit_id
join (select pt.name,nguonnx_id from phuongtien pt join loai_phuongtien lpt on pt.loaiphuongtien_id=lpt.id where lpt.type like 'MAYBAY') pts on pts.nguonnx_id=u.id
left join (select nhiemvu_hanmuc_id, sum(l.giohd_md::interval) as dur_md, sum(l.giohd_tk::interval) as dur_tk, sum(ld.thuc_xuat) as tx_md,sum(ld.thuc_xuat_tk) as tx_tk,sum(haohut_sl) as haohut 
from ledger_details ld join ledgers l on ld.ledger_id=l.id where l.quarter_id=20 group by nhiemvu_hanmuc_id) lsd on lsd.nhiemvu_hanmuc_id=hmnv.id
GROUP BY ROLLUP(pts.name,ten_nv,priority,nhiemvu)
order by ten desc, grouping(priority) asc, priority asc,grouping(nhiemvu) desc, nhiemvu desc) abss 
where ten_gr <> 1 and (abss.nv_count + abss.nhiemvu_gr<>2 and priority is not null) and priority=0)
union 
(select 'C' as charseq,*,dense_rank() over (order by priority asc) as seqnum2 
from (select pts.name as ten,dense_rank() over (order by pts.name) as seqnum,
CASE WHEN GROUPING(nhiemvu)=1 and GROUPING(ten_nv)=1 then pts.name
WHEN GROUPING(nhiemvu)=1 and GROUPING(ten_nv)=0 then ten_nv else nhiemvu end as nhiem_vu,
CASE WHEN GROUPING(ten_nv)=1 then 0 else priority end,
concat(extract(hour from SUM(case when ct_tk is null then '00:00' else ct_tk end)),':',extract(minute from SUM(case when ct_tk is null then '00:00' else ct_tk end))) as sum_cttk,
concat(extract(hour from SUM(case when ct_md is null then '00:00' else ct_md end)),':',extract(minute from SUM(case when ct_md is null then '00:00' else ct_md end))) as sum_ctmd,
concat(extract(hour from SUM(case when ct_md+ct_tk is null then '00:00' else ct_md+ct_tk end)),':',extract(minute from SUM(case when ct_md+ct_tk is null then '00:00' else ct_md+ct_tk end))) as sum_dinhmuc,
SUM(consumpt) AS sum_csump, 
concat(extract(hour from SUM(case when dur_md is null then '00:00' else dur_md end)),':',extract(minute from SUM(case when dur_md is null then '00:00' else dur_md end))) as sum_durmd,
concat(extract(hour from SUM(case when dur_tk is null then '00:00' else dur_tk end)),':',extract(minute from SUM(case when dur_tk is null then '00:00' else dur_tk end))) as sum_durtk,
concat(extract(hour from SUM(case when dur_tk+dur_md is null then '00:00' else dur_tk+dur_md end)),':',extract(minute from SUM(case when dur_tk+dur_md is null then '00:00' else dur_tk+dur_md end))) as sum_giobay,
SUM(case when tx_md is null then 0 else tx_md end) sum_txmd,
SUM(case when tx_tk is null then 0 else tx_tk end) as sum_txtk,
SUM(case when tx_md+tx_tk is null then 0 else tx_md+tx_tk end) as sum_tieuthu,
SUM(case when haohut is null then 0 else haohut end) as haohut,
grouping(pts.name) as ten_gr,grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nhiemvu_gr, count(nhiemvu) as nv_count
from hanmuc_nhiemvu hmnv
join chitiet_nhiemvu ctnv on hmnv.nhiemvu_id=ctnv.id
join nhiemvu nv on nv.id= ctnv.nhiemvu_id
join nguon_nx u on u.id = hmnv.unit_id
join (select pt.name,nguonnx_id from phuongtien pt join loai_phuongtien lpt on pt.loaiphuongtien_id=lpt.id where lpt.type like 'MAYBAY') pts on pts.nguonnx_id=u.id
left join (select nhiemvu_hanmuc_id, sum(l.giohd_md::interval) as dur_md, sum(l.giohd_tk::interval) as dur_tk, sum(ld.thuc_xuat) as tx_md,sum(ld.thuc_xuat_tk) as tx_tk,sum(haohut_sl) as haohut 
from ledger_details ld join ledgers l on ld.ledger_id=l.id where l.quarter_id=20 group by nhiemvu_hanmuc_id) lsd on lsd.nhiemvu_hanmuc_id=hmnv.id
GROUP BY ROLLUP(pts.name,ten_nv,priority,nhiemvu)
order by ten desc, grouping(priority) asc, priority asc,grouping(nhiemvu) desc, nhiemvu desc) abss 
where ten_gr <> 1 and (abss.nv_count + abss.nhiemvu_gr<>2 and priority is not null))
union 
(select 'D' as charseq,*,dense_rank() over (order by priority asc) as seqnum2 
from (select ten,dense_rank() over (order by ten) as seqnum,
CASE WHEN GROUPING(nhiemvu)=1 and GROUPING(ten_nv)=1 then ten 
WHEN GROUPING(nhiemvu)=1 and GROUPING(ten_nv)=0 then ten_nv else nhiemvu end as nhiem_vu,
CASE WHEN GROUPING(ten_nv)=1 then 0 else priority end,
concat(extract(hour from SUM(case when ct_tk is null then '00:00' else ct_tk end)),':',extract(minute from SUM(case when ct_tk is null then '00:00' else ct_tk end))) as sum_cttk,
concat(extract(hour from SUM(case when ct_md is null then '00:00' else ct_md end)),':',extract(minute from SUM(case when ct_md is null then '00:00' else ct_md end))) as sum_ctmd,
concat(extract(hour from SUM(case when ct_md+ct_tk is null then '00:00' else ct_md+ct_tk end)),':',extract(minute from SUM(case when ct_md+ct_tk is null then '00:00' else ct_md+ct_tk end))) as sum_dinhmuc,
SUM(consumpt) AS sum_csump, 
concat(extract(hour from SUM(case when dur_md is null then '00:00' else dur_md end)),':',extract(minute from SUM(case when dur_md is null then '00:00' else dur_md end))) as sum_durmd,
concat(extract(hour from SUM(case when dur_tk is null then '00:00' else dur_tk end)),':',extract(minute from SUM(case when dur_tk is null then '00:00' else dur_tk end))) as sum_durtk,
concat(extract(hour from SUM(case when dur_tk+dur_md is null then '00:00' else dur_tk+dur_md end)),':',extract(minute from SUM(case when dur_tk+dur_md is null then '00:00' else dur_tk+dur_md end))) as sum_giobay,
SUM(case when tx_md is null then 0 else tx_md end) sum_txmd,
SUM(case when tx_tk is null then 0 else tx_tk end) as sum_txtk,
SUM(case when tx_md+tx_tk is null then 0 else tx_md+tx_tk end) as sum_tieuthu,
SUM(case when haohut is null then 0 else haohut end) as haohut,
grouping(ten) as ten_gr,grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nhiemvu_gr, count(nhiemvu) as nv_count
from hanmuc_nhiemvu hmnv
join chitiet_nhiemvu ctnv on hmnv.nhiemvu_id=ctnv.id
join nhiemvu nv on nv.id= ctnv.nhiemvu_id
join nguon_nx u on u.id = hmnv.unit_id
left join (select nhiemvu_hanmuc_id, sum(l.giohd_md::interval) as dur_md, sum(l.giohd_tk::interval) as dur_tk, sum(ld.thuc_xuat) as tx_md,sum(ld.thuc_xuat_tk) as tx_tk,sum(haohut_sl) as haohut 
from ledger_details ld join ledgers l on ld.ledger_id=l.id where l.quarter_id=20 group by nhiemvu_hanmuc_id) lsd on lsd.nhiemvu_hanmuc_id=hmnv.id
GROUP BY ROLLUP(ten,ten_nv,priority,nhiemvu)
order by ten desc, grouping(priority) asc, priority asc,grouping(nhiemvu) desc, nhiemvu desc) abss 
where abss.ten_gr <> 1 and (abss.nv_count + abss.nhiemvu_gr<>2 and priority is not null)) 
order by charseq asc,ten desc, priority asc, nv_count desc

