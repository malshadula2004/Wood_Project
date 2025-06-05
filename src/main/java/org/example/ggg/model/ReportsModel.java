package org.example.ggg.model;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.ReportsDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportsModel {

    // Save Report
    public static String saveReport(ReportsDto report) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Reports ( Title, GeneratedBy, Content, DATE) VALUES ( ?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,

                report.getTitle(),
                report.getGeneratedBy(),
                report.getContent(),
                report.getGeneratedDate()
        );
        return isAdded ? "Successfully added report!" : "Failed to add report!";
    }

    // Update Report
    public static String updateReport(ReportsDto report) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Reports SET Title = ?, GeneratedBy = ?, Content = ?,DATE = ? WHERE ReportID = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                report.getTitle(),
                report.getGeneratedBy(),
                report.getContent(),
                report.getGeneratedDate(),
                report.getReportId()
        );
        return isUpdated ? "Successfully updated report!" : "Failed to update report!";
    }

    // Delete Report
    public static String deleteReport(String reportId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Reports WHERE ReportID = ?";
        boolean isDeleted = CrudUtil.execute(sql, reportId);
        return isDeleted ? "Successfully deleted report!" : "Failed to delete report!";
    }

    // Get all reports
    public static ArrayList<ReportsDto> getAllReports() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Reports";
        ResultSet resultSet = CrudUtil.executeQuery(sql); // Assuming `CrudUtil.executeQuery` returns ResultSet
        ArrayList<ReportsDto> reportsList = new ArrayList<>();

        while (resultSet.next()) {
            reportsList.add(new ReportsDto(
                    resultSet.getString("ReportID"),
                    resultSet.getString("Title"),
                    resultSet.getString("GeneratedBy"),
                    resultSet.getString("Content"),
                    resultSet.getString("DATE")
            ));
        }
        return reportsList;
    }

    public static ArrayList<ReportsDto> searchReports(String keyword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Reports WHERE Title LIKE ? OR DATE LIKE ?";
        ResultSet resultSet = CrudUtil.executeQuery(sql, "%" + keyword + "%", "%" + keyword + "%");
        ArrayList<ReportsDto> filteredReports = new ArrayList<>();

        while (resultSet.next()) {
            filteredReports.add(new ReportsDto(
                    resultSet.getString("ReportID"),
                    resultSet.getString("Title"),
                    resultSet.getString("GeneratedBy"),
                    resultSet.getString("Content"),
                    resultSet.getString("DATE")
            ));
        }
        return filteredReports;
    }

}
