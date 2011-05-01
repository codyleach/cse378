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
	
	private static MarketDbAdapter _instance;
	
	// Variables
	private DbOpenHelper 	_dbOpener;
	private SQLiteDatabase	_database;
	private Context			_context;
	
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
			_dbOpener = DbOpenHelper.getInstance(_context);
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
	
	public boolean updateMarket(String query) {
		open();
		if (query.equals(""))
			return false;
		_database.rawQuery(query, null);
		
		return true;
		
	}
	
	public boolean addMarket(Market market) {
		open();
		if (market.getName().equals("") 
				|| market.getAddress().equals("") 
				|| market.getStartDate().equals("")
				|| market.getEndDate().equals("") 
				|| market.getStartTime().equals("") 
				|| market.getEndTime().equals(""))
			return false;
		
		ContentValues values = new ContentValues();
		values.put(MARKET_NAME_FIELD, market.getName());
		values.put(ADDRESS_FIELD, market.getAddress());
		values.put(START_DATE_FIELD, market.getStartDate());
		values.put(END_DATE_FIELD, market.getEndDate());
		values.put(START_TIME_FIELD, market.getStartTime());
		values.put(END_TIME_FIELD, market.getEndTime());
		values.put(NUM_STALLS_FIELD, market.getNumberOfStalls());
		
		// Does this market already exist?
		if (doesExist(market.getMarketID())) {
			String condition = MARKET_ID_FIELD + "='" + market.getMarketID() + "'";
			_database.update(DbOpenHelper.MARKETS_TABLE_NAME, values, 
					condition, null);
		} else {
	        _database.insert(DbOpenHelper.MARKETS_TABLE_NAME, null, values);
		}
		close();
		return true;
	}
	
	public boolean doesExist(int marketID) {	//returns true if does exist
		open();
		String query = "SELECT * FROM " + DbOpenHelper.MARKETS_TABLE_NAME 
		+ " WHERE " + MARKET_ID_FIELD + " = '" + marketID + "'";
	
		Cursor cursor = _database.rawQuery(query, null);
		
		if (cursor.getCount() == 0)		//if there are no matches, return negative
			return false;
		return true;
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
			Cursor cursor = _database.query(DbOpenHelper.MARKETS_TABLE_NAME, fields, condition, null, null, null, null);
			
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
		String query = "SELECT * FROM '" + DbOpenHelper.MARKETS_TABLE_NAME + "'";
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
