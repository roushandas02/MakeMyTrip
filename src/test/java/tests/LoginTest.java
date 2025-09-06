package tests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.DriverSetup;
import pages.Login;

public class LoginTest extends DriverSetup {
	Login login;
	private static final Logger logger = LoggerFactory.getLogger(Login.class);

	@BeforeClass
	public void setup() {  
		login = new Login(driver);  
		logger.info("Page Objects for Login, Flight, and Hotel modules Implemented");
	}
	
	
	@Test(priority=2, description = "Login with valid Credentials")
	public void EnterValidLoginCredential() throws InterruptedException {
		logger.info("Login with valid Credentials");
		
		login.enterMobileNo("9999999999");
		login.clickContinueButton();
	}


	@Test(priority=3, description = "Verify Login UI elements")
	public void testClosePopup() {
		logger.info("Login UI elements verified");
		login.handleInitialPopup();  
	}


}
