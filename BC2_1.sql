select * from (select nv.id,ten_nv,team_id,priority_bc2, t.priority,t.name,tt from nhiemvu nv join team t on nv.team_id=t.id where nv.priority_bc2 is not null order by t.priority,nv.priority_bc2) data1 
join chitiet_nhiemvu ct on data1.id=ct.nhiemvu_id
