package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScreenshotUtil {

 
    public static void takeScreenshot(WebDriver driver) {
    	File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);//cast driver into TakesScreenshot interface
	    File dest = new File("target/screenshots/screenshot.png");//Creates the file at desired location
	    dest.getParentFile().mkdirs();//if directory not present,create it
	    try {
			FileUtils.copyFile(src, dest);
			System.out.println("	Screenshot Captured");
		} catch (IOException e) {
			System.out.println("Screenshot Capture Failure");
		}
    }
}
