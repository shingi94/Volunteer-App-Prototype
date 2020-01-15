package com.lengwe.iVolunteer;

public class ReportModel {

    private String reportHeader;
    private String reportContent;
    private String reportDate;

    public ReportModel(String reportHeader, String reportContent, String reportDate) {
        this.reportHeader = reportHeader;
        this.reportContent = reportContent;
        this.reportDate = reportDate;
    }

    public String getReportHeader() {
        return reportHeader;
    }

    public String getReportContent() {
        return reportContent;
    }

    public String getReportDate() {
        return reportDate;
    }
}
