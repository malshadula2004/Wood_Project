package org.example.ggg.Util;

import org.example.ggg.dbconnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CRUD Utility Class for Database Operations
 */
public class CrudUtil {

    /**
     * Generic method to execute SQL queries.
     *
     * @param sql  The SQL query to be executed (SELECT/INSERT/UPDATE/DELETE)
     * @param args Parameters to set in the query
     * @param <T>  Generic return type (ResultSet or Boolean)
     * @return ResultSet for SELECT or Boolean for other queries
     * @throws SQLException If a database access error occurs
     */
                    public static <T> T execute(String sql, Object... args) throws SQLException, ClassNotFoundException {
                        Connection connection = DBConnection.getInstance().getConnection();
                        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                            // Set the parameters in the SQL query
                            for (int i = 0; i < args.length; i++) {
                                preparedStatement.setObject(i + 1, args[i]);
                            }

                            // If the query is SELECT, return ResultSet
                            if (sql.trim().toLowerCase().startsWith("select")) {
                                ResultSet resultSet = preparedStatement.executeQuery();
                                return (T) resultSet; // Returning ResultSet
                            } else {
                                // For INSERT, UPDATE, DELETE, return true/false based on affected rows
                                int affectedRows = preparedStatement.executeUpdate();
                                return (T) (Boolean) (affectedRows > 0); // Return success as boolean
                            }
                        }
                    }

                    /**
                     * Executes a SELECT query and returns the ResultSet.
                     *
                     * @param sql The SQL query to be executed
                     * @param args Parameters to set in the query
                     * @return The ResultSet obtained from the query execution
                     * @throws SQLException           If a database access error occurs
                     * @throws ClassNotFoundException If the JDBC driver class is not found
                     */
                    public static ResultSet executeQuery(String sql, Object... args) throws SQLException, ClassNotFoundException {
                        Connection connection = DBConnection.getInstance().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);

                        // Set the parameters in the SQL query
                        for (int i = 0; i < args.length; i++) {
                            preparedStatement.setObject(i + 1, args[i]);
                        }

                        // Execute the SELECT query and return the ResultSet
                        return preparedStatement.executeQuery(); // Return ResultSet
                    }
                }
