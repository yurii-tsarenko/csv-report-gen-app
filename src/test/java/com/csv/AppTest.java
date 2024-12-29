package com.csv;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest extends TestSuite {

    @Test
    public void successAppStart() {
        Assertions.assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
