package com.communitymarket;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MarketDbAdapter {
	// Field Names
	public static String MARKET_NAME_FIELD = "marketName";
	public static String ADDRESS_FIELD	= "marketAddress";
	public static String START_DATE_FIELD = "startDate";
	public static String END_DATE_FIELD = "endDate";
	public static String START_TIME_FIELD = "startTime";
	public static String END_TIME_FIELD	= "endTime";
	public static String NUM_STALLS_FIELD = "numberOfStalls";
	public static String MARKET_ID_FIELD = "marketID";
	
	private static Market _currentMarket;
	private static MarketDbAdapter _instance;
	
	// Variables
	private MarketDbOpenHelper _dbOpener;
	private SQLiteDatabase	  _database;
	private Context			  _context;
	
	/**
	 * Constructor
	 */
	public MarketDbAdapter(Context context) {
		_context = context;
	}
	
	public static MarketDbAdapter getInstance(Context context) {
		if(_instance == null) {
			_instance = new MarketDbAdapter(null);
		}
		_instance._context = context;
		return _instance;
	}
	
	public void open() throws SQLException {
		if (_dbOpener == null) {
			_dbOpener = new MarketDbOpenHelper(_context);
		}
		
		if (_database == null) {
			_database = _dbOpener.getWritableDatabase();
		}
	}
	
	public void close() {
		if(_database != null) {
			_database.close();
			_database = null;
		}
	}
	
//	public boolean addMarket(String name, String address, String startDate, String endDate, 
//			String startTime, String endTime, int numStalls) {
//		// Make sure we're all set up
//		open();
//		
//		// Make sure the inputs aren't null
//		if (name.equals("") || address.equals("") || startDate.equals("") 
//				|| endDate.equals("") || startTime.equals("") || endTime.equals("")) {
//			return false;
//		}
//		
//		ContentValues values = new ContentValues();
//		values.put(MARKET_NAME_FIELD, name);
//		values.put(ADDRESS_FIELD, address);
//		values.put(START_DATE_FIELD, startDate);
//		values.put(END_DATE_FIELD, endDate);
//		values.put(START_TIME_FIELD, startTime);
//		values.put(END_TIME_FIELD, endTime);
//		values.put(NUM_STALLS_FIELD, numStalls);
//		_database.insert(RatingDbOpenHelper.TABLE_NAME, null, values);
//		
//		close();
//		return true;
//	}
	
	public boolean updateMarket(String query) {
		open();
		if (query.equals(""))
			return false;
		_database.rawQuery(query, null);
		
		return true;
		
	}
	
	public boolean addMarket(String sName, String sAddress, String sStartDate, String sEndDate, 
	String sStartTime, String sEndTime, String sNumStalls) {
		open();
		if (sName.equals("") || sAddress.equals("") || sStartDate.equals("")
				|| sEndDate.equals("") || sStartTime.equals("") || sEndTime.equals("")
				|| sNumStalls.equals(""))
			return false;
		
		ContentValues values = new ContentValues();
		values.put(MarketDbAdapter.MARKET_NAME_FIELD, sName);
		values.put(MarketDbAdapter.ADDRESS_FIELD, sAddress);
		values.put(MarketDbAdapter.START_DATE_FIELD, sStartDate);
		values.put(MarketDbAdapter.END_DATE_FIELD, sEndDate);
		values.put(MarketDbAdapter.START_TIME_FIELD, sStartTime);
		values.put(MarketDbAdapter.END_TIME_FIELD, sEndTime);
		values.put(MarketDbAdapter.NUM_STALLS_FIELD, sNumStalls);
        _database.insert(MarketDbOpenHelper.TABLE_NAME, null, values);
		close();
		return true;
	}
	
	public boolean doesExist(int marketID) {	//returns true if does exist
		open();
		String query = "SELECT * FROM " + MarketDbOpenHelper.TABLE_NAME 
		+ " WHERE " + MARKET_ID_FIELD + "' = '" + marketID + "'";
	
		Cursor cursor = _database.rawQuery(query, null);
		
		if (cursor.getCount() == 0)		//if there are no matches, return negative
			return false;
		return true;
	}
	
	public String getName(int marketID) {	//returns "fuck" if can't find market
		if (!doesExist(marketID))
			return "fuck";
		
		// Make sure we're all set up
		open();
		
		String query = "SELECT * FROM " + MarketDbOpenHelper.TABLE_NAME 
			+ " WHERE " + MARKET_ID_FIELD + "' = '" + marketID + "'";
		
		Cursor cursor = _database.rawQuery(query, null);
		
		//make sure at first
		cursor.moveToFirst();
		String result = cursor.getString(cursor.getColumnIndex(MARKET_NAME_FIELD));
		
		//Always close the database.
		close();
		return result;
	}
	
	public String getAddress(int marketID) {	//returns "fuck" if can't find market
		if (!doesExist(marketID))
			return "fuck";
		
		// Make sure we're all set up
		open();
		
		String query = "SELECT * FROM " + MarketDbOpenHelper.TABLE_NAME 
			+ " WHERE " + MARKET_ID_FIELD + "' = '" + marketID + "'";
		
		Cursor cursor = _database.rawQuery(query, null);
		
		if (cursor.getCount() == 0) {	//if there are no matches, return negative
			return "fuck";
		}
		
		//make sure at first
		cursor.moveToFirst();
		String result = cursor.getString(cursor.getColumnIndex(ADDRESS_FIELD));
		
		//Always close the database.
		close();
		return result;
	}
	
	public String getStartDate(int marketID) {	//returns "fuck" if can't find market
		if (!doesExist(marketID))
			return "fuck";
		
		// Make sure we're all set up
		open();
		
		String query = "SELECT * FROM " + MarketDbOpenHelper.TABLE_NAME 
			+ " WHERE " + MARKET_ID_FIELD + "' = '" + marketID + "'";
		
		Cursor cursor = _database.rawQuery(query, null);
		
		if (cursor.getCount() == 0) {	//if there are no matches, return negative
			return "fuck";
		}
		
		//make sure at first
		cursor.moveToFirst();
		String result = cursor.getString(cursor.getColumnIndex(START_DATE_FIELD));
		
		//Always close the database.
		close();
		return result;
	}
	
	public String getEndDate(int marketID) {	//returns "fuck" if can't find market
		if (!doesExist(marketID))
			return "fuck";
		
		// Make sure we're all set up
		open();
		
		String query = "SELECT * FROM " + MarketDbOpenHelper.TABLE_NAME 
			+ " WHERE " + MARKET_ID_FIELD + "' = '" + marketID + "'";
		
		Cursor cursor = _database.rawQuery(query, null);
		
		if (cursor.getCount() == 0) {	//if there are no matches, return negative
			return "fuck";
		}
		
		//make sure at first
		cursor.moveToFirst();
		String result = cursor.getString(cursor.getColumnIndex(END_DATE_FIELD));
		
		//Always close the database.
		close();
		return result;
	}
	
	public String getStartTime(int marketID) {	//returns "fuck" if can't find market
		if (!doesExist(marketID))
			return "fuck";
		
		// Make sure we're all set up
		open();
		
		String query = "SELECT * FROM " + MarketDbOpenHelper.TABLE_NAME 
			+ " WHERE " + MARKET_ID_FIELD + "' = '" + marketID + "'";
		
		Cursor cursor = _database.rawQuery(query, null);
		
		if (cursor.getCount() == 0) {	//if there are no matches, return negative
			return "fuck";
		}
		
		//make sure at first
		cursor.moveToFirst();
		String result = cursor.getString(cursor.getColumnIndex(START_TIME_FIELD));
		
		//Always close the database.
		close();
		return result;
	}
	
	public String getEndTime(int marketID) {	//returns "fuck" if can't find market
		if (!doesExist(marketID))
			return "fuck";
		
		// Make sure we're all set up
		open();
		
		String query = "SELECT * FROM " + MarketDbOpenHelper.TABLE_NAME 
			+ " WHERE " + MARKET_ID_FIELD + "' = '" + marketID + "'";
		
		Cursor cursor = _database.rawQuery(query, null);
		
		if (cursor.getCount() == 0) {	//if there are no matches, return negative
			return "fuck";
		}
		
		//make sure at first
		cursor.moveToFirst();
		String result = cursor.getString(cursor.getColumnIndex(END_TIME_FIELD));
		
		//Always close the database.
		close();
		return result;
	}
	
	public String getNumberOfStalls(int marketID) {		//returns "fuck" if can't find market
		if (!doesExist(marketID))
			return "fuck";
		
		// Make sure we're all set up
		open();
		
		String query = "SELECT * FROM " + MarketDbOpenHelper.TABLE_NAME 
			+ " WHERE " + MARKET_ID_FIELD + "' = '" + marketID + "'";
		
		Cursor cursor = _database.rawQuery(query, null);
		
		if (cursor.getCount() == 0) {	//if there are no matches, return negative
			return "fuck";
		}
		
		//make sure at first
		cursor.moveToFirst();
		String result = cursor.getString(cursor.getColumnIndex(NUM_STALLS_FIELD));
		
		//Always close the database.
		close();
		return result;
	}
	
	public Market getMarket(int marketID) {
		// Make sure we're all set up
		open();
		
		Market result = null;
		
		// Make sure inputs aren't null
		if (marketID < 0) {
			result = null;
		} else {
			// Query the database
			String[] fields = { MARKET_NAME_FIELD, ADDRESS_FIELD, START_DATE_FIELD, END_DATE_FIELD,
					START_TIME_FIELD, END_TIME_FIELD, NUM_STALLS_FIELD, MARKET_ID_FIELD };
			String condition = MARKET_ID_FIELD + "='" + marketID + "'";
			Cursor cursor = _database.query(MarketDbOpenHelper.TABLE_NAME, fields, condition, null, null, null, null);
			
			// Does it exist?
			if (cursor == null)
				result = null;
			else if (cursor.getCount() == 0)
				result = null;
			else {
				// Get the first record
				cursor.moveToFirst();
				
				// Fill in the user data
				Market dbMarket = new Market();
				dbMarket.setName(cursor.getString(cursor.getColumnIndex(MARKET_NAME_FIELD)));
				dbMarket.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS_FIELD)));
				dbMarket.setStartDate(cursor.getString(cursor.getColumnIndex(START_DATE_FIELD)));
				dbMarket.setEndDate(cursor.getString(cursor.getColumnIndex(END_DATE_FIELD)));
				dbMarket.setStartTime(cursor.getString(cursor.getColumnIndex(START_TIME_FIELD)));
				dbMarket.setEndTime(cursor.getString(cursor.getColumnIndex(END_TIME_FIELD)));
				dbMarket.setNumberOfStalls(cursor.getInt(cursor.getColumnIndex(NUM_STALLS_FIELD)));
				dbMarket.setMarketID(cursor.getInt(cursor.getColumnIndex(MARKET_ID_FIELD)));
				result = dbMarket;
			}
		}
		close();
		return result;
	}
	
	public ArrayList<Market> getMarkets() {
		// Make sure we're all set up
		open();
		
		ArrayList<Market> resultList = new ArrayList<Market>();
		
		// Query the database
		//String[] fields = { MARKET_NAME_FIELD, ADDRESS_FIELD, START_DATE_FIELD, END_DATE_FIELD,
			//	START_TIME_FIELD, END_TIME_FIELD, NUM_STALLS_FIELD, MARKET_ID_FIELD };
		//String condition = TYPE_FIELD + "='" + UserType.Producer.toString() + "'";
		String query = "SELECT * FROM '" + MarketDbOpenHelper.TABLE_NAME + "'";
		Cursor cursor = _database.rawQuery(query, null);
		
		// Does it exist?
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				// Go to the first record
				cursor.moveToFirst();
				
				// Get all the results
				do {
					Market dbMarket = new Market();
					dbMarket.setName(cursor.getString(cursor.getColumnIndex(MARKET_NAME_FIELD)));
					dbMarket.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS_FIELD)));
					dbMarket.setStartDate(cursor.getString(cursor.getColumnIndex(START_DATE_FIELD)));
					dbMarket.setEndDate(cursor.getString(cursor.getColumnIndex(END_DATE_FIELD)));
					dbMarket.setStartTime(cursor.getString(cursor.getColumnIndex(START_TIME_FIELD)));
					dbMarket.setEndTime(cursor.getString(cursor.getColumnIndex(END_TIME_FIELD)));
					dbMarket.setNumberOfStalls(cursor.getInt(cursor.getColumnIndex(NUM_STALLS_FIELD)));
					dbMarket.setMarketID(cursor.getInt(cursor.getColumnIndex(MARKET_ID_FIELD)));
					
					resultList.add(dbMarket);
				} while (cursor.moveToNext());
			}
		}
		
		// Return what was found
		close();
		return resultList;
	}
}
