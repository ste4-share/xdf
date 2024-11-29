select * from (select ten_nv,nhiemvu, count(nhiemvu) as nv from nhiemvu n 
join chitiet_nhiemvu ct on n.id=ct.nhiemvu_id
where n.assignment_type_id=1 or n.assignment_type_id=3
group by rollup(ten_nv, nhiemvu)) nv
where nv.nhiemvu is null