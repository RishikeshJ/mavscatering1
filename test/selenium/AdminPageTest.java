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

public class AdminPageTest extends CateringManagementFunctions{
	
	private StringBuffer verificationErrors = new StringBuffer();
	public String sAppURL, sSharedUIMapPath, testDelay,username,password;
	
	public String[][] getTableContentsFromPage(int rows){

		String [][] userArray = new String[rows-1][4];
		for (int i=0; i<rows-1; i++) {
			userArray[i][0]=  driver.findElement(By.xpath(prop.getProperty("Txt_seachUserResults_prefix")+(i+2)+
								prop.getProperty("Txt_searchUserResults_LastNameCol"))).getText();
			userArray[i][1] = driver.findElement(By.xpath(prop.getProperty("Txt_seachUserResults_prefix")+(i+2)+
								prop.getProperty("Txt_searchUserResults_FirstNameCol"))).getText();
			userArray[i][2] = driver.findElement(By.xpath(prop.getProperty("Txt_seachUserResults_prefix")+(i+2)+
								prop.getProperty("Txt_searchUserResults_UserNameCol"))).getText();
			userArray[i][3] = driver.findElement(By.xpath(prop.getProperty("Txt_seachUserResults_prefix")+(i+2)+
								prop.getProperty("Txt_searchUserResults_RoleCol"))).getText();
		
			System.out.println("Table:");
			System.out.println(userArray[i][0]+userArray[i][1]+userArray[i][2]+userArray[i][3]);

		}
		System.out.println("IS EMPTY? "+userArray.length);
	  return userArray;

	}
	
	private Boolean arraysDiff (String [][] array1, String [][] array2) { // this method compares the contents of the two tables
		  Boolean diff= false || (array1.length!=array2.length);
		  for (int i=0;i<array1.length && !diff;i++) {
			 diff  = !array1[i][0].equals(array2[i][0]) || !array1[i][1].equals(array2[i][1]) || 
					 !array1[i][2].equals(array2[i][2]) || !array1[i][3].equals(array2[i][3]);
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
		@FileParameters("test/selenium/AdminHomePage1.csv")
		public void test1(int testCaseNumber, String title, String logout,String searchUser, String viewProfile) throws InterruptedException {
			//fail("Not yet implemented");
			driver.get(sAppURL);
			CM_Login(driver,"axk987","Bhumit!23","AdminHomePageLogin"+testCaseNumber);
			verifyAdminHomePageElements(driver,title,searchUser,viewProfile,logout,"AdminHomePageVerify");
		}
	
		@Test
		@FileParameters("test/selenium/AdminHomePage2.csv")
		public void test2(int testCaseNumber,String title,String logout, String hLastName) throws InterruptedException {
			//fail("Not yet implemented");
			driver.get(sAppURL);
			CM_Login(driver,"axk987","Bhumit!23","AdminHomePageLogin"+testCaseNumber);
			verifySearchUserPageElements(driver,title,logout,hLastName,"SearchUserVerification"+testCaseNumber);
			
		}

		@Test
		@FileParameters("test/selenium/AdminHomePage3.csv")
		public void test3(int testCaseNumber,String lastName,String errMsg,String lastNameErr) throws InterruptedException {
			
			driver.get(sAppURL);
			CM_Login(driver,"axk987","Bhumit!23","AdminHomePageLogin"+testCaseNumber);
			driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_Search_for_User"))).click();
			Thread.sleep(1000);
			validateSearchUser(driver,lastName,errMsg,lastNameErr,"SearchUserLastNameValidations"+testCaseNumber);

			
		}
		
		@Test
		@FileParameters("test/selenium/AdminHomePage4.csv")
		public void test4(int testCaseNumber,String lastNameHeader,String firstNameHeader,String userNameHeader,String roleHeader,String lastName) throws InterruptedException {
			//fail("Not yet implemented");
			driver.get(sAppURL);
			CM_Login(driver,"axk987","Bhumit!23","AdminHomePageLogin"+testCaseNumber);
			driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_Search_for_User"))).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(prop.getProperty("Txt_AdminSearchUser_UsersLastname"))).clear();
			driver.findElement(By.xpath(prop.getProperty("Txt_AdminSearchUser_UsersLastname"))).sendKeys(lastName);
			driver.findElement(By.xpath(prop.getProperty("Link_AdminSearchUser_submit"))).click();
			Thread.sleep(1000);

			verifySearchUserResultsHeaders(driver,lastNameHeader,firstNameHeader,userNameHeader,roleHeader,"verifySearchUserResultsHeaders"+testCaseNumber);
			WebElement userTable = driver.findElement(By.xpath(prop.getProperty("Table_SearchUser_table")));
			int rows = userTable.findElements(By.tagName("tr")).size();
			try {
				assertFalse(arraysDiff(getUsersFromDB(rows,lastName), getTableContentsFromPage(rows)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread.sleep(1000);
		}
		
		@Test
		@FileParameters("test/selenium/AdminHomePage5.csv")
		public void test5(int testCaseNumber,String title,String subtitle,String h1,String h2,String h3,String h4,String h5,String h6,String h7,String h8,
				String h9,String h10,String h11,String h12,String lastName) throws InterruptedException {
			//fail("Not yet implemented");
			driver.get(sAppURL);
			CM_Login(driver,"axk987","Bhumit!23","AdminHomePageLogin"+testCaseNumber);
			driver.findElement(By.xpath(prop.getProperty("Link_AdminHomePage_Search_for_User"))).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(prop.getProperty("Txt_AdminSearchUser_UsersLastname"))).clear();
			driver.findElement(By.xpath(prop.getProperty("Txt_AdminSearchUser_UsersLastname"))).sendKeys(lastName);
			driver.findElement(By.xpath(prop.getProperty("Link_AdminSearchUser_submit"))).click();
			Thread.sleep(1000);
			String username = driver.findElement(By.xpath(prop.getProperty("Txt_seachUserResults_prefix")+"2"+prop.getProperty("Txt_searchUserResults_UserNameCol"))).getText();
			//System.out.println("Username: "+ username);
			driver.findElement(By.xpath(prop.getProperty("Link_ViewProfile_view1"))).click();
			Thread.sleep(1000);
			verifyViewProfilePageHeaders(driver,"Header_ViewProfile_header",title,"Header2_ViewProfile_header2",subtitle,
					"Header_ViewProfile_Username",h1,
					"Header_ViewProfile_Role",h2,
					"Header_ViewProfile_UTAid",h3,
					"Header_ViewProfile_FirstName",h4,
					"Header_ViewProfile_LastName",h5,
					"Header_ViewProfile_Phone",h6,
					"Header_ViewProfile_Email",h7,
					"Header_ViewProfile_StreetNumber",h8,
					"Header_ViewProfile_StreetName",h9,
					"Header_ViewProfile_City",h10,
					"Header_ViewProfile_State",h11,
					"Header_ViewProfile_Zipcode",h12,
					"ViewProfilePageHeaderTestCase"+testCaseNumber);
			verifyViewUserContent(driver,username,"VerifyViewUserContent"+testCaseNumber);
			driver.findElement(By.xpath(prop.getProperty("Link_ViewProfile_Logout"))).click();
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
