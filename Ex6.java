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

public class Ex6 {
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
    public void testEx6() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Пропустить')]"),
                "Cannot find skip button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Поиск по Википедии')]"),
                "Cannot find search line",
                5
        );

        //Переключаемся на англоязычный поиск
        WebElement search_lang_button = waitForElementPresent(
                (By.id("org.wikipedia:id/search_lang_button")),
                "Cannot find language button",
                5
        );

        String search_lang = search_lang_button.getAttribute("text");

        if (!search_lang.equals("EN")) {
            search_lang_button.click();
            waitForElementAndClick(
                    By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.TextView[1]"),
                    "Cannot find EN language",
                    15
            );
        }

        search_lang = search_lang_button.getAttribute("text");

        Assert.assertEquals(
                "Cannot switch to EN language",
                "EN",
                search_lang
        );

        //Печатаем в строке поиска "Android"
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Поиск')]"),
                "Android",
                "Cannot find 'Search Wikipedia' input",
                5
        );

        //Кликаем по статье с заголовком "Android (operating system)"
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Android (operating system)']"),
                "Cannot find article #1 in search",
                30
        );

        //Ищем title у статьи (без ожидания)
        assertElementPresent (
                By.id("org.wikipedia:id/view_page_title_text"),
                "We did not found article title"
        );
    }


    private void assertElementPresent (By by, String error_message)
    {
        Assert.assertFalse(
                error_message,
                (driver.findElements(by).isEmpty()) == true
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
