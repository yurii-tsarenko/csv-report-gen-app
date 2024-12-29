package com.csv.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public record IssueModel(
        @JsonProperty("Issue key")
        String issueKey,

        @JsonProperty("Issue id")
        long issueId,

        @JsonProperty("Parent id")
        long parentId,

        @JsonProperty("Summary")
        String summary,

        @JsonProperty("Status")
        String status,

        @JsonProperty("Issue Type")
        String issueType,

        @JsonProperty("Original Estimate")
        int originalEstimate,

        @JsonProperty("Priority")
        String priority,

        @JsonProperty("Team")
        String team
) {
}
