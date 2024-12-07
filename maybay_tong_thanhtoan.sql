select 'A' as stt,'maybay' as ten,'Cho máy bay' as ten_nv,name_pt as nhiemvu,
EXTRACT(epoch FROM sum(tk)) as tk,EXTRACT(epoch FROM sum(md)) as md, EXTRACT(epoch FROM sum(cong_giobay)) as cong_giobay,
sum(nhienlieu) as nhienlieu,
case when EXTRACT(epoch FROM sum(giohd_md)) is null then 0 else EXTRACT(epoch FROM sum(giohd_md)) end as giohd_md,
case when EXTRACT(epoch FROM sum(giohd_tk)) is null then 0 else EXTRACT(epoch FROM sum(giohd_tk)) end as giohd_tk, 
case when EXTRACT(epoch FROM sum(tong_giohd)) is null then 0 else EXTRACT(epoch FROM sum(tong_giohd)) end as tong_giohd,
case when sum(nltt_md) is null then 0 else sum(nltt_md) end as nltt_md,
case when sum(nltt_tk) is null then 0 else sum(nltt_tk) end as nltt_tk,
case when sum(cong_nltt) is null then 0 else sum(cong_nltt) end as cong_nltt,
case when sum(haohut) is null then 0 else sum(haohut) end as haohut,
case when sum(tongcong) is null then 0 else sum(tongcong) end as tongcong,'1' as pri,
grouping(name_pt) as ten_gr,'0' as tennv_gr,'0' as nv_gr
from (select * from (SELECT quy_id as quy,pt_id,pt.name as name_pt,sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,sum(nhienlieu) as nhienlieu
FROM hanmuc_nhiemvu_taubay hmnvtb 
left join phuongtien pt on pt.id=hmnvtb.pt_id 
where quy_id=20 and pt_id <> 0
group by quy_id,pt_id,name_pt) a
left join (SELECT quarter_id,phuongtien_id,sum(giohd_md::interval) as giohd_md,sum(giohd_tk::interval) as giohd_tk,sum(giohd_md::interval)+sum(giohd_tk::interval) as tong_giohd,
sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,
sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l 
join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id 
where lpt_2 like 'MAYBAY'
group by quarter_id,phuongtien_id) b on (a.quy=b.quarter_id and a.pt_id=b.phuongtien_id)) c
group by rollup(name_pt) order by ten_gr desc,name_pt desc
