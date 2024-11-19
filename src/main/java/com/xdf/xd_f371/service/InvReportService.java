package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.InvReport;

import java.util.List;

public interface InvReportService {
    List<InvReport> getAll();
    List<InvReport> getAllByPetroleumId(int petroleumID);
    int updateReport(InvReport petroleumId);
    int create(InvReport invReport);
    InvReport findByPetroleum(int petroleumId, int quarter_id, int header_id);
}
