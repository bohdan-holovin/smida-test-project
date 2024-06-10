package com.holovin.smidatestproject.utils;

import com.holovin.smidatestproject.model.*;
import org.jeasy.random.EasyRandom;

import java.math.BigDecimal;

public class RandomUtils {

    static EasyRandom generator = new EasyRandom();

    public static Company createCompany() {
        return generator.nextObject(Company.class);
    }

    public static User createUser() {
        User user = generator.nextObject(User.class);
        user.setRoles("USER");
        return user;
    }

    public static Report createReport(Company company) {
        Report report = generator.nextObject(Report.class);
        report.setCompany(company);
        return report;
    }

    public static Report createReportBigDecimal(Company company) {
        Report report = generator.nextObject(Report.class);
        report.setCompany(company);
        report.setTotalRevenue(BigDecimal.ONE);
        report.setNetProfit(BigDecimal.ONE);
        return report;
    }

    public static ReportDetails createReportDetails() {
        return generator.nextObject(ReportDetails.class);
    }

    public static FullReport createFullReport() {
        return generator.nextObject(FullReport.class);
    }
    public static FullReport createFullReportBigDecimal() {
        FullReport fullReport = generator.nextObject(FullReport.class);
        fullReport.setTotalRevenue(BigDecimal.ONE);
        fullReport.setNetProfit(BigDecimal.ONE);
        return fullReport;
    }
}
