select ten,ten_nv,nhiemvu,ct_md,ct_tk,consumpt,dur_text,dur_text_tk, thuc_xuat, thuc_xuat_tk from hanmuc_nhiemvu
join chitiet_nhiemvu on hanmuc_nhiemvu.nhiemvu_id=chitiet_nhiemvu.id
join nhiemvu on nhiemvu.id= chitiet_nhiemvu.nhiemvu_id
join nguon_nx on nguon_nx.id = hanmuc_nhiemvu.unit_id
full join ledger_details on hanmuc_nhiemvu.id = ledger_details.nhiemvu_hanmuc_id
where hanmuc_nhiemvu.quarter_id=20