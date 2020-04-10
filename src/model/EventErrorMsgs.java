package model;

public class EventErrorMsgs {

	private String errorMsg;
	private String duplicateResMsg;
	private String invalidCCNum;
	private String invalidpin;
	private String invalidExpDate;
	private String timeerror;
	private String capacityError;
	private String staffError;
	private String eventNameError;
	private String pastdateError;
	private String durationError;
	private String samedayReserveError;
	private String sameweekReserverError;

	
	public EventErrorMsgs () {
		this.errorMsg="";
		this.duplicateResMsg="";
		this.invalidCCNum="";
		this.invalidpin="";
		this.invalidExpDate = "";
		this.timeerror = "";
		this.setCapacityError("");
		this.setStaffError("");
		this.eventNameError = "";
		this.pastdateError = "";
		this.durationError = "";
		this.samedayReserveError = "";
		this.sameweekReserverError = "";

	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg() {
		if (!duplicateResMsg.equals("")
				||!invalidCCNum.equals("")
				||!invalidpin.equals("")
				||!invalidExpDate.equals("")
				||!timeerror.equals("")
				||!staffError.equals("")
				||!eventNameError.equals("")
				||!pastdateError.equals("")
				||!durationError.equals("")
				||!capacityError.equals("")
				||!samedayReserveError.equals("")
				||!sameweekReserverError.equals(""))

		  errorMsg="Please correct the following errors";
	}
	
	public String getduplicateResMsg() {
		return duplicateResMsg;
	}

	public void setduplicateResMsg(String duplicateResMsg) {
		this.duplicateResMsg = duplicateResMsg;
	}

	public String getinvalidCCNum() {
		return invalidCCNum;
	}

	public void setinvalidCCNum(String invalidCCNum) {
		this.invalidCCNum = invalidCCNum;
	}
	
	public String getinvalidpin() {
		return invalidpin;
	}

	public void setinvalidpin(String invalidpin) {
		this.invalidpin = invalidpin;
	}
	
	public String getinvalidExpDate() {
		return invalidExpDate;
	}

	public void setinvalidExpDate(String invalidExpDate) {
		this.invalidExpDate = invalidExpDate;
	}
	
	public String gettimeerror() {
		return invalidCCNum;
	}

	public void settimeerror(String timeerror) {
		this.timeerror = timeerror;
	}

	public String getCapacityError() {
		return capacityError;
	}

	public void setCapacityError(String capacityError) {
		this.capacityError = capacityError;
	}

	public String getStaffError() {
		return staffError;
	}

	public void setStaffError(String staffError) {
		this.staffError = staffError;
	}

	public String getEventNameError() {
		return eventNameError;
	}

	public void setEventNameError(String eventNameError) {
		this.eventNameError = eventNameError;
	}

	public String getPastdateError() {
		return pastdateError;
	}

	public void setPastdateError(String pastdateError) {
		this.pastdateError = pastdateError;
	}

	public String getDurationError() {
		return durationError;
	}

	public void setDurationError(String durationError) {
		this.durationError = durationError;
	}
	public String getsamedayReserveError() {
		return samedayReserveError;
	}

	public void setsamedayReserveError(String samedayReserveError) {
		this.samedayReserveError = samedayReserveError;
	}

	public  String getsameweekReserverError() {
		return sameweekReserverError;
	}

	public void setsameweekReserverError(String sameweekReserverError) {
		this.sameweekReserverError = sameweekReserverError;
	}




}