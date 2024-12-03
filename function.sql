CREATE OR REPLACE FUNCTION totalLoaiXd2(q_id int, tt text, lxd_id int, lp text)
RETURNS integer AS $total$
declare
	total integer;
BEGIN
   select soluong into total from (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like lp and tructhuoc like tt and quarter_id=q_id and loaixd_id=lxd_id group by loaixd_id limit 1) a;
   RETURN total;
END;
$total$ LANGUAGE plpgsql;