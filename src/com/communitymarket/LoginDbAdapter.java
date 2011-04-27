package com.communitymarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LoginDbAdapter {
	// Field Names
	public static String TYPE_FIELD 	= "type";
	public static String NAME_FIELD 	= "name";
	public static String EMAIL_FIELD	= "email";
	public static String USERNAME_FIELD = "username";
	public static String PASSWORD_FIELD = "password";
	public static String PRODUCTS_FIELD = "products";
	
	// Variables
	private LoginDbOpenHelper _dbOpener;
	private SQLiteDatabase	  _database;
	private Context			  _context;
	
	// Current user
	private static User _currentUser;
	private static LoginDbAdapter _instance;
	
	protected LoginDbAdapter() {
		// Ensures that only the singleton exists
	}
	
	public static LoginDbAdapter getInstance(Context context) {
		if (_instance == null) {
			_instance = new LoginDbAdapter();
		}
		_instance._context = context;
		return _instance;
	}
	
	public static User getCurrentUser() {
		return _currentUser;
	}
	
	public static void logout() {
		_currentUser = null;
	}
	
	public void open() {
		if (_dbOpener == null) {
			_dbOpener = new LoginDbOpenHelper(_context);
		}
		
		if (_database == null) {
			_database = _dbOpener.getWritableDatabase();
		}
	}
	
	public boolean addUser(User user) {
		// Make sure we're all set up
		open();
		
		// Make sure the inputs aren't null
		if (user.getName().equals("") || user.getUsername().equals("") || 
			user.getEmail().equals("") || user.getPassword().equals("")) {
			return false;
		}
		
		ContentValues values = new ContentValues();
		values.put(TYPE_FIELD, user.getType().toString());
		values.put(NAME_FIELD, user.getName());
		values.put(EMAIL_FIELD, user.getEmail());
		values.put(USERNAME_FIELD, user.getUsername());
		values.put(PASSWORD_FIELD, user.getPassword());
		values.put(PRODUCTS_FIELD, user.getProducts());
		
		long result = _database.insert(LoginDbOpenHelper.TABLE_NAME, null, values);
		
		if (result != -1) {
			user.setPassword("");
			_currentUser = user;
		}
		
		return true;
	}
	
	public boolean doesUserExist(String username) {
		// Make sure we're all set up
		open();
		
		// Make sure input isn't null
		if (username.equals(""))
			return false;
		
		// Query the database
		String[] fields = { USERNAME_FIELD };
		String condition = USERNAME_FIELD + "='" + username + "'";
		Cursor cursor = _database.query(LoginDbOpenHelper.TABLE_NAME, fields, condition, null, null, null, null);
		
		// Does it exist?
		if (cursor == null)
			return false;
		if (cursor.getCount() == 0)
			return false;
		else
			return true;
	}
	
	public User authUser(String username, String password) {
		// Make sure we're all set up
		open();
		
		// Make sure inputs aren't null
		if (username.equals("") || password.equals(""))
			return null;
		
		// Query the database
		String[] fields = { TYPE_FIELD, NAME_FIELD, EMAIL_FIELD, USERNAME_FIELD, PASSWORD_FIELD, PRODUCTS_FIELD };
		String condition = USERNAME_FIELD + "='" + username + "'";
		Cursor cursor = _database.query(LoginDbOpenHelper.TABLE_NAME, fields, condition, null, null, null, null);
		
		// Does it exist?
		if (cursor == null)
			return null;
		if (cursor.getCount() == 0)
			return null;
		
		// Get the first record
		cursor.moveToFirst();
		
		// Check the password
		String dbPassword = cursor.getString(cursor.getColumnIndex(PASSWORD_FIELD));
		if (dbPassword.equals(password)) {
			User dbUser = new User();
			dbUser.setType(cursor.getString(cursor.getColumnIndex(TYPE_FIELD)));
			dbUser.setName(cursor.getString(cursor.getColumnIndex(NAME_FIELD)));
			dbUser.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_FIELD)));
			dbUser.setUsername(cursor.getString(cursor.getColumnIndex(TYPE_FIELD)));
			dbUser.setProducts(cursor.getString(cursor.getColumnIndex(PRODUCTS_FIELD)));
			_currentUser = dbUser;
			return dbUser;
		}
		_currentUser = null;
		return null;
	}
}
