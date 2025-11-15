package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

public class CodeChefIDEPage {
    private final WebDriver driver;

    //locator for codechef id page
    public By runButton = By.xpath("//span[text()='Run']");         
    public By outputArea = By.xpath("(//pre/code)[last()]");        
    public By editorTextArea = By.xpath("//div[@class='ace_content']");    
    public By compilerSelectorDropdown = By.xpath("//div[@role='combobox']");  
    public By python3Option = By.xpath("//li[text()='Python3']");  
    public By inputArea = By.xpath("//div[contains(@class,'Input')]//textarea");
    public By outputStatus = By.xpath("//div[contains(@class,'_status')]//div//div");
    public By time = By.xpath("//span[text()='Time:']/following-sibling::span");
    public By memory = By.xpath("//span[text()='Memory:']/following-sibling::span");
    
    public CodeChefIDEPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openIDE() {
        driver.get("https://www.codechef.com/ide");
    }

    //method to paste the copied code to codechef editor, it also selects python3 from dropdown
    public void pasteCodeIntoEditor(String code) {
        try {
        	driver.findElement(compilerSelectorDropdown).click();
        	driver.findElement(python3Option).click();
        	driver.findElement(editorTextArea).click();
        	copyTextToClipboard(code);
        	Thread.sleep(3000);
        	new Actions(driver).keyDown(Keys.CONTROL)
            .sendKeys("v")
            .keyUp(Keys.CONTROL)
            .perform();
        	
        } catch (Exception e) {
            System.out.println("pasteCodeIntoEditor JS failed: " + e.getMessage());
        }
    }
    
    //provide input in input area
    public void provideInput(String input1, String input2) {
    	WebElement textArea=driver.findElement(inputArea);
    	textArea.clear();
    	textArea.sendKeys(input1 + Keys.ENTER + input2);
    }
    
    //click on run button
    public void clickRun() {
        driver.findElement(runButton).click();
    }
    
    
    //read output from output area adding some wait time
    public List<String> readOutputWaiting(int waitMs) {
    	List<String> output = new ArrayList<String>();
        try {
            Thread.sleep(waitMs);
        } catch (InterruptedException ignored) {}
        try {
        	String status = driver.findElement(outputStatus).getText();
        	String timeText = driver.findElement(time).getText();
        	String memoryText = driver.findElement(memory).getText();
            String outputText = driver.findElement(outputArea).getText();
            output.add(status);
            output.add(timeText);
            output.add(memoryText);
            output.add(outputText);
            return output;
        } catch (Exception e) {
            System.out.println("Failed to read output area: " + e.getMessage());
            return null;
        }
    }
    
    //a custom function to copy text to clipboard
    public static void copyTextToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
