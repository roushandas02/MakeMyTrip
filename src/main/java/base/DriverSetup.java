package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetup {
    public static WebDriver driver;

    @Parameters("browser")
    @BeforeSuite
    public void openBrowser(String browser) {
        System.out.println("Opening browser: " + browser);

        // Load URL from properties file using ConfigLoader class
        ConfigLoader config = new ConfigLoader();
        String url = config.getProperty("url").trim();

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
//                FirefoxOptions options = new FirefoxOptions();
//                options.addArguments("--headless");  // Run without GUI
//                options.addArguments("--no-sandbox");
//                options.addArguments("--disable-dev-shm-usage");
//                driver = new FirefoxDriver(options);
                driver = new FirefoxDriver();
                break;
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver.manage().window().maximize();
        driver.get(url);  
    }
    
    

    @AfterSuite
    public void closeBrowser() {
        driver.quit();
    }
}
