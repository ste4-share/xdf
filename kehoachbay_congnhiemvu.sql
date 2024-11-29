select * from (select * from (select *,'A' as chr,'9' as seqnum2
from (select 'ten' as ten,'10' as seqnum,
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
'0' as ten_gr,grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nhiemvu_gr, count(nhiemvu) as nv_count
from hanmuc_nhiemvu
join chitiet_nhiemvu on hanmuc_nhiemvu.nhiemvu_id=chitiet_nhiemvu.id
join nhiemvu on nhiemvu.id= chitiet_nhiemvu.nhiemvu_id
left join (select nhiemvu_hanmuc_id, sum(l.giohd_md::interval) as dur_md, sum(l.giohd_tk::interval) as dur_tk, sum(ld.thuc_xuat) as tx_md,sum(ld.thuc_xuat_tk) as tx_tk,sum(haohut_sl) as haohut from ledger_details ld join ledgers l on ld.ledger_id=l.id  group by nhiemvu_hanmuc_id) lsd on lsd.nhiemvu_hanmuc_id=hanmuc_nhiemvu.id
GROUP BY ROLLUP(ten_nv,priority,nhiemvu)
order by ten desc, grouping(priority) asc, priority asc,grouping(nhiemvu) desc, nhiemvu desc) abss 
where (abss.nv_count + abss.nhiemvu_gr<>2 and priority is not null)
order by abss.ten desc, priority asc, abss.nv_count desc) nv1
left join (select * from (select ten_nv,nhiemvu, count(nhiemvu) as nv from nhiemvu n 
join chitiet_nhiemvu ct on n.id=ct.nhiemvu_id
where n.assignment_type_id=1 or n.assignment_type_id=3
group by rollup(ten_nv, nhiemvu)) nv
where nv.nhiemvu is null) nv2 on nv1.nhiem_vu=nv2.ten_nv) nv3
where ((nv3.nv+nv3.nhiemvu_gr) <> 2) or nv3.nv is null
