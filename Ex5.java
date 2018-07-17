import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class Ex5 {
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
    public void main06_Ex5()
    {
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

        if ( !search_lang.equals("EN") )
        {
            search_lang_button.click();
            waitForElementAndClick (
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
                5
        );

        //Ждем загрузки статьи
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        //Кликаем на меню
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='Другие параметры']"),
                "Cannot find button",
                15
        );

        //Дожидаемся, пока сформируется меню (иначе кликаем не в то место)
        waitForElementNotPresent(
                By.xpath("//*[@text='Удалить из списков для чтения']"),
                "Cannot find option",
                15
        );

        //Кликаем на пункт меню "Добавить в список для чтения"
        waitForElementAndClick(
                By.xpath("//*[@text='Добавить в список для чтения']"),
                "Cannot find option",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' button",
                5
        );

        //Кликаем на "Создать новый список"
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Создать новый']"),
                "Cannot find button",
                5
        );

        //Очищаем поле для ввода названия списка
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find text input",
                5
        );

        String name_of_folder = "Operation systems";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='ОК']"),
                "Cannot press OK button",
                5
        );

        //Закрываем статью
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Перейти вверх']"),
                "Cannot click close button",
                5
        );

        //Печатаем в строке поиска "Android"
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Поиск')]"),
                "Android",
                "Cannot find 'Search Wikipedia' input",
                5
        );

        //Кликаем по статье с заголовком "Android version history"
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Android version history']"),
                "Cannot find article #2 in search",
                5
        );

        //Ждем загрузки статьи
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        //Запоминаем название статьи
        String title_before_edition = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                10
        );

        //Кликаем на меню
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='Другие параметры']"),
                "Cannot find button",
                5
        );

        //Дожидаемся, пока сформируется меню (иначе кликаем не в то место)
        waitForElementNotPresent(
                By.xpath("//*[@text='Удалить из списков для чтения']"),
                "Cannot find option",
                15
        );

        //Кликаем на пункт меню "Добавить в список для чтения"
        waitForElementAndClick(
                By.xpath("//*[@text='Добавить в список для чтения']"),
                "Cannot find option 'Add to list'",
                5
        );

        //Кликаем на список под названием "Operation systems"
        waitForElementAndClick(
                By.xpath("//*[@text='Operation systems']"),
                "Cannot find 'Operation systems' list",
                5
        );

        //Закрываем статью
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Перейти вверх']"),
                "Cannot click close button",
                5
        );

        //Кликаем на кнопку "Мои списки"
        waitForElementAndClick(
                By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.View[1]/android.widget.FrameLayout[2]/android.widget.ImageView"),
                "Cannot find 'My lists' button",
                5
        );

        //Ждем появления нашего списка для чтения
        waitForElementPresent(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                15
        );

        //Кликаем на наш список для чтения
        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );

        //Свайпаем влево по статье с заголовком "Android (operating system)"
        swipeElementToLeft(
                By.xpath("//*[@text='Android (operating system)']"),
                "Cannot find saved article"
        );

        //Убеждаемся, что статья с заголовком "Android (operating system)" пропала из списка
        waitForElementNotPresent(
                By.xpath("//*[@text='Android (operating system)']"),
                "Cannot delete saved article",
                5
        );

        //Убеждаемся, что статья с заголовком "Android version history" присутствует в списке
        waitForElementPresent(
                By.xpath("//*[@text='Android version history']"),
                "Cannot find article #2",
                15
        );

        //Кликаем на статью с заголовком "Android version history"
        waitForElementAndClick(
                By.xpath("//*[@text='Android version history']"),
                "Cannot open article #2",
                5
        );

        //Запоминаем название статьи
        String title_after_edition = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                10
        );

        //Сравниваем названия статей
        Assert.assertEquals(
                "Article title has been change after edition",
                title_before_edition,
                title_after_edition
        );
    }

    private String waitForElementAndGetAttribute (By by, String attribute, String error_message, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        return element.getAttribute(attribute);
    }

    protected void swipeElementToLeft (By by, String error_message)
    {
        WebElement element = waitForElementPresent(by, error_message, 10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(150)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
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

    private boolean waitForElementNotPresent (By by, String error_messge, long timeOutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_messge + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear (By by, String error_messge, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_messge, timeOutInSeconds);
        element.clear();
        return element;
    }
}
