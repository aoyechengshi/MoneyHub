package Exception;

import java.util.ArrayList;

public class UserBusinessException extends Exception {
	private ArrayList<String>messageList = new ArrayList<>();
	
	public UserBusinessException(String message) {
		super(message);
	}
	
	public UserBusinessException(ArrayList<String>messageList) {
		super();
		this.messageList = messageList;
	}

	//getter
	public ArrayList<String> getMessageList() {
		return messageList;
	}

	//setter
	public void setMessageList(ArrayList<String> messageList) {
		this.messageList = messageList;
	}
}
