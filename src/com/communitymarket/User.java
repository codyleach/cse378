package com.communitymarket;

public class User {
	private UserType _type;
	private String 	 _name;
	private String 	 _email;
	private String	 _username;
	private String 	 _password;
	private String	 _products;
	
	public User() {
		this (UserType.Consumer, "", "", "", "", "");
	}
	
	public User(UserType type, String name, String email, String username, String password) {
		this(type, name, email, username, password, "");
	}
	
	public User(UserType type, String name, String email, String username, String password, String products) {
		setType(type);
		setName(name);
		setEmail(email);
		setUsername(username);
		setPassword(password);
		setProducts(products);
	}
	
	public void setType(UserType type) {
		_type = type;
	}
	
	public void setType(String type) {
		_type = UserType.valueOf(type);
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public void setEmail(String email) {
		_email = email;
	}
	
	public void setUsername(String username) {
		_username = username;
	}
	
	public void setPassword(String password) {
		_password = password;
	}
	
	public void setProducts(String products) {
		_products = products;
	}
	
	public UserType getType() {
		return _type;
	}
	
	public String getName() {
		return _name;
	}
	
	public String getEmail() {
		return _email;
	}
	
	public String getUsername() {
		return _username;
	}
	
	public String getPassword() {
		return _password;
	}
	
	public String getProducts() {
		return _products;
	}
}
