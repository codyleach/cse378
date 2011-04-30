package com.communitymarket;

public class Market {
	private String 	 _name;
	private String 	 _address;
	private String	 _endDate;
	private String 	 _startDate;
	private String	 _startTime;
	private String	 _endTime;
	private int	 _numberOfStalls;
	private int	 _marketID;
	
	public Market() {
		this ("", "", "", "", "", "", 0);
	}
	
	public Market(String name, String address, String endDate, String startDate, 
			String endTime, String startTime, int numStalls) {
		setName(name);
		setAddress(address);
		setEndDate(endDate);
		setStartDate(startDate);
		setEndTime(endTime);
		setStartTime(startTime);
		setNumberOfStalls(numStalls);
	}

	public void setName(String name) {
		_name = name;
	}
	
	public void setAddress(String address) {
		_address = address;
	}
	public void setEndDate(String endDate) {
		_endDate = endDate;
	}
	
	public void setStartDate(String startDate) {
		_startDate = startDate;
	}
	
	public void setEndTime(String endTime) {
		_endTime = endTime;
	}
	
	public void setStartTime(String startTime) {
		_startTime = startTime;
	}
	
	public void setNumberOfStalls(int numStalls) {
		_numberOfStalls = numStalls;
	}
	
	public void setMarketID(int id) {
		_marketID = id;
	}
	
	public String getName() {
		return _name;
	}
	
	public String getAddress() {
		return _address;
	}
	
	public String getEndDate() {
		return _endDate;
	}
	
	public String getStartDate() {
		return _startDate;
	}
	
	public String getEndTime() {
		return _endTime;
	}
	
	public String getStartTime() {
		return _startTime;
	}
	
	public int getNumberOfStalls() {
		return _numberOfStalls;
	}
	
	public int getMarketID() {
		return _marketID;
	}
}
