package functions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import model.Event;
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
	 
//	 public static Event getSpecificEvent(String eventID) {
//			Statement stmt = null;   
//			Connection conn = null;  
//			Event event = new Event();
//			try {   
//				conn = SQLConnection.getDBConnection();  
//				stmt = conn.createStatement();
//				String searchSpecificEvent = " SELECT * from eventdetails WHERE eventID = '"+eventID+"'";
//				ResultSet eventList = stmt.executeQuery(searchSpecificEvent);
//				while(eventList.next()) {
//					String lastName = eventList.getString("lastName");
//					String firstName  = eventList.getString("firstName");
//					String date = eventList.getString("date");
//					String startTime  = eventList.getString("startTime");
//					String duration  = eventList.getString("duration");
//					String hallName = eventList.getString("hallName");
//					String estAttendees  = eventList.getString("estAttendees");
//					String eventName  = eventList.getString("eventName");
//					String foodType  = eventList.getString("foodType");
//					String meal  = eventList.getString("meal");
//					String mealFormality = eventList.getString("mealFormality");
//					String drinkType  = eventList.getString("drinkType");
//					String entertainmentItems  = eventList.getString("entertainmentItems");
//					String eventStatus  = eventList.getString("eventStatus");
//					String EventID  = eventList.getString("eventID");
//					String ccNumber = eventList.getString("ccnum");
//					String ccpin = eventList.getString("cvvnum");
//					String ccexpdate = eventList.getString("expdate");
//					String userid = eventList.getString("userid");
//					String depositAmount = eventList.getString("depositAmount");
//					event.setEvent(lastName, firstName, date, startTime, duration, hallName, estAttendees, eventName, foodType, meal, mealFormality, drinkType, 
//							entertainmentItems, eventStatus, EventID, ccNumber, ccpin, ccexpdate, userid,depositAmount);
//				}
//				
//				} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					conn.close();
//					stmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				};
//			return event;
//		}
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
			 String zipcode, String zipcodeErr,String errMsgs,String SnapshotName) {
		 
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Username"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Username"))).sendKeys(username);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Password"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Password"))).sendKeys(password);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Role"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Role"))).sendKeys(role);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_UTAID"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_UTAID"))).sendKeys(utaid);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_FirstName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_FirstName"))).sendKeys(firstname);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_LastName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_LastName"))).sendKeys(lastname);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Phone"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Phone"))).sendKeys(phone);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Email"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_Email"))).sendKeys(email);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetNumber"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetNumber"))).sendKeys(streetnumber);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetName"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_StreetName"))).sendKeys(streetname);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_City"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_City"))).sendKeys(city);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_State"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_State"))).sendKeys(state);
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_ZipCode"))).clear();
		 driver.findElement(By.xpath(prop.getProperty("Txt_Register_ZipCode"))).sendKeys(zipcode);
		 driver.findElement(By.xpath(prop.getProperty("Btn_Register_Register"))).click();
		 
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
	 }
}
