import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import java.time.Duration;
import java.util.function.Function;

public class SampleTest {

    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
        driver.get("https://ffair-sandbox-console.firebaseapp.com/login");
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void signInTest() {
        String startURL = "https://ffair-sandbox-console.firebaseapp.com/";
        String email = "baryshev.alexandr89@gmail.com";
        String password = "123123123";
        driver.findElement(By.xpath("//button[@type='submit' and (@disabled)]"));
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit' and not(@disabled)]")).click();

        driver.getPageSource().contains("Sign In");
       Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(5))
            .ignoring(NoSuchElementException.class);

       WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
          public WebElement apply(WebDriver driver) {
              return driver.findElement(By.id("cy-console-role"));
          }
       });

        String userRole = driver.findElement(By.id("cy-console-role")).getText();
        Assert.assertTrue("exhibitor", userRole.contains(userRole));

        driver.getPageSource().contains("Yafim Yakunin");
        driver.getPageSource().contains("Select show to review");
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, startURL );
    }
}
