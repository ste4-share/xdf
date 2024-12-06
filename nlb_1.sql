SELECT n.id as nnx_id,ct.id as ctnv_id,ten,ten_nv,nhiemvu,ct_tk,ct_md,ct_tk::interval+ct_md::interval as cong,consumpt,
giohd_tk,giohd_md,tong_giohd,nltt_md,nltt_tk,cong_nltt,haohut,tongcong
FROM hanmuc_nhiemvu hm
join nguon_nx n on hm.unit_id=n.id
join chitiet_nhiemvu ct on ct.id=hm.nhiemvu_id
join nhiemvu nv on nv.id=ct.nhiemvu_id
left join (SELECT dvi_xuat_id,l.nhiemvu_id as ctnv_id,sum(giohd_md::interval) as giohd_md,sum(giohd_tk::interval) as giohd_tk,sum(giohd_md::interval)+sum(giohd_tk::interval) as tong_giohd,
sum(thuc_xuat) as nltt_md,sum(thuc_xuat_tk) as nltt_tk,sum(thuc_xuat)+sum(thuc_xuat_tk) as cong_nltt,sum(haohut_sl) as haohut,sum(haohut_sl)+sum(thuc_xuat)+sum(thuc_xuat_tk) as tongcong
FROM ledgers l join ledger_details ld on l.id=ld.ledger_id join chitiet_nhiemvu ct on ct.id= l.nhiemvu_id group by dvi_xuat_id,l.nhiemvu_id) a on (a.dvi_xuat_id=n.id and a.ctnv_id=ct.id)
order by ten desc