select 'B' as stt,'nhiemvu' as ten,ten_nv, nhiemvu, 
case when EXTRACT(epoch FROM max(tk)) is null then 0 else EXTRACT(epoch FROM max(tk)) end as tk,
case when EXTRACT(epoch FROM max(md)) is null then 0 else EXTRACT(epoch FROM max(md)) end as md,
case when EXTRACT(epoch FROM max(cong_giobay)) is null then 0 else EXTRACT(epoch FROM max(cong_giobay)) end as cong_giobay,max(nhienlieu) as nhienlieu, 
case when EXTRACT(epoch FROM max(giohd_md)) is null then 0 else EXTRACT(epoch FROM max(giohd_md)) end as giohd_md,
case when EXTRACT(epoch FROM max(giohd_tk)) is null then 0 else EXTRACT(epoch FROM max(giohd_tk)) end as giohd_tk,
case when EXTRACT(epoch FROM max(tong_giohd)) is null then 0 else EXTRACT(epoch FROM max(tong_giohd)) end as tong_giohd,
case when max(nltt_md) is null then 0 else max(nltt_md) end as nltt_md,
case when max(nltt_tk) is null then 0 else max(nltt_tk) end as nltt_tk, 
case when max(cong_nltt) is null then 0 else max(cong_nltt) end as cong_nltt, 
case when max(haohut) is null then 0 else max(haohut) end as haohut,
case when max(tongcong) is null then 0 else max(tongcong) end as tongcong,
'0' as ten_gr,max(tennv_gr) as tennv_gr,max(nv_gr) as nv_gr,max(pri) as pri
from (select max(ct_id) as ct_id,ten_nv,case when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then ten_nv else nhiemvu end as nhiemvu,
max(pri) as pri, sum(tk) as tk,sum(md) as md,sum(cong_giobay) as cong_giobay,
sum(nhienlieu) as nhienlieu,sum(giohd_md) as giohd_md,sum(giohd_tk) as giohd_tk,sum(tong_giohd) as tong_giohd,sum(nltt_md) as nltt_md,
sum(nltt_tk) as nltt_tk, sum(cong_nltt) as cong_nltt, sum(haohut) as haohut, sum(tongcong) as tongcong,
grouping(ten_nv) as tennv_gr, grouping(nhiemvu) as nv_gr
from (select * from (SELECT quy_id as quy,hmnvtb.ctnv_id as ct_id,ten_nv,nhiemvu,
max(priority_bc2) as pri,sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,sum(nhienlieu) as nhienlieu
FROM hanmuc_nhiemvu_taubay hmnvtb 
left join phuongtien pt on pt.id=hmnvtb.pt_id 
left join chitiet_nhiemvu ct on ct.id=hmnvtb.ctnv_id 
left join nhiemvu nv on nv.id=ct.nhiemvu_id
where quy_id=20 group by quy_id,ct_id,ten_nv,nhiemvu) rat2
left join (SELECT quarter_id,l.nhiemvu_id as ctnv_id,ct.nhiemvu as nv,sum(giohd_md::interval) as giohd_md,sum(giohd_tk::interval) as giohd_tk,
sum(giohd_md::interval)+sum(giohd_tk::interval) as tong_giohd,
sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,
sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l 
join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id 
group by quarter_id,l.nhiemvu_id,nv) c on (rat2.quy=c.quarter_id and c.ctnv_id=rat2.ct_id)) d
group by rollup(ten_nv,nhiemvu)) z
group by ten_nv, nhiemvu order by tennv_gr desc,pri,ten_nv, nv_gr desc,nhiemvu