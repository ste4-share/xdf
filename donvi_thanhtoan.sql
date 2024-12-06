select nnx,ct_id,quy,pri,ten,ten_nv,nhiemvu,max(tk) as tk,max(md) as md,max(congiobay) as congiobay, max(nhienlieu) as nhienlieu,max(giohd_md) as giohd_md,
max(giohd_tk) as giohd_tk,max(tonggiohd) as tonggiohd, max(nltt_md) as nltt_md, max(nltt_tk) as nltt_tk,max(cong_nltt) as cong_nltt,max(haohut) as haohut,
max(tongcong) as tongcong, max(dm_tk) as dm_tk, max(dm_md) as dm_md, max(ten_gr) as ten_gr,max(tennv_gr) as tennv_gr,max(nv_gr) as nv_gr
from (select max(nnx) as nnx,max(ct_id) as ct_id,max(quy_id) as quy,max(pri) as pri,
ten,ten_nv,case when grouping(nhiemvu)=1 and grouping(ten_nv)=0 then ten_nv else nhiemvu end as nhiemvu,sum(tk) as tk,sum(md) as md,sum(cong_giobay) as congiobay,sum(nhienlieu) as nhienlieu,
sum(giohd_md) as giohd_md,sum(giohd_tk) as giohd_tk,sum(tong_giohd) as tonggiohd,
sum(nltt_md) as nltt_md,sum(nltt_tk) as nltt_tk,sum(cong_nltt) as cong_nltt,sum(haohut) as haohut,sum(tongcong) as tongcong,
max(dm_tk) as dm_tk,max(dm_md) as dm_md,
grouping(ten) as ten_gr, grouping(ten_nv) as tennv_gr,grouping(nhiemvu) as nv_gr
from (SELECT hmnvtb.dvi_xuat_id as nnx,hmnvtb.ctnv_id as ct_id,quy_id,ten,ten_nv,nhiemvu,max(priority_bc2) as pri,
sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,
sum(nhienlieu) as nhienlieu,max(dm_tk_gio) as dm_tk,max(dm_md_gio) as dm_md
FROM hanmuc_nhiemvu_taubay hmnvtb left join nguon_nx n on hmnvtb.dvi_xuat_id=n.id 
left join phuongtien pt on pt.id=hmnvtb.pt_id 
left join chitiet_nhiemvu ct on ct.id=hmnvtb.ctnv_id 
left join nhiemvu nv on nv.id=ct.nhiemvu_id 
left join dinhmuc dm on (dm.phuongtien_id=pt.id and dm.quarter_id=hmnvtb.quy_id)
where quy_id=20 group by hmnvtb.dvi_xuat_id,hmnvtb.ctnv_id,quy_id,ten,ten_nv,nhiemvu order by ten) rat 
left join (SELECT quarter_id,dvi_xuat_id,l.nhiemvu_id as ctnv_id,sum(giohd_md::interval) as giohd_md,sum(giohd_tk::interval) as giohd_tk,
sum(giohd_md::interval)+sum(giohd_tk::interval) as tong_giohd,sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,
sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l 
join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id 
group by quarter_id,dvi_xuat_id,l.nhiemvu_id) a on (quy_id=a.quarter_id and a.dvi_xuat_id=rat.nnx and a.ctnv_id=rat.ct_id)
group by rollup(ten,ten_nv,nhiemvu)) b
group by nnx,ct_id,quy,pri,ten,ten_nv,nhiemvu
order by ten_gr desc, ten desc,tennv_gr desc,pri asc,ten_nv,nv_gr desc

