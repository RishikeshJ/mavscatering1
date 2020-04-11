package functions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import util.SQLConnection;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.util.Properties;

import static org.junit.Assert.assertTrue;



public class CateringManagementFunctions {
	
	 public static WebDriver driver;
	 public static Properties prop;
	 static SQLConnection DBMgr = SQLConnection.getInstance();
	 public enum FunctionEnum {login}
	 
	 public WebDriver invokeCorrectBrowser () {
		System.setProperty("webdriver.chrome.driver", "C:/chromedriver_win32/chromedriver.exe");
		return new ChromeDriver();
	 }
	 
	 public void takeScreenshot(WebDriver driver, String screenshotname) {
		  try
		  {
			  File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);			
			  FileUtils.copyFile(source, new File("./screenShots/" + screenshotname +".png"));
		  }
		  catch(IOException e) {}
		  try {
//				  Change the delay value to 1_000 to insert a 1 second delay after 
//				  each screenshot
			  Thread.sleep(1000);
		} catch (InterruptedException e) {}
	 }
	 
	 public void CM_Login(WebDriver driver, String username, String password, String SnapshotName) {
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_login_Username"))).sendKeys(username);
		 driver.findElement(By.xpath(prop.getProperty("Txt_login_Password"))).sendKeys(password);
		 driver.findElement(By.xpath(prop.getProperty("Btn_login_login"))).click();
		 takeScreenshot(driver,SnapshotName);
		 
	 }
	 
	 public void verifyLoginErrorMessages(WebDriver driver,String errorMsgs, String usernameError, String passwordError, String SnapshotName) {
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("login_errorMsgs"))).getAttribute("value").equals(errorMsgs));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("login_usernameError"))).getAttribute("value").equals(usernameError));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("login_passwordError"))).getAttribute("value").equals(passwordError));
		 takeScreenshot(driver,SnapshotName);
		 
	 }


}
