package selenium;

import static org.junit.Assert.fail;

import java.io.FileInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Properties;

import functions.CateringManagementFunctions;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class LoginPageTest extends CateringManagementFunctions{
	
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
	@FileParameters("test/selenium/LoginPageTestCase.csv")
	public void test(int testCaseNumber, String username, String password,String errorMsgs, String usernameError, String passwordError) {
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
