select * from (select nxx.id as nguonnx_id,ct.id as ctnv_id,ten,ten_nv,nhiemvu,ct_tk::interval,ct_md::interval,ct_tk::interval+ct_md::interval as cong_ct_gio,consumpt,priority, priority_bc2 from hanmuc_nhiemvu hmnv 
join nguon_nx nxx on hmnv.unit_id=nxx.id 
join chitiet_nhiemvu ct on ct.id=hmnv.nhiemvu_id
join nhiemvu n on n.id=ct.nhiemvu_id
order by ten) sour
left join (select dvi_xuat_id,nhiemvu_id, sum(giohd_tk::interval) as hdtk,sum(giohd_md::interval) as hdmd,sum(giohd_tk::interval)+sum(giohd_md::interval) as sum_hd,sum(thuc_xuat) as txmd, sum(thuc_xuat_tk) as txtk,sum(thuc_xuat)+sum(thuc_xuat_tk) as sum_tt, sum(haohut_sl) as haohut,sum(thuc_xuat)+sum(thuc_xuat_tk)+sum(haohut_sl) as tongcong from ledger_details ld
join ledgers l on ld.ledger_id=l.id
group by dvi_xuat_id,nhiemvu_id) dic
on (sour.nguonnx_id=dic.dvi_xuat_id and sour.ctnv_id=dic.nhiemvu_id)
order by ten, priority, priority_bc2