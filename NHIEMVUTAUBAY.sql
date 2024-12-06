SELECT hmnvtb.dvi_xuat_id as nnx,pt.id as ptid,hmnvtb.ctnv_id,quy_id,ten,pt.name as tenmb,ten_nv,nhiemvu,tk,md,tk::interval+md::interval as cong_giobay,nhienlieu,dm_tk_gio as dm_tk,dm_md_gio as dm_md 
FROM hanmuc_nhiemvu_taubay hmnvtb left join nguon_nx n on hmnvtb.dvi_xuat_id=n.id 
left join phuongtien pt on pt.id=hmnvtb.pt_id 
left join chitiet_nhiemvu ct on ct.id=hmnvtb.ctnv_id 
left join nhiemvu nv on nv.id=ct.nhiemvu_id 
left join dinhmuc dm on (dm.phuongtien_id=pt.id and dm.quarter_id=hmnvtb.quy_id)
where quy_id=20