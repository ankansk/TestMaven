package homework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
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

import java.io.File;
import java.time.Duration;

public class DemoQATests {

    String firstName = "Ivan";
    String lastName = "Ivanov";
    String email = "Ivan@mail.ru";
    String mobile = "8888888888";
    String currentAddress = "Nsk, Super street";
    String state = "Rajasthan";
    String city = "Jaiselmer";


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
        driver.findElement(By.cssSelector("#firstName")).sendKeys(firstName);
        driver.findElement(By.cssSelector("#lastName")).sendKeys(lastName);
        Thread.sleep(1000);

        // Заполнение email
        driver.findElement(By.cssSelector("#userEmail")).sendKeys(email);
        Thread.sleep(1000);

        // Заполнение gender
        driver.findElement(By.xpath("//label[@for='gender-radio-1']")).click();
        Thread.sleep(1000);

        // Заполнение mobile
        driver.findElement(By.cssSelector("#userNumber")).sendKeys(mobile);
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
        File picture = new File("src/cat.jpg");
        driver.findElement(By.id("uploadPicture")).sendKeys(picture.getAbsolutePath());
        Thread.sleep(1000);

        // Заполнение Current Address
        driver.findElement(By.id("currentAddress")).sendKeys(currentAddress);
        Thread.sleep(1000);

        // Скролл до кнопки
        WebElement submitButton = driver.findElement(By.id("submit"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", submitButton);
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
        driver.switchTo().activeElement().sendKeys(state);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        driver.findElement(By.id("city")).click();
        driver.switchTo().activeElement().sendKeys(city);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

        // Клик по кнопке
        driver.findElement(By.id("submit")).click();

        //Check form (SoftAssert позволяют продолжать проверки даже если одна упадет)
        SoftAssertions softAssert = new SoftAssertions();

        WebElement studentName = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr/td[2]"));
        String actualStudentName = studentName.getText();
        softAssert.assertThat(actualStudentName).isEqualTo((firstName + " " + lastName));

        WebElement studentEmail = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[2]/td[2]"));
        String actualEmail = studentEmail.getText();
        softAssert.assertThat(actualEmail).isEqualTo(email);

        WebElement studentGender = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[3]/td[2]"));
        String expectedGender = "Male";
        String actualGender = studentGender.getText();
        softAssert.assertThat(actualGender).isEqualTo(expectedGender);

        WebElement studentMobile = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[4]/td[2]"));
        String actualMobile = studentMobile.getText();
        softAssert.assertThat(actualMobile).isEqualTo(mobile);

        WebElement studentBirthDate = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[5]/td[2]"));
        String expectedBirthDate = "11 August,1988";
        String actualBirthDate = studentBirthDate.getText();
        softAssert.assertThat(actualBirthDate).isEqualTo(expectedBirthDate);

        WebElement subjects = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[6]/td[2]"));
        String expectedSubjects = "Biology";
        String actualSubjects = subjects.getText();
        softAssert.assertThat(actualSubjects).isEqualTo(expectedSubjects);

        WebElement hobbies = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[7]/td[2]"));
        String expectedHobbies = "Reading";
        String actualHobbies = hobbies.getText();
        softAssert.assertThat(actualHobbies).isEqualTo(expectedHobbies);

        WebElement pictureText = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[8]/td[2]"));
        String expectedPicture = "cat.jpg";
        String actualPicture = pictureText.getText();
        softAssert.assertThat(actualPicture).isEqualTo(expectedPicture);

        WebElement studentAddress = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[9]/td[2]"));
        String actualAddress = studentAddress.getText();
        softAssert.assertThat(actualAddress).isEqualTo(currentAddress);

        WebElement stateCity = driver.findElement(By.xpath("//*[@class='table-responsive']//tbody/tr[10]/td[2]"));
        String actualStateCity = stateCity.getText();
        softAssert.assertThat(actualStateCity).isEqualTo((state + " " + city));

        softAssert.assertAll();
    }

    @AfterEach

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}