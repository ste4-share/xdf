select phuongtien_id,lpt.type,sum(thuc_xuat), sum(thuc_nhap),sum(so_luong),sum(sl_tieuthu_md),sum(sl_tieuthu_tk),sum(giohd_md::interval),sum(giohd_tk::interval),sum(so_km) from ledger_details ld 
join ledgers l on ld.ledger_id=l.id
join phuongtien pt on pt.id=ld.phuongtien_id
join loai_phuongtien lpt on lpt.id=pt.loaiphuongtien_id
join loaixd2 lxd on ld.loaixd_id=lxd.id
right join chungloaixd clxd on lxd.petroleum_type_id=clxd.id
where lpt.type='XE' OR lpt.type='MAY' and clxd.code='XANG_O_TO'
group by phuongtien_id,lpt.type
