package homework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class OrderFormTests {

    private WebDriver driver;

    @Test
    public void errorTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://formdesigner.ru/examples/order.html");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // клик Принять всё
        driver.findElement(By.xpath("//button[contains(text(), 'Принять все')]")).click();
        // Thread.sleep(2000);

        //скролл к форме
        WebElement titleForm = driver.findElement(By.xpath("//h3[contains(text(), 'Пример готовой формы')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", titleForm);

        //переключаюсь на iframe
        WebElement iframe = driver.findElement(By.cssSelector("#form_1006>iframe"));
        driver.switchTo().frame(iframe);
        //   Thread.sleep(2000);

        //нажать кнопку
        driver.findElement(By.xpath("//button[contains(text(), 'Отправить')]")).click();
        //   Thread.sleep(2000);

        // Check error text
        SoftAssertions softAssert = new SoftAssertions();

        WebElement error1 = driver.findElement(By.xpath("//*[contains(@class,'errorSummary')]/ul/li"));
        String expectedError1 = "Необходимо заполнить поле ФИО:.";
        String actualError1 = error1.getText();
        softAssert.assertThat(actualError1).isEqualTo(expectedError1);

        WebElement error2 = driver.findElement(By.xpath("//*[contains(@class,'errorSummary')]/ul/li[2]"));
        String expectedError2 = "Необходимо заполнить поле E-mail.";
        String actualError2 = error2.getText();
        softAssert.assertThat(actualError2).isEqualTo(expectedError2);

        WebElement error3 = driver.findElement(By.xpath("//*[contains(@class,'errorSummary')]/ul/li[3]"));
        String expectedError3 = "Необходимо заполнить поле Количество.";
        String actualError3 = error3.getText();
        softAssert.assertThat(actualError3).isEqualTo(expectedError3);

        WebElement error4 = driver.findElement(By.xpath("//*[contains(@class,'errorSummary')]/ul/li[4]"));
        String expectedError4 = "Необходимо заполнить поле Дата доставки.";
        String actualError4 = error4.getText();
        softAssert.assertThat(actualError4).isEqualTo(expectedError4);

        softAssert.assertAll();

        System.out.println("Actual result = " + actualError1);
        System.out.println("Actual result = " + actualError2);
        System.out.println("Actual result = " + actualError3);
        System.out.println("Actual result = " + actualError4);

        driver.quit();
    }
}