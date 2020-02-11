package model;

import model.UserErrorMsgs;
import data.UserDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String lastname;
	private String firstname;
	private String role;
	private String utaid;
	private String phone;
	private String email;
	private String streetnumber;
	private String streetname;
	private String city;
	private String state;
	private String zipcode;
	
	public User() {
		this.username = "";
		this.password = "";
		this.lastname = "";
		this.firstname = "";
		this.role = "";
		this.utaid = "";
		this.phone = "";
		this.email = "";
		this.streetnumber = "";
		this.streetname = "";
		this.city = "";
		this.state = "";
		this.zipcode = "";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUtaid() {
		return utaid;
	}
	public void setUtaid(String utaid) {
		this.utaid = utaid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStreetnumber() {
		return streetnumber;
	}
	public void setStreetnumber(String streetnumber) {
		this.streetnumber = streetnumber;
	}
	public String getStreetname() {
		return streetname;
	}
	public void setStreetname(String streetname) {
		this.streetname = streetname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public static boolean isStringOnlyAlphabet(String str) 
	{ 
	    for (int i = 0; i < str.length(); i++) { 
	        char ch = str.charAt(i); 
	        if ((!(ch >= 'A' && ch <= 'Z')) 
	            && (!(ch >= 'a' && ch <= 'z'))) { 
	            return false; 
	        } 
	    } 
	    return true; 
	}
	
	public static boolean isStringOnlyNumber(String str) 
	{ 
	    for (int i = 0; i < str.length(); i++) { 
	        char ch = str.charAt(i); 
	        if ((!(ch >= '0' && ch <= '9'))) { 
	            return false; 
	        } 
	    } 
	    return true; 
	}
	
	public void validateUser(String action, User user,UserErrorMsgs errorMsgs) {
		if(action.equals("searchUser")) {
			errorMsgs.setLastNameError(validateLastname(user.getLastname()));
		}
		else if(action.equals("registerUser")) {
			//System.out.println(user.getRole());
			errorMsgs.setFirstNameError(validateFirstname(user.getFirstname()));
			errorMsgs.setLastNameError(validateLastname(user.getLastname()));
			errorMsgs.setUtaIdError(validateutaid(user.getUtaid()));
			errorMsgs.setUsernameError(validateusername(user.getUsername()));
			errorMsgs.setPhoneError(validatePhone(user.getPhone()));
			errorMsgs.setStreetNumberError(validateStreetnumber(user.getStreetnumber()));
			errorMsgs.setStreetNameError(validateStreetname(user.getStreetname()));
			errorMsgs.setZipcodeError(validatezipcode(user.getZipcode()));
			errorMsgs.setCityError(validatecity(user.getCity()));
			errorMsgs.setStateError(validatestate(user.getState()));
			errorMsgs.setRoleError(validateRole(user.getRole()));
			errorMsgs.setPasswordError(validatePassword(user.getPassword()));
		}
		else if(action.equals("modifyUserProfile")) {
			errorMsgs.setRoleError(validateRole(user.getRole()));
		}
	}
	
	public String validateFirstname(String firstname) {
		String error = "";
		
		//System.out.println("In validate fname");
		if(firstname.isEmpty())
			error = "First Name can not be blank.";
		else if(!Character.isUpperCase(firstname.charAt(0)))
				error = "First name must start with a capital letter";
		else if(firstname.length() <= 2 || firstname.length() >= 30)
			error = "First name length must be >2 and <30";
		else if(!isStringOnlyAlphabet(firstname))
			error = "First name cannot contain a number or special characters";
		return error;
	}

	public String validateLastname(String lastname) {
		String error = "";
		
		//System.out.println("In validate lname");
		if(lastname.isEmpty())
			error = "Last Name can not be blank.";
		else if(!Character.isUpperCase(lastname.charAt(0)))
				error = "Last name must start with a capital letter";
		else if(lastname.length() <= 2 || lastname.length() >= 30)
			error = "Last name length must be >2 and <30";
		else if(!isStringOnlyAlphabet(lastname))
			error = "Last name cannot contain a number or special characters";
		return error;
	}

	public String validateutaid(String utaid) {
		String error = "";
		ArrayList<String> ids = UserDAO.getUTAId();
		//System.out.println("In validate fname");
		if(utaid.isEmpty())
			error = "UTA ID can not be blank.";
		else if(!isStringOnlyNumber(utaid))
				error = "UTA Id must be numeric";
		else if(utaid.length() != 10)
			error = "UTA Id must have a length of 10";
		else if(ids.contains(utaid))
			error = "There can only be one account per UTA ID.";
		return error;
	}

	public String validateusername(String username) {
		String error = "";
		
		//System.out.println("In validate fname");
		if(username.isEmpty())
			error = "Username can not be blank.";
		else if(!Character.isAlphabetic(username.charAt(0)))
				error = "Username must start with letter";
		else if(username.length() <= 4 || username.length() >= 21)
			error = "Username length must be >4 and <21";
		else if(!(isStringOnlyNumber(username) || !isStringOnlyAlphabet(username)))
			error = "Username cannot contain special characters.";
		return error;
	}
	
	public String validatePassword(String password) {
		String error = "";
		if(!password.matches(".*[A-Z].*"))
			error = "Password must contain an uppercase letter.";
		else if(!password.matches(".*[a-z].*"))
			error = "Password must contain an lowercase letter";
		else if(!password.matches(".*[0-9].*"))
			error = "Password must contain a number.";
		else if(!password.matches(".*[`~!@#$%^&*()\\\\-_=+\\\\\\\\|\\\\[{\\\\]};:'\\\",<.>/?].*"))
			error = "Password must contain a special character.";
		else if(!(password.length() > 7 && password.length() < 30))
			error = "Password length must be >7 and <30";
		return error;
	}

	public String validatePhone(String phone) {

		String error = "";
		
		//System.out.println("In validate phone");
		if(!phone.isEmpty()) {
			if(!isStringOnlyNumber(phone))
				error = "Phone number must be numeric";
			else if(phone.length() != 10)
				error = "Phone number must have 10 digits";
		}
		return error;
	}
	
	public String validateStreetnumber(String streetnumber) {
		String error = "";
		
		//System.out.println("In validate streetnumber");
		if(!streetnumber.isEmpty()) {
			if(!(streetnumber.length() < 7 && streetnumber.length() > 0))
				error = "Street number length must be >0 and <7.";
			else if(!isStringOnlyNumber(streetnumber))
				error = "Street number must be numeric";
		}
		return error;
		
	}
	
	public String validateStreetname(String streetname) {
		String error = "";
		
		//System.out.println("In validate streetname");
		if(!streetname.isEmpty()) {
			if(!(streetname.length() > 0 && streetname.length() < 40))
				error = "Street number length must be >0 and <40.";
			else if(!isStringOnlyAlphabet(streetname))
				error = "Street number must be non-numeric";
		}
		return error;
		
	}
	
	public String validatezipcode(String zipcode) {
		String error = "";
		//System.out.println("In validate zipcode");
		if(!zipcode.isEmpty()) {
			if(!isStringOnlyNumber(zipcode))
				error = "Zipcode must be numeric";
			else if(zipcode.length() != 5)
				error = "Zipcode must have a length of 5";
			
		}
		return error;
	}
	
	public String validatecity(String city ){
		String error = "";
		//System.out.println("In validate city");
		if(!city.isEmpty()) {
			if(!Character.isUpperCase(city.charAt(0)))
					error = "City must start with a capital letter";
			else if(city.length() <= 2 || city.length() >= 30)
				error = "City length must be >2 and <30";
			else if(!isStringOnlyAlphabet(city))
				error = "City cannot contain a number or special characters";
			
		}
		return error;
	}

	public String validatestate(String state) {
		String error = "";
		String[] states = { "AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA","MD","ME","MH","MI","MN","MO",
				"MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY" };
		List<String> list = Arrays.asList(states);
		if(!state.isEmpty()) {
			if(!isStringOnlyAlphabet(state))
				error = "State must be non numeric.";
			else if(state.length() != 2)
				error = "State must be 2 letter abbreviation.";
			else if(!list.contains(state))
				error = "State abbreviation not found.";
			
		}
		return error;
	}
	
	public String validateRole(String Role) {
		//System.out.println("In validate role");
		String error = "";
		ArrayList<String> roles = UserDAO.checkRoles();
		if(Role.equals("Admin") && roles.contains("Admin"))
			error = "There can only be one admin";
		else if(Role.equals("Caterer Manager") && roles.contains("Caterer Manager"))
			error = "There can only be one Caterer Manager";
		return error;
	}
	
	public void setUser(String username, String password, String lastName, String firstName, String role, String utaId,
			String phone, String email, String streetnumber, String streetname, String city,
			String state, String zipcode) {
		// TODO Auto-generated method stub
		// System.out.println(username+" : "+role+": "+firstName);
		this.username = username;
		this.password = password;
		this.firstname = firstName;
		this.lastname = lastName;
		this.role = role;
		this.utaid = utaId;
		this.phone = phone;
		this.email = email;
		this.streetnumber = streetnumber;
		this.streetname = streetname;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		
	}

	public void verifyUser (User user, UserErrorMsgs errorMsgs) {		
		errorMsgs.setUsernameError(verifyUsername(user.getUsername()));
		errorMsgs.setPasswordError(verifyPassword(user.getPassword(),user.getUsername()));	
		errorMsgs.setErrorMsgs();
	}
	
	private String verifyUsername(String username) {
		String result="";
		username = username.trim();
		if (username.isEmpty())
			result = "Username cannot be blank";
		else
			if (UserDAO.uniqueUsername(username))
				result="This username is not registered yet";
		return result;				
	}
	
	private String verifyPassword(String password, String username) {
		String result="";
		if (password.isEmpty())
			result = "Password cannot be blank";
		else
			if (!UserDAO.uniqueUsername(username))//if user exists, get password from DB & check for match
				if (! (UserDAO.getUser(username).getPassword().equals(password)) )
					result="Incorrect password";
		return result;	
	}

}
