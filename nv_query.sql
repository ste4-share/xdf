select hanmuc_nhiemvu.id, ten,ten_nv,nhiemvu,ct_md,ct_tk,consumpt,cast(dur_md as text),cast(dur_tk as text),cast((dur_md+dur_tk) as text) as sum_giobay , tx_md, tx_tk,tx_md+tx_tk as sum_tieuthu from hanmuc_nhiemvu
join chitiet_nhiemvu on hanmuc_nhiemvu.nhiemvu_id=chitiet_nhiemvu.id
join nhiemvu on nhiemvu.id= chitiet_nhiemvu.nhiemvu_id
join nguon_nx on nguon_nx.id = hanmuc_nhiemvu.unit_id
left join (select nhiemvu_hanmuc_id, sum(ld.dur_text_md2) as dur_md, sum(ld.dur_text_tk2) as dur_tk,sum(ld.thuc_xuat) as tx_md,sum(ld.thuc_xuat_tk) as tx_tk  from ledger_details ld group by nhiemvu_hanmuc_id) lsd on lsd.nhiemvu_hanmuc_id=hanmuc_nhiemvu.id
where hanmuc_nhiemvu.quarter_id=20