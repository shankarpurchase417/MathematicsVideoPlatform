package in.sl.backend;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:mysql://mysql.railway.internal:3306/railway";
    private static final String USER = "root";  // Change to your MySQL username
    private static final String PASSWORD = "zVJJPsdigxpOjHbyIkieXFqAKikWvXDw";  // Change to your MySQL password

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
