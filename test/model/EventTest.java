package model;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class EventTest {
	
	Event event;
	EventErrorMsgs EerrMsgs;

	@Before
	public void setUp() throws Exception {
		event = new Event();
		EerrMsgs = new EventErrorMsgs();
	}

	@Test
	@FileParameters("test/model/EventTestCases.csv")
	public void test(int testCaseNo,String action,String lastName, String firstName,String date,String startTime,String duration, String hallName,String estAttendees,
			String eventName, String foodType, String meal, String mealFormality,String drinkType,String entItems,String userID,String ccnum,String cvvnum,String expdate,
			String stafffname, String stafflname,String errMsgs, String pastdateErr, String duplicateResMsg, String timeErr, String capacityErr,String eventNameErr,String durationErr,
			String samedayReserveError,String sameweekReserverError, String invalidCCNum,String invalidpin, String invalidExpDate,String staffErr) throws ParseException {
		
		event.setEvent(lastName, firstName, date, startTime, duration, hallName, estAttendees, eventName, foodType, meal, mealFormality, drinkType, entItems,
				"", "", ccnum, cvvnum, expdate, userID, "");
		event.validateEvent(action,event, EerrMsgs);
		EerrMsgs.setErrorMsg();

		assertTrue(errMsgs.equals(EerrMsgs.getErrorMsg()));
		assertTrue(pastdateErr.equals(EerrMsgs.getPastdateError()));
		assertTrue(duplicateResMsg.equals(EerrMsgs.getduplicateResMsg()));
		assertTrue(timeErr.equals(EerrMsgs.gettimeerror()));
		assertTrue(capacityErr.equals(EerrMsgs.getCapacityError()));
		assertTrue(eventNameErr.equals(EerrMsgs.getEventNameError()));
		assertTrue(durationErr.equals(EerrMsgs.getDurationError()));
		assertTrue(samedayReserveError.equals(EerrMsgs.getsamedayReserveError()));
		assertTrue(sameweekReserverError.equals(EerrMsgs.getsameweekReserverError()));
		assertTrue(invalidCCNum.equals(EerrMsgs.getinvalidCCNum()));
		assertTrue(invalidpin.equals(EerrMsgs.getinvalidpin()));
		assertTrue(invalidExpDate.equals(EerrMsgs.getinvalidExpDate()));
		assertTrue(staffErr.equals(EerrMsgs.getStaffError()));
	}

}
