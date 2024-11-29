select * from (select ct.id as ctnv_id,data1.priority_bc2,data1.priority,data1.name,data1.ten_nv,nhiemvu,diezel,xang,daubay,(diezel+xang+daubay) as tongcong 
from (select nv.id as nv_id,ten_nv,team_id,priority_bc2, t.priority,t.name,tt 
from nhiemvu nv join team t on nv.team_id=t.id where nv.priority_bc2 is not null order by t.priority,nv.priority_bc2) data1 
join chitiet_nhiemvu ct on data1.nv_id=ct.nhiemvu_id
join (select * from hanmuc_nhiemvu2 hm
join nhiemvu nv on hm.nhiemvu_id=nv.id) hmnv on hmnv.nhiemvu_id=data1.nv_id) hm2
left join (select nhiemvu_id,sum(so_km) as km,sum(giohd_md::interval) as gio from ledger_details ld
join ledgers l on l.id=ld.ledger_id
join phuongtien pt on pt.id=ld.phuongtien_id
join loai_phuongtien lpt on lpt.id=pt.loaiphuongtien_id
where lpt.type_name like 'XE_CHAY_XANG' OR lpt.type_name like 'MAY_CHAY_XANG' and ld.chung_loai like 'Xăng' and l.quarter_id=20
group by nhiemvu_id) hdxm_xang on hm2.ctnv_id=hdxm_xang.nhiemvu_id
left join (select nhiemvu_id,sum(so_km) as km,sum(giohd_md::interval) as gio from ledger_details ld
join ledgers l on l.id=ld.ledger_id
join phuongtien pt on pt.id=ld.phuongtien_id
join loai_phuongtien lpt on lpt.id=pt.loaiphuongtien_id
where lpt.type_name like 'XE_CHAY_DIEZEL' OR lpt.type_name like 'MAY_CHAY_DIEZEL' and ld.chung_loai like 'Diezel' and l.quarter_id=20
group by nhiemvu_id) hdxm_diezel on hm2.ctnv_id=hdxm_diezel.nhiemvu_id
left join (select nhiemvu_id,sum(giohd_md::interval) as gio from ledger_details ld
join ledgers l on l.id=ld.ledger_id
join phuongtien pt on pt.id=ld.phuongtien_id
join loai_phuongtien lpt on lpt.id=pt.loaiphuongtien_id
where lpt.type like 'MAYBAY' and (ld.chung_loai like '%Dầu Hạ cấp%' or ld.chung_loai like '%Dầu bay%') and l.quarter_id=20
group by nhiemvu_id) hdmb on hm2.ctnv_id=hdmb.nhiemvu_id