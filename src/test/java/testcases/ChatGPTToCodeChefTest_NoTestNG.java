package testcases;

import pages.ChatGPTMockPage;
import pages.CodeChefIDEPage;
import utils.CSVDataReader;
import utils.DriverManager;
import utils.HTMLReportGenerator;
import utils.HTMLReportGenerator.Result;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ChatGPTToCodeChefTest_NoTestNG {
    public static void main(String[] args) throws Exception {
        WebDriver driver = DriverManager.getDriver();
        try {
            // 1) Generate python script from local mock
            ChatGPTMockPage mock = new ChatGPTMockPage(driver);
            mock.openLocal();
            
            //adding minor delays
            Thread.sleep(3000);
      
            // (optional) set prompt
            mock.setPrompt("Please provide a Python script that reads two integers (one per line) from stdin and prints the sum.");
            
            //adding minor delays
            Thread.sleep(3000);
            
            //clicking on generate
            mock.clickGenerate();
            
            // small wait for generation
            Thread.sleep(3000); 
            
            //Storing generated script
            String pythonScript = mock.getGeneratedScript();
            System.out.println("Generated script length: " + (pythonScript != null ? pythonScript.length() : 0));

            // 2) Open CodeChef IDE and paste code
            CodeChefIDEPage ide = new CodeChefIDEPage(driver);
            ide.openIDE();
            Thread.sleep(2000); // let page load
            ide.pasteCodeIntoEditor(pythonScript);

            // 3) Read TestCases
            Path csvPath = Path.of(System.getProperty("user.dir"), "testdata", "testdata.csv");
            List<CSVDataReader.Case> cases = CSVDataReader.readCases(csvPath);

            List<Result> results = new ArrayList<>();
            
            for (CSVDataReader.Case tc : cases) {
                System.out.printf("Running case: %s, %s (expected: %s)%n", tc.input1, tc.input2, tc.expected);

                //providing input from test data
                ide.provideInput(tc.input1, tc.input2);
                
                // Click run 
                ide.clickRun();

                //getting the output parameters after clicking on run button
                List<String> output = ide.readOutputWaiting(10000); // waits 5s then attempts to read
                
                //setting pass fail according to value of expected and actual sum
                boolean passed = output.get(3).equals(tc.expected);
                results.add(new Result(tc.input1, tc.input2, tc.expected,output.get(0),output.get(1),output.get(2),output.get(3), passed));
            }

            // generating the report in html format
            HTMLReportGenerator.writeReport(results);
        } finally {
            DriverManager.quitDriver();
        }
    }
}