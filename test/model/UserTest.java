package model;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class UserTest {
	
	User user;
	UserErrorMsgs UerrMsgs;

	@Before
	public void setUp() throws Exception {
		user = new User();
		UerrMsgs = new UserErrorMsgs();
	}

	@Test
	@FileParameters("test/model/UserTestCases.csv")
	public void test(int testCaseNo,String action,String username,String pwd, String fname, String lname,String role,String utaid,String phone,String email,
			String streetnumber,String streetname,String city, String state, String zipcode, String err,String unameerr,String pwderr,String fnameerr, String lnameerr,
			String roleerr, String utaiderr, String phoneerr, String emailerr, String snumbererr, String snameerr,String cityerr,String stateerr, String ziperr,
			String a1,String a2) {
		user.setUser(username, pwd, lname, fname, role, utaid, phone, email, streetnumber, streetname, city, state, zipcode);
		user.validateUser(action, user, UerrMsgs);
		UerrMsgs.setErrorMsgs();
		assertTrue(err.equals(UerrMsgs.getErrorMsgs()));
		assertTrue(unameerr.equals(UerrMsgs.getUsernameError()));
		assertTrue(pwderr.equals(UerrMsgs.getPasswordError()));
		assertTrue(fnameerr.equals(UerrMsgs.getFirstNameError()));
		assertTrue(lnameerr.equals(UerrMsgs.getLastNameError()));
		assertTrue(roleerr.equals(UerrMsgs.getRoleError()));
		assertTrue(utaiderr.equals(UerrMsgs.getUtaIdError()));
		assertTrue(phoneerr.equals(UerrMsgs.getPhoneError()));
		assertTrue(emailerr.equals(UerrMsgs.getEmailError()));
		assertTrue(snumbererr.equals(UerrMsgs.getStreetNumberError()));
		assertTrue(snameerr.equals(UerrMsgs.getStreetNameError()));
		assertTrue(cityerr.equals(UerrMsgs.getCityError()));
		assertTrue(stateerr.equals(UerrMsgs.getStateError()));
		assertTrue(ziperr.equals(UerrMsgs.getZipcodeError()));

	}

}
