package functions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import model.Event;
import model.User;
import util.SQLConnection;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
			  Thread.sleep(2000);
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
	 
	 public void viewAssignedEventStaff(WebDriver driver, String date,String time,String SnapshotName) {
		 
		 driver.findElement(By.xpath(prop.getProperty("Date_CatererStaff_date"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Date_CatererStaff_date"))).sendKeys(date);
		 driver.findElement(By.xpath(prop.getProperty("Time_CatererStaff_time"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Time_CatererStaff_time"))).sendKeys(time);
		 driver.findElement(By.xpath(prop.getProperty("Btn_Submit_submit"))).click();
		 takeScreenshot(driver,SnapshotName);
	 }
	 
	 private static ArrayList<Event> ReturnMatchingEventList (String queryString) {
			ArrayList<Event> eventListInDB = new ArrayList<Event>();
			System.out.println("Line 87"+queryString);
			Statement stmt = null;
			Connection conn = SQLConnection.getDBConnection();  
			try {
				stmt = conn.createStatement();
				ResultSet eventList = stmt.executeQuery(queryString);
				while (eventList.next()) {
					Event staff = new Event(); 
					staff.seteventName(eventList.getString("eventname"));
					staff.setdate(eventList.getString("date"));
					staff.setstartTime(eventList.getString("startTime"));
					staff.setduration(eventList.getString("duration"));
					staff.sethallName(eventList.getString("hallname"));
					staff.setLastName(eventList.getString("lastname"));
					staff.setfirstName(eventList.getString("firstname"));
					staff.seteventID(eventList.getString("eventID"));
					staff.seteventStatus(eventList.getString("eventStatus"));
					//System.out.println(staff.getEventname());
					eventListInDB.add(staff);	
				}
				System.out.println("Is Empty? "+eventListInDB.isEmpty());
			} catch (SQLException e) {}
			return eventListInDB;
		}
	 
	 public static ArrayList<Event>  listEvents2(String edate, String etime, String fname, String lname) {  
			
			return ReturnMatchingEventList(" SELECT eventname, date, starttime, duration, hallname, lastname, firstname, eventID,eventStatus from eventdetails where date_format(date(concat(date,' ',starttime)), '%m-%d-%Y %H:%i') >= date_format(date(concat('"+edate+"',' ','"+etime+"')), '%m-%d-%Y %H:%i') and staff_firstname='"+fname+"' and staff_lastname='"+lname+"' order by date,startTime");
		}
	 
	 public void verifyHeadersViewAssignedEvents(WebDriver driver,String eventName, String eventDate,String eventTime, String eventDuration,
			 String eventHallName, String userLastName, String userFirstName,String eventViewDetails, String SnapshotName) {
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_ViewAssignedEvents_EventName"))).getText().equals(eventName));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_ViewAssignedEvents_Date"))).getText().equals(eventDate));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_ViewAssignedEvents_startTime"))).getText().equals(eventTime));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_ViewAssignedEvents_Duration"))).getText().equals(eventDuration));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_ViewAssignedEvents_HallName"))).getText().equals(eventHallName));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_ViewAssignedEvents_UserLastName"))).getText().equals(userLastName));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_ViewAssignedEvents_UserFirstName"))).getText().equals(userFirstName));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_ViewAssignedEvents_ViewDetails"))).getText().equals(eventViewDetails));
		 takeScreenshot(driver,SnapshotName);
	 }
	 
	 public String[][] getAssignedEventsFromDB(int rows,String edate, String etime, String fname, String lname) throws SQLException{
		 ArrayList<Event> fromDB = new ArrayList<Event>();
		 fromDB= listEvents2(edate,etime,fname,lname);
		 System.out.println("Is Empty? "+fromDB.isEmpty());
		    String [][] arrayDB = new String [rows-1][7];
		    int i=0;
		    for (Event e:fromDB) {
		    	
		    	arrayDB[i][0]=e.geteventName();
		    	arrayDB[i][1]=e.getdate();
		    	arrayDB[i][2]=e.getstartTime();
		    	arrayDB[i][3]=e.getduration();
		    	arrayDB[i][4]=e.gethallName();
		    	arrayDB[i][5]=e.getLastName();
		    	arrayDB[i][6]=e.getfirstName();
		    	System.out.println(arrayDB[i][0]+arrayDB[i][1]+arrayDB[i][2]+arrayDB[i][3]+arrayDB[i][4]+arrayDB[i][5]+arrayDB[i][6]);
		 		i++;
		    }
		    return arrayDB;
	 }
	 
	 public static Event getSpecificEvent(String eventID) {
			Statement stmt = null;   
			Connection conn = null;  
			Event event = new Event();
			try {   
				conn = SQLConnection.getDBConnection();  
				stmt = conn.createStatement();
				String searchSpecificEvent = " SELECT * from eventdetails WHERE eventID = '"+eventID+"'";
				ResultSet eventList = stmt.executeQuery(searchSpecificEvent);
				while(eventList.next()) {
					String lastName = eventList.getString("lastName");
					String firstName  = eventList.getString("firstName");
					String date = eventList.getString("date");
					String startTime  = eventList.getString("startTime");
					String duration  = eventList.getString("duration");
					String hallName = eventList.getString("hallName");
					String estAttendees  = eventList.getString("estAttendees");
					String eventName  = eventList.getString("eventName");
					String foodType  = eventList.getString("foodType");
					String meal  = eventList.getString("meal");
					String mealFormality = eventList.getString("mealFormality");
					String drinkType  = eventList.getString("drinkType");
					String entertainmentItems  = eventList.getString("entertainmentItems");
					String eventStatus  = eventList.getString("eventStatus");
					String EventID  = eventList.getString("eventID");
					String ccNumber = eventList.getString("ccnum");
					String ccpin = eventList.getString("cvvnum");
					String ccexpdate = eventList.getString("expdate");
					String userid = eventList.getString("userid");
					String depositAmount = eventList.getString("depositAmount");
					event.setEvent(lastName, firstName, date, startTime, duration, hallName, estAttendees, eventName, foodType, meal, mealFormality, drinkType, 
							entertainmentItems, eventStatus, EventID, ccNumber, ccpin, ccexpdate, userid,depositAmount);
				}
				
				} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				};
			return event;
		}
//
//	 
//	 public String[][] getEventDetailsFromDB(String eid) {
//		 Event fromDB = new Event();
//		 String [][] arrayDB = new String[1][13];
//		 fromDB = getSpecificEvent(eid);
//		 
//		 arrayDB[1][0] = fromDB.getfirstName();
//		 arrayDB[1][1] = fromDB.getLastName();
//		 arrayDB[1][2] = fromDB.getdate();
//		 arrayDB[1][3] = fromDB.getstartTime();
//		 arrayDB[1][4] = fromDB.getduration();
//		 arrayDB[1][5] = fromDB.gethallName();
//		 arrayDB[1][6] = fromDB.getestAttendees();
//		 arrayDB[1][7] = fromDB.geteventName();
//		 arrayDB[1][8] = fromDB.getfoodType();
//		 arrayDB[1][9] = fromDB.getmeal();
//		 arrayDB[1][10] = fromDB.getmealFormality();
//		 arrayDB[1][11] = fromDB.getdrinkType();
//		 arrayDB[1][12] = fromDB.getentertainmentItems();
//		 return arrayDB;
//	 }
	 
	 public void verifyEventDetailsHeader(WebDriver driver,String h1OnPage,String exph1,
			 String h2OnPage,String exph2,
			 String h3OnPage,String exph3,
			 String h4OnPage,String exph4,
			 String h5OnPage,String exph5,
			 String h6OnPage,String exph6,
			 String h7OnPage,String exph7,
			 String h8OnPage,String exph8,
			 String h9OnPage,String exph9,
			 String h10OnPage,String exph10,
			 String h11OnPage,String exph11,
			 String h12OnPage,String exph12,
			 String h13OnPage,String exph13,String SnapshotName) {
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h1OnPage))).getText().equals(exph1));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h2OnPage))).getText().equals(exph2));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h3OnPage))).getText().equals(exph3));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h4OnPage))).getText().equals(exph4));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h5OnPage))).getText().equals(exph5));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h6OnPage))).getText().equals(exph6));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h7OnPage))).getText().equals(exph7));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h8OnPage))).getText().equals(exph8));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h9OnPage))).getText().equals(exph9));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h10OnPage))).getText().equals(exph10));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h11OnPage))).getText().equals(exph11));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h12OnPage))).getText().equals(exph12));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h13OnPage))).getText().equals(exph13));
		 takeScreenshot(driver,SnapshotName);
		 
	 }
	 
	 public void verifyHomePageElements(WebDriver driver,String SnapshotName) throws InterruptedException{
		 
		 if(driver.findElements(By.xpath(prop.getProperty("Link_CatererStaff_ViewProfile"))).size()>0) {
			 driver.findElement(By.xpath(prop.getProperty("Link_CatererStaff_ViewProfile"))).click();
			 Thread.sleep(1000);

			 driver.navigate().back();  
			 Thread.sleep(1000);

		 }
		 takeScreenshot(driver,SnapshotName);
	 }
	 
	 public void verifyEventContents(WebDriver driver,String eventFirstNameValueOnpage, String expectedEventFirstNameValue,
			 String eventLastNameValueOnpage, String expectedEventLastNameValue,
			 String eventDateValueOnpage, String expectedEventDateValue,
			 String eventStartTimeValueOnpage, String expectedEventStartTimeValue,
			 String eventDurationOnpage, String expectedEventDurationValue,
			 String eventHallNameValueOnpage, String expectedEventHallNameValue,
			 String eventEstAtndValueOnpage, String expectedEventEstAtndValue,
			 String eventNameValueOnpage, String expectedEventNameValue,
			 String eventFoodTypeValueOnpage, String expectedEventFoodTypeValue,
			 String eventMealValueOnpage, String expectedEventMealValue,
			 String eventMealFormalityValueOnpage, String expectedEventMealFormalityValue,
			 String eventDrinkTypeValueOnpage, String expectedEventDrinkTypeValue,
			 String eventEntItemsValueOnpage, String expectedEventEntItemsValue,String SnapshotName) {
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventFirstNameValueOnpage))).getText().equals(expectedEventFirstNameValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventLastNameValueOnpage))).getText().equals(expectedEventLastNameValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventDateValueOnpage))).getText().equals(expectedEventDateValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventStartTimeValueOnpage))).getText().equals(expectedEventStartTimeValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventDurationOnpage))).getText().equals(expectedEventDurationValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventHallNameValueOnpage))).getText().equals(expectedEventHallNameValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventEstAtndValueOnpage))).getText().equals(expectedEventEstAtndValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventNameValueOnpage))).getText().equals(expectedEventNameValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventFoodTypeValueOnpage))).getText().equals(expectedEventFoodTypeValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventMealValueOnpage))).getText().equals(expectedEventMealValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventMealFormalityValueOnpage))).getText().equals(expectedEventMealFormalityValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventDrinkTypeValueOnpage))).getText().equals(expectedEventDrinkTypeValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventEntItemsValueOnpage))).getText().equals(expectedEventEntItemsValue));
		 takeScreenshot(driver,SnapshotName);
	 }
	 
	 public void verifyRegisterPageHeaders(WebDriver driver,String title,String expTitle,
			 String Subtitle,String expSubtitle,
			 String h1OnPage,String exph1,
			 String h2OnPage,String exph2,
			 String h3OnPage,String exph3,
			 String h4OnPage,String exph4,
			 String h5OnPage,String exph5,
			 String h6OnPage,String exph6,
			 String h7OnPage,String exph7,
			 String h8OnPage,String exph8,
			 String h9OnPage,String exph9,
			 String h10OnPage,String exph10,
			 String h11OnPage,String exph11,
			 String h12OnPage,String exph12,
			 String h13OnPage,String exph13, String SnapshotName) {
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(title))).getText().equals(expTitle));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(Subtitle))).getText().equals(expSubtitle));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h1OnPage))).getText().equals(exph1));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h2OnPage))).getText().equals(exph2));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h3OnPage))).getText().equals(exph3));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h4OnPage))).getText().equals(exph4));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h5OnPage))).getText().equals(exph5));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h6OnPage))).getText().equals(exph6));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h7OnPage))).getText().equals(exph7));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h8OnPage))).getText().equals(exph8));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h9OnPage))).getText().equals(exph9));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h10OnPage))).getText().equals(exph10));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h11OnPage))).getText().equals(exph11));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h12OnPage))).getText().equals(exph12));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h13OnPage))).getText().equals(exph13));
		 takeScreenshot(driver,SnapshotName);

	}
	 
	 public void validateRegistrationFields(WebDriver driver,String username,String usernameErr,
			 String password,String pwdErr,
			 String role, String roleErr,
			 String utaid,String utaidErr,
			 String firstname,String firstnameErr,
			 String lastname, String lastnameErr,
			 String phone, String phoneErr,
			 String email, String emailErr,
			 String streetnumber, String streetnumberErr,
			 String streetname, String streetnameErr,
			 String city,String cityErr,
			 String state, String stateErr,
			 String zipcode, String zipcodeErr,String errMsgs,String SnapshotName) throws InterruptedException {
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Username"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Username"))).sendKeys(username);
		 //Thread.sleep(1000);
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Password"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Password"))).sendKeys(password);
		 //Thread.sleep(1000);
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Role"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Role"))).sendKeys(role);
		 //Thread.sleep(1000);
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_UTAID"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_UTAID"))).sendKeys(utaid);
		 //Thread.sleep(1000);
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_FirstName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_FirstName"))).sendKeys(firstname);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_LastName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_LastName"))).sendKeys(lastname);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Phone"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Phone"))).sendKeys(phone);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Email"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Email"))).sendKeys(email);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetNumber"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetNumber"))).sendKeys(streetnumber);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetName"))).sendKeys(streetname);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_City"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_City"))).sendKeys(city);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_State"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_State"))).sendKeys(state);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_ZipCode"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_ZipCode"))).sendKeys(zipcode);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Btn_Register_Register"))).click();
		 Thread.sleep(1000);
		 
		 System.out.println("Text: "+driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_ErrMsg"))).getAttribute("value"));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_ErrMsg"))).getAttribute("value").equals(errMsgs));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_Username"))).getAttribute("value").equals(usernameErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_Password"))).getAttribute("value").equals(pwdErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_Role"))).getAttribute("value").equals(roleErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_UTAID"))).getAttribute("value").equals(utaidErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_FirstName"))).getAttribute("value").equals(firstnameErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_LastName"))).getAttribute("value").equals(lastnameErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_Phone"))).getAttribute("value").equals(phoneErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_Email"))).getAttribute("value").equals(emailErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_StreetNumber"))).getAttribute("value").equals(streetnumberErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_StreetName"))).getAttribute("value").equals(streetnameErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_City"))).getAttribute("value").equals(cityErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_State"))).getAttribute("value").equals(stateErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_Register_ZipCode"))).getAttribute("value").equals(zipcodeErr));
		 takeScreenshot(driver,SnapshotName);
		 Thread.sleep(1000);

	 }
	 
	 public void RegisterCatererManager(WebDriver driver,String username,
			 String password,
			 String role,
			 String utaid,
			 String firstname,
			 String lastname, 
			 String phone, 
			 String email, 
			 String streetnumber,
			 String streetname, 
			 String city,
			 String state, 
			 String zipcode, String SnapshotName) throws InterruptedException {
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Username"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Username"))).sendKeys(username);
		 //Thread.sleep(1000);
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Password"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Password"))).sendKeys(password);
		 //Thread.sleep(1000);
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Role"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Role"))).sendKeys(role);
		 //Thread.sleep(1000);
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_UTAID"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_UTAID"))).sendKeys(utaid);
		 //Thread.sleep(1000);
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_FirstName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_FirstName"))).sendKeys(firstname);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_LastName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_LastName"))).sendKeys(lastname);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Phone"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Phone"))).sendKeys(phone);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Email"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Email"))).sendKeys(email);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetNumber"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetNumber"))).sendKeys(streetnumber);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetName"))).sendKeys(streetname);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_City"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_City"))).sendKeys(city);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_State"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_State"))).sendKeys(state);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_ZipCode"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_ZipCode"))).sendKeys(zipcode);
		 //Thread.sleep(1000);

		 driver.findElement(By.xpath(prop.getProperty("Btn_Register_Register"))).click();
		 Thread.sleep(10000);

		 
	 }
	 
	 public void verifyCatererManagerHomeElements(WebDriver driver,String Title,String subtitle,String viewSummary,String viewProfile,String SnapshotName) throws InterruptedException {
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CatererManagerHome_Title"))).getText().equals(Title));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CatererManagerHome_SubTitle"))).getText().equals(subtitle));
		 if(driver.findElements(By.xpath(prop.getProperty("Link_CatererManagerHome_ViewEventSummary"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Link_CatererManagerHome_ViewEventSummary"))).getText().equals(viewSummary));
			 driver.findElement(By.xpath(prop.getProperty("Link_CatererManagerHome_ViewEventSummary"))).click();
			 Thread.sleep(1000);

			 driver.navigate().back();  
			 Thread.sleep(1000);

		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Link_CatererManagerHome_ViewProfile"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Link_CatererManagerHome_ViewProfile"))).getText().equals(viewProfile));
			 driver.findElement(By.xpath(prop.getProperty("Link_CatererManagerHome_ViewProfile"))).click();
			 Thread.sleep(1000);

			 driver.navigate().back();  
			 Thread.sleep(1000);

		 }
		 if(driver.findElements(By.xpath(prop.getProperty("DatePicker_CatererManagerHome_Date"))).size()>0) {
			 driver.findElement(By.xpath(prop.getProperty("DatePicker_CatererManagerHome_Date"))).clear();
			 driver.findElement(By.xpath(prop.getProperty("DatePicker_CatererManagerHome_Date"))).sendKeys("04/15/2020");
			 Thread.sleep(1000);

		 }
		 if(driver.findElements(By.xpath(prop.getProperty("DatePicker_CatererManagerHome_Time"))).size()>0) {
			 driver.findElement(By.xpath(prop.getProperty("DatePicker_CatererManagerHome_Date"))).clear();
			 driver.findElement(By.xpath(prop.getProperty("DatePicker_CatererManagerHome_Date"))).sendKeys("09:00AM");
			 Thread.sleep(1000);

		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Btn_CatererManagerHome_Submit"))).size()>0) {
			 driver.findElement(By.xpath(prop.getProperty("Btn_CatererManagerHome_Submit"))).click();
			 Thread.sleep(1000);

			 driver.navigate().back();
			 Thread.sleep(1000);

		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Btn_CatereManagerHome_Logout"))).size()>0) {
			 driver.findElement(By.xpath(prop.getProperty("Btn_CatereManagerHome_Logout"))).click();
			 Thread.sleep(1000);

		 }
		 takeScreenshot(driver,SnapshotName);
		 
	 }
	 
	 public void verifyCMSummaryHeaders(WebDriver driver,String eventID,String eventName,String duration,String firstName,String lastName,String startTime,String hallName,
			 String eventDate, String estAtnds,String SnapshotName) {
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CatererManagerEventSummary_eventID"))).getText().equals(eventID));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CatererManagerEventSummary_eventName"))).getText().equals(eventName));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CatererManagerEventSummary_duration"))).getText().equals(duration));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CatererManagerEventSummary_firstname"))).getText().equals(firstName));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CatererManagerEventSummary_lastname"))).getText().equals(lastName));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CatererManagerEventSummary_startTime"))).getText().equals(startTime));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CatererManagerEventSummary_hallName"))).getText().equals(hallName));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CatererManagerEventSummary_date"))).getText().equals(eventDate));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CatererManagerEventSummary_estAtnds"))).getText().equals(estAtnds));
		 
		 takeScreenshot(driver,SnapshotName);
	 
	 }
	 
	 public static ArrayList<Event> getEventSummary() {
			Statement stmt = null; 	
			Connection conn = null;  
			ArrayList<Event> eventlist = new ArrayList<Event>();
			try {   
				conn = SQLConnection.getDBConnection();  
				stmt = conn.createStatement();
				String searchSpecificEvent = " SELECT * from eventdetails order by date,startTime;";
				ResultSet eventList = stmt.executeQuery(searchSpecificEvent);
				while(eventList.next()) {
					Event event= new Event();
					String lastName = eventList.getString("lastName");
					String firstName  = eventList.getString("firstName");
					String date = eventList.getString("date");
					String startTime  = eventList.getString("startTime");
					String duration  = eventList.getString("duration");
					String hallName = eventList.getString("hallName");
					String estAttendees  = eventList.getString("estAttendees");
					String eventName  = eventList.getString("eventName");
					String foodType  = eventList.getString("foodType");
					String meal  = eventList.getString("meal");
					String mealFormality = eventList.getString("mealFormality");
					String drinkType  = eventList.getString("drinkType");
					String entertainmentItems  = eventList.getString("entertainmentItems");
					String eventStatus  = eventList.getString("eventStatus");
					String eventID  = eventList.getString("eventID");

					event.setEvent(lastName, firstName, date, startTime, duration, hallName, estAttendees, eventName, foodType, meal, mealFormality, drinkType, 
							entertainmentItems, eventStatus, eventID, "", "", "", "","");
					eventlist.add(event);
				}
				
				} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				};
			return eventlist;
		}
	 
	 public String[][] getEventSummary(int rows){
		 String [][] arrayDB = new String [rows-1][9];
		 ArrayList<Event> fromDB = getEventSummary();
		    int i=0;
		    for (Event e:fromDB) {
		    	
		    	arrayDB[i][0]=e.geteventID();
		    	arrayDB[i][1]=e.geteventName();
		    	arrayDB[i][2]=e.getduration();
		    	arrayDB[i][3]=e.getfirstName();
		    	arrayDB[i][4]=e.getLastName();
		    	arrayDB[i][5]=e.getstartTime();
		    	arrayDB[i][6]=e.gethallName();
		    	arrayDB[i][7]=e.getdate();
		    	arrayDB[i][8]=e.getestAttendees();

		    	System.out.println(i +" "+arrayDB[i][0]+" "+arrayDB[i][1]+" "+arrayDB[i][2]+" "+arrayDB[i][3]+" "+arrayDB[i][4]+" "+arrayDB[i][5]+" "
						+arrayDB[i][6]+" "+arrayDB[i][7]+" "+arrayDB[i][8]);
		 		i++;
		    }

		 return arrayDB;
	 }
	 
	 public void verifyEventSummaryDetailsHeader(WebDriver driver,String h1OnPage,String exph1,
			 String h2OnPage,String exph2,
			 String h3OnPage,String exph3,
			 String h4OnPage,String exph4,
			 String h5OnPage,String exph5,
			 String h6OnPage,String exph6,
			 String h7OnPage,String exph7,
			 String h8OnPage,String exph8,
			 String h9OnPage,String exph9,
			 String h10OnPage,String exph10,
			 String h11OnPage,String exph11,
			 String h12OnPage,String exph12,
			 String h13OnPage,String exph13,
			 String h14OnPage,String exph14,String SnapshotName) {
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h1OnPage))).getText().equals(exph1));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h2OnPage))).getText().equals(exph2));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h3OnPage))).getText().equals(exph3));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h4OnPage))).getText().equals(exph4));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h5OnPage))).getText().equals(exph5));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h6OnPage))).getText().equals(exph6));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h7OnPage))).getText().equals(exph7));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h8OnPage))).getText().equals(exph8));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h9OnPage))).getText().equals(exph9));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h10OnPage))).getText().equals(exph10));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h11OnPage))).getText().equals(exph11));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h12OnPage))).getText().equals(exph12));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h13OnPage))).getText().equals(exph13));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h14OnPage))).getText().equals(exph14));
		 takeScreenshot(driver,SnapshotName);
		 
	 }
	 
	 public void verifyEventSummaryContents(WebDriver driver,String eventIDValueOnpage, String expectedEventIDValue,
			 String eventFirstNameValueOnpage, String expectedEventFirstNameValue,
			 String eventLastNameValueOnpage, String expectedEventLastNameValue,
			 String eventDateValueOnpage, String expectedEventDateValue,
			 String eventStartTimeValueOnpage, String expectedEventStartTimeValue,
			 String eventDurationOnpage, String expectedEventDurationValue,
			 String eventHallNameValueOnpage, String expectedEventHallNameValue,
			 String eventEstAtndValueOnpage, String expectedEventEstAtndValue,
			 String eventNameValueOnpage, String expectedEventNameValue,
			 String eventFoodTypeValueOnpage, String expectedEventFoodTypeValue,
			 String eventMealValueOnpage, String expectedEventMealValue,
			 String eventMealFormalityValueOnpage, String expectedEventMealFormalityValue,
			 String eventDrinkTypeValueOnpage, String expectedEventDrinkTypeValue,
			 String eventEntItemsValueOnpage, String expectedEventEntItemsValue,String SnapshotName) {
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventIDValueOnpage))).getText().equals(expectedEventIDValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventFirstNameValueOnpage))).getText().equals(expectedEventFirstNameValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventLastNameValueOnpage))).getText().equals(expectedEventLastNameValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventDateValueOnpage))).getText().equals(expectedEventDateValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventStartTimeValueOnpage))).getText().equals(expectedEventStartTimeValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventDurationOnpage))).getText().equals(expectedEventDurationValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventHallNameValueOnpage))).getText().equals(expectedEventHallNameValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventEstAtndValueOnpage))).getText().equals(expectedEventEstAtndValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventNameValueOnpage))).getText().equals(expectedEventNameValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventFoodTypeValueOnpage))).getText().equals(expectedEventFoodTypeValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventMealValueOnpage))).getText().equals(expectedEventMealValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventMealFormalityValueOnpage))).getText().equals(expectedEventMealFormalityValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventDrinkTypeValueOnpage))).getText().equals(expectedEventDrinkTypeValue));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(eventEntItemsValueOnpage))).getText().equals(expectedEventEntItemsValue));
		 takeScreenshot(driver,SnapshotName);
	 }

	 public void verifyAssignStaffPageElements(WebDriver driver, String title,String subtitle,String firstname,String lastname, String SnapshotName) throws InterruptedException {

		 if(driver.findElements(By.xpath(prop.getProperty("Header_CMAssignStaff_Title"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CMAssignStaff_Title"))).getText().equals(title));
		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Header_CMAssignStaff_Subtitle"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CMAssignStaff_Subtitle"))).getText().equals(subtitle));
		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Header_CMAssignStaff_firstName"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CMAssignStaff_firstName"))).getText().equals(firstname));
		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Header_CMAssignStaff_lastName"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_CMAssignStaff_lastName"))).getText().equals(lastname));
		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Txt_CMAssignStaff_firstName"))).size()>0) {
			 driver.findElement(By.xpath(prop.getProperty("Txt_CMAssignStaff_firstName"))).clear();
			 driver.findElement(By.xpath(prop.getProperty("Txt_CMAssignStaff_firstName"))).sendKeys("abscdds");
			 Thread.sleep(1000);

		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Txt_CMAssignStaff_lastName"))).size()>0) {
			 driver.findElement(By.xpath(prop.getProperty("Txt_CMAssignStaff_lastName"))).clear();
			 driver.findElement(By.xpath(prop.getProperty("Txt_CMAssignStaff_lastName"))).sendKeys("abscdds");
			 Thread.sleep(1000);

		 }
		 
		 takeScreenshot(driver,SnapshotName);
	 }
	 
	 public void validateAssignStaffError(WebDriver driver,String firstname,String lastname,String err,String staffErr,String SnapshotName) {
		 driver.findElement(By.xpath(prop.getProperty("Txt_CMAssignStaff_firstName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_CMAssignStaff_firstName"))).sendKeys(firstname);
		 driver.findElement(By.xpath(prop.getProperty("Txt_CMAssignStaff_lastName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_CMAssignStaff_lastName"))).sendKeys(lastname);
		 driver.findElement(By.xpath(prop.getProperty("Btn_CMAssignStaff_submit"))).click();
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_CMAssignStaff_Err"))).getAttribute("value").equals(err));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("TxtMsg_CMAssignStaff_Err1"))).getAttribute("value").equals(staffErr));
		 takeScreenshot(driver,SnapshotName);
	 }
	 
	 public void verifyAdminHomePageElements(WebDriver driver,String Title,String searchUser, String modifyProfile, String logout, String SnapshotName) throws InterruptedException {
		 if(driver.findElements(By.xpath(prop.getProperty("Header_AdminHomePage_CateringManagementApplication"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_AdminHomePage_CateringManagementApplication"))).getText().equals(Title));
		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Link_AdminHomePage_Logout"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_Logout"))).getText().equals(logout));
			 driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_Logout"))).click();
			 Thread.sleep(1000);
			 CM_Login(driver,"axk987","Bhumit!23","AdminLogin");
			 Thread.sleep(1000);
			 
		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Link_AdminHomePage_Search_for_User"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_Search_for_User"))).getText().equals(searchUser));
			 driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_Search_for_User"))).click();
			 Thread.sleep(1000);
			 driver.navigate().back();
			 Thread.sleep(1000);
		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Link_AdminHomePage_view/ModifyProfile"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_view/ModifyProfile"))).getText().equals(modifyProfile));
			 driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_view/ModifyProfile"))).click();
			 Thread.sleep(1000);
			 driver.navigate().back();
			 Thread.sleep(1000);

		 }
		 takeScreenshot(driver,SnapshotName);


	 }
	 
	 public void verifySearchUserPageElements(WebDriver driver, String title,String logout,String userLastNameHeader,String SnapshotName) throws InterruptedException {
		 driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_Search_for_User"))).click();
		 Thread.sleep(1000);
		 if(driver.findElements(By.xpath(prop.getProperty("Header_AdminSearchUser_CateringManagementApplication"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_AdminSearchUser_CateringManagementApplication"))).getText().equals(title));
			 driver.findElement(By.xpath(prop.getProperty("Header_AdminSearchUser_CateringManagementApplication"))).click();
			 Thread.sleep(1000);
			 driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_Search_for_User"))).click();
			 Thread.sleep(1000);
		 }	 
		 if(driver.findElements(By.xpath(prop.getProperty("Header_AdminSearchUser_UsersLastname"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_AdminSearchUser_UsersLastname"))).getText().equals(userLastNameHeader));
		 }	 
		 if(driver.findElements(By.xpath(prop.getProperty("Txt_AdminSearchUser_UsersLastname"))).size()>0) {
			 driver.findElement(By.xpath(prop.getProperty("Txt_AdminSearchUser_UsersLastname"))).clear();
			 driver.findElement(By.xpath(prop.getProperty("Txt_AdminSearchUser_UsersLastname"))).sendKeys("Patel");

		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Link_AdminSearchUser_Logout"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Link_AdminSearchUser_Logout"))).getText().equals(logout));
			 driver.findElement(By.xpath(prop.getProperty("Link_AdminSearchUser_Logout"))).click();
			 Thread.sleep(1000);
			 CM_Login(driver,"axk987","Bhumit!23","AdminLogin");
			 Thread.sleep(1000);
			 driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_Search_for_User"))).click();
			 Thread.sleep(1000);
		 }
		 takeScreenshot(driver,SnapshotName);

	 }
	 
	 public void validateSearchUser(WebDriver driver, String lastName,String errMsg,String lastNameErr,String SnapshotName ) {

		 driver.findElement(By.xpath(prop.getProperty("Txt_AdminSearchUser_UsersLastname"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_AdminSearchUser_UsersLastname"))).sendKeys(lastName);
		 driver.findElement(By.xpath(prop.getProperty("Link_AdminSearchUser_submit"))).click();
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_AdminSearchUser_ErrMsg"))).getAttribute("value").equals(errMsg));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_AdminSearchUser_LastNameErrMsg"))).getAttribute("value").equals(lastNameErr));
		 takeScreenshot(driver,SnapshotName);
	 }
	 
	 private static ArrayList<User> ReturnMatchingUsersList (String queryString) {
			ArrayList<User> userListInDB = new ArrayList<User>();
			
			Statement stmt = null;
			Connection conn = SQLConnection.getDBConnection();  
			try {
				stmt = conn.createStatement();
				ResultSet userList = stmt.executeQuery(queryString);
				while (userList.next()) {
					User user = new User(); 
					user.setLastname(userList.getString("lastname"));
					user.setFirstname(userList.getString("firstname"));
					user.setUsername(userList.getString("username"));
					user.setRole(userList.getString("role"));
					user.setUtaid(userList.getString("utaid"));
					user.setCity(userList.getString("city"));
					user.setState(userList.getString("state"));
					user.setZipcode(userList.getString("zipcode"));
					user.setStreetname(userList.getString("streetname"));
					user.setStreetnumber(userList.getString("streetno"));
					user.setEmail(userList.getString("email"));
					user.setPhone(userList.getString("phone"));
					userListInDB.add(user);	
				}
			} catch (SQLException e) {}
			return userListInDB;
		}
		
	 public static ArrayList<User>  searchUsers(String userlastname)  {  
			return ReturnMatchingUsersList(" SELECT * from user WHERE lastname LIKE '%"+userlastname+"%' ORDER BY lastname,firstname,role");
		}

	 public String[][] getUsersFromDB(int rows,String lastName) throws SQLException{
		 ArrayList<User> fromDB = new ArrayList<User>();
		 fromDB = searchUsers(lastName);
		 System.out.println("Is Empty? "+fromDB.isEmpty());
		    String [][] arrayDB = new String [rows-1][4];
		    int i=0;
		    for (User e:fromDB) {
		    	
		    	arrayDB[i][0]=e.getLastname();
		    	arrayDB[i][1]=e.getFirstname();
		    	arrayDB[i][2]=e.getUsername();
		    	arrayDB[i][3]=e.getRole();
		    	System.out.println(arrayDB[i][0]+arrayDB[i][1]+arrayDB[i][2]+arrayDB[i][3]);
		 		i++;
		    }
		    return arrayDB;
	 }

	 public void verifySearchUserResultsHeaders(WebDriver driver,String lastName,String firstName,String userName,String role,String SnapshotName) throws InterruptedException {
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_AdminViewUser_Tablelastname"))).getText().equals(lastName));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_AdminViewUser_TableFirstname"))).getText().equals(firstName));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_AdminViewUser_TableUsername"))).getText().equals(userName));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_AdminViewUser_TableRole"))).getText().equals(role));
		 takeScreenshot(driver,SnapshotName);


	 }
	 
	 public void verifyViewProfilePageHeaders(WebDriver driver,String title,String expTitle,
			 String Subtitle,String expSubtitle,
			 String h1OnPage,String exph1,
			 String h2OnPage,String exph2,
			 String h3OnPage,String exph3,
			 String h4OnPage,String exph4,
			 String h5OnPage,String exph5,
			 String h6OnPage,String exph6,
			 String h7OnPage,String exph7,
			 String h8OnPage,String exph8,
			 String h9OnPage,String exph9,
			 String h10OnPage,String exph10,
			 String h11OnPage,String exph11,
			 String h12OnPage,String exph12,
			 String SnapshotName) {
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(title))).getText().equals(expTitle));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(Subtitle))).getText().equals(expSubtitle));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h1OnPage))).getText().equals(exph1));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h2OnPage))).getText().equals(exph2));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h3OnPage))).getText().equals(exph3));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h4OnPage))).getText().equals(exph4));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h5OnPage))).getText().equals(exph5));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h6OnPage))).getText().equals(exph6));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h7OnPage))).getText().equals(exph7));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h8OnPage))).getText().equals(exph8));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h9OnPage))).getText().equals(exph9));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h10OnPage))).getText().equals(exph10));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h11OnPage))).getText().equals(exph11));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty(h12OnPage))).getText().equals(exph12));
		 takeScreenshot(driver,SnapshotName);

	}

	 public static ArrayList<User>   searchUser (String username)  {  
		return ReturnMatchingUsersList(" SELECT * from user WHERE username = '"+username+"'");
	 }
	 
	 public void verifyViewUserContent(WebDriver driver,String username,String SnapshotName) {
		 //System.out.println("Username: "+username);
		 ArrayList<User> userDB = searchUser(username);
		 System.out.println("Username: "+userDB.get(0).getUsername());
		 System.out.println("Username1: "+driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_Username"))).getAttribute("value"));
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_Username"))).getAttribute("value").equals(userDB.get(0).getUsername()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_Role"))).getAttribute("value").equals(userDB.get(0).getRole()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_UTAid"))).getAttribute("value").equals(userDB.get(0).getUtaid()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_FirstName"))).getAttribute("value").equals(userDB.get(0).getFirstname()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_LastName"))).getAttribute("value").equals(userDB.get(0).getLastname()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_Phone"))).getAttribute("value").equals(userDB.get(0).getPhone()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_Email"))).getAttribute("value").equals(userDB.get(0).getEmail()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_StreetNumber"))).getAttribute("value").equals(userDB.get(0).getStreetnumber()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_StreetName"))).getAttribute("value").equals(userDB.get(0).getStreetname()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_City"))).getAttribute("value").equals(userDB.get(0).getCity()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_State"))).getAttribute("value").equals(userDB.get(0).getState()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewProfile_Zipcode"))).getAttribute("value").equals(userDB.get(0).getZipcode()));
		 takeScreenshot(driver,SnapshotName);

	 }

	 public void verifyUserHomePageElements(WebDriver driver,String header,String l1,String l2,String l3,String SnapshotName) {
		 if(driver.findElements(By.xpath(prop.getProperty("Header_UserHome_UserHome"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_UserHome_UserHome"))).getText().equals(header));
		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Link_UserHome_RequestEvent"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Link_UserHome_RequestEvent"))).getText().equals(l1));
		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Link_UserHome_ViewEventSummary"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Link_UserHome_ViewEventSummary"))).getText().equals(l2));
		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Link_UserHome_ViewProfile"))).size()>0) {
			 assertTrue(driver.findElement(By.xpath(prop.getProperty("Link_UserHome_ViewProfile"))).getText().equals(l3));
		 }
		 if(driver.findElements(By.xpath(prop.getProperty("Link_UserHome_Logout"))).size()>0) {
			 driver.findElement(By.xpath(prop.getProperty("Link_UserHome_Logout"))).click();
		 }
		 takeScreenshot(driver,SnapshotName);
	 }
	
	 public static User getUser(String username) {
			Statement stmt = null;   
			Connection conn = null;  
			User user = new User();
			try {   
				conn = SQLConnection.getDBConnection();  
				stmt = conn.createStatement();
				String searchUsername = " SELECT * from USER WHERE USERNAME = '"+username+"'";
				ResultSet userList = stmt.executeQuery(searchUsername);
				while(userList.next()) {
					String password = userList.getString("password");
					String role  = userList.getString("role");
					String utaId = userList.getString("utaid");
					String firstName  = userList.getString("firstname");
					String lastName  = userList.getString("lastname");
					String phone = userList.getString("phone");
					String email  = userList.getString("email");
					String streetname  = userList.getString("streetname");
					String streetno  = userList.getString("streetno");
					String city  = userList.getString("city");
					String state  = userList.getString("state");
					String zipcode  = userList.getString("zipcode");
					user.setUser( username, password, lastName, firstName, role, utaId, phone, email, streetno, streetname, city, state, zipcode);				  	
				}
				
				} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				};
			return user;
		}

	 public void verifyViewMyProfileContent(WebDriver driver,String username,String SnapshotName) {
		 
		 User userDB = getUser(username);
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_Username"))).getAttribute("value").equals(userDB.getUsername()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_Password"))).getAttribute("value").equals(userDB.getPassword()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_Role"))).getAttribute("value").equals(userDB.getRole()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_UTAid"))).getAttribute("value").equals(userDB.getUtaid()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_FirstName"))).getAttribute("value").equals(userDB.getFirstname()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_LastName"))).getAttribute("value").equals(userDB.getLastname()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_Phone"))).getAttribute("value").equals(userDB.getPhone()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_Email"))).getAttribute("value").equals(userDB.getEmail()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_StreetNumber"))).getAttribute("value").equals(userDB.getStreetnumber()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_StreetName"))).getAttribute("value").equals(userDB.getStreetname()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_City"))).getAttribute("value").equals(userDB.getCity()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_State"))).getAttribute("value").equals(userDB.getState()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyProfile_Zipcode"))).getAttribute("value").equals(userDB.getZipcode()));
		 takeScreenshot(driver,SnapshotName);
		 driver.findElement(By.xpath(prop.getProperty("Link_ViewMyProfile_Logout"))).click();
		 
	 }
	 
	 public void verifyEventRequestPageHeaders(WebDriver driver,String title,String h1,String h2,String h3,String h4,String h5,String h6,String h7,
			 String h8,String h9,String h10,String h11,String h12,String h13) {
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_header"))).getText().equals(title));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_LastName"))).getText().equals(h1));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_FirstName"))).getText().equals(h2));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_Date"))).getText().equals(h3));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_Time"))).getText().equals(h4));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_Duration"))).getText().equals(h5));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_HallName"))).getText().equals(h6));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_EstAtnds"))).getText().equals(h7));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_EventName"))).getText().equals(h8));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_FoodType"))).getText().equals(h9));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_Meal"))).getText().equals(h10));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_MealFormality"))).getText().equals(h11));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_DrinkType"))).getText().equals(h12));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Header_EventBook_DrinkType"))).getText().equals(h13));
		 
	 }
	 
	 public void veriyfEventRequestPageInputs(WebDriver driver) {
		 
		 if(driver.findElements(By.xpath(prop.getProperty("Txt_EventBook_Duration"))).size()>0) {
			 
		 }
		 
	 }
	 
	 public void validateEventRequestPage(WebDriver driver, String FirstName,String LastName,String Date,String Time,String Duration,String HallName,
			 String estAtnds,String EventName,String foodType,String Meal,String mealFormality,String DrinkType,String entItems,
			 String pastDateErr,String durationErr,String capacityErr,String estAtndsErr,String eventNameErr,String sameDayErr,String sameWeekErr,
			 String timeErr,String SnapshotName) throws InterruptedException {
		 
		 
		 driver.findElement(By.xpath(prop.getProperty("Link_UserHome_RequestEvent"))).click();
		 Thread.sleep(1000);
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_EventRequest_Date"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_EventRequest_Date"))).sendKeys(Date);

		 driver.findElement(By.xpath(prop.getProperty("Txt_EventRequest_Time"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_EventRequest_Time"))).sendKeys(Time);
		 
		 Thread.sleep(1000);
		 
		 driver.findElement(By.xpath(prop.getProperty("btn_EventRequest_Next"))).click();
		 Thread.sleep(1000);

		 Select durationDrpDown = new Select(driver.findElement(By.xpath(prop.getProperty("Txt_EventBook_Duration"))));
		 Select hallDrpDown = new Select(driver.findElement(By.xpath(prop.getProperty("Txt_EventBook_HallName"))));
		 Select foodTypeDrpDown = new Select(driver.findElement(By.xpath(prop.getProperty("Txt_EventBook_FoodType"))));
		 Select mealDrpDown = new Select(driver.findElement(By.xpath(prop.getProperty("Txt_EventBook_Meal"))));
		 Select mealFormalityDrpDown = new Select(driver.findElement(By.xpath(prop.getProperty("Txt_EventBook_MealFormality"))));
		 Select drinkTypeDrpDown = new Select(driver.findElement(By.xpath(prop.getProperty("Txt_EventBook_DrinkType"))));
		 Select entItemsDrpDown = new Select(driver.findElement(By.xpath(prop.getProperty("Txt_EventBook_EntItems"))));

		 durationDrpDown.selectByVisibleText(Duration);
		 hallDrpDown.selectByVisibleText(HallName);

		 driver.findElement(By.xpath(prop.getProperty("Txt_EventBook_EstAtnds"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_EventBook_EstAtnds"))).sendKeys(estAtnds);

		 driver.findElement(By.xpath(prop.getProperty("Txt_EventBook_EventName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_EventBook_EventName"))).sendKeys(EventName);

		 foodTypeDrpDown.selectByVisibleText(foodType);
		 mealDrpDown.selectByVisibleText(Meal);
		 mealFormalityDrpDown.selectByVisibleText(mealFormality);
		 drinkTypeDrpDown.selectByVisibleText(DrinkType);
		 entItemsDrpDown.selectByVisibleText(entItems);
		 
		 Thread.sleep(1000);
		 
		 driver.findElement(By.xpath(prop.getProperty("Btn_EventBook_bookEvent"))).click();
		 Thread.sleep(1000);
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Err_EventBook_PastDate"))).getAttribute("value").equals(pastDateErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Err_EventBook_Duration"))).getAttribute("value").equals(durationErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Err_EventBook_Capacity"))).getAttribute("value").equals(capacityErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Err_EventBook_EstAtnds"))).getAttribute("value").equals(estAtndsErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Err_EventBook_EventName"))).getAttribute("value").equals(eventNameErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Err_EventBook_SameDay"))).getAttribute("value").equals(sameDayErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Err_EventBook_SameWeek"))).getAttribute("value").equals(sameWeekErr));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Err_EventBook_Time"))).getAttribute("value").equals(timeErr));

		 takeScreenshot(driver,SnapshotName);
		 
	 }
	 
	 public static ArrayList<Event> getUserEventSummary(String Username, String firstname,String lastname) {
			Statement stmt = null;
			Statement stmt1 = null;
			Connection conn = null;  
			ArrayList<Event> eventlist = new ArrayList<Event>();
			try {   
				conn = SQLConnection.getDBConnection();  
				stmt = conn.createStatement();
				String updateuserevent = "update eventdetails set firstname='"+firstname+"', lastname='"+lastname+"' where userid='"+Username+"';";
				System.out.print(updateuserevent);
				stmt1 = conn.createStatement();
				stmt1.executeUpdate(updateuserevent);
				conn.commit();
				String searchSpecificEvent = "SELECT * from eventdetails where userid = '"+Username+"' order by date,startTime;";
				ResultSet eventList = stmt.executeQuery(searchSpecificEvent);
				while(eventList.next()) {
					Event event= new Event();
					String lastName = eventList.getString("lastName");
					String firstName  = eventList.getString("firstName");
					String date = eventList.getString("date");
					String startTime  = eventList.getString("startTime");
					String duration  = eventList.getString("duration");
					String hallName = eventList.getString("hallName");
					String estAttendees  = eventList.getString("estAttendees");
					String eventName  = eventList.getString("eventName");
					String foodType  = eventList.getString("foodType");
					String meal  = eventList.getString("meal");
					String mealFormality = eventList.getString("mealFormality");
					String drinkType  = eventList.getString("drinkType");
					String entertainmentItems  = eventList.getString("entertainmentItems");
					String eventStatus  = eventList.getString("eventStatus");
					String eventID  = eventList.getString("eventID");

					event.setEvent(lastName, firstName, date, startTime, duration, hallName, estAttendees, eventName, foodType, meal, mealFormality, drinkType, 
							entertainmentItems, eventStatus, eventID, "", "", "", "","");
					eventlist.add(event);
					
				}
				
				} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				};
			return eventlist;
		}
	 
	 public String[][] userEventsummaryfromDB(int rows,String username,String firstname,String lastname) {
		 String [][] arrayDB = new String [rows-1][9];
		 ArrayList<Event> fromDB = getUserEventSummary(username,firstname,lastname);
		    int i=0;
		    for (Event e:fromDB) {
		    	
		    	arrayDB[i][0]=e.geteventID();
		    	arrayDB[i][1]=e.geteventName();
		    	arrayDB[i][2]=e.getduration();
		    	arrayDB[i][3]=e.getfirstName();
		    	arrayDB[i][4]=e.getLastName();
		    	arrayDB[i][5]=e.getstartTime();
		    	arrayDB[i][6]=e.gethallName();
		    	arrayDB[i][7]=e.getdate();
		    	arrayDB[i][8]=e.getestAttendees();

		    	System.out.println(i +" "+arrayDB[i][0]+" "+arrayDB[i][1]+" "+arrayDB[i][2]+" "+arrayDB[i][3]+" "+arrayDB[i][4]+" "+arrayDB[i][5]+" "
						+arrayDB[i][6]+" "+arrayDB[i][7]+" "+arrayDB[i][8]);
		 		i++;
		    }

		 return arrayDB;
	 }
	 
	 public void verifyViewEventContent(WebDriver driver,String EventID,String SnapshotName) {
		 //System.out.println("Username: "+username);
		 Event userDB = getSpecificEvent(EventID);
		 System.out.print("Event id: "+userDB.geteventID());
		 System.out.print("Event id1: "+driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_eventID"))).getText());
		 
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_eventID"))).getText().equals(userDB.geteventID()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_Lastname"))).getText().equals(userDB.getLastName()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_Firstname"))).getText().equals(userDB.getfirstName()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_eventDate"))).getText().equals(userDB.getdate()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_startTime"))).getText().equals(userDB.getstartTime()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_duration"))).getText().equals(userDB.getduration()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_hallName"))).getText().equals(userDB.gethallName()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_estAtnds"))).getText().equals(userDB.getestAttendees()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_eventName"))).getText().equals(userDB.geteventName()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_foodtype"))).getText().equals(userDB.getfoodType()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_meal"))).getText().equals(userDB.getmeal()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_mealFormality"))).getText().equals(userDB.getmealFormality()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_drinkType"))).getText().equals(userDB.getdrinkType()));
		 assertTrue(driver.findElement(By.xpath(prop.getProperty("Txt_ViewUserEvent_entItems"))).getText().equals(userDB.getentertainmentItems()));

		 takeScreenshot(driver,SnapshotName);

	 }


}
