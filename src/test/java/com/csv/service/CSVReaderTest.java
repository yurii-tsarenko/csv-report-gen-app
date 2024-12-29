package com.csv.service;

import com.csv.TestProfile;
import com.csv.model.IssueModel;
import com.csv.service.file.CSVIssuesReader;
import com.csv.service.file.FileReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest extends TestProfile {

    @Test
    void shouldReadCSV() {
        final String testFileName = "/unit-test-data.csv";
        FileReader<List<IssueModel>> reader = new CSVIssuesReader();

        List<IssueModel> models = reader.read(CSVReaderTest.class.getResourceAsStream(testFileName));

        assertNotNull(models, "The result should not be null");
        assertFalse(models.isEmpty(), "The result should not be empty");

        assertEquals(18, models.size(), "The result should contain exactly 18 valid rows");

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