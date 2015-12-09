package it.mapler.walla.api.response;

import it.mapler.walla.enumeration.STATUS;

import java.io.Serializable;

public abstract class AbsResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4598627948658214573L;
	
	protected String status;
	protected String message;
	
	public void setStatusOK(){
		this.status = STATUS.OK.name();
	}
	
	public void setStatusError(STATUS status,String errorMessage){
		this.status = status.name();
		this.message = errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
