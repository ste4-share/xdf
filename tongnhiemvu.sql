select quy,ct_id,ten_nv,nhiemvu,max(pri) as pri,max(tk) as tk, max(md) as md, max(cong_giobay) as cong_giobay, max(nhienlieu) as nhienlieu,max(tennv_gr) as tennv_gr,max(nhiemvu_gr) as nv_gr
from (SELECT max(quy_id) as quy,max(hmnvtb.ctnv_id) as ct_id,ten_nv,
case when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then ten_nv else nhiemvu end as nhiemvu,
max(priority_bc2) as pri,sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,sum(nhienlieu) as nhienlieu,
grouping(ten_nv) as tennv_gr, grouping(nhiemvu) as nhiemvu_gr
FROM hanmuc_nhiemvu_taubay hmnvtb 
left join phuongtien pt on pt.id=hmnvtb.pt_id 
left join chitiet_nhiemvu ct on ct.id=hmnvtb.ctnv_id 
left join nhiemvu nv on nv.id=ct.nhiemvu_id 
where quy_id=20 
group by rollup(ten_nv,nhiemvu)) t
group by quy,ct_id,ten_nv,nhiemvu
order by tennv_gr desc,ten_nv, nv_gr desc,nhiemvu,pri