package com.holovin.smidatestproject.utils;

import com.holovin.smidatestproject.model.*;
import org.jeasy.random.EasyRandom;

import java.math.BigDecimal;
import java.util.Set;

public class RandomUtils {

    static EasyRandom generator = new EasyRandom();

    public static Company createRandomCompany() {
        return generator.nextObject(Company.class);
    }

    public static User createRandomUser() {
        User user = generator.nextObject(User.class);
        user.setRoles(Set.of(Role.USER));
        return user;
    }

    public static Report createRandomReport(Company company) {
        Report report = generator.nextObject(Report.class);
        report.setCompany(company);
        return report;
    }

    public static Report createRandomReportBigDecimal(Company company) {
        Report report = generator.nextObject(Report.class);
        report.setCompany(company);
        report.setTotalRevenue(BigDecimal.ONE);
        report.setNetProfit(BigDecimal.ONE);
        return report;
    }

    public static ReportDetails createRandomReportDetails() {
        return generator.nextObject(ReportDetails.class);
    }

    public static ReportDetails createRandomReportDetails(Report report) {
        ReportDetails reportDetails = generator.nextObject(ReportDetails.class);
        reportDetails.setReportId(report.getId());
        return reportDetails;
    }

    public static FullReport createRandomFullReport() {
        return generator.nextObject(FullReport.class);
    }

    public static FullReport createRandomFullReportBigDecimal() {
        FullReport fullReport = generator.nextObject(FullReport.class);
        fullReport.setTotalRevenue(BigDecimal.ONE);
        fullReport.setNetProfit(BigDecimal.ONE);
        return fullReport;
    }
}
