package com.holovin.smidatestproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "report_details")
@Getter
@Setter
public class ReportDetails {

    @Id
    private UUID reportId;

    private String financialData;

    private String comments;
}
