package com.zb.secondary_market.otherclasses;

import java.io.Serializable;

public class Item_Detail implements Serializable{
	private String imageurl;
	private String vid;
	private String title;
	private String type;
	private String price;
	private String place;
	private String originalprice;
	private String id;
	private String location;
	private String nickname;
	private String datetime;
	private String amount;
	private String discription;
	private String sex;
	private String school;
	private String declaration;
	private String content;
	private String contentaddress;
	private String contentdetail;
	private String contentsource;
	private String hometown;
	private String hobby;

	public String getPlace(){
		return place;
	}
	
	public void setPlace(String place){
		this.place = place;
	}
	
	public String getHometown(){
		return hometown;
	}
	
	public void setHometown(String hometown){
		this.hometown = hometown;
	}
	
	public String getHobby(){
		return hobby;
	}
	
	public void setHobby(String hobby){
		this.hobby = hobby;
	}

	public String getContentAddress(){
		return contentaddress;
	}
	
	public void setContentAddress(String contentaddress){
		this.contentaddress = contentaddress;
	}
	
	public String getContentDetail(){
		return contentdetail;
	}
	
	public void setContentDetail(String contentdetail){
		this.contentdetail = contentdetail;
	}
	
	public String getContentSource(){
		return contentsource;
	}
	
	public void setContentSource(String contentsource){
		this.contentsource = contentsource;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content) {
		// TODO Auto-generated method stub
		this.content = content;
	}
	
	public String getSex(){
		return sex;
	}
	
	public void setSex(String sex){
		this.sex = sex;
	}
	
	public String getSchool(){
		return school;
	}
	
	public void setSchool(String school){
		this.school = school;
	}
	
	public String getDeclaration(){
		return declaration;
	}
	
	public void setDeclaration(String declaration){
		this.declaration = declaration;
	}
	
	public String getImageurl(){
		return imageurl;
	}
	
	public void setImagerul(String imageurl){
		this.imageurl = imageurl;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public String getVid(){
		return vid;
	}
	
	public void setVid(String vid){
		this.vid = vid;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getPrice(){
		return price;
	}
	
	public void setPrice(String price){
		this.price = price;
	}
	
	public String getOriginalPrice(){
		return originalprice;
	}
	
	public void setOriginalPrice(String originalprice){
		this.originalprice = originalprice;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDateTime() {
		return datetime;
	}

	public void setDateTime(String datetime) {
		this.datetime = datetime;
	}

	public String getAmount(){
		return amount;
	}
	
	public void setAmount(String amount){
		this.amount = amount;
	}
	
	public String getDiscription(){
		return discription;
	}
	
	public void setDiscription(String discription){
		this.discription = discription;
	}
}
	
