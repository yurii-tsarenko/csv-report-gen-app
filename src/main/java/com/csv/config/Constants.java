package com.csv.config;

public class Constants {
    //  properties constants - could be changed in property file
    public static final String LOG_PATTERN = "logger.pattern";
    public static final String LOG_ENCODING = "logger.encoding";
    public static final String LOG_FILE = "logger.file";
    public static final String LOG_LEVEL = "logger.level";

    // app constants - using for autoconfiguration
    public static final String CONFIG_FILE = "application.properties";
    public static final String CONFIG_ENV_FILE = "application-%s.properties";
    public static final String PROFILE_PROPERTY = "app.profile";
    public static final String BANNER_FILE = "banner.txt";
    public static final String CLASS_LOADER = "app.class.loader";
}
