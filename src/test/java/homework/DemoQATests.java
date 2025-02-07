package homework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DemoQATests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {

        String browser = System.getProperty("browser");

        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
    }

    @Test
    public void fillFormTest() throws InterruptedException {
        driver.get("https://demoqa.com/automation-practice-form");

        // Заполнение Name
        driver.findElement(By.cssSelector("#firstName")).sendKeys("Ivan");
        driver.findElement(By.cssSelector("#lastName")).sendKeys("Ivanov");
        Thread.sleep(1000);

        // Заполнение email
        driver.findElement(By.cssSelector("#userEmail")).sendKeys("Ivan@mail.ru");
        Thread.sleep(1000);

        // Заполнение gender
        driver.findElement(By.xpath("//label[@for='gender-radio-1']")).click();
        Thread.sleep(1000);

        // Заполнение mobile
        driver.findElement(By.cssSelector("#userNumber")).sendKeys("8999999999");
        Thread.sleep(1000);

        // Заполнение date of birth
        driver.findElement(By.cssSelector("#dateOfBirthInput")).click();
        driver.findElement(By.xpath("//*[contains(@class, 'month-select')]/option[8]")).click();
        driver.findElement(By.xpath("//*[contains(@class, 'year-select')]/option[@value='1988']")).click();
        driver.findElement(By.xpath("//*[contains(@class, 'day--011')]")).click();


        // Заполнение Subjects
        driver.findElement(By.id("subjectsInput")).sendKeys("Bio");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Biology']"))).click();

        // Заполнение Hobbies
        driver.findElement(By.xpath("//label[@for='hobbies-checkbox-2']")).click();
        Thread.sleep(1000);

        // Заполнение Picture
        driver.findElement(By.id("uploadPicture")).sendKeys("/home/ann/Downloads/_Формат файлов для теста/cat.jpg");
        Thread.sleep(1000);

        // Заполнение Current Address
        driver.findElement(By.id("currentAddress")).sendKeys("Nsk, Super street");
        Thread.sleep(1000);

        // Скролл вниз страницы
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000);");
        Thread.sleep(1000);

        // Заполнение State and City
//        driver.findElement(By.xpath("//div[text()='Select State']")).click();
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//div[text()='Rajasthan']")).click();
//        Thread.sleep(2000);
//
//        driver.findElement(By.xpath("//div[text()='Select City']")).click();
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//div[text()='Jaiselmer']")).click();
//        Thread.sleep(2000);

        driver.findElement(By.id("state")).click();
        driver.switchTo().activeElement().sendKeys("Rajasthan");
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        driver.findElement(By.id("city")).click();
        driver.switchTo().activeElement().sendKeys("Jaiselmer");
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        // Клик по кнопке
        driver.findElement(By.id("submit")).click();

        //Check form
        WebElement studentName = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr/td[2]"));
        String expectedStudentName = "Ivan Ivanov";
        String actualStudentName = studentName.getText();
        Assertions.assertEquals(expectedStudentName, actualStudentName);

        WebElement studentEmail = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[2]/td[2]"));
        String expectedEmail = "Ivan@mail.ru";
        String actualEmail = studentEmail.getText();
        Assertions.assertEquals(expectedEmail, actualEmail);

        WebElement gender = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[3]/td[2]"));
        String expectedGender = "Male";
        String actualGender = gender.getText();
        Assertions.assertEquals(expectedGender, actualGender);

        WebElement mobile = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[4]/td[2]"));
        String expectedMobile = "8999999999";
        String actualMobile = mobile.getText();
        Assertions.assertEquals(expectedMobile, actualMobile);

        WebElement birthDate = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[5]/td[2]"));
        String expectedBirthDate = "11 August,1988";
        String actualBirthDate = birthDate.getText();
        Assertions.assertEquals(expectedBirthDate, actualBirthDate);

        WebElement subjects = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[6]/td[2]"));
        String expectedSubjects = "Biology";
        String actualSubjects = subjects.getText();
        Assertions.assertEquals(expectedSubjects, actualSubjects);

        WebElement hobbies = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[7]/td[2]"));
        String expectedHobbies = "Reading";
        String actualHobbies = hobbies.getText();
        Assertions.assertEquals(expectedHobbies, actualHobbies);

        WebElement picture = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[8]/td[2]"));
        String expectedPicture = "cat.jpg";
        String actualPicture = picture.getText();
        Assertions.assertEquals(expectedPicture, actualPicture);

        WebElement address = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[9]/td[2]"));
        String expectedAddress = "Nsk, Super street";
        String actualAddress = address.getText();
        Assertions.assertEquals(expectedAddress, actualAddress);

        WebElement stateCity = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[10]/td[2]"));
        String expectedStateCity = "Rajasthan Jaiselmer";
        String actualStateCity = stateCity.getText();
        Assertions.assertEquals(expectedStateCity, actualStateCity);
    }

    @AfterEach

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}