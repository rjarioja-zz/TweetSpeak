package tweetspeak.objects;

public class ERROR {
	
	private String errorMessage;
	private int indexNumber;
	
	public ERROR (String errorMessage, int indexNumber) {
		setErrorMessage(errorMessage);
		setIndexNumber(indexNumber);
	}
	//getters
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public int getIndexNumber () {
		return indexNumber;
	}
	
	//setters
	public void setErrorMessage (String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void setIndexNumber (int indexNumber) {
		this.indexNumber = indexNumber;
	}

}
