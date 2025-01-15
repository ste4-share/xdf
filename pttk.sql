select RANK() OVER (PARTITION BY loai ORDER BY tenxd DESC) AS ranks,loai,tenxd,
(case when NHAP_e916.tonkho is null then 0 else NHAP_e916.tonkho end)-(case when XUAT_e916.tonkho is null then 0 else XUAT_e916.tonkho end) as e916,
(case when NHAP_e921.tonkho is null then 0 else NHAP_e921.tonkho end)-(case when XUAT_e921.tonkho is null then 0 else XUAT_e921.tonkho end) as e921,
(case when NHAP_e923.tonkho is null then 0 else NHAP_e923.tonkho end)-(case when XUAT_e923.tonkho is null then 0 else XUAT_e923.tonkho end) as e923,
(case when NHAP_e927.tonkho is null then 0 else NHAP_e927.tonkho end)-(case when XUAT_e927.tonkho is null then 0 else XUAT_e927.tonkho end) as e927,
(case when NHAP_dnb.tonkho is null then 0 else NHAP_dnb.tonkho end)-(case when XUAT_dnb.tonkho is null then 0 else XUAT_dnb.tonkho end) as d_noibai,
(case when NHAP_dka.tonkho is null then 0 else NHAP_dka.tonkho end)-(case when XUAT_dka.tonkho is null then 0 else XUAT_dka.tonkho end) as d_kienan,
(case when NHAP_dvi.tonkho is null then 0 else NHAP_dvi.tonkho end)-(case when XUAT_dvi.tonkho is null then 0 else XUAT_dvi.tonkho end) as d_vinh,
(case when NHAP_dns.tonkho is null then 0 else NHAP_dns.tonkho end)-(case when XUAT_dns.tonkho is null then 0 else XUAT_dns.tonkho end) as d_nasan,
(case when NHAP_fb.tonkho is null then 0 else NHAP_fb.tonkho end)-(case when XUAT_fb.tonkho is null then 0 else XUAT_fb.tonkho end) as fb
from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id 
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'e916' and loai_phieu like 'NHAP' group by 1) NHAP_e916 on lxd.id=NHAP_e916.loaixd_id
left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_xuat like 'e916' and loai_phieu like 'XUAT' group by 1) XUAT_e916 on lxd.id=XUAT_e916.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'e921' and loai_phieu like 'NHAP' group by 1) NHAP_e921 on lxd.id=NHAP_e921.loaixd_id
left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_xuat like 'e921' and loai_phieu like 'XUAT' group by 1) XUAT_e921 on lxd.id=XUAT_e921.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'e923' and loai_phieu like 'NHAP' group by 1) NHAP_e923 on lxd.id=NHAP_e923.loaixd_id
left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_xuat like 'e923' and loai_phieu like 'XUAT' group by 1) XUAT_e923 on lxd.id=XUAT_e923.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'e927' and loai_phieu like 'NHAP' group by 1) NHAP_e927 on lxd.id=NHAP_e927.loaixd_id
left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_xuat like 'e927' and loai_phieu like 'XUAT' group by 1) XUAT_e927 on lxd.id=XUAT_e927.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'd Nội Bài' and loai_phieu like 'NHAP' group by 1) NHAP_dnb on lxd.id=NHAP_dnb.loaixd_id
left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_xuat like 'd Nội Bài' and loai_phieu like 'XUAT' group by 1) XUAT_dnb on lxd.id=XUAT_dnb.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'd Kiến An' and loai_phieu like 'NHAP' group by 1) NHAP_dka on lxd.id=NHAP_dka.loaixd_id
left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_xuat like 'd Kiến An' and loai_phieu like 'XUAT' group by 1) XUAT_dka on lxd.id=XUAT_dka.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'd Vinh' and loai_phieu like 'NHAP' group by 1) NHAP_dvi on lxd.id=NHAP_dvi.loaixd_id
left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_xuat like 'd Vinh' and loai_phieu like 'XUAT' group by 1) XUAT_dvi on lxd.id=XUAT_dvi.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'd Nà Sản' and loai_phieu like 'NHAP' group by 1) NHAP_dns on lxd.id=NHAP_dns.loaixd_id
left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_xuat like 'd Nà Sản' and loai_phieu like 'XUAT' group by 1) XUAT_dns on lxd.id=XUAT_dns.loaixd_id
left join (select loaixd_id, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_nhan like 'f Bộ' and loai_phieu like 'NHAP' group by 1) NHAP_fb on lxd.id=NHAP_fb.loaixd_id
left join (select loaixd_id, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id
where status like 'ACTIVE' and dvi_xuat like 'f Bộ' and loai_phieu like 'XUAT' group by 1) XUAT_fb on lxd.id=XUAT_fb.loaixd_id
order by priority_1,priority_2,priority_3