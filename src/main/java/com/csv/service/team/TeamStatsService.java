package com.csv.service.team;

import com.csv.model.IssueModel;
import com.csv.model.ReportModel;

import java.util.List;

public interface TeamStatsService {
    List<ReportModel> computeVelocity(List<IssueModel> issues);
}
