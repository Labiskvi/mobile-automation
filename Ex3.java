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

public class Ex3 {
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
    public void main05_Ex3 ()
    {
        //Если встретили кнопку "Пропустить", нажимаем ее
        if ( ! driver.findElements(By.xpath("//*[contains(@text, 'Пропустить')]")).isEmpty() )
                {
            WebElement skip_button = driver.findElement(By.xpath("//*[contains(@text, 'Пропустить')]"));
            skip_button.click();
        }

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Поиск по Википедии')]"),
                "Cannot find search line",
                5
        );

        //Переключаемся на русскоязычный поиск
        WebElement search_lang_button = waitForElementPresent(
                (By.id("org.wikipedia:id/search_lang_button")),
                "Cannot find language button",
                5
        );

        String search_lang = search_lang_button.getAttribute("text");

        if ( !search_lang.equals("RU") )
        {
            search_lang_button.click();
            waitForElementAndClick (
                    By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.TextView"),
                    "Cannot find RU language",
                    15
            );
        }

        search_lang = search_lang_button.getAttribute("text");

        Assert.assertEquals(
                "Cannot switch to RU language",
                "RU",
                search_lang
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Поиск')]"),
                "java",
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java']"),
                "Cannot find first search result",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='JavaScript']"),
                "Cannot find second search result",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='JavaServer Pages']"),
                //By.xpath("//*[contains(@text, 'JavaServer Pages')]"),
                "Cannot find third search result",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Поиск')]"),
                "Cannot find search line",
                5
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

    private WebElement waitForElementAndClick(By by, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }
}
