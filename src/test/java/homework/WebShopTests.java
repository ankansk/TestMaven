package homework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WebShopTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demowebshop.tricentis.com/cart");
        driver.manage().window().maximize();
    }

    @ParameterizedTest(name = "{index} - Add to cart")
    @ValueSource(strings = {"Laptop", "Smartphone", "Fiction"})
    void cartAdd(String param) throws InterruptedException {
        driver.findElement(By.id("small-searchterms")).sendKeys(param, Keys.ENTER);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("item-box")));

        driver.findElement(By.xpath("//*[@type='button' and @value='Add to cart']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bar-notification")));

        driver.findElement(By.xpath("//*[contains(@class, 'ico-cart')]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'cart-item')]")));

        Assertions.assertTrue(driver.findElement(By.className("cart-item-row")).isDisplayed());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}