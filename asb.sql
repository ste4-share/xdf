select nhiemvu_id,sum(giohd_md::interval + giohd_tk::interval) as gio from ledger_details ld join ledgers l on ld.ledger_id=l.id 
where l.lpt like 'MAYBAY' group by nhiemvu_id