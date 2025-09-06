package pages;

import java.util.Scanner;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Login {

    WebDriver driver;
    WebDriverWait wait;
    

    public Login(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(xpath="//input[@placeholder='Enter Mobile Number' and @data-cy='userName']")
    WebElement phoneNumber;

    @FindBy(xpath="//button[@class='capText font16']")
    WebElement continueBtn;

    @FindBy(xpath="//input[@id='otp']")
    WebElement otp;

    @FindBy(xpath="//button[@data-cy='continueBtn']") 
    WebElement loginBtn;
    
    @FindBy(xpath="//label[@for='username']") 
    WebElement label;
    
    @FindBy(xpath="//span[@data-cy='serverError']") 
    WebElement invalidCred;
    
    @FindBy(xpath="//span[@class='commonModal__close']") 
    WebElement closeBtn;

    //Enter mobile number
    public void enterMobileNo(String mobile) {
        WebElement number = wait.until(ExpectedConditions.visibilityOf(phoneNumber));
        Assert.assertTrue(number.isDisplayed(), "Phone number input box is not visible!");
        phoneNumber.clear();
        number.sendKeys(mobile);    
        if(mobile.length()<10 || mobile.length()>10) {
        	System.out.println("	Login failed: " + mobile);
        }else {
        	System.out.println("	Login Successful: "+mobile);
        }
    }
    
    //Click on the continue button
    public void clickContinueButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-cy='continueBtn']")));
        Assert.assertTrue(btn.isDisplayed() && btn.isEnabled(), "Continue button is not clickable!");
        btn.click();
    }
   
    
    //Close login popup
    public void handleInitialPopup() {
        WebElement closePopup = wait.until(ExpectedConditions.elementToBeClickable(closeBtn));
        Assert.assertTrue(closePopup.isDisplayed(), "Close popup button not visible!");
        closePopup.click();
    }
    


}
