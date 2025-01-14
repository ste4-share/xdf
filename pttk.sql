select RANK() OVER (PARTITION BY loai ORDER BY tenxd DESC) AS ranks,loai,tenxd,
NHAP_e916.tonkho as e916,
NHAP_e921.tonkho as e921,
NHAP_e923.tonkho as e923,
NHAP_e927.tonkho as e927,
NHAP_dnb.tonkho as d_noibai,
NHAP_dka.tonkho as d_kienan,
NHAP_dvi.tonkho as d_vinh,
NHAP_dns.tonkho as d_nasan,
NHAP_fb.tonkho as fb
from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id 
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and (dvi_nhan like 'e916' or dvi_xuat like 'e916') group by 1) NHAP_e916 on lxd.id=NHAP_e916.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and (dvi_nhan like 'e921' or dvi_xuat like 'e921') group by 1) NHAP_e921 on lxd.id=NHAP_e921.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and (dvi_nhan like 'e923' or dvi_xuat like 'e923') group by 1) NHAP_e923 on lxd.id=NHAP_e923.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and (dvi_nhan like 'e927' or dvi_xuat like 'e927') group by 1) NHAP_e927 on lxd.id=NHAP_e927.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and (dvi_nhan like 'd Nội Bài' or dvi_xuat like 'd Nội Bài') group by 1) NHAP_dnb on lxd.id=NHAP_dnb.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and (dvi_nhan like 'd Kiến An' or dvi_xuat like 'd Kiến An') group by 1) NHAP_dka on lxd.id=NHAP_dka.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and (dvi_nhan like 'd Vinh' or dvi_xuat like 'd Vinh') group by 1) NHAP_dvi on lxd.id=NHAP_dvi.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and (dvi_nhan like 'd Nà Sản' or dvi_xuat like 'd Nà Sản') group by 1) NHAP_dns on lxd.id=NHAP_dns.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd-xuat_nvdx-xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and (dvi_nhan like 'f Bộ' or dvi_xuat like 'f Bộ') group by 1) NHAP_fb on lxd.id=NHAP_fb.loaixd_id
order by priority_1,priority_2,priority_3