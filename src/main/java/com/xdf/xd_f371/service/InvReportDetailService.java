package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.InvReportDetail;

import java.util.List;

public interface InvReportDetailService {
    List<InvReportDetail> getAll();
    int createNew(InvReportDetail invReportDetail);
    int updateNew(InvReportDetail invReportDetail);
    int update(InvReportDetail invReportDetail);
    int updateTitleId(int pre_id, int current_id);
    int deleteAll();
    InvReportDetail findByIds(int xd_id, int quarter_id, int titleId);
}
