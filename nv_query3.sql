select hanmuc_nhiemvu.id, ten,ten_nv,nhiemvu,ct_md,ct_tk,consumpt,
dense_rank() over (order by ten desc) seqnum from hanmuc_nhiemvu
join chitiet_nhiemvu on hanmuc_nhiemvu.nhiemvu_id=chitiet_nhiemvu.id
join nhiemvu on nhiemvu.id= chitiet_nhiemvu.nhiemvu_id
join nguon_nx on nguon_nx.id = hanmuc_nhiemvu.unit_id
where hanmuc_nhiemvu.quarter_id=20
order by ten