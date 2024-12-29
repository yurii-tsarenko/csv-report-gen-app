package com.csv.service.file;

import java.io.InputStream;
import java.util.Scanner;

public class PlainTextReader implements FileReader<String> {
    @Override
    public String read(InputStream stream) {
        try (Scanner scanner = new Scanner(stream)) {
            StringBuilder builder = new StringBuilder();
            builder.append("\n");
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append("\n");
            }
            return builder.toString();
        }
    }
}
