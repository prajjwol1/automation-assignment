package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Random;

public class DataGenerator {

    public static void insertRandomData() {
        String sql = "INSERT INTO salary (employee_name, join_date, salary) VALUES (?,?,?)";

        Random random = new Random();

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 1; i <= 100; i++) {
                String name = "Employee_" + i;
                LocalDate date = LocalDate.now().minusDays(random.nextInt(300));
                double salary = 5000 + random.nextInt(20000);

                ps.setString(1, name);
                ps.setString(2, date.toString());
                ps.setDouble(3, salary);

                ps.executeUpdate();
            }

            System.out.println("100 Random Records Inserted!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
