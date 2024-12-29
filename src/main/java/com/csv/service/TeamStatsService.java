package com.csv.service;

import com.csv.model.IssueModel;
import com.csv.model.ReportModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeamStatsService {
    private static final Logger logger = LoggerFactory.getLogger(TeamStatsService.class);

    public List<ReportModel> computeVelocity(List<IssueModel> issues) {
        logger.info("Starting to compute teams velocity report for issues");
        final double ONE_HOUR = 3600.0;
        List<ReportModel> reports = new ArrayList<>();

        Map<String, List<IssueModel>> groupedByTeam = issues.stream()
                .collect(Collectors.groupingBy(IssueModel::team));
        logger.info("Grouped issues by team: [{}]", groupedByTeam.keySet().size());

        groupedByTeam.forEach((team, teamIssues) -> {
            logger.debug("Processing team: [{}]", team);

            double totalEffort = teamIssues.stream()
                    .mapToInt(IssueModel::originalEstimate)
                    .sum() / ONE_HOUR;
            logger.debug("Total effort for team [{}]: [{}] hours", team, totalEffort);

            double remainingEffort = teamIssues.stream()
                    .filter(issue -> issue.status().equals("Open"))
                    .mapToInt(IssueModel::originalEstimate)
                    .sum() / ONE_HOUR;
            logger.debug("Remaining effort for team [{}]: [{}] hours", team, remainingEffort);

            reports.add(new ReportModel(team, totalEffort, remainingEffort));
            logger.debug("Report data for team [{}] added to the report list", team);
        });

        logger.info("Finished computing velocity report. Total teams processed: [{}]", reports.size());
        return reports;
    }
}
