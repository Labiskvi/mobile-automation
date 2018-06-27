import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class Ex2 {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Zera S");
        capabilities.setCapability("platformVersion", "4.4.2");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");

        driver = new AndroidDriver(new URL( "http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void main04_Ex2 ()
    {
        if ( ! driver.findElements(By.xpath("//*[contains(@text, 'Пропустить')]")).isEmpty() )
        {
            WebElement skip_button = driver.findElement(By.xpath("//*[contains(@text, 'Пропустить')]"));
            skip_button.click();
        }

        WebElement search_input = waitForElementPresent(
                By.xpath("//*[contains(@text, 'Поиск по Википедии')]"),
                "Cannot find search input",
                5
        );

        String text_in_search_input = search_input.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected text in search input",
                "Поиск по Википедии",
                text_in_search_input
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeOutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
}
