package tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.DriverSetup;
import pages.Flight;
import pages.Login;

public class FlightTest extends DriverSetup {
	
	Flight flight;
	private static final Logger logger = LoggerFactory.getLogger(Login.class);
	
	@BeforeClass
    public void setup() { 
        flight = new Flight(driver);
    }
	
	@Test(priority = 1, description = "Close Ads if appearing")
	public void ads() {
		flight.closeAds();
	}
	
	@Test(priority=2, description = "Search one-way flight with valid cities & future date")
	public void OneWayTrip() throws InterruptedException {
		logger.info("Searching one-way flight with valid cities & future date");
		
		flight.selectFromLocation("Kolkata");
		flight.selectToLocation("Delhi");
		flight.selectDepartureDate("Thu Sep 25 2025");
		flight.clickSearch();
		flight.navigateToHome();
		flight.closeAds();
	}
	
	@Test(priority = 3, description = "Search round-trip flight with valid inputs")
	public void RoundTrip() throws InterruptedException {
		logger.info("Searching round-trip flight with valid inputs");
		
		flight.clickCheckBox();
		flight.selectFromLocation("Delhi");
		flight.selectToLocation("Kolkata");
		flight.selectDepartureDate("Thu Sep 25 2025");
		flight.selectReturnDate("Mon Sep 29 2025");
		flight.clickSearch();
		flight.navigateToHome();
		flight.closeAds();
	}
	
	
}
