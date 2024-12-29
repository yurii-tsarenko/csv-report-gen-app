package com.csv.service;

import com.csv.TestSuite;
import com.csv.model.IssueModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest extends TestSuite {

    @Test
    void shouldReadCSV() {
        final String testFileName = "/unit-test-data.csv";
        FileReader<List<IssueModel>> reader = new CSVReader();

        List<IssueModel> models = reader.read(CSVReaderTest.class.getResourceAsStream(testFileName));

        assertNotNull(models, "The result should not be null");
        assertFalse(models.isEmpty(), "The result should not be empty");

        assertEquals(19, models.size(), "The result should contain exactly 19 valid rows");

        IssueModel firstModel = models.get(0);
        assertEquals("MCPU-10140", firstModel.issueKey());
        assertEquals(2748508L, firstModel.issueId());
        assertEquals(2660783L, firstModel.parentId());
        assertEquals("QA Design- Notify Parties Assignment Confirmed", firstModel.summary());
        assertEquals("Open", firstModel.status());
        assertEquals("Sub-task", firstModel.issueType());
        assertEquals(28800L, firstModel.originalEstimate());
        assertEquals("Medium", firstModel.priority());
        assertEquals("London", firstModel.team());
    }
}