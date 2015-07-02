package com.zb.secondary_market.otherclasses;

public class Login_Return_Item {
	private String image_url;
	private boolean isMatched;

	public void setImage_Url(String image_url){
		this.image_url = image_url;
	}
	
	public String getImage_Url(){
		return image_url;
	}
	
	public void setisMatched(boolean isMatched){
		this.isMatched = isMatched;
	}
	
	public boolean getisMatched(){
		return isMatched;
	}
}
