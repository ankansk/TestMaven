package homework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.assertj.core.api.SoftAssertions;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductsTests {

    private WebDriver driver;

    @Test
    public void ProductNumbersTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        SoftAssertions softAssert = new SoftAssertions();

        driver.findElement(By.xpath("//*[contains(@class, 'category-navigation')]//li[1]/a")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.id("products-pagesize"))).click();

        driver.switchTo().activeElement().sendKeys("8");
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        List<WebElement> products8 = driver.findElements(By.className("item-box"));
        softAssert.assertThat(products8.size()).isLessThanOrEqualTo(8);

        driver.findElement(By.id("products-pagesize")).click();
        driver.switchTo().activeElement().sendKeys("4");
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        List<WebElement> products4 = driver.findElements(By.className("item-box"));
        softAssert.assertThat(products4.size()).isLessThanOrEqualTo(4);

        softAssert.assertAll();

        driver.quit();
    }
}