package com.communitymarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LoginDbAdapter {
	// Field Names
	public static String USERNAME_FIELD = "username";
	public static String EMAIL_FIELD	= "email";
	public static String PASSWORD_FIELD = "password";
	
	// Variables
	private LoginDbOpenHelper _dbOpener;
	private SQLiteDatabase	  _database;
	private Context			  _context;
	
	/**
	 * Constructor
	 */
	public LoginDbAdapter(Context context) {
		_context = context;
	}
	
	public void open() {
		if (_dbOpener == null) {
			_dbOpener = new LoginDbOpenHelper(_context);
		}
		
		if (_database == null) {
			_database = _dbOpener.getWritableDatabase();
		}
	}
	
	public boolean addUser(String username, String email, String password) {
		// Make sure we're all set up
		open();
		
		// Make sure the inputs aren't null
		if (username.equals("") || email.equals("") || password.equals("")) {
			return false;
		}
		
		ContentValues values = new ContentValues();
		values.put(USERNAME_FIELD, username);
		values.put(EMAIL_FIELD, email);
		values.put(PASSWORD_FIELD, password);
		
		_database.insert(LoginDbOpenHelper.TABLE_NAME, null, values);
		
		return true;
	}
	
	public boolean authUser(String username, String password) {
		// Make sure we're all set up
		open();
		
		// Make sure inputs aren't null
		if (username.equals("") || password.equals(""))
			return false;
		
		// Query the database
		String[] fields = { USERNAME_FIELD, PASSWORD_FIELD };
		String condition = USERNAME_FIELD + "='" + username + "'";
		Cursor cursor = _database.query(LoginDbOpenHelper.TABLE_NAME, fields, condition, null, null, null, null);
		
		// Does it exist?
		if (cursor == null)
			return false;
		if (cursor.getCount() == 0)
			return false;
		
		// Get the first record
		cursor.moveToFirst();
		
		// Check the password
		if (cursor.getString(cursor.getColumnIndex(PASSWORD_FIELD)).equals(password))
			return true;
		return false;
	}
}
