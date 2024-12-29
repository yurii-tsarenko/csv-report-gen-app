package com.csv;

import org.junit.jupiter.api.BeforeAll;

import static com.csv.config.Constants.PROFILE_PROPERTY;

public class TestProfile {

    @BeforeAll
    static void setup() {
        System.setProperty(PROFILE_PROPERTY, "test");
    }
}
