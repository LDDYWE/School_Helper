package com.zb.secondary_market.otherclasses;

import java.io.Serializable;

public class DB_Message_Item implements Serializable{
	public String datetimeString;
	public String messageString;
	public String sendString;
	public String receiveString;
	public String idString;
	
	private void setId(String id) {
		// TODO Auto-generated method stub
		this.idString = id;
	}
	
	private String getId() {
		// TODO Auto-generated method stub
		return idString;
	}
	
	private void setDatetime(String datetime) {
		// TODO Auto-generated method stub
		this.datetimeString = datetime;
	}
	
	private String getDatetime() {
		// TODO Auto-generated method stub
		return datetimeString;
	}
	
	private void setMessage(String message) {
		// TODO Auto-generated method stub
		this.messageString = message;
	}
	
	private String getMessage() {
		// TODO Auto-generated method stub
		return messageString;
	}
	
	private void setSend(String send) {
		// TODO Auto-generated method stub
		this.sendString = send;
	}
	
	private String getSend() {
		// TODO Auto-generated method stub
		return sendString;
	}
	
	private void setReceive(String receive) {
		// TODO Auto-generated method stub
		this.receiveString = receive;
	}
	
	private String getReceive() {
		// TODO Auto-generated method stub
		return receiveString;
	}
}
	

