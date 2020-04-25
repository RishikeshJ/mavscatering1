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
	@FileParameters("test/model/EventTestCases1.csv")
	public void test(int testCaseNo,String action,String userID,String date,String startTime,String duration, String hallName,String estAttendees,
			String eventName, String ccnum,String cvvnum,String expdate,String staffFirstName,String staffLastName,String errMsgs, String duplicateResMsg,
			String timeErr, String capacityErr,String staffErr,String eventNameErr,String pastdateErr,String durationErr,String samedayReserveError,
			String sameweekReserverError, String invalidCCNum,String invalidpin, String invalidExpDate) throws ParseException {
		
		event.setEvent("", "", date, startTime, duration, hallName, estAttendees, eventName, "", "", "", "", "",
				"", "", ccnum, cvvnum, expdate, userID, "");
		event.setStaff_fname(staffFirstName);
		event.setStaff_lname(staffLastName);
		event.validateEvent(action,event, EerrMsgs);

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
