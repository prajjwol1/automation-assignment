package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseUtil {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:employees.db");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS salary (
                    employee_name TEXT,
                    join_date TEXT,
                    salary REAL
                );
            """;

        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            st.execute(sql);
            System.out.println("Table Created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
