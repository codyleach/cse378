package com.communitymarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RatingDbAdapter {
	// Field Names
	public static String FARMER_ID_FIELD = "farmerID";
	public static String RATING_FIELD	= "rating";
	public static String USERNAME_FIELD = "username";		//Username should be unique, no need for ID
	
	// Variables
	private RatingDbOpenHelper _dbOpener;
	private SQLiteDatabase	  _database;
	private Context			  _context;
	
	/**
	 * Constructor
	 */
	public RatingDbAdapter(Context context) {
		_context = context;
	}
	
	public void open() {
		if (_dbOpener == null) {
			_dbOpener = new RatingDbOpenHelper(_context);
		}
		
		if (_database == null) {
			_database = _dbOpener.getWritableDatabase();
		}
	}
	
	public boolean addRating(int farmer, int rating, String username) {
		// Make sure we're all set up
		open();
		
		// Make sure the inputs aren't null
		if (username.equals("")) {
			return false;
		}
		
		ContentValues values = new ContentValues();
		values.put(FARMER_ID_FIELD, farmer);
		values.put(RATING_FIELD, rating);
		values.put(USERNAME_FIELD, username);
		
		_database.insert(RatingDbOpenHelper.TABLE_NAME, null, values);
		
		return true;
	}
	
	public boolean modifyRating(int farmer, int rating, String username) {
		// Make sure we're all set up
		open();
		
		// Make sure inputs aren't null
		if (username.equals(""))
			return false;
		
		//Query the database
		_database.execSQL("UPDATE " + RatingDbOpenHelper.TABLE_NAME + " SET rating = " +
				rating + " WHERE farmerID = " + farmer + 
				" AND username = " + username + ";");
		
		return true;
	}
}
