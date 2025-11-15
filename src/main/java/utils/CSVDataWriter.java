package utils;
import JdbcPojo.Salary;

import java.io.FileWriter;
import java.util.List;

public class CSVDataWriter {

    public static void writeCSV(String fileName, List<Salary> list) {
        try (FileWriter fw = new FileWriter(fileName)) {

            fw.write("employee_name,join_date,salary\n");

            for (Salary s : list) {
                fw.write(s.getEmployeeName() + ","
                        + s.getJoinDate() + ","
                        + s.getSalary() + "\n");
            }

            System.out.println("CSV exported: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
