package com.csv.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReportModel(
        @JsonProperty("Team")
        String team,

        @JsonProperty("Total Effort")
        double totalEffort,

        @JsonProperty("Remaining Effort")
        double remainingEffort
) {

}
