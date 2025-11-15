package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class HTMLReportGenerator {
    public static class Result {
        public final String input1;
        public final String input2;
        public final String expected;
        public final String actual;
        public final String outputStatus;
        public final String time;
        public final String memory;
        public final boolean passed;
        public Result(String i1, String i2, String expected,String outputStatus, String time, String memory, String actual, boolean passed) {
            this.input1 = i1; this.input2 = i2; this.expected = expected; this.actual = actual; this.passed = passed;
            this.outputStatus=outputStatus; this.time= time; this.memory=memory;
        }
    }

    public static void writeReport(List<Result> results) throws Exception {
        File dir = new File("reports");
        if (!dir.exists()) dir.mkdirs();
        File out = new File(dir, "report.html");
        try (PrintWriter pw = new PrintWriter(new FileWriter(out))) {
            pw.println("<html><head><meta charset='utf-8'/><title>Test Report</title>");
            pw.println("<style>table{border-collapse:collapse;width:100%}td,th{border:1px solid #ddd;padding:8px}tr.pass{background:#e6ffe6}tr.fail{background:#ffe6e6}</style>");
            pw.println("</head><body>");
            pw.println("<h2>ChatGPT -> CodeChef Data-driven Report</h2>");
            pw.println("<table>");
            pw.println("<tr><th>Input1</th><th>Input2</th><th>Expected</th><th>Actual</th><th>OutputStatus</th><th>Time</th><th>Memory</th><th>Status</th></tr>");
            for (Result r : results) {
                pw.printf("<tr class=\"%s\">", r.passed ? "pass" : "fail");
                pw.printf("<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>", esc(r.input1), esc(r.input2), esc(r.expected),esc(r.actual),esc(r.outputStatus),esc(r.time),esc(r.memory), r.passed ? "PASS" : "FAIL");
                pw.println("</tr>");
            }
            pw.println("</table></body></html>");
        }
        System.out.println("Report generated at: " + out.getAbsolutePath());
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\n","\\n");
    }
}
