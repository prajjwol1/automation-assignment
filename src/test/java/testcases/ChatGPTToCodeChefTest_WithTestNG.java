package testcases;

import pages.ChatGPTMockPage;
import pages.CodeChefIDEPage;
import utils.CSVDataReader;
import utils.DriverManager;
import utils.HTMLReportGenerator;
import utils.HTMLReportGenerator.Result;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ChatGPTToCodeChefTest_WithTestNG {

    WebDriver driver;
    CodeChefIDEPage ide;
    List<Result> results = new ArrayList<>();
    @BeforeClass
    public void setup() throws InterruptedException {
       
    	//setting up driver
    	driver = DriverManager.getDriver();
        
    	//opening mock chat gpt page
        ChatGPTMockPage mock = new ChatGPTMockPage(driver);
        mock.openLocal();
        Thread.sleep(2000);
        
        //providing promt and clicking on generate
        mock.setPrompt("Please provide a Python script that reads two integers (one per line) from stdin and prints the sum.");
        Thread.sleep(2000);
        mock.clickGenerate();
        Thread.sleep(2000);
        
        //saving python script on string variable
        String pythonScript = mock.getGeneratedScript();
        System.out.println("Generated script length: " + (pythonScript != null ? pythonScript.length() : 0));

        // 2) Open CodeChef IDE and paste code
        ide = new CodeChefIDEPage(driver);
        ide.openIDE();
        Thread.sleep(2000);
        ide.pasteCodeIntoEditor(pythonScript);
    }

    @AfterClass
    public void teardown() throws Exception {
    	HTMLReportGenerator.writeReport(results);
        DriverManager.quitDriver();
    }

  
    //setting data provider using csv file
    @DataProvider(name = "csvData")
    public Object[][] csvDataProvider() throws Exception {
        Path csvPath = Path.of(System.getProperty("user.dir"), "testdata", "testdata.csv");
        List<CSVDataReader.Case> cases = CSVDataReader.readCases(csvPath);
        Object[][] data = new Object[cases.size()][1];
        for (int i = 0; i < cases.size(); i++) {
            data[i][0] = cases.get(i);
        }
        return data;
    }

    
   
    //running test for each case
    @Test(dataProvider = "csvData")
    public void runTest(CSVDataReader.Case testCase) throws Exception {
        //Providing input from CSV row
        System.out.printf("Running case: %s, %s (expected: %s)%n", testCase.input1, testCase.input2, testCase.expected);
        ide.provideInput(testCase.input1, testCase.input2);
        ide.clickRun();

        List<String> output = ide.readOutputWaiting(10000);
        boolean passed = output.get(3).equals(testCase.expected);
        System.out.print("actual "+output.get(3));
        
        //adding the generated result to html report
        results.add(new Result(testCase.input1, testCase.input2, testCase.expected,output.get(0),output.get(1),output.get(2),output.get(3), passed));
        
        // assert to let TestNG track pass/fail
        assert passed : "Test failed for inputs: " + testCase.input1 + ", " + testCase.input2;
    }
}
