package selenium;

import static org.junit.Assert.fail;

import java.io.FileInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

import functions.CateringManagementFunctions;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CatererManagerTest extends CateringManagementFunctions{
	
	private StringBuffer verificationErrors = new StringBuffer();
	public String sAppURL, sSharedUIMapPath, testDelay;

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
	@FileParameters("test/selenium/CatererManageTestCase2.csv")
	public void test(int testCaseNumber,String title,String subtitle,String h1,String h2,String h3,String h4,String h5,String h6,String h7,String h8,
			String h9,String h10,String h11,String h12,String h13,
			String username,String password, String role,String utaid,String firstname, String lastname, String phone,String email,String streetnumber,
			String streetname,String city,String state, String zipcode,
			String errMsgs,String usernameErr,String pwdErr, String roleErr,String utaidErr,String firstnameErr,String lastnameErr,String phoneErr, String emailErr,
			String streetnumberErr,String streetnameErr,String cityErr,String stateErr,String zipcodeErr) {
		//go to website
		//click register
		//validations one at a time?
		driver.get(sAppURL);
		driver.findElement(By.xpath(prop.getProperty("Btn_login_Register"))).click();
		//validateRegisterUser()
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
	public void test1(int testCaseNumber, String username, String password,String errorMsgs, String usernameError, String passwordError) {
		//fail("Not yet implemented");
		driver.get(sAppURL);
		CM_Login(driver,username,password,"loginFunctionTestCase"+testCaseNumber);
		verifyLoginErrorMessages(driver,errorMsgs,usernameError,passwordError,"loginFunctionTestCaseError"+testCaseNumber);
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
