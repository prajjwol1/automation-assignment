package JdbcPojo;

import java.sql.*;
import java.util.*;

import utils.CSVDataWriter;
import utils.DataGenerator;
import utils.DatabaseUtil;

public class Main {

    public static void main(String[] args) {

        // Step 1: Create DB + Table
        DatabaseUtil.createTable();

        // Step 2: Insert random data
        DataGenerator.insertRandomData();

        // Step 3: Required operations
        printHighestSalary();
        exportSalaryAbove10k();
        exportJoinedLastMonth();
        printAscendingOrder();
    }

    // ---------------- OPERATION 1 ----------------
    public static void printHighestSalary() {
        System.out.println("\nEmployee with Highest Salary:");

        String sql = "SELECT * FROM salary ORDER BY salary DESC LIMIT 1";

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Salary s = new Salary(
                        rs.getString("employee_name"),
                        rs.getString("join_date"),
                        rs.getDouble("salary"));

                System.out.println(s.getEmployeeName() + " | " + s.getSalary());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- OPERATION 2 ----------------
    public static void exportSalaryAbove10k() {
        System.out.println("\nExporting salary > 10000 ...");

        String sql = "SELECT * FROM salary WHERE salary > 10000";
        List<Salary> list = new ArrayList<>();

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Salary(
                        rs.getString("employee_name"),
                        rs.getString("join_date"),
                        rs.getDouble("salary")));
            }

            CSVDataWriter.writeCSV("salary_gt_10k.csv", list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- OPERATION 3 ----------------
    public static void exportJoinedLastMonth() {
        System.out.println("\nExporting employees joined in last 30 days ...");

        String sql = "SELECT * FROM salary WHERE join_date >= date('now', '-30 days')";
        List<Salary> list = new ArrayList<>();

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Salary(
                        rs.getString("employee_name"),
                        rs.getString("join_date"),
                        rs.getDouble("salary")));
            }

            CSVDataWriter.writeCSV("joined_last_month.csv", list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- OPERATION 4 ----------------
    public static void printAscendingOrder() {
        System.out.println("\nEmployees in ascending order by name:");

        String sql = "SELECT * FROM salary";
        List<Salary> list = new ArrayList<>();

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Salary(
                        rs.getString("employee_name"),
                        rs.getString("join_date"),
                        rs.getDouble("salary")));
            }

            // Sort using POJO
            list.sort(Comparator.comparing(Salary::getEmployeeName));

            for (Salary s : list) {
                System.out.println(s.getEmployeeName() + " | "
                        + s.getJoinDate() + " | "
                        + s.getSalary());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
