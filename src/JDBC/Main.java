package com.example.demo;

import java.sql.*;
import java.util.Scanner;

public class Main {
    // Method to create the connection and set up database resources
    private static Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/java30", "root", "123456");
    }

    // Method to close the connection and other resources
    private static void closeResources(Connection conn, Statement stm, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to insert a new brand into the database
    static int insertBrand(Connection conn, String name) throws SQLException {
        String insertQuery = "INSERT INTO brands (name) VALUES (?)";
        PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
        insertStatement.setString(1, name);
        return insertStatement.executeUpdate();
    }

    // Method to update the name of a brand based on its ID
    private static int updateBrandName(Connection conn, int id, String newName) throws SQLException {
        String updateQuery = "UPDATE brands SET name = ? WHERE id = ?";
        PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
        updateStatement.setString(1, newName);
        updateStatement.setInt(2, id);
        return updateStatement.executeUpdate();
    }

    // Method to delete a brand based on its ID
    private static int deleteBrandById(Connection conn, int id) throws SQLException {
        String deleteQuery = "DELETE FROM brands WHERE id = ?";
        PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);
        deleteStatement.setInt(1, id);
        return deleteStatement.executeUpdate();
    }

    // Method to search for a brand by its name
    private static void searchBrandByName(Connection conn, String searchKeyword) throws SQLException {
        String searchQuery = "SELECT * FROM brands WHERE name LIKE ?";
        PreparedStatement searchStatement = conn.prepareStatement(searchQuery);
        searchStatement.setString(1, "%" + searchKeyword + "%");
        ResultSet rs = searchStatement.executeQuery();

        // Display search results
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println("ID: " + id + ", Name: " + name);
        }
    }

    public static void main(String[] args) {
        try (Connection conn = createConnection();
             Statement stm = conn.createStatement()) {

            System.out.println("Connected to the database");

            // Insert new brands
            int rowsInserted = insertBrand(conn, "nike");
            System.out.println("Rows inserted: " + rowsInserted);

            insertBrand(conn, "puma");
            insertBrand(conn, "conver");

            // Retrieve all brands
            ResultSet rs = stm.executeQuery("SELECT * FROM brands");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println("ID: " + id + ", Name: " + name);
            }

            // Assume we want to update the name of the brand with ID 1
            int rowsUpdated = updateBrandName(conn, 1, "new_brand_name");
            System.out.println("Rows updated: " + rowsUpdated);

            // Assume we want to delete the brand with ID 3
            int rowsDeleted = deleteBrandById(conn, 3);
            System.out.println("Rows deleted: " + rowsDeleted);

            // Create a Scanner object to read user input
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the brand name to search:");
            String searchKeyword = scanner.nextLine();

            // Perform the search based on user input
            searchBrandByName(conn, searchKeyword);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
