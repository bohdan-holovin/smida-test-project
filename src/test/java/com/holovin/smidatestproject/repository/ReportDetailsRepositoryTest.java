//package com.holovin.smidatestproject.repository;
//
//import com.holovin.smidatestproject.model.ReportDetails;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataMongoTest
//public class ReportDetailsRepositoryTest {
//
//    @Autowired
//    private ReportDetailsRepository reportDetailsRepository;
//
//    @Test
//    public void testSaveReportDetails() {
//        // Create new report details
//        ReportDetails reportDetails = new ReportDetails();
//        reportDetails.setReportId(UUID.randomUUID());
//        reportDetails.setFinancialData("{\"revenue\": 10000, \"expenses\": 5000}");
//        reportDetails.setComments("Test comments");
//
//        // Save the report details
//        ReportDetails savedReportDetails = reportDetailsRepository.save(reportDetails);
//
//        // Verify the report details were saved
//        Optional<ReportDetails> retrievedReportDetails = reportDetailsRepository.findById(savedReportDetails.getReportId());
//        assertThat(retrievedReportDetails).isPresent();
//        assertThat(retrievedReportDetails.get().getFinancialData()).isEqualTo("{\"revenue\": 10000, \"expenses\": 5000}");
//        assertThat(retrievedReportDetails.get().getComments()).isEqualTo("Test comments");
//    }
//}
