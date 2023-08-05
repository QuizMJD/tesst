package com;


import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    private static String DB_URL = "jdbc:mysql://localhost:3306/java30";
    private static String USER_NAME = "root";
    private static String PASSWORD = "123456";
    // Method to create the connection and set up database resources

    public static Connection createConnection() {

        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }

        return conn;
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
        queryAll(searchStatement.executeQuery());
    }

    public static void main(String[] args) {
        try (Connection conn = createConnection();
             Statement stm = conn.createStatement()) {

            System.out.println("Connected to the database");

            // Insert new brands
            int rowsInserted = insertBrand(conn, "nike");
            System.out.println("Rows inserted: " + rowsInserted);

            insertBrand(conn, "hwllo");
            insertBrand(conn, "conver3333333");

            // Retrieve all brands
            queryAll(stm.executeQuery("SELECT * FROM brands"));
            // id
            //todo: fixbug
            filterById(conn,9);
            filterByName(conn,"puma");


            //name



            // Assume we want to update the name of the brand with ID 1
            int rowsUpdated = updateBrandName(conn, 99, "444444444444");
            System.out.println("Rows updated: " + rowsUpdated);

            // Assume we want to delete the brand with ID 3
            int rowsDeleted = deleteBrandById(conn, 117);
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

    //TODO:FIX BUG
    private static void filterByName(Connection conn ,String name) throws SQLException {
//        PreparedStatement filterByNameStmt = conn.prepareStatement("SELECT name FROM brands WHERE name=?");
        PreparedStatement filterByNameStmt = conn.prepareStatement("SELECT id, name FROM brands WHERE name=?");

        filterByNameStmt.setString(1, name);
        ResultSet filterByNameResultSet = filterByNameStmt.executeQuery();

        while (filterByNameResultSet.next()) {
             name = filterByNameResultSet.getString("name");
            int id = filterByNameResultSet.getInt("id");
            System.out.println("ID: " + id + ", Name: " + name);
        }
    }
    //TODO:FIX BUG
    private static void filterById(Connection conn,int id) throws SQLException {
//        PreparedStatement filterByIdStmt = conn.prepareStatement("SELECT id FROM brands WHERE id=?");
        PreparedStatement filterByIdStmt = conn.prepareStatement("SELECT id, name FROM brands WHERE id=?");

        filterByIdStmt.setInt(1, id);
        ResultSet filterByIdResultSet = filterByIdStmt.executeQuery();

        while (filterByIdResultSet.next()) {
            id = filterByIdResultSet.getInt("id");
            String  name = filterByIdResultSet.getString("name");

            System.out.println("ID: " + id + ", Name: " + name);
        }
    }

    private static void queryAll(ResultSet stm) throws SQLException {
        ResultSet selectAll = stm;
        while (selectAll.next()) {
            int id = selectAll.getInt("id");
            String name = selectAll.getString("name");
            System.out.println("ID: " + id + ", Name: " + name);
        }
    }
}
