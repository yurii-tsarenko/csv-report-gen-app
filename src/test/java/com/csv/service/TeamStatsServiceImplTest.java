package com.csv.service;

import com.csv.TestProfile;
import com.csv.model.IssueModel;
import com.csv.model.ReportModel;
import com.csv.service.file.CSVIssuesReader;
import com.csv.service.team.TeamStatsServiceImpl;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TeamStatsServiceImplTest extends TestProfile {

    @Test
    void shouldComputeVelocityFromCSVData() {
        TeamStatsServiceImpl teamStatsServiceImpl = new TeamStatsServiceImpl();
        InputStream inputStream = getClass().getResourceAsStream("/unit-test-data.csv");
        CSVIssuesReader reader = new CSVIssuesReader();
        List<IssueModel> issues = reader.read(inputStream);

        List<ReportModel> result = teamStatsServiceImpl.computeVelocity(issues);

        assertNotNull(result);
        assertEquals(3, result.size());

        ReportModel londonReport = result.stream()
                .filter(report -> "London".equals(report.team()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("London team not found"));
        assertEquals(29.0, londonReport.totalEffort(), "Total effort for London team should be 29.0 hours");
        assertEquals(12.5, londonReport.remainingEffort(), "Remaining effort for London team should be 12.5 hours");

        result.stream()
                .filter(report -> "UI".equals(report.team()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("UI team not found"));

        result.stream()
                .filter(report -> "India".equals(report.team()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("India team not found"));
    }
}