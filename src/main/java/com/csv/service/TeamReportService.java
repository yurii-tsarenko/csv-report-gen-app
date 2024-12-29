package com.csv.service;

import com.csv.model.IssueModel;
import com.csv.model.ReportModel;
import com.csv.service.file.FileReader;
import com.csv.service.file.FileWriter;
import com.csv.service.team.TeamStatsService;
import com.csv.util.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static com.csv.config.Constants.CSV_REPORT_FILE;

public class TeamReportService {
    private static final Logger logger = LoggerFactory.getLogger(TeamReportService.class);

    private final Environment environment;
    private final FileReader<List<IssueModel>> csvIssuesReader;
    private final FileWriter<List<ReportModel>> csvReportWriter;
    private final TeamStatsService teamStatsService;

    public TeamReportService(Environment environment,
                             FileReader<List<IssueModel>> csvIssuesReader,
                             FileWriter<List<ReportModel>> csvReportWriter,
                             TeamStatsService teamStatsService) {
        this.environment = environment;
        this.csvIssuesReader = csvIssuesReader;
        this.csvReportWriter = csvReportWriter;
        this.teamStatsService = teamStatsService;
    }

    public void generateReport(String inputFilePath) throws FileNotFoundException {
        File file = new File(inputFilePath);
        List<IssueModel> issues = csvIssuesReader.read(new FileInputStream(file));
        List<ReportModel> reportList = teamStatsService.computeVelocity(issues);
        logger.info("Created {} records for reporting file", reportList.size());
        csvReportWriter.write(reportList, environment.getString(CSV_REPORT_FILE));
        logger.info("Report saved to file:{}", file.getName());
    }
}
