package JdbcPojo;

public class Salary {
    private String employeeName;
    private String joinDate;
    private double salary;

    public Salary(String employeeName, String joinDate, double salary) {
        this.employeeName = employeeName;
        this.joinDate = joinDate;
        this.salary = salary;
    }

    public String getEmployeeName() { return employeeName; }
    public String getJoinDate() { return joinDate; }
    public double getSalary() { return salary; }
}

