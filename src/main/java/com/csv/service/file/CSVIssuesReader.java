package com.csv.service.file;

import com.csv.model.IssueModel;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CSVIssuesReader implements FileReader<List<IssueModel>> {

    private static final Logger logger = LoggerFactory.getLogger(CSVIssuesReader.class);

    @Override
    public List<IssueModel> read(InputStream stream) {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        List<IssueModel> validRecords = new ArrayList<>();
        try {
            MappingIterator<IssueModel> iterator = csvMapper.readerFor(IssueModel.class)
                    .with(schema)
                    .readValues(stream);

            while (iterator.hasNext()) {
                tryParseRaw(iterator).ifPresent(validRecords::add);
            }
        } catch (IOException e) {
            logger.error("Error reading CSV file", e);
            throw new RuntimeException("Error reading CSV file", e);
        }
        return validRecords;
    }

    private Optional<IssueModel> tryParseRaw(MappingIterator<IssueModel> iterator) {
        try {
            return Optional.of(iterator.next());
        } catch (Exception e) {
            logger.warn("Skipping invalid row: {}", e.getMessage());
            return Optional.empty();
        }
    }
}