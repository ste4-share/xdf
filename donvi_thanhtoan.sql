select nnx,ct_id,quy_id,ten,ten_nv,tk,md,cong_giobay,nhienlieu,nhiemvu,quy_id,giohd_md,giohd_tk,tong_giohd,nltt_md,nltt_tk,cong_nltt,haohut,tongcong,dm_tk,dm_md 
from (SELECT hmnvtb.dvi_xuat_id as nnx,hmnvtb.ctnv_id as ct_id,quy_id,ten,ten_nv,nhiemvu,sum(tk::interval) as tk,sum(md::interval) as md,sum(tk::interval+md::interval) as cong_giobay,
sum(nhienlieu) as nhienlieu,max(dm_tk_gio) as dm_tk,max(dm_md_gio) as dm_md
FROM hanmuc_nhiemvu_taubay hmnvtb left join nguon_nx n on hmnvtb.dvi_xuat_id=n.id 
left join phuongtien pt on pt.id=hmnvtb.pt_id 
left join chitiet_nhiemvu ct on ct.id=hmnvtb.ctnv_id 
left join nhiemvu nv on nv.id=ct.nhiemvu_id 
left join dinhmuc dm on (dm.phuongtien_id=pt.id and dm.quarter_id=hmnvtb.quy_id)
where quy_id=20
group by hmnvtb.dvi_xuat_id,hmnvtb.ctnv_id,quy_id,ten,ten_nv,nhiemvu
order by ten) rat 
left join (SELECT quarter_id,dvi_xuat_id,l.nhiemvu_id as ctnv_id,sum(giohd_md::interval) as giohd_md,sum(giohd_tk::interval) as giohd_tk,
sum(giohd_md::interval)+sum(giohd_tk::interval) as tong_giohd,sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,
sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong FROM ledgers l 
join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id 
group by quarter_id,dvi_xuat_id,l.nhiemvu_id) a on (quy_id=a.quarter_id and a.dvi_xuat_id=rat.nnx and a.ctnv_id=rat.ct_id)


