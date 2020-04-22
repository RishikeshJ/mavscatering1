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
	
	@Test
	@FileParameters("test/selenium/LoginPageTestCase.csv")
	public void test1(int testCaseNumber, String username, String password,String errorMsgs, String usernameError, String passwordError) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,username,password,"loginFunctionTestCase"+testCaseNumber);
		verifyLoginErrorMessages(driver,errorMsgs,usernameError,passwordError,"loginFunctionTestCaseError"+testCaseNumber);
	}
	
	@Test
	@FileParameters("test/selenium/UserPagetTestCase1.csv")
	public void test2(int testCaseNumber,String header,String l1,String l2, String l3) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit&89","loginFunctionTestCase"+testCaseNumber);
		verifyUserHomePageElements(driver,header,l1,l2,l3,"verifyUserHomePageElements"+testCaseNumber);
	}

	@Test
	@FileParameters("test/selenium/UserPagetTestCase2.csv")
	public void test3(int testCaseNumber,String username,String title,String subtitle, String h1,String h2,String h3,String h4,String h5
			,String h6,String h7,String h8,String h9,String h10,String h11,String h12,String h13) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit&89","loginFunctionTestCase"+testCaseNumber);
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

//	@Test
//	@FileParameters("test/selenium/UserPagetTestCase3.csv")
//	public void test4(int testCaseNumber,String title, String h1,String h2,String h3,String h4,String h5
//			,String h6,String h7,String h8,String h9,String h10,String h11,String h12,String h13) throws InterruptedException {
//		//fail("Not yet implemented");
//		
//		driver.get(sAppURL);
//		CM_Login(driver,"bdf252","Bhumit&89","loginFunctionTestCase"+testCaseNumber);
//		verifyEventRequestPageHeaders(driver,title,h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13);
//	}
	
	@Test
	@FileParameters("test/selenium/UserPagetTestCase4.csv")
	public void test5(int testCaseNumber,String FirstName,String LastName,String Date,String Time,String Duraion,String HallName,
			 String estAtnds,String EventName,String foodType,String Meal,String mealFormality,String DrinkType,String entItems,
			 String pastDateErr,String durationErr,String capacityErr,String estAtndsErr,String eventNameErr,String sameDayErr,String sameWeekErr,
			 String timeErr) throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit&89","loginFunctionTestCase"+testCaseNumber);
		//verifyEventRequestPageHeaders(driver,title,h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13);
		validateEventRequestPage(driver, FirstName,LastName,Date,Time,Duraion,HallName,
				 estAtnds,EventName,foodType,Meal,mealFormality,DrinkType,entItems,
				 pastDateErr,durationErr,capacityErr,estAtndsErr,eventNameErr,sameDayErr,sameWeekErr,
				 timeErr,"RequestEventValidation"+testCaseNumber);
	}
	
	@Test
	public void test6() throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit&89","loginFunctionTestCase");
		driver.findElement(By.xpath(prop.getProperty("Link_UserHome_ViewEventSummary"))).click();
		Thread.sleep(1000);
		//verifyViewMyEventSummaryHeaders();
		WebElement eventTable = driver.findElement(By.xpath(prop.getProperty("Table_ViewMyEventSummary_table")));
		int rows = eventTable.findElements(By.tagName("tr")).size();
		assertFalse(arraysDiff(userEventsummaryfromDB(rows,"bdf252","Bhumit","Shah"), getTableContentsFromUserSummaryPage(rows)));

	}

	@Test
	public void test7() throws InterruptedException {
		//fail("Not yet implemented");
		
		driver.get(sAppURL);
		CM_Login(driver,"bdf252","Bhumit&89","loginFunctionTestCase");
		driver.findElement(By.xpath(prop.getProperty("Link_UserHome_ViewEventSummary"))).click();
		Thread.sleep(1000);
		//verifyViewMyEventSummaryHeaders();
		String eventID = driver.findElement(By.xpath(prop.getProperty("Txt_ViewMyEventSummary_eventPrefix")+"2"+prop.getProperty("Txt_ViewMyEventSummary_eventIDCol"))).getText();
		driver.findElement(By.xpath(prop.getProperty("Link_ViewMyEventSummary_view"))).click();
		Thread.sleep(1000);
		verifyViewEventContent(driver,eventID,"ViewUserEventDetails");
		driver.navigate().back();
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
