public class Duration {
	private Long startTime;
	private Long endTime;

	private String startMessage;
	private String endMessage;

	//constructor
	public Duration() {
		//set all the values to null for later checking
		startTime = null;
		endTime = null;

		startMessage = "";
		endMessage = "";
	}




	//getter and setter for start time
	public void setStartTime(long start) {
		startTime = start;
	}
	public Long getStartTime() {
		return startTime;
	}



	//getter and setter for end time
	public void setEndTime(long end) {
		endTime = end;
	}
	public Long getEndTime() {
		return endTime;
	}



	//getter and setter for start msg
	public void setStartMessage(String msg) {
		startMessage = msg;
	}
	public String getStartMessage() {
		return startMessage;
	}



	//getter and setter for end msg
	public void setEndMessage(String msg) {
		endMessage = msg;
	}
	public String getEndMessage() {
		return endMessage;
	}

}