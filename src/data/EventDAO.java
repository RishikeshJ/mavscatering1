package data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Event;
//import model.User;
import util.SQLConnection;

public class EventDAO {
static SQLConnection DBMgr = SQLConnection.getInstance();
public static void registerEvent(Event event) {
	Statement stmt = null;   
	Connection conn = SQLConnection.getDBConnection();  
	String registerUser = "INSERT INTO EVENTDETAILS (lastName, firstName, date, startTime, duration, hallName, "
			+ "estAttendees, eventName, foodType, meal, mealFormality, drinkType, entertainmentItems, eventStatus,userid,ccnum,cvvnum,expdate,depositAmount) ";					
	registerUser += " VALUES ('"  
			+ event.getLastName()+ "','"
			+ event.getfirstName()+ "','"		
			+ event.getdate() + "','"
			+ event.getstartTime()+ "','" 
			+ event.getduration()+ "','"
			+ event.gethallName()+ "',"
			+ event.getestAttendees()+ ",'"		
			+ event.geteventName()+ "','"
			+ event.getfoodType()+ "','"
			+ event.getmeal()+ "','"
			+ event.getmealFormality()+ "','"
			+ event.getdrinkType()+ "','"
			+ event.getentertainmentItems()+ "','"
			+ event.geteventStatuss()+ "','"
			+ event.getuserid()+ "','"
			+ event.getccnumber()+ "','"
			+ event.getccpin()+ "','"
			+ event.getccexpdate()+ "','"
			+ event.getDepositAmount()+ "')";
	
	try {   
	conn = SQLConnection.getDBConnection();  
	conn.setAutoCommit(false);   
	stmt = conn.createStatement();
	stmt.executeUpdate(registerUser);
	conn.commit();					 
} catch (SQLException sqle) { 
	sqle.printStackTrace();
} finally {
	try {
		conn.close();
		stmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
	};
}

}

private static ArrayList<Event> ReturnPendingEventList (String queryString) {
	ArrayList<Event> PendingEventsinDB= new ArrayList<Event>();
	
	Statement stmt = null;
	Connection conn = SQLConnection.getDBConnection(); 
	try {
		stmt = conn.createStatement();
		ResultSet eventList = stmt.executeQuery(queryString);
		while (eventList.next()) {
			Event event = new Event(); 
			event.setLastName(eventList.getString("lastName"));
			event.setfirstName(eventList.getString("firstName"));
			event.setdate(eventList.getString("date"));
			event.setstartTime(eventList.getString("startTime"));  
			event.setduration(eventList.getString("duration"));
			event.sethallName(eventList.getString("hallName"));
			event.setestAttendees(eventList.getString("estAttendees"));
			event.seteventName(eventList.getString("eventName"));
			event.setfoodType(eventList.getString("foodType"));
			
			event.setmeal(eventList.getString("meal"));
			event.setmealFormality(eventList.getString("mealFormality"));
			event.setdrinkType(eventList.getString("drinkType"));
			event.setentertainmentItems(eventList.getString("entertainmentItems"));
			event.seteventStatus(eventList.getString("eventStatus"));
			
			event.seteventID(eventList.getString("eventID"));
			
			PendingEventsinDB.add(event);	
		}
	} catch (SQLException e) {}
	return PendingEventsinDB;
}

public static ArrayList<Event> listEvents() {
	// TODO Auto-generated method stub
	return ReturnPendingEventList("SELECT * from eventdetails where eventStatus = 'Pending'");
}

public static void UpdateRequest(String userid, String date, String time, String hallname, String ccnum, String cvvnum, String expdate, String depositAmount) {
	Statement stmt = null;
	Connection conn = SQLConnection.getDBConnection();
	String Status = "Reserved";
	try
	{
		stmt = conn.createStatement();
		conn.setAutoCommit(false);
		String editUser = "UPDATE mavs_catering.eventdetails SET eventStatus = '"+Status+"', ccnum = '"+ccnum+"',"
				+ " cvvnum = '"+cvvnum+"',"
				+ "expdate = '"+expdate+"' "
				+ "WHERE userid = '"+userid+"' and date = '"+date+"' and startTime = '"+time+"' and hallName = '"+hallname+"'";
		stmt.executeUpdate(editUser);	
		conn.commit();
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
}

public static int CheckReservations(String date, String time, String hallname) {
	Statement stmt = null;
	Connection conn = SQLConnection.getDBConnection();
	String count = "0";
	try
	{
		stmt = conn.createStatement();
		conn.setAutoCommit(false);
		String reservations = "select count(*) as count from eventdetails "
				+ "WHERE Time_to_sec('"+time+"') between Time_to_sec(startTime)"
						+ " and (Time_to_Sec(concat(duration,':00'))  +  Time_to_sec(startTime)) and date = '"+date+"'"
						+ " and eventStatus = 'Reserved' and hallName = '"+hallname+"'";
		ResultSet Result = stmt.executeQuery(reservations);	
		while(Result.next()) {count = Result.getString("count");}
		
		conn.commit();
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}	
	return Integer.parseInt(count);
}

public static int CheckDailyReservations(String date, String UserProfile) {
	Statement stmt = null;
	Connection conn = SQLConnection.getDBConnection();
	String count = "0";
	try
	{
		stmt = conn.createStatement();
		conn.setAutoCommit(false);
		String reservations = "select count(*) as count from eventdetails Where date = '"+date+"'"
						+ " and eventStatus = 'Reserved' and userid = '"+UserProfile+"'";
		ResultSet Result = stmt.executeQuery(reservations);	
		while(Result.next()) {count = Result.getString("count");}
		
		conn.commit();
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	return Integer.parseInt(count);
}

public static int CheckWeeklyReservations(String date, String UserProfile) {
	Statement stmt = null;
	Connection conn = SQLConnection.getDBConnection();
	String count = "0";
	try
	{
		stmt = conn.createStatement();	
		conn.setAutoCommit(false);
		String reservations = "select count(*) as count from eventdetails WHERE YEARWEEK(date) = YEARWEEK('"+date+"') and userid = '"+UserProfile+"'";
		ResultSet Result = stmt.executeQuery(reservations);	
		while(Result.next()) {count = Result.getString("count");}
		conn.commit();
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	return Integer.parseInt(count);
}


public static String getlastname(String userid) {
	Statement stmt = null;
	Connection conn = SQLConnection.getDBConnection();
	String lastName = "";
	try
	{
		stmt = conn.createStatement();
		conn.setAutoCommit(false);
		String reservations = "select lastname from user "
				+ "WHERE username = '"+userid+"'";
		ResultSet Result = stmt.executeQuery(reservations);	
		while(Result.next()) {lastName = Result.getString("lastname");}
		
		conn.commit();
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	return lastName;
}

public static String getfirstname(String userid) {
	Statement stmt = null;
	Connection conn = SQLConnection.getDBConnection();
	String lastName = "";
	try
	{
		stmt = conn.createStatement();
		conn.setAutoCommit(false);
		String reservations = "select firstname from user "
				+ "WHERE username = '"+userid+"'";
		ResultSet Result = stmt.executeQuery(reservations);	
		while(Result.next()) {
			lastName = Result.getString("firstname");}
		
		conn.commit();
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	return lastName;
}


public static Event getSpecificEvent(String eventID) {
	Statement stmt = null;   
	Connection conn = null;  
	Event event = new Event();
	try {   
		conn = SQLConnection.getDBConnection();  
		stmt = conn.createStatement();
		String searchSpecificEvent = " SELECT * from eventdetails WHERE eventID = '"+eventID+"'";
		ResultSet eventList = stmt.executeQuery(searchSpecificEvent);
		while(eventList.next()) {
			String lastName = eventList.getString("lastName");
			String firstName  = eventList.getString("firstName");
			String date = eventList.getString("date");
			String startTime  = eventList.getString("startTime");
			String duration  = eventList.getString("duration");
			String hallName = eventList.getString("hallName");
			String estAttendees  = eventList.getString("estAttendees");
			String eventName  = eventList.getString("eventName");
			String foodType  = eventList.getString("foodType");
			String meal  = eventList.getString("meal");
			String mealFormality = eventList.getString("mealFormality");
			String drinkType  = eventList.getString("drinkType");
			String entertainmentItems  = eventList.getString("entertainmentItems");
			String eventStatus  = eventList.getString("eventStatus");
			String EventID  = eventList.getString("eventID");
			String ccNumber = eventList.getString("ccnum");
			String ccpin = eventList.getString("cvvnum");
			String ccexpdate = eventList.getString("expdate");
			String userid = eventList.getString("userid");
			String depositAmount = eventList.getString("depositAmount");
			event.setEvent(lastName, firstName, date, startTime, duration, hallName, estAttendees, eventName, foodType, meal, mealFormality, drinkType, 
					entertainmentItems, eventStatus, EventID, ccNumber, ccpin, ccexpdate, userid,depositAmount);
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		};
	return event;
}

/*public static Event get_Specific_Event_details(String eventID) {
	Statement stmt = null;   
	Connection conn = null;  
	Event event = new Event();
	try {   
		conn = SQLConnection.getDBConnection();  
		stmt = conn.createStatement();
		String searchSpecificEvent = " SELECT * from eventdetails WHERE eventID = '"+eventID+"'";
		ResultSet eventList = stmt.executeQuery(searchSpecificEvent);
		while(eventList.next()) {
			String lastName = eventList.getString("lastName");
			String firstName  = eventList.getString("firstName");
			String date = eventList.getString("date");
			String startTime  = eventList.getString("startTime");
			String duration  = eventList.getString("duration");
			String hallName = eventList.getString("hallName");
			String estAttendees  = eventList.getString("estAttendees");
			String eventName  = eventList.getString("eventName");
			String foodType  = eventList.getString("foodType");
			String meal  = eventList.getString("meal");
			String mealFormality = eventList.getString("mealFormality");
			String drinkType  = eventList.getString("drinkType");
			String entertainmentItems  = eventList.getString("entertainmentItems");
			String eventStatus  = eventList.getString("eventStatus");
			String EventID  = eventList.getString("eventID");
			String staff_fname = eventList.getString("staff_firstname");
			String staff_lname = eventList.getString("staff_lastname");

			event.setEvent_v2(lastName, firstName, date, startTime, duration, hallName, eventName, meal, mealFormality, foodType, drinkType
					,estAttendees,entertainmentItems, EventID,staff_fname,staff_lname);
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		};
	return event;
}*/

public static void Modifyevent_v2 (Event event,String old_eventID) {
	Statement stmt = null; 
	Connection conn = SQLConnection.getDBConnection(); 
	
	System.out.print(event.getfirstName());
	String assignstaff = "update eventdetails set staff_firstname = '"+event.getStaff_fname()+"',staff_lastname = '"+event.getStaff_lname()+
			"',firstName = '"+event.getfirstName()+ 	"',lastName = '"+event.getLastName()+"',date = '"+event.getdate()+
			"',duration = '"+event.getduration()+	"',startTime = '"+event.getstartTime()+			"',hallName = '"+event.gethallName()+
			"',estAttendees = '"+event.getestAttendees()+			"',eventName = '"+event.geteventName()+			"',foodType = '"+event.getfoodType()+
			"',meal = '"+event.getmeal()+			"',mealFormality = '"+event.getmealFormality()+			"',drinkType = '"+event.getdrinkType()+
			"',entertainmentItems = '"+event.getentertainmentItems()+			"',eventID = '"+event.geteventID()+"' where eventID = '"+old_eventID+"';";					
	
	try {   
	conn = SQLConnection.getDBConnection();  
	conn.setAutoCommit(false);   
	stmt = conn.createStatement();
	stmt.executeUpdate(assignstaff);
	conn.commit();					 
} catch (SQLException sqle) { 
	sqle.printStackTrace();
} finally {
	try {
		conn.close();
		stmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
	};
}
}


public static ArrayList<Event> getEventSummary() {
	Statement stmt = null; 	
	Connection conn = null;  
	ArrayList<Event> eventlist = new ArrayList<Event>();
	try {   
		conn = SQLConnection.getDBConnection();  
		stmt = conn.createStatement();
		String searchSpecificEvent = " SELECT * from eventdetails order by date,startTime;";
		ResultSet eventList = stmt.executeQuery(searchSpecificEvent);
		while(eventList.next()) {
			Event event= new Event();
			String lastName = eventList.getString("lastName");
			String firstName  = eventList.getString("firstName");
			String date = eventList.getString("date");
			String startTime  = eventList.getString("startTime");
			String duration  = eventList.getString("duration");
			String hallName = eventList.getString("hallName");
			String estAttendees  = eventList.getString("estAttendees");
			String eventName  = eventList.getString("eventName");
			String foodType  = eventList.getString("foodType");
			String meal  = eventList.getString("meal");
			String mealFormality = eventList.getString("mealFormality");
			String drinkType  = eventList.getString("drinkType");
			String entertainmentItems  = eventList.getString("entertainmentItems");
			String eventStatus  = eventList.getString("eventStatus");
			String eventID  = eventList.getString("eventID");

			event.setEvent(lastName, firstName, date, startTime, duration, hallName, estAttendees, eventName, foodType, meal, mealFormality, drinkType, 
					entertainmentItems, eventStatus, eventID, "", "", "", "","");
			eventlist.add(event);
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		};
	return eventlist;
}

public static void Modifyevent (Event event) {
	Statement stmt = null;   
	Connection conn = SQLConnection.getDBConnection();  
	//Event event= new Event();
	String assignstaff = "update eventdetails set staff_firstname = '"+event.getStaff_fname()+"',staff_lastname = '"+event.getStaff_lname()+"' where eventID = '"+event.geteventID()+"';";					
	
	try {   
	conn = SQLConnection.getDBConnection();  
	conn.setAutoCommit(false);   
	stmt = conn.createStatement();
	stmt.executeUpdate(assignstaff);
	conn.commit();					 
} catch (SQLException sqle) { 
	sqle.printStackTrace();
} finally {
	try {
		conn.close();
		stmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
	};
}
}
public static ArrayList<Event>  listEvents1(String edate, String etime) {  
	
	return ReturnMatchingEventList(" SELECT eventname, date, starttime, duration, hallname, lastname, firstname, eventID,eventStatus from eventdetails where date_format(date(concat(date,' ',starttime)), '%m-%d-%Y %H:%i') >= date_format(date(concat('"+edate+"',' ','"+etime+"')), '%m-%d-%Y %H:%i') order by date,startTime");
}

public static ArrayList<Event>  listEvents2(String edate, String etime, String fname, String lname) {  
	
	return ReturnMatchingEventList(" SELECT eventname, date, starttime, duration, hallname, lastname, firstname, eventID,eventStatus from eventdetails where date_format(date(concat(date,' ',starttime)), '%m-%d-%Y %H:%i') >= date_format(date(concat('"+edate+"',' ','"+etime+"')), '%m-%d-%Y %H:%i') and staff_firstname='"+fname+"' and staff_lastname='"+lname+"' order by date,startTime");
}

private static ArrayList<Event> ReturnMatchingEventList (String queryString) {
	ArrayList<Event> eventListInDB = new ArrayList<Event>();
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
			eventListInDB.add(staff);	
		}
	} catch (SQLException e) {}
	return eventListInDB;
}

public static void Modifyevent_User (Event event,String old_eventID) {
	Statement stmt = null; 
	Connection conn = SQLConnection.getDBConnection(); 
	
	System.out.print(event.getfirstName());
	String assignstaff = "update eventdetails set firstName = '"+event.getfirstName()+ 	"',lastName = '"+event.getLastName()+"',date = '"+event.getdate()+
			"',duration = '"+event.getduration()+	"',startTime = '"+event.getstartTime()+			"',hallName = '"+event.gethallName()+
			"',estAttendees = '"+event.getestAttendees()+			"',eventName = '"+event.geteventName()+			"',foodType = '"+event.getfoodType()+
			"',meal = '"+event.getmeal()+			"',mealFormality = '"+event.getmealFormality()+			"',drinkType = '"+event.getdrinkType()+
			"',entertainmentItems = '"+event.getentertainmentItems()+			"',eventID = '"+event.geteventID()+"' where eventID = '"+old_eventID+"';";					
	
	try {   
	conn = SQLConnection.getDBConnection();  
	conn.setAutoCommit(false);   
	stmt = conn.createStatement();
	stmt.executeUpdate(assignstaff);
	conn.commit();					 
} catch (SQLException sqle) { 
	sqle.printStackTrace();
} finally {
	try {
		conn.close();
		stmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
	};
}
}

public static Event getSpecificEventdetails(String eventID) {
	Statement stmt = null;   
	Connection conn = null;  
	Event event = new Event();
	try {   
		conn = SQLConnection.getDBConnection();  
		stmt = conn.createStatement();
		String searchSpecificEvent = " SELECT * from eventdetails WHERE eventID = '"+eventID+"'";
		ResultSet eventList = stmt.executeQuery(searchSpecificEvent);
		while(eventList.next()) {
			String lastName = eventList.getString("lastName");
			String firstName  = eventList.getString("firstName");
			String date = eventList.getString("date");
			String startTime  = eventList.getString("startTime");
			String duration  = eventList.getString("duration");
			String hallName = eventList.getString("hallName");
			String estAttendees  = eventList.getString("estAttendees");
			String eventName  = eventList.getString("eventName");
			String foodType  = eventList.getString("foodType");
			String meal  = eventList.getString("meal");
			String mealFormality = eventList.getString("mealFormality");
			String drinkType  = eventList.getString("drinkType");
			String entertainmentItems  = eventList.getString("entertainmentItems");
			String eventStatus  = eventList.getString("eventStatus");
			String EventID  = eventList.getString("eventID");

			event.setEventForUpdate(lastName, firstName, date, startTime, duration, hallName, eventName, meal, mealFormality, foodType, drinkType
					,estAttendees,entertainmentItems, EventID);
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		};
	return event;
}

public static ArrayList<Event> getUserEventSummary(String Username, String firstname,String lastname) {
	Statement stmt = null;
	Statement stmt1 = null;
	Connection conn = null;  
	ArrayList<Event> eventlist = new ArrayList<Event>();
	try {   
		conn = SQLConnection.getDBConnection();  
		stmt = conn.createStatement();
		String updateuserevent = "update eventdetails set firstname='"+firstname+"', lastname='"+lastname+"' where userid='"+Username+"';";
		System.out.print(updateuserevent);
		stmt1 = conn.createStatement();
		stmt1.executeUpdate(updateuserevent);
		conn.commit();
		String searchSpecificEvent = "SELECT * from eventdetails where userid = '"+Username+"' order by date,startTime;";
		ResultSet eventList = stmt.executeQuery(searchSpecificEvent);
		while(eventList.next()) {
			Event event= new Event();
			String lastName = eventList.getString("lastName");
			String firstName  = eventList.getString("firstName");
			String date = eventList.getString("date");
			String startTime  = eventList.getString("startTime");
			String duration  = eventList.getString("duration");
			String hallName = eventList.getString("hallName");
			String estAttendees  = eventList.getString("estAttendees");
			String eventName  = eventList.getString("eventName");
			String foodType  = eventList.getString("foodType");
			String meal  = eventList.getString("meal");
			String mealFormality = eventList.getString("mealFormality");
			String drinkType  = eventList.getString("drinkType");
			String entertainmentItems  = eventList.getString("entertainmentItems");
			String eventStatus  = eventList.getString("eventStatus");
			String eventID  = eventList.getString("eventID");

			event.setEvent(lastName, firstName, date, startTime, duration, hallName, estAttendees, eventName, foodType, meal, mealFormality, drinkType, 
					entertainmentItems, eventStatus, eventID, "", "", "", "","");
			eventlist.add(event);
			
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		};
	return eventlist;
}
public static ArrayList<Event> getUserEventSummary1(String Username) {
	Statement stmt = null;
	Statement stmt1 = null;
	Connection conn = null;  
	ArrayList<Event> eventlist = new ArrayList<Event>();
	try {   
		conn = SQLConnection.getDBConnection();  
		stmt = conn.createStatement();
		String searchSpecificEvent = "SELECT * from eventdetails where userid = '"+Username+"' order by date,startTime;";
		ResultSet eventList = stmt.executeQuery(searchSpecificEvent);
		while(eventList.next()) {
			Event event= new Event();
			String lastName = eventList.getString("lastName");
			String firstName  = eventList.getString("firstName");
			String date = eventList.getString("date");
			String startTime  = eventList.getString("startTime");
			String duration  = eventList.getString("duration");
			String hallName = eventList.getString("hallName");
			String estAttendees  = eventList.getString("estAttendees");
			String eventName  = eventList.getString("eventName");
			String foodType  = eventList.getString("foodType");
			String meal  = eventList.getString("meal");
			String mealFormality = eventList.getString("mealFormality");
			String drinkType  = eventList.getString("drinkType");
			String entertainmentItems  = eventList.getString("entertainmentItems");
			String eventStatus  = eventList.getString("eventStatus");
			String eventID  = eventList.getString("eventID");

			event.setEvent(lastName, firstName, date, startTime, duration, hallName, estAttendees, eventName, foodType, meal, mealFormality, drinkType, 
					entertainmentItems, eventStatus, eventID, "", "", "", "","");
			eventlist.add(event);
			
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		};
	return eventlist;
}


}