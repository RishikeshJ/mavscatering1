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
public class CatererManagerTest extends CateringManagementFunctions{
	
	private StringBuffer verificationErrors = new StringBuffer();
	public String sAppURL, sSharedUIMapPath, testDelay;
	
	public String[][] getTableContentsFromCMSummaryPage(int rows){

		String [][] eventArray = new String[rows-1][9];
		for (int i=0; i<rows-1; i++) {
			eventArray[i][0]=  driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_CatererManagerEventSummary_eventIDCol"))).getText();
			eventArray[i][1] = driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_CatererManagerEventSummary_eventNameCol"))).getText();
			eventArray[i][2] = driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_CatererManagerEventSummary_durationCol"))).getText();
			eventArray[i][3] = driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_CatererManagerEventSummary_firstnameCol"))).getText();
			eventArray[i][4] = driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_CatererManagerEventSummary_lastnameCol"))).getText();
			eventArray[i][5] = driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_CatererManagerEventSummary_startTimeCol"))).getText();
			eventArray[i][6] = driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_CatererManagerEventSummary_hallNameCol"))).getText();
			eventArray[i][7] = driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_CatererManagerEventSummary_dateCol"))).getText();
			eventArray[i][8] = driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+
								prop.getProperty("Txt_CatererManagerEventSummary_estAtndsCol"))).getText();

			
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
	
	public void verifyLinks(int rows) throws InterruptedException {
		for(int i = 0;i<1;i++) {
			System.out.println("Link: "+prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+prop.getProperty("link_CatererManagerEventSummary_viewEvent"));
			 if(driver.findElements(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+prop.getProperty("link_CatererManagerEventSummary_viewEvent"))).size()>0) {
				 driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+prop.getProperty("link_CatererManagerEventSummary_viewEvent"))).click();
				 Thread.sleep(1000);

				 driver.navigate().back();  
				 Thread.sleep(1000);

			 }
			 if(driver.findElements(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+prop.getProperty("link_CatererManagerEventSummary_assignEvent"))).size()>0) {
				 driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+prop.getProperty("link_CatererManagerEventSummary_assignEvent"))).click();
				 Thread.sleep(1000);

				 driver.navigate().back();  
				 Thread.sleep(1000);

			 }
			 if(driver.findElements(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+prop.getProperty("link_CatererManagerEventSummary_modifyEvent"))).size()>0) {
				 driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+(i+2)+prop.getProperty("link_CatererManagerEventSummary_modifyEvent"))).click();
				 Thread.sleep(1000);

				 driver.navigate().back();  
				 Thread.sleep(1000);

			 }

		}
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
	@FileParameters("test/selenium/CatererManageTestCase1.csv")
	public void test0(int testCaseNumber,String username,String password,String role,String UTAid, String Firstname,String LastName,String phone,
			String email,String sno,String sname,String city,String state,String zipcode) throws InterruptedException {

		driver.get(sAppURL);
		driver.findElement(By.xpath(prop.getProperty("Btn_login_Register"))).click();
		Thread.sleep(1000);
		RegisterCatererManager(driver,username,
		 password,
		 role,
		 UTAid,
		 Firstname,
		 LastName, 
		 phone, 
		 email, 
		 sno,
		 sname, 
		 city,
		 state, 
		 zipcode, "CMRegistraion");

		
	}

	@Test
	@FileParameters("test/selenium/CatererManageTestCase2.csv")
	public void test1(int testCaseNumber,String title,String subtitle,String h1,String h2,String h3,String h4,String h5,String h6,String h7,String h8,
			String h9,String h10,String h11,String h12,String h13,
			String username,String password, String role,String utaid,String firstname, String lastname, String phone,String email,String streetnumber,
			String streetname,String city,String state, String zipcode,
			String errMsgs,String usernameErr,String pwdErr, String roleErr,String utaidErr,String firstnameErr,String lastnameErr,String phoneErr, String emailErr,
			String streetnumberErr,String streetnameErr,String cityErr,String stateErr,String zipcodeErr) throws InterruptedException {
		driver.get(sAppURL);
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("Btn_login_Register"))).click();
		Thread.sleep(1000);

		verifyRegisterPageHeaders(driver,"Header_Register_Title",title,"Header_Register_Subtitle",subtitle,
				"Header_Register_Username",h1,
				"Header_Register_Password",h2,
				"Header_Register_Role",h3,
				"Header_Register_UTAID",h4,
				"Header_Register_FirstName",h5,
				"Header_Register_LastName",h6,
				"Header_Register_Phone",h7,
				"Header_Register_Email",h8,
				"Header_Register_StreetNumber",h9,
				"Header_Register_StreetName",h10,
				"Header_Register_City",h11,
				"Header_Register_State",h12,
				"Header_Register_ZipCode",h13,"RegisterPageHeaderTestCase"+testCaseNumber);
		//driver.findElement(By.xpath(prop.getProperty("Header_Register_Title"))).click();
		Thread.sleep(1000);
		validateRegistrationFields(driver,username,usernameErr,
				 password,pwdErr,
				 role, roleErr,
				 utaid,utaidErr,
				 firstname,firstnameErr,
				 lastname, lastnameErr,
				 phone, phoneErr,
				 email, emailErr,
				 streetnumber, streetnumberErr,
				 streetname, streetnameErr,
				 city, cityErr,
				 state, stateErr,
				 zipcode, zipcodeErr,errMsgs,"RegisterPageValidationTestCase"+testCaseNumber);
		
	}
	
	@Test
	@FileParameters("test/selenium/LoginPageTestCase.csv")
	public void test2(int testCaseNumber, String username, String password,String errorMsgs, String usernameError, String passwordError) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,username,password,"loginFunctionTestCase"+testCaseNumber);
		verifyLoginErrorMessages(driver,errorMsgs,usernameError,passwordError,"loginFunctionTestCaseError"+testCaseNumber);
	}
	
	@Test
	@FileParameters("test/selenium/CatererManagerHomeTestCase.csv")
	public void test3(int testCaseNumber,String Title,String subtitle,String viewSummary,String viewProfile) throws InterruptedException {
		driver.get(sAppURL);
		CM_Login(driver,"bxs5836","Asdf!234","loginFunctionTestCase2"+testCaseNumber);
		verifyCatererManagerHomeElements(driver,Title,subtitle,viewSummary,viewProfile,"CatererManagerHomeElementTestCase"+testCaseNumber);
	}

	@Test
	@FileParameters("test/selenium/CatererManagerEventSummaryTestCase.csv")
	public void test4(int testCaseNumber,String eventID,String eventName,String duration,String firstName,String lastName,String startTime,String hallName,
			 String eventDate, String estAtnds) throws InterruptedException {
		driver.get(sAppURL);
		Thread.sleep(1000);

		CM_Login(driver,"bxs5836","Asdf!234","loginFunctionTestCase2"+testCaseNumber);
		driver.findElement(By.xpath(prop.getProperty("Link_CatererManagerHome_ViewEventSummary"))).click();
		Thread.sleep(1000);

		verifyCMSummaryHeaders(driver,eventID,eventName,duration,firstName,lastName,startTime,hallName,
				 eventDate,estAtnds,"CatererManagerEventSummaryTestCaseHeader"+testCaseNumber);
		WebElement eventTable = driver.findElement(By.xpath(prop.getProperty("Table_CatererManagerEventSummary_Table")));
		int rows = eventTable.findElements(By.tagName("tr")).size();
		assertFalse(arraysDiff(getEventSummary(rows), getTableContentsFromCMSummaryPage(rows)));
		verifyLinks(rows);	
		

	}
	
	@Test
	@FileParameters("test/selenium/CatererManagerEventSummaryDetailsTestCase.csv")
	public void test5(int testCaseNumber,String exph1, String exph2, String exph3, String exph4, String exph5, String exph6, String exph7, String exph8, String exph9,
			 String exph10, String exph11, String exph12, String exph13, String exph14, String eventID,String eventFirstName,String eventLastName,String eventDate,String eventStartTime,String eventDuration,
			 String eventHallName,String eventEstAtnds,String eventName,String eventFoodType,String eventMeal,String eventMealFormality,
			 String eventDrinkType,String eventEntItems) throws InterruptedException {
		driver.get(sAppURL);
		CM_Login(driver,"bxs5836","Asdf!234","loginFunctionTestCase3"+testCaseNumber);
		driver.findElement(By.xpath(prop.getProperty("Link_CatererManagerHome_ViewEventSummary"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+"2"+prop.getProperty("link_CatererManagerEventSummary_viewEvent"))).click();
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
				 "Header_CatererManagerEventSummaryDetails_entItems",exph14,"CatererManagerEventSummaryDetailsHeadersTestCase"+testCaseNumber);
		verifyEventSummaryContents(driver,"Txt_CatererManagerEventSummaryDetails_eventID", eventID,
				 "Txt_CatererManagerEventSummaryDetails_firstName", eventFirstName,
				 "Txt_CatererManagerEventSummaryDetails_lastName", eventLastName,
				 "Txt_CatererManagerEventSummaryDetails_eventDate", eventDate,
				 "Txt_CatererManagerEventSummaryDetails_startTime", eventStartTime,
				 "Txt_CatererManagerEventSummaryDetails_duration", eventDuration,
				 "Txt_CatererManagerEventSummaryDetails_hallName", eventHallName,
				 "Txt_CatererManagerEventSummaryDetails_estAtnds", eventEstAtnds,
				 "Txt_CatererManagerEventSummaryDetails_eventName", eventName,
				 "Txt_CatererManagerEventSummaryDetails_foodType", eventFoodType,
				 "Txt_CatererManagerEventSummaryDetails_meal", eventMeal,
				 "Txt_CatererManagerEventSummaryDetails_mealFormality", eventMealFormality,
				 "Txt_CatererManagerEventSummaryDetails_drinkType", eventDrinkType,
				 "Txt_CatererManagerEventSummaryDetails_entItems", eventEntItems,"CatererManagerEventSummaryDetailsTestCase"+testCaseNumber);
	}
	
	@Test
	public void test6() throws InterruptedException {
		driver.get(sAppURL);
		CM_Login(driver,"bxs5836","Asdf!234","loginFunctionTestCase3");
		driver.findElement(By.xpath(prop.getProperty("Link_CatererManagerHome_ViewEventSummary"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+"2"+prop.getProperty("link_CatererManagerEventSummary_assignEvent"))).click();
		assignStaff(driver,"Mary","Levine","AssigningStaff");

	}

	
	@Test
	@FileParameters("test/selenium/CatererManagerAssignStaffTestCase.csv")
	public void test7(int testCaseNumber,String firstname,String lastname,String err,String staffErr) throws InterruptedException {
		driver.get(sAppURL);
		CM_Login(driver,"bxs5836","Asdf!234","loginFunctionTestCase3"+testCaseNumber);
		driver.findElement(By.xpath(prop.getProperty("Link_CatererManagerHome_ViewEventSummary"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(prop.getProperty("Txt_CatererManagerEventSummary_eventPrefix")+"2"+prop.getProperty("link_CatererManagerEventSummary_assignEvent"))).click();
		verifyAssignStaffPageElements(driver,"Mavs Catering System","Assign Staff","First Name (*):","Last Name (*):","CatererManagerAssignStaffTestCase"+testCaseNumber);
		validateAssignStaffError(driver,firstname,lastname,err,staffErr,"CatererManagerAssignStaffDetailsTestCase"+testCaseNumber);
		driver.findElement(By.xpath(prop.getProperty("link_CMAssignStaff_logout"))).click();
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
