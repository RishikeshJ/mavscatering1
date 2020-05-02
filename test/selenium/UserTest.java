package selenium;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import functions.CateringManagementFunctions;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest extends CateringManagementFunctions{
	
	private StringBuffer verificationErrors = new StringBuffer();
	public String sAppURL, sSharedUIMapPath, testDelay;
	
	public String[][] getTableContentsFromUserSummaryPage(int rows){

		String [][] eventArray = new String[rows-1][9];
		for (int i=0; i<rows-1; i++) {
			eventArray[i][0]=  driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewMyEventSummary_eventIDCol"))).getText();
			eventArray[i][1] = driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewMyEventSummary_eventNameCol"))).getText();
			eventArray[i][2] = driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewMyEventSummary_durationCol"))).getText();
			eventArray[i][3] = driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewMyEventSummary_firstNameCol"))).getText();
			eventArray[i][4] = driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewMyEventSummary_lastNameCol"))).getText();
			eventArray[i][5] = driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewMyEventSummary_startTimeCol"))).getText();
			eventArray[i][6] = driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewMyEventSummary_hallNameCol"))).getText();
			eventArray[i][7] = driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewMyEventSummary_eventDateCol"))).getText();
			eventArray[i][8] = driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_ViewMyEventSummary_estAtndsCol"))).getText();

			
			System.out.println("Table:");
			System.out.println(i +" "+eventArray[i][0]+" "+eventArray[i][1]+" "+eventArray[i][2]+" "+eventArray[i][3]+" "+eventArray[i][4]+" "+eventArray[i][5]+" "
					+eventArray[i][6]+" "+eventArray[i][7]+" "+eventArray[i][8]);

		}
		//System.out.println("IS EMPTY? "+eventArray.length);
	  return eventArray;

	}

	private Boolean arraysDiff (String [][] array1, String [][] array2) { // this method compares the contents of the two tables
		  Boolean diff= false || (array1.length!=array2.length);
		  for (int i=0;i<array1.length && !diff;i++) {
			 diff  = !array1[i][0].equals(array2[i][0]) || !array1[i][1].equals(array2[i][1]) || 
					 !array1[i][2].equals(array2[i][2]) || !array1[i][3].equals(array2[i][3]) ||
					 !array1[i][4].equals(array2[i][4]) || !array1[i][5].equals(array2[i][5]) ||
					 !array1[i][6].equals(array2[i][6]) || !array1[i][7].equals(array2[i][7]) ||
					 !array1[i][8].equals(array2[i][8]);
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
	
	//Login Validations
	@Test
	@FileParameters("test/selenium/LoginPageTestCase.csv")
	public void test1(int testCaseNumber, String username, String password,String errorMsgs, String usernameError, String passwordError) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,username,password,"loginFunctionTestCase"+testCaseNumber);
		verifyLoginErrorMessages(driver,errorMsgs,usernameError,passwordError,"loginFunctionTestCaseError"+testCaseNumber);
	}
	
	//verifying user home page elements
	@Test
	@FileParameters("test/selenium/UserPagetTestCase1.csv")
	public void test2(int testCaseNumber,String header,String l1,String l2, String l3) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit!23","loginFunctionTestCase"+testCaseNumber);
		verifyUserHomePageElements(driver,header,l1,l2,l3,"verifyUserHomePageElements"+testCaseNumber);
	}

	//verifying profile headers and contents
	@Test
	@FileParameters("test/selenium/UserPagetTestCase2.csv")
	public void test3(int testCaseNumber,String username,String title,String subtitle, String h1,String h2,String h3,String h4,String h5
			,String h6,String h7,String h8,String h9,String h10,String h11,String h12,String h13) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit!23","loginFunctionTestCase"+testCaseNumber);
		driver.findElement(By.xpath(prop.getProperty("Link_UserHome_ViewProfile"))).click();
		Thread.sleep(1000);
		verifyRegisterPageHeaders(driver,"Header_ViewMyProfile_header",title,"Header2_ViewMyProfile_header2",subtitle,
				"Header_ViewMyProfile_Username",h1,
				"Header_ViewMyProfile_Password",h2,
				"Header_ViewMyProfile_Role",h3,
				"Header_ViewMyProfile_UTAid",h4,
				"Header_ViewMyProfile_FirstName",h5,
				"Header_ViewMyProfile_LastName",h6,
				"Header_ViewMyProfile_Phone",h7,
				"Header_ViewMyProfile_Email",h8,
				"Header_ViewMyProfile_StreetNumber",h9,
				"Header_ViewMyProfile_StreetName",h10,
				"Header_ViewMyProfile_City",h11,
				"Header_ViewMyProfile_State",h12,
				"Header_ViewMyProfile_Zipcode",h13,"verifyViewMyProfileHeaders"+testCaseNumber);

		verifyViewMyProfileContent(driver,username,"verifyViewMyProfileContent"+testCaseNumber);

	}
//
//	//Request event page elements verification
	@Test
	@FileParameters("test/selenium/UserPagetTestCase3.csv")
	public void test4(int testCaseNumber,String title, String h1,String h2,String h3,String h4,String h5
			,String h6,String h7,String h8,String h9,String h10,String h11,String h12,String h13) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit!23","loginFunctionTestCase"+testCaseNumber);
		driver.findElement(By.xpath(prop.getProperty("Link_UserHome_RequestEvent"))).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("btn_EventRequest_Next"))).click();
		Thread.sleep(1000);
		verifyEventRequestPageHeaders(driver,title,h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13);
		verifyEventRequestPageElements(driver);
	}
	
	//request event validation
	@Test
	@FileParameters("test/selenium/UserPagetTestCase4.csv")
	public void test5(int testCaseNumber,String FirstName,String LastName,String Date,String Time,String Duraion,String HallName,
			 String estAtnds,String EventName,String foodType,String Meal,String mealFormality,String DrinkType,String entItems,
			 String pastDateErr,String durationErr,String capacityErr,String estAtndsErr,String eventNameErr,String sameDayErr,String sameWeekErr,
			 String timeErr) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit!23","loginFunctionTestCase"+testCaseNumber);
		//verifyEventRequestPageHeaders(driver,title,h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13);
		validateEventRequestPage(driver, FirstName,LastName,Date,Time,Duraion,HallName,
				 estAtnds,EventName,foodType,Meal,mealFormality,DrinkType,entItems,
				 pastDateErr,durationErr,capacityErr,estAtndsErr,eventNameErr,sameDayErr,sameWeekErr,
				 timeErr,"RequestEventValidation"+testCaseNumber);
	}
	
	//registerEvent and Paydeposit Valiadations
	@Test
	@FileParameters("test/selenium/UserPagetTestCase7.csv")
	public void test6(int testCaseNumber,String FirstName,String LastName,String Date,String Time,String Duration,String HallName,String estAtnds,
			String EventName,String foodType,String Meal,String mealFormality,String DrinkType,String entItems,String Pending,String username,String CCnum,
			String CCPin,String expDate,String Amount,String invalidCCNum,String invalidCCPin,String invalidexpDate) throws InterruptedException {
		driver.get(sAppURL);
		CM_Login(driver,"bxs5835","Bhumit&89","loginFunctionTestCase");

		registerEvent(driver,FirstName,LastName,Date,Time,Duration,HallName,
				 estAtnds,EventName,foodType,Meal,mealFormality,DrinkType,entItems,Pending,
				 username,"N/A","N/A","N/A",Amount,"RegisterEvent"+testCaseNumber);
		payDepositValidation(driver,CCnum,CCPin,expDate,Amount,invalidCCNum,invalidCCPin,
				 invalidexpDate,"PayDepositValidation"+testCaseNumber);

	}
	
	@Test
	@FileParameters("test/selenium/UserPagetTestCase9.csv")
	public void test7(int testCaseNumber,String eventID,String eventFirstName,String eventLastName,String eventDate,String eventStartTime,String eventDuration,
			 String eventHallName,String eventEstAtnds,String eventName,String eventFoodType,String eventMeal,String eventMealFormality,
			 String eventDrinkType,String eventEntItems) throws InterruptedException {
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit!23","loginFunctionTestCase");
		driver.findElement(By.xpath(prop.getProperty("Link_UserHome_ViewEventSummary"))).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath(prop.getProperty("Link_ViewMyEventSummary_modify"))).click();
		Thread.sleep(1000);
		
		ModifyEventDetailsUser(driver,eventID,eventFirstName,eventLastName,eventDate,eventStartTime,eventDuration,
		 eventHallName,eventEstAtnds,eventName,eventFoodType,eventMeal,eventMealFormality,
		 eventDrinkType,eventEntItems,"ModifyEventDetailsUser"+testCaseNumber);


	}
	
	
	//Paydeposit
	@Test
	@FileParameters("test/selenium/UserPagetTestCase8.csv")
	public void test7(int testCaseNumber,String FirstName,String LastName,String Date,String Time,String Duration,String HallName,String estAtnds,
			String EventName,String foodType,String Meal,String mealFormality,String DrinkType,String entItems,String Pending,String username,String CCnum,
			String CCPin,String expDate,String Amount) throws InterruptedException {
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit!23","loginFunctionTestCase");

		registerEvent(driver,FirstName,LastName,Date,Time,Duration,HallName,
				 estAtnds,EventName,foodType,Meal,mealFormality,DrinkType,entItems,Pending,
				 username,"N/A","N/A","N/A",Amount,"RegisterEvent"+testCaseNumber);
		payDeposit(driver,CCnum,CCPin,expDate,"PayDeposit"+testCaseNumber);

	}

	//verifying event summary page
	@Test
	@FileParameters("test/selenium/UserPagetTestCase5.csv")
	public void test8(int testCaseNumber,String eventID,String eventName,String duration,String firstName,String lastName,
			 String startTime,String hallName,String eventDate,String estAtnds) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit!23","loginFunctionTestCase");
		
		driver.findElement(By.xpath(prop.getProperty("Link_UserHome_ViewEventSummary"))).click();
		Thread.sleep(1000);
		verifyViewMyEventSummaryHeaders(driver,eventID,eventName,duration,firstName,lastName,
				 startTime,hallName,eventDate,estAtnds,"verifyViewMyEventSummaryHeaders"+testCaseNumber);
		WebElement eventTable = driver.findElement(By.xpath(prop.getProperty("Table_ViewMyEventSummary_table")));
		int rows = eventTable.findElements(By.tagName("tr")).size();
		assertFalse(arraysDiff(userEventsummaryfromDB(rows,"bdf252","Bhumit","Shah"), getTableContentsFromUserSummaryPage(rows)));

	}
	
	//Modifying Profile User
	@Test
	@FileParameters("test/selenium/UserPagetTestCase10.csv")
	public void test9(int testCaseNumber,String firstNameErr,String firstName,String lastName,String Phone,String email,String StreetNo,String StreetName,
			 String City,String State,String Zipcode,String err,String err1) throws InterruptedException {
		//fail("Not yet implemented");
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit!23","UserHomePageLogin"+testCaseNumber);
		driver.findElement(By.xpath(prop.getProperty("Link_UserHome_ViewProfile"))).click();
		Thread.sleep(1000);
		modifyProfile(driver,firstNameErr,firstName,lastName,Phone,email,StreetNo,StreetName,
				 City,State,Zipcode,err,err1,"ModifyProfileUser"+testCaseNumber);
		
	}


	//verifying view selected event
	@Test
	@FileParameters("test/selenium/UserPagetTestCase6.csv")
	public void test10(int testCaseNumber,String exph1, String exph2, String exph3, String exph4, String exph5, String exph6, String exph7, String exph8, String exph9,
			 String exph10, String exph11, String exph12, String exph13, String exph14) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit!23","loginFunctionTestCase");
		driver.findElement(By.xpath(prop.getProperty("Link_UserHome_ViewEventSummary"))).click();
		Thread.sleep(1000);

		String eventID = driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyEventSummary_eventPrefix")+"2"+prop.getProperty("Txt_ViewMyEventSummary_eventIDCol"))).getText();
		driver.findElement(By.xpath(prop.getProperty("Link_ViewMyEventSummary_view"))).click();
		Thread.sleep(1000);
		//Same as event details page for caterer manager
		verifyEventSummaryDetailsHeader(driver,"Header_CatererManagerEventSummaryDetails_eventID",exph1,
				 "Header_CatererManagerEventSummaryDetails_lastName",exph2,
				 "Header_CatererManagerEventSummaryDetails_firstName",exph3,
				 "Header_CatererManagerEventSummaryDetails_eventDate",exph4,
				 "Header_CatererManagerEventSummaryDetails_startTime",exph5,
				 "Header_CatererManagerEventSummaryDetails_duration",exph6,
				 "Header_CatererManagerEventSummaryDetails_hallName",exph7,
				 "Header_CatererManagerEventSummaryDetails_estAtnds",exph8,
				 "Header_CatererManagerEventSummaryDetails_eventName",exph9,
				 "Header_CatererManagerEventSummaryDetails_foodType",exph10,
				 "Header_CatererManagerEventSummaryDetails_meal",exph11,
				 "Header_CatererManagerEventSummaryDetails_mealFormality",exph12,
				 "Header_CatererManagerEventSummaryDetails_drinkType",exph13,
				 "Header_CatererManagerEventSummaryDetails_entItems",exph14,"verifyEventSummaryDetailsHeader"+testCaseNumber);
		verifyViewEventContent(driver,eventID,"ViewUserEventDetails");
		driver.navigate().back();
		Thread.sleep(1000);
		
		driver.findElement(By.xpath(prop.getProperty("Link_ViewMyEventSummary_logout"))).click();
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
