package org.example.ggg.dto;

public class ReportsDto {
    private String reportId;
    private String title;
    private String generatedBy;
    private String generatedDate;
    private String content;

    public ReportsDto() {
    }

    public ReportsDto(String reportId, String title, String generatedBy, String generatedDate, String content) {
        this.reportId = reportId;
        this.title = title;
        this.generatedBy = generatedBy;
        this.generatedDate = generatedDate;
        this.content = content;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ReportsDto{" +
                "reportId='" + reportId + '\'' +
                ", title='" + title + '\'' +
                ", generatedBy='" + generatedBy + '\'' +
                ", generatedDate='" + generatedDate + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
