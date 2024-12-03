select tinhchat,loai,CASE WHEN tenxd is null and grouping(tinhchat)=0 then loai else tenxd end,sum(tdk_sscd) as tdk_sscd,sum(tdk_nvdx) as tdk_nvdx,sum(tdk_sscd+tdk_nvdx) as cong,
sum(case when totalLoaiXd2(20, '', lxd.id,'NHAP') is null then 0 else totalLoaiXd2(20, 20, lxd.id) end) as a,
grouping(tinhchat) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as xd_gr, max(priority_3) as pr
from inventory i 
join loaixd2 lxd on i.petro_id=lxd.id
join chungloaixd cl on lxd.petroleum_type_id=cl.id
where tinhchat like 'Nhiên liệu'
group by rollup(tinhchat,loai,tenxd)
order by tc_gr desc,loai_gr desc,pr asc,xd_gr desc
