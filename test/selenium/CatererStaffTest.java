package selenium;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Properties;

import functions.CateringManagementFunctions;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CatererStaffTest extends CateringManagementFunctions{

	private StringBuffer verificationErrors = new StringBuffer();
	public String sAppURL, sSharedUIMapPath, testDelay,username,password;
	
	public String[][] getTableContentsFromPage(int rows){

		String [][] eventArray = new String[rows-1][7];
		for (int i=0; i<rows-1; i++) {
			eventArray[i][0]=  driver.findElement(By.xpath(prop.getProperty("Txt_EventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewAssignedEvents_EventNameCol"))).getText();
			eventArray[i][1] = driver.findElement(By.xpath(prop.getProperty("Txt_EventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewAssignedEvents_DateCol"))).getText();
			eventArray[i][2] = driver.findElement(By.xpath(prop.getProperty("Txt_EventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewAssignedEvents_startTimeCol"))).getText();
			eventArray[i][3] = driver.findElement(By.xpath(prop.getProperty("Txt_EventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewAssignedEvents_DurationCol"))).getText();
			eventArray[i][4] = driver.findElement(By.xpath(prop.getProperty("Txt_EventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewAssignedEvents_HallNameCol"))).getText();
			eventArray[i][5] = driver.findElement(By.xpath(prop.getProperty("Txt_EventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewAssignedEvents_UserLastNameCol"))).getText();
			eventArray[i][6] = driver.findElement(By.xpath(prop.getProperty("Txt_EventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewAssignedEvents_UserFirstNameCol"))).getText();
			
			System.out.println("Table:");
			System.out.println(eventArray[i][0]+eventArray[i][1]+eventArray[i][2]+eventArray[i][3]+eventArray[i][4]+eventArray[i][5]+eventArray[i][6]);

		}
		System.out.println("IS EMPTY? "+eventArray.length);
	  return eventArray;

	}
	
	private Boolean arraysDiff (String [][] array1, String [][] array2) { // this method compares the contents of the two tables
		  Boolean diff= false || (array1.length!=array2.length);
		  for (int i=0;i<array1.length && !diff;i++) {
			 diff  = !array1[i][0].equals(array2[i][0]) || !array1[i][1].equals(array2[i][1]) || 
					 !array1[i][2].equals(array2[i][2]) || !array1[i][3].equals(array2[i][3]) ||
					 !array1[i][4].equals(array2[i][4]) || !array1[i][5].equals(array2[i][5]) ||
					 !array1[i][6].equals(array2[i][6]);
		  }
		  return diff;
	  }

	
	@Before
	public void setUp() throws Exception {
		driver = invokeCorrectBrowser();
	    //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    prop = new Properties();	  
	    prop.load(new FileInputStream("./configuration/configuration.properties"));
		sAppURL = prop.getProperty("sAppURL");
		sSharedUIMapPath = prop.getProperty("SharedUIMap");
		testDelay=prop.getProperty("testDelay");
		prop.load(new FileInputStream(sSharedUIMapPath));
	}

	@Test
	@FileParameters("test/selenium/CatererStaffTestCase1.csv")
	public void test(int testCaseNumber,String eventName,String eventDate, String eventTime, String eventDuration, String eventHallName,
			String userLastName, String userFirstName,String eventViewDetails) throws InterruptedException {
		//fail("Not yet implemented");
		driver.get(sAppURL);
		CM_Login(driver,"fbf2883c","Db@10uta","ViewAssignedEventTestCaseCatererStaff"+testCaseNumber);
		verifyHomePageElements(driver,"CatererStaffVerifyElements"+testCaseNumber);
		driver.findElement(By.xpath(prop.getProperty("Link_CatererStaff_Logout"))).click();
		Thread.sleep(1000);
		CM_Login(driver,"fbf2883c","Db@10uta","ViewAssignedEventTestCaseCatererStaff"+testCaseNumber);
		viewAssignedEventStaff(driver,"04/10/2020","12:00AM","ViewAssignedEventTestCaseCatererStaffDate"+testCaseNumber);
		verifyHeadersViewAssignedEvents(driver,eventName,eventDate,eventTime,eventDuration,
				 eventHallName,userLastName,userFirstName,eventViewDetails,"ViewAssignedEventTestCaseCatererStaffHeader"+testCaseNumber);
		WebElement eventTable = driver.findElement(By.xpath(prop.getProperty("Table_EventList_EventTable")));
		int rows = eventTable.findElements(By.tagName("tr")).size();
		try {
			assertFalse(arraysDiff(getAssignedEventsFromDB(rows,"2020-04-10","00:00","Mary","Levine"), getTableContentsFromPage(rows)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath(prop.getProperty("Btn_ViewAssignedEvents_Logout"))).click();
		Thread.sleep(1000);

	}
	
	@Test
	@FileParameters("test/selenium/CatererStaffTestCase2.csv")
	public void test1(int testCaseNumber,String eventID,String eventFirstName,String eventLastName,String eventDate,String eventStartTime,String eventDuration,
			String eventHallName,String eventEstAtnds,String eventName,String eventFoodType,String eventMeal,String eventMealFormality,
			String eventDrinkType,String eventEntItems,String h1,String h2,String h3,String h4,String h5,String h6,String h7,String h8,String h9,
			String h10,String h11,String h12,String h13) throws InterruptedException {
		driver.get(sAppURL);
		CM_Login(driver,"fbf2883c","Db@10uta","ViewAssignedEventTestCaseCatererStaff"+testCaseNumber);
		Thread.sleep(1000);

		viewAssignedEventStaff(driver,"04/10/2020","12:00AM","ViewAssignedEventTestCaseCatererStaffDate"+testCaseNumber);
		driver.findElement(By.xpath(prop.getProperty("Btn_ViewAssignedEvents_ViewDetails1"))).click();
		Thread.sleep(1000);

		verifyEventDetailsHeader(driver,"Header_ViewSelectedEvent_FirstName",h1,
				"Header_ViewSelectedEvent_LastName",h2,"Header_ViewSelectedEvent_Date",h3,
				"Header_ViewSelectedEvent_StartTime",h4,
				"Header_ViewSelectedEvent_Duration",h5,
				"Header_ViewSelectedEvent_HallName",h6,
				"Header_ViewSelectedEvent_EstAtnds",h7,
				"Header_ViewSelectedEvent_EventName",h8,
				"Header_ViewSelectedEvent_FoodType",h9,
				"Header_ViewSelectedEvent_Meal",h10,
				"Header_ViewSelectedEvent_MealFormality",h11,
				"Header_ViewSelectedEvent_DrinkType",h12,
				"Header_ViewSelectedEvent_EntItems",h13,"ViewSelectedEventHeaderTestCase"+testCaseNumber);
		verifyEventContents(driver,"Txt_ViewSelectedEvent_FirstName",eventFirstName,"Txt_ViewSelectedEvent_LastName",eventLastName,
				"Txt_ViewSelectedEvent_Date",eventDate,"Txt_ViewSelectedEvent_StartTime",eventStartTime,"Txt_ViewSelectedEvent_Duration",eventDuration,
				"Txt_ViewSelectedEvent_HallName",eventHallName,"Txt_ViewSelectedEvent_EstAtnds",eventEstAtnds,"Txt_ViewSelectedEvent_EventName",eventName,
				"Txt_ViewSelectedEvent_FoodType",eventFoodType,"Txt_ViewSelectedEvent_Meal",eventMeal,"Txt_ViewSelectedEvent_MealFormality",eventMealFormality,
				"Txt_ViewSelectedEvent_DrinkType",eventDrinkType,"Txt_ViewSelectedEvent_EntItems",eventEntItems,"ViewSelectedEventsDetailsTestCase"+testCaseNumber);
		driver.findElement(By.xpath(prop.getProperty("Btn_ViewSelectedEvent_Logout"))).click();
		Thread.sleep(1000);

		
	}
	
	  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }
	
}
