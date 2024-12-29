package com.csv;

import com.csv.config.ApplicationConfiguration;
import com.csv.service.PlainTextReader;
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
    }
}
