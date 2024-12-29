package com.csv;

import com.csv.config.ApplicationConfiguration;
import com.csv.model.IssueModel;
import com.csv.model.ReportModel;
import com.csv.service.CSVReader;
import com.csv.service.PlainTextReader;
import com.csv.service.TeamStatsService;
import com.csv.util.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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
        File file = new File(filePath);
        CSVReader csvReader = new CSVReader();
        List<IssueModel> issues = csvReader.read(new FileInputStream(file));
        TeamStatsService teamStatsService = new TeamStatsService();
        List<ReportModel> reportList = teamStatsService.computeVelocity(issues);
        logger.info("created {} records from reporting file", reportList.size());
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
