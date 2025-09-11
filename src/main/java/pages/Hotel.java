package pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ScreenshotUtil;
import utils.WaitUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Hotel {
	WebDriver driver;
	Actions actions;
	WebDriverWait wait;
	JavascriptExecutor jse;
	public String parentHandle;
	public Hotel(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
		jse = (JavascriptExecutor)driver;
		parentHandle = driver.getWindowHandle();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);

	}

	// Locators ---------------------------------------------------------------------------------------
	
	@FindBy(className="chHotels")
	private WebElement hotelIcon;

	@FindBy(id="city")
	private WebElement destinationInput;
	
	@FindBy(id="//li[@id='react-autowhatever-1-section-0-item-0']")
	private WebElement destinationFirstOption;

	@FindBy(css=".react-autosuggest__input")
	private WebElement searchBox;

	@FindBy(xpath="//label[@for='checkin']")
	private WebElement checkInField;

	@FindBy(xpath="//label[@for='checkout']")
	private WebElement checkOutField;

	@FindBy(css = ".btnApplyNew")
	WebElement applyBtn;

	@FindBy(xpath="//button[@id='hsw_search_button']")
	private WebElement searchButton;

	@FindBy(xpath="//div[@id='hotelListingContainer']")
	private WebElement searchResults;

	@FindBy(xpath = "//span[@aria-label='Next Month']")
	WebElement nextMonthBtn;

	@FindBy(xpath="//input[@aria-label='4 Star']")
	WebElement star4;

	@FindBy(xpath="//button[contains(@class, 'bkngOption__cta') and normalize-space(text())='BOOK THIS NOW']")
	WebElement bookNowButton;

	@FindBy(name="fName")
	WebElement guestFname;
	@FindBy(name="lName")
	WebElement guestLname;
	@FindBy(name="email")
	WebElement guestEmail;
	@FindBy(name="mNo")
	WebElement guestpH;


	@FindBy(xpath = "//div[@id='USER_RATING_MMT_BRAND']//li[1]//span[1]//label[1]")
	WebElement userRatingCheckbox;
	@FindBy(id="NOT_SELECTED")
	WebElement notSelect;

	@FindBy(xpath="//a[normalize-space()='Pay Now']")
	WebElement payBtn;
	@FindBy(xpath="//div[@class='payment__options__tab']")
	WebElement paymentsTab;

	@FindBy(xpath="//p[@class='bkngOption__title']")
	WebElement hotelDetailRoomTypes;
	
	@FindBy(xpath="//div[@id='STAR_CATEGORY']//li[2]//span[1]//label[1]")
	WebElement fourStarFilterCheckbox;
	
	@FindBy(xpath="//label[@for='Swimming Pool']")
	WebElement swimmingPoolFilterCheckbox;
	
	@FindBy(xpath="//label[contains(text(),'₹ 4000 - ₹ 8000')]")
	WebElement priceFilterCheckbox;
	
	@FindBy(xpath="//span[normalize-space()='(Lowest First)']")
	WebElement lowestPriceSort;

	@FindBy(xpath="//input[@id='city']")
	WebElement cityOfHotelSearched;

	
	
	//Related Functions----------------------------------------------------------------------------------

	public void closeLogin() {
		try {
			WebElement close = wait.until(ExpectedConditions.elementToBeClickable(By.className("commonModal__close")));
			close.click();
		} catch (Exception e) {
			System.out.println("	Modal not found or already closed.");
		}
	}

	//To select the hotel booking option
	public void selectHotelOption() {
		wait.until(ExpectedConditions.elementToBeClickable(hotelIcon)).click();
	}

	//Enter Destination
	public void enterDestination(String destination) {
		wait.until(ExpectedConditions.elementToBeClickable(destinationInput));
		destinationInput.click();
		
		wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		searchBox.sendKeys(destination);
		
		try {
			WaitUtils.waitForClickable(driver, By.xpath("(//b[contains(text(),'" + destination + "')])[2]"), 10).click();
		}catch(Exception e) {
			System.out.println();
		}
	}

	//Select the check-in date
	public int selectCheckInDate(String date) {
	    try {
	        wait.until(ExpectedConditions.visibilityOf(checkInField));
	        actions.moveToElement(checkInField).click().perform();

	        boolean dateFound = false;
	        while (!dateFound) {
	            try {
	                String xpath = String.format("//div[@aria-label='%s']", date);
	                WebElement checkInDate = driver.findElement(By.xpath(xpath));

	                // checking if disabled
	                if (checkInDate.getAttribute("class").contains("disabled")) {
	                    System.out.println("	Check-in date is disabled: " + date);
	                    return -1;
	                }

	                actions.moveToElement(checkInDate).click().perform();
	                System.out.println("	Clicked on Check-in date: " + date);
	                dateFound = true;
	                return 0;
	            } catch (Exception e) {
	                actions.moveToElement(nextMonthBtn).click().perform();
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("	Error while selecting check-in date: " + e.getMessage());
	    }
	    return -1;
	}

	// Select check-out date
	public int selectCheckOutDate(String date) {
	    try {
	        wait.until(ExpectedConditions.visibilityOf(checkOutField));
	        actions.moveToElement(checkOutField).click().perform();

	        boolean dateFound = false;
	        while (!dateFound) {
	            try {
	                String xpath = String.format("//div[@aria-label='%s']", date);
	                WebElement checkOutDate = driver.findElement(By.xpath(xpath));

	                if (checkOutDate.getAttribute("class").contains("disabled")) {
	                    System.out.println("	Check-out date is disabled: " + date);
	                    return -1;
	                }

	                actions.moveToElement(checkOutDate).click().perform();
	                System.out.println("	Clicked on Check-out date: " + date);
	                dateFound = true;
	                return 0;
	            } catch (Exception e) {
	                actions.moveToElement(nextMonthBtn).click().perform();
	            }
	        }
	        Thread.sleep(2000);
	    } catch (Exception e) {
	        System.out.println("	Error while selecting check-out date: " + e.getMessage());
	    }
	    return -1;
	}

	//Selecting no of rooms and no of guests
	public void selectRoomAndGuest() {
		wait.until(ExpectedConditions.visibilityOf(applyBtn)).click();
	}

	//Click Search Button
	public void clickSearch() {
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		searchButton.click();
	}
	
	//Get destination from hotel list view
	public void getDestination() throws InterruptedException {
		try {
			String city=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='city']"))).getAttribute("value");
			System.out.println("	Searched With Default City: "+city);
		}catch(Exception e) {
			System.out.println();
		}
		
	}
	
	//Filter 1 - Star Rating
	public void applyStarRating() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(fourStarFilterCheckbox));
		fourStarFilterCheckbox.click();
		Thread.sleep(2000);
		System.out.println("	4 star filter applied");
	}
	
	//Filter 2 - Swimming Pool Amenities 
	public void applyAmenitiesFilter() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(swimmingPoolFilterCheckbox));
		swimmingPoolFilterCheckbox.click();
		Thread.sleep(2000);
		System.out.println("	swimming pool amenities filter applied");
	}
	
	//Filter 3 - 8000-12000 price filter
	public void applyPriceFilter() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(priceFilterCheckbox));
		priceFilterCheckbox.click();
		Thread.sleep(2000);
		System.out.println("	price filter applied");
	}
	
	//Filter 4 - Sort by lowest price first
	public void sortByLowestPrice() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(lowestPriceSort));
		lowestPriceSort.click();
		Thread.sleep(2000);
		System.out.println("	Sort by lowest price first filter applied");

	}



	//If the search results are displayed
	public boolean isSearchResultsDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(searchResults));
			return searchResults.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}


	//choosing hostel from the list
	public void openHotelDetailPage() throws InterruptedException {
		WebElement hotel = driver.findElement(By.id("hlistpg_hotel_name"));
		parentHandle = driver.getWindowHandle();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlistpg_hotel_name")));

		actions.moveToElement(hotel).click(hotel).perform();

		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(parentHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}

		//Thread.sleep() used for visibility
		Thread.sleep(3000);
	}
	
	//get room type and the price
	public void getRoomTypeAndPrice() throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(hotelDetailRoomTypes));
		String roomType = hotelDetailRoomTypes.getText();
		String roomPrice = driver.findElement(By.xpath("//span[contains(@class, 'font28')]")).getText();

		System.out.println("	Room Type: " + roomType);
		System.out.println("	Room Price: " + roomPrice);
		Thread.sleep(2000);
	}

	//click on book now and fill details and proceed to payment
	public void bookRoom(String firstName, String lastName, String email, String phNo) throws IOException, InterruptedException {
	    wait.until(ExpectedConditions.elementToBeClickable(bookNowButton)).click();;
	    
	    wait.until(ExpectedConditions.visibilityOf(guestFname));
	    guestFname.sendKeys(firstName);
	    guestLname.sendKeys(lastName);
	    System.out.println("	Passenger Name: "+firstName+" "+lastName);
	    guestEmail.sendKeys(email);
	    System.out.println("	Passenger Email: "+email);
	    guestpH.sendKeys(phNo);
	    System.out.println("	Passenger Mobile: "+phNo);

	    try {
	    	WaitUtils.waitForClickable(driver, By.xpath("//input[@id='NOT_SELECTED']"), 10).click();
	    }catch(Exception e) {
	    	System.out.println();
	    }
	    

	    WaitUtils.waitForClickable(driver, By.xpath("//a[normalize-space()='Pay Now']"), 10).click();

	    //used thread.sleep to keep focus on payment page
	    Thread.sleep(3000);

	    // Take screenshot
	    ScreenshotUtil.takeScreenshot(driver);

	    //used thread.sleep to keep focus on payment page
	    Thread.sleep(2000);
	    
	    driver.switchTo().window(parentHandle);
	}
	
	//move back
	public void moveBack() throws InterruptedException {
		driver.navigate().back();
		Thread.sleep(3000);
		
	}	

}


