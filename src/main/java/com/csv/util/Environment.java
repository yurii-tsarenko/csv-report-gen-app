package com.csv.util;

import com.csv.App;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.csv.config.Constants.CLASS_LOADER;
import static com.csv.config.Constants.CONFIG_ENV_FILE;
import static com.csv.config.Constants.CONFIG_FILE;
import static com.csv.config.Constants.PROFILE_PROPERTY;

public class Environment {

    private static final Map<String, Object> appProperties = new ConcurrentHashMap<>();

    public Environment load() throws IOException {
        String profile = System.getenv(PROFILE_PROPERTY);
        if (profile == null) {
            profile = System.getProperty(PROFILE_PROPERTY);
        }

        Map<String, Object> properties = loadProperties(CONFIG_FILE);
        if (profile != null) {
            String configFile = String.format(CONFIG_ENV_FILE, profile);
            Map<String, Object> envProperties = loadProperties(configFile);
            properties.putAll(envProperties);
            properties.put(PROFILE_PROPERTY, profile);
        } else {
            properties.put(PROFILE_PROPERTY, "default");
        }
        appProperties.putAll(properties);
        appProperties.put(CLASS_LOADER, App.class.getClassLoader());
        return this;
    }

    public <T> T get(String key, Class<T> type) {
        Object value = appProperties.get(key);
        if (Objects.isNull(value)) {
            return null;
        }
        return type.cast(value);
    }

    public String getString(String key) {
        return get(key, String.class);
    }

    private Map<String, Object> loadProperties(String configFile) throws IOException {
        Properties properties = new Properties();
        properties.load(App.class.getClassLoader().getResourceAsStream(configFile));
        return properties.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> String.valueOf(entry.getKey()), Map.Entry::getValue));
    }
}
