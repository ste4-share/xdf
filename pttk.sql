select RANK() OVER (PARTITION BY loai ORDER BY tenxd DESC) AS ranks,loai,tenxd,e916.tonkho as e916,e921.tonkho as e921,e923.tonkho as e923,e927.tonkho as e927,
dnb.tonkho as d_noi_bai,dka.tonkho as d_kien_an,dvi.tonkho as d_vinh,dns.tonkho as d_na_san,fb.tonkho as f_bo
from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id 
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'e916' or dvi_xuat like 'e916' group by 1) e916 on lxd.id=e916.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'e921' or dvi_xuat like 'e921' group by 1) e921 on lxd.id=e921.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'e923' or dvi_xuat like 'e923' group by 1) e923 on lxd.id=e923.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'e927' or dvi_xuat like 'e927' group by 1) e927 on lxd.id=e927.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'd Nội Bài' or dvi_xuat like 'd Nội Bài' group by 1) dnb on lxd.id=dnb.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'd Kiến An' or dvi_xuat like 'd Kiến An' group by 1) dka on lxd.id=dka.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'd Vinh' or dvi_xuat like 'd Vinh' group by 1) dvi on lxd.id=dvi.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'd Nà Sản' or dvi_xuat like 'd Nà Sản' group by 1) dns on lxd.id=dns.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'f Bộ' or dvi_xuat like 'f Bộ' group by 1) fb on lxd.id=fb.loaixd_id
order by priority_1,priority_2,priority_3