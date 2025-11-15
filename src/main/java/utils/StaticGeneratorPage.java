package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StaticGeneratorPage {
    private WebDriver driver;

    // Locators (IDs used in static HTML). Replace if necessary.
    private By promptArea = By.id("prompt");
    private By generateButton = By.id("generate");
    private By outputPre = By.id("output");

    public StaticGeneratorPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openLocalPage() {
        String path = System.getProperty("user.dir") + "/src/test/resources/static/index.html";
        driver.get("file://" + path);
    }

    public void setPrompt(String prompt) {
        WebElement ta = driver.findElement(promptArea);
        ta.clear();
        ta.sendKeys(prompt);
    }

    public void clickGenerate() {
        driver.findElement(generateButton).click();
    }

    public String getGeneratedScript() {
        WebElement pre = driver.findElement(outputPre);
        return pre.getText();
    }
}
