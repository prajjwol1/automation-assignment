package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVDataReader {

    public static class Case {
        public final String input1;
        public final String input2;
        public final String expected;

        public Case(String input1, String input2, String expected) {
            this.input1 = input1;
            this.input2 = input2;
            this.expected = expected;
        }
    }

    public static List<Case> readCases(Path csvPath) throws Exception {
        List<Case> out = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(csvPath);
             CSVParser parser = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()             // use first row as header
                     .setSkipHeaderRecord(true)
                     .setTrim(true)
                     .setIgnoreEmptyLines(true)
                     .setCommentMarker('#')
                     .build()
                     .parse(reader)) {

            // Normalize header names (trim + lowercase) for safe mapping
            Map<String, String> normalizedHeaders = parser.getHeaderMap().keySet()
                    .stream()
                    .collect(Collectors.toMap(h -> h.trim().toLowerCase(), h -> h));

            for (CSVRecord record : parser) {
                String a = record.get(getHeader(normalizedHeaders, "input1"));
                String b = record.get(getHeader(normalizedHeaders, "input2"));
                String e = record.get(getHeader(normalizedHeaders, "expected"));
                out.add(new Case(a, b, e));
            }
        }

        return out;
    }

    // Helper method to safely get header, case-insensitive
    private static String getHeader(Map<String, String> normalizedHeaders, String key) {
        String header = normalizedHeaders.get(key.toLowerCase());
        if (header == null) {
            throw new RuntimeException("CSV header not found: " + key);
        }
        return header;
    }
}
