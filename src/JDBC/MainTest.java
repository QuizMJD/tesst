//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.lang.reflect.Method;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class MainTest {
//
//    private static Connection conn;
//
//    @BeforeAll
//    public static void setup() throws Exception {
//        // Create a connection to the test database
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_database", "test_user", "test_password");
//
//        // Set up the test environment (e.g., create necessary tables)
//        try (Statement stm = conn.createStatement()) {
//            // Create the brands table (assuming it does not exist)
//            stm.executeUpdate("CREATE TABLE IF NOT EXISTS brands (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255))");
//        }
//    }
//
//    @AfterAll
//    public static void teardown() throws Exception {
//        // Clean up the test environment (e.g., drop tables)
//        try (Statement stm = conn.createStatement()) {
//            // Drop the brands table
//            stm.executeUpdate("DROP TABLE IF EXISTS brands");
//        }
//
//        // Close the connection
//        if (conn != null) {
//            conn.close();
//        }
//    }
//
//    @Test
//    public void testInsertBrand() throws Exception {
//        // Use reflection to access the private method
//        Method insertBrandMethod = Main.class.getDeclaredMethod("insertBrand", Connection.class, String.class);
//        insertBrandMethod.setAccessible(true);
//
//        int rowsInserted = (int) insertBrandMethod.invoke(null, conn, "nike");
//        assertEquals(1, rowsInserted);
//
//        // Add more insert test cases here
//    }
//
//    @Test
//    public void testUpdateBrandName() throws Exception {
//        // Use reflection to access the private method
//        Method updateBrandNameMethod = Main.class.getDeclaredMethod("updateBrandName", Connection.class, int.class, String.class);
//        updateBrandNameMethod.setAccessible(true);
//
//        // First, insert a brand to update
//        Main.insertBrand(conn, "test_brand");
//
//        int rowsUpdated = (int) updateBrandNameMethod.invoke(null, conn, 1, "new_brand_name");
//        assertEquals(1, rowsUpdated);
//
//        // Add more update test cases here
//    }
//
//    @Test
//    public void testDeleteBrandById() throws Exception {
//        // Use reflection to access the private method
//        Method deleteBrandByIdMethod = Main.class.getDeclaredMethod("deleteBrandById", Connection.class, int.class);
//        deleteBrandByIdMethod.setAccessible(true);
//
//        // First, insert a brand to delete
//        Main.insertBrand(conn, "test_brand");
//
//        int rowsDeleted = (int) deleteBrandByIdMethod.invoke(null, conn, 1);
//        assertEquals(1, rowsDeleted);
//
//        // Add more delete test cases here
//    }
//
//    @Test
//    public void testSearchBrandByName() throws Exception {
//        // Use reflection to access the private method
//        Method searchBrandByNameMethod = Main.class.getDeclaredMethod("searchBrandByName", Connection.class, String.class);
//        searchBrandByNameMethod.setAccessible(true);
//
//        // First, insert brands to search
//        Main.insertBrand(conn, "nike");
//        Main.insertBrand(conn, "puma");
//        Main.insertBrand(conn, "converse");
//
//        // Test case: Search for "nike"
//        searchBrandByNameMethod.invoke(null, conn, "nike");
//        // Add assertions here for the expected output
//
//        // Test case: Search for "uma"
//        searchBrandByNameMethod.invoke(null, conn, "uma");
//        // Add assertions here for the expected output
//
//        // Test case: Search for "abc"
//        searchBrandByNameMethod.invoke(null, conn, "abc");
//        // Add assertions here for the expected output
//
//        // Add more search test cases here
//    }
//}
