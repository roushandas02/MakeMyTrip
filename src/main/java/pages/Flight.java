package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v124.dom.DOM.PerformSearchResponse;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utils.WaitUtils;

public class Flight {
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor jse;
	public Flight(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		 jse = (JavascriptExecutor) driver;
	}

	//Locators-------------------------------------------------------------------------------------------
	
	@FindBy(xpath ="//span[@class='styles__Close-sc-1bytt3z-0 kezeYI']") 
	WebElement ads;
	
	@FindBy(xpath ="//input[@id='fromCity']") 
	WebElement fromCity;
	
	@FindBy(xpath ="//input[@class='react-autosuggest__input react-autosuggest__input--open']") 
	WebElement fromCitySearch;
	
	@FindBy(xpath="//li[@id='react-autowhatever-1-section-0-item-0']") 
	WebElement fromCityOption;
	
	@FindBy(xpath ="//input[@id='toCity']") 
	WebElement toCity;
	
	@FindBy(xpath ="//input[@class='react-autosuggest__input react-autosuggest__input--open']") 
	WebElement toCitySearch;
	
	@FindBy(xpath="//li[@id='react-autowhatever-1-section-0-item-0']") 
	WebElement toCityOption;
	
	@FindBy(xpath="//div[@class='DayPicker']") 
	WebElement DepartureDayPicker;
	
	@FindBy(xpath="//span[text()='Departure']") 
	WebElement departure;
	
	@FindBy(xpath = "//div[contains(@class,'DayPicker-Month')]") 
	WebElement calender;
	
	@FindBy(xpath = "//a[normalize-space()='Search']") 
	WebElement searchBtn;
	
	@FindBy(xpath="//li[@data-cy='roundTrip']")
	WebElement roundTripCheckBox;
	
	@FindBy(xpath="//span[text()='Return']") 
	WebElement returnDate;
	
	
	//Related Functions--------------------------------------------------------------------------------
	
	//Close the ad
	public void closeAds() {
		try {
			WebElement adsClose = wait.until(ExpectedConditions.visibilityOf(ads));
			Assert.assertTrue(adsClose.isDisplayed(), "Ad close button is not visible!");
			adsClose.click();	
		}catch(Exception e) {
			System.out.println("	Ads not visible");
		}
		
	}

	//Select source location
    public void selectFromLocation(String location) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(fromCity)).click();
        
        WebElement fromSearch = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//input[@placeholder='From']")));
        fromSearch.sendKeys(location);
        
        try {
        	WaitUtils.waitForClickable(driver, By.xpath("(//span[contains(text(),'" + location + "')])[1]"), 10).click();
        }catch(Exception e) {
        	System.out.println();
        }
        
    }

    //select destination
    public void selectToLocation(String location) throws InterruptedException {
    	wait.until(ExpectedConditions.elementToBeClickable(toCity)).click();
    	
    	WebElement toSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='To']")));
    	toSearch.sendKeys(location);
    	
    	try {
        	WaitUtils.waitForClickable(driver, By.xpath("(//span[contains(text(),'" + location + "')])[1]"), 10).click();
        }catch(Exception e) {
        	System.out.println();
        }
    }
    
    //Select departure date
    public void selectDepartureDate(String date) {
        try {
           
            WebElement departureField = wait.until(ExpectedConditions.elementToBeClickable(departure));
            departureField.click();
            
            wait.until(ExpectedConditions.visibilityOf(calender));
             
            String xpath = String.format("//div[@aria-label='%s']", date);
            WebElement departureDate = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            departureDate.click();
            System.out.println("	Clicked on Departure date: " + date);

        } catch (Exception e) {
            System.out.println("	Error while selecting departure date: " + e.getMessage());
            
        }
    }
    
    //Click Round Trip Checkbox
    public void clickCheckBox() {
    	wait.until(ExpectedConditions.visibilityOf(roundTripCheckBox)).click();
    }
    
    
    //Select return date
    public void selectReturnDate(String date) {
        try {
            
            WebElement returnField = wait.until(ExpectedConditions.elementToBeClickable(returnDate));
            returnField.click();
            
            wait.until(ExpectedConditions.visibilityOf(calender));

            String xpath = String.format("//div[@aria-label='%s']", date);
            WebElement returnDateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            returnDateElement.click();
            System.out.println("	Clicked on Return date: " + date);

        } catch (Exception e) {
            System.out.println("	Error while selecting return date: " + e.getMessage());
        }
    }

    //Click Search
    public void clickSearch() throws InterruptedException {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
            jse.executeScript("arguments[0].click();", button);
            Thread.sleep(5000);
    }
    
    //Navigate to home
    public void navigateToHome() {
        driver.navigate().back();
        driver.navigate().refresh(); 
    }

    
    

}
