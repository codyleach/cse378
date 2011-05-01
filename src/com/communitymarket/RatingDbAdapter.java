package com.communitymarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RatingDbAdapter {
	// Field Names
	public static String FARMER_ID_FIELD = "farmerID";
	public static String RATING_FIELD	= "rating";
	public static String USERNAME_FIELD = "username";		//Username should be unique, no need for ID
	
	// Variables
	private DbOpenHelper _dbOpener;
	private SQLiteDatabase	  _database;
	private Context			  _context;
	
	/**
	 * Constructor
	 */
	public RatingDbAdapter(Context context) {
		_context = context;
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
	
	public boolean addRating(String farmer, int rating, String username) {
		// Make sure we're all set up
		open();
		
		// Make sure the inputs aren't null
		if (username.equals("") || farmer.equals("")) {
			close();
			return false;
		}
		
		String sqlQuery = "Select * FROM " + DbOpenHelper.RATINGS_TABLE_NAME +
			" WHERE farmerID = '" + farmer + "' AND username = '" + username + "'";
		
		Cursor cursor = _database.rawQuery(sqlQuery, null);
		
		if (cursor.getCount() == 0) {
			ContentValues values = new ContentValues();
			values.put(FARMER_ID_FIELD, farmer);
			values.put(RATING_FIELD, rating);
			values.put(USERNAME_FIELD, username);
			_database.insert(DbOpenHelper.RATINGS_TABLE_NAME, null, values);
		}
		else {
			_database.execSQL("UPDATE " + DbOpenHelper.RATINGS_TABLE_NAME + " SET rating = '" +
					rating + "' WHERE farmerID = '" + farmer + 
					"' AND username = '" + username + "';");
		}
		close();
		return true;
	}
	
	
	public int getRating(String farmer, String username) {
		// Make sure we're all set up
		open();
		
		// Make sure the inputs aren't null
		if (username.equals("") || farmer.equals("")) {
			close();
			return 0;
		}
		
		String sqlQuery = "Select * FROM " + DbOpenHelper.RATINGS_TABLE_NAME +
		" WHERE farmerID = '" + farmer + "' AND username = '" + username + "'";
	
		Cursor cursor = _database.rawQuery(sqlQuery, null);
		
		//check if empty
		if (cursor.getCount() == 0) {
			close();
			return 0;
		}
		
		//make sure at first
		cursor.moveToFirst();
		int result = cursor.getInt(cursor.getColumnIndex(RATING_FIELD));
		close();
		return result;
		
	}
	
	public float[] getAvgRating(String farmer) {
		// Make sure we're all set up
		open();
		float[] result = new float[] {0,0};
		
		// Make sure the inputs aren't null
		if (farmer.equals("")) {
			close();
			return result;
		}
		
		String sqlQuery = "Select count(" + RatingDbAdapter.RATING_FIELD + "), total("
			+ RatingDbAdapter.RATING_FIELD + ") FROM " + DbOpenHelper.RATINGS_TABLE_NAME +
		" WHERE farmerID = '" + farmer + "'";
	
		Cursor cursor = _database.rawQuery(sqlQuery, null);
		
		//check if empty
		if (cursor.getCount() == 0) {
			close();
			return result;
		}
		
		//make sure at first
		cursor.moveToFirst();
		result[0] = cursor.getInt(0);
		result[1] = cursor.getInt(1);
		close();
		
		// Find the average
		if (result[0] > 0) {
			result[1] = result[1] / result[0];
		}
		
		return result;
		
	}
	
//	public boolean modifyRating(String farmer, int rating, String username) {
//		// Make sure we're all set up
//		open();
//		
//		// Make sure inputs aren't null
//		if (username.equals("") || farmer.equals(""))
//			return false;
//		
//		//Query the database
//		_database.execSQL("UPDATE " + RatingDbOpenHelper.TABLE_NAME + " SET rating = " +
//				rating + " WHERE farmerID = " + farmer + 
//				" AND username = " + username + ";");
//		
//		return true;
//	}
}
