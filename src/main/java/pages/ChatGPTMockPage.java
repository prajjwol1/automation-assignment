package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ChatGPTMockPage {
    private final WebDriver driver;

    //locator for chat gpt mock page
    private final By promptArea = By.id("prompt");
    private final By generateButton = By.id("generate");
    private final By generatedArea = By.id("generated");

    public ChatGPTMockPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openLocal() {
        String path = System.getProperty("user.dir") + "/chatgptmock.html";
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
        return driver.findElement(generatedArea).getAttribute("value");
    }
}
