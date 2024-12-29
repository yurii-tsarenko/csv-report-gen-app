package com.csv.service.file;

import com.csv.model.ReportModel;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CsvReportWriter implements FileWriter<List<ReportModel>> {

    @Override
    public void write(List<ReportModel> content, String filePath) {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("Team", CsvSchema.ColumnType.STRING)
                .addColumn("Total Effort", CsvSchema.ColumnType.NUMBER)
                .addColumn("Remaining Effort", CsvSchema.ColumnType.NUMBER)
                .build()
                .withHeader();

        File file = new File(filePath);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            csvMapper.writerFor(List.class).with(schema).writeValue(outputStream, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}