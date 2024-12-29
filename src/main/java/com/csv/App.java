package com.csv;

import com.csv.config.ApplicationConfiguration;
import com.csv.service.file.CSVIssuesReader;
import com.csv.service.file.CsvReportWriter;
import com.csv.service.file.PlainTextReader;
import com.csv.service.TeamReportService;
import com.csv.service.team.TeamStatsServiceImpl;
import com.csv.util.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.csv.config.Constants.PROFILE_PROPERTY;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Environment environment = new Environment().load();
        PlainTextReader reader = new PlainTextReader();
        ApplicationConfiguration configuration = new ApplicationConfiguration(environment, reader);
        configuration.loadLoggerConfig();
        configuration.printBanner();
        long end = System.currentTimeMillis();
        String profile = environment.getString(PROFILE_PROPERTY);
        logger.info("Csv reporting app started under profile:{}, took: {} milliseconds", profile, (end - start));

        String filePath = resolveCsvFilePathArg(args);
        TeamReportService teamReportService = new TeamReportService(environment, new CSVIssuesReader(),
                new CsvReportWriter(), new TeamStatsServiceImpl());
        teamReportService.generateReport(filePath);
    }

    private static String resolveCsvFilePathArg(String[] args) {
        if (args.length == 0) {
            logger.error("CSV file path arg not found");
            printUsage();
        }
        String arg = args[0];
        boolean containsFileName = arg.contains(".csv");

        if (!containsFileName) {
            logger.error("Provide file name within Csv file path");
            printUsage();
        }
        return arg;
    }

    private static void printUsage() {
        logger.error("Usage: java -jar csv-reporting-app.jar <csv-file-path>/<file-name>.csv");
        System.exit(1);
    }
}
