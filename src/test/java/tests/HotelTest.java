package tests;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.DriverSetup;
import pages.Flight;
import pages.Hotel;
import pages.Login;

public class HotelTest extends DriverSetup{

	Hotel hotel;
	private static final Logger logger = LoggerFactory.getLogger(Login.class);

	@BeforeClass
	public void Setup() {  

		hotel = new Hotel(driver);
	}

	@Test(priority = 1, description = "Search hotels in a valid location with future check-in and check-out dates")
	public void hotelSearchWithFutureDatesTest() {
		logger.info("Searching hotels in a valid location with future check-in and check-out dates");
		
		hotel.selectHotelOption();
		hotel.enterDestination("Kolkata");
		hotel.selectCheckInDate("Thu Sep 25 2025");
		hotel.selectCheckOutDate("Mon Sep 29 2025");
		hotel.selectRoomAndGuest();
		hotel.clickSearch();

		Assert.assertTrue(hotel.isSearchResultsDisplayed(), "Hotel results should be displayed");
	}
	
	@Test(priority = 2, description = "Search hotels and then apply filters")
	public void applyHotelFilter() throws InterruptedException {
		logger.info("Applying filters to the list");
		
		hotel.applyStarRating();
		hotel.applyAmenitiesFilter();
		hotel.applyPriceFilter();
	}
	
	@Test(priority = 3, description = "Search hotels and then apply filters")
	public void sortByLowestPriceFirst() throws InterruptedException {
		logger.info("Sorting hotel list by lowest price first");
		
		hotel.sortByLowestPrice();
	}
	


	@Test(priority = 4, description = "Open hotel detail page and verify room types and prices")
	public void hotelDetailPageValidation() throws InterruptedException {
		logger.info("Opening hotel detail page and verifying room types and prices");
		
		hotel.openHotelDetailPage();  
		hotel.getRoomTypeAndPrice();
	}
	
	@Test(priority = 5, description = "Book a hotel room with valid guest details")
	public void hotelRoomBooking() throws IOException, InterruptedException {
		logger.info("Book a hotel room with valid guest details");
		
	    hotel.bookRoom("John", "Doe", "johndoe@gmail.com", "9876543210");
	}

	@Test(priority=6, description = "Validate past Check-in dates selection")
	public void pastCheckInDate() throws InterruptedException {
		logger.info("Validating past Check-in dates selection");

		driver.switchTo().window(hotel.parentHandle);

//		hotel.selectHotelOption();
		hotel.moveBack();

		hotel.enterDestination("Mumbai");

		String pastDate = "Mon Sep 01 2025"; 
		boolean pastDateSelected = false;
		try {
			hotel.selectCheckInDate(pastDate);
			Thread.sleep(2000);
			pastDateSelected = true;
		} catch (Exception e) {
			System.out.println("    Past check-in date was disabled: " + pastDate);
		}

		if (pastDateSelected) {
			System.out.println("	Test Passed: Past check-in date cannot be selected");
		} else {
			System.out.println("	Test Failed: Past check-in date got selected ");
		}


	}
	
	

	@Test(priority = 7, description = "Validate same check-in and check-out date selection")
	public void sameCheckInCheckOutDate() throws InterruptedException {
		logger.info("Validating same check-in and check-out date selection");

	    driver.switchTo().window(hotel.parentHandle);
	    
	    hotel.selectHotelOption();

	    hotel.enterDestination("Delhi");

	    String sameDate = "Thu Sep 25 2025";
	    int checkInResult = hotel.selectCheckInDate(sameDate);
	    int checkOutResult = hotel.selectCheckOutDate(sameDate);

	    if (checkInResult == 0 && checkOutResult == 0) {
	        hotel.selectRoomAndGuest();
	        hotel.clickSearch();
	    } else {
	        System.out.println("	Test Passed: Same check-in/check-out not allowed");
	    }

	    hotel.moveBack();  
	}
	
	@Test(priority=8, description = "Hotel Search without destination")
	public void searchHotelWithoutDestination() throws InterruptedException {
		logger.info("Validating Hotel Search without destination");
		
		hotel.selectHotelOption();
		hotel.getDestination();
		hotel.clickSearch();
	}
	
	
	
	


}
