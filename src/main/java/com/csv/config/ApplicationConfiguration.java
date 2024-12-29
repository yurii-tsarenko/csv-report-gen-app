package com.csv.config;

import com.csv.service.file.PlainTextReader;
import com.csv.util.Environment;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.io.InputStream;

import static com.csv.config.Constants.BANNER_FILE;
import static com.csv.config.Constants.CLASS_LOADER;
import static com.csv.config.Constants.LOG_ENCODING;
import static com.csv.config.Constants.LOG_FILE;
import static com.csv.config.Constants.LOG_LEVEL;
import static com.csv.config.Constants.LOG_PATTERN;

public class ApplicationConfiguration {
    private static final Logger logger = Logger.getLogger(ApplicationConfiguration.class);


    private final Environment environment;
    private final PlainTextReader plainTextReader;

    public ApplicationConfiguration(Environment environment, PlainTextReader plainTextReader) {
        this.environment = environment;
        this.plainTextReader = plainTextReader;
    }

    public void loadLoggerConfig() {
        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern(environment.getString(LOG_PATTERN));

        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(layout);
        consoleAppender.setEncoding(environment.getString(LOG_ENCODING));
        consoleAppender.activateOptions();


        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.toLevel(environment.getString(LOG_LEVEL)));
        rootLogger.removeAllAppenders();
        rootLogger.addAppender(consoleAppender);
        if (environment.getString(LOG_FILE) != null) {
            DailyRollingFileAppender rollingFileAppender = new DailyRollingFileAppender();
            rollingFileAppender.setEncoding(environment.getString(LOG_ENCODING));
            rollingFileAppender.setFile(environment.getString(LOG_FILE));
            rollingFileAppender.setLayout(layout);
            rollingFileAppender.setDatePattern("'.'yyyy-MM-dd");
            rollingFileAppender.activateOptions();
            rootLogger.addAppender(rollingFileAppender);
        }
    }

    public void printBanner() {
        InputStream resource = environment.get(CLASS_LOADER, ClassLoader.class).getResourceAsStream(BANNER_FILE);
        String banner = plainTextReader.read(resource);
        logger.info(banner);
    }
}
