package com.communitymarket;

import java.util.ArrayList;

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
	private DbOpenHelper _dbOpener;
	private SQLiteDatabase	  _database;
	private Context			  _context;
	
	// Current user
	private static User _currentUser;
	private static LoginDbAdapter _instance;
	
	/**
	 * Constructor
	 * 
	 * Is protected to ensure that only the singleton exists.
	 */
	protected LoginDbAdapter() { }
	
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
			_dbOpener = DbOpenHelper.getInstance(_context);
		}
		
		if (_database == null) {
			_database = _dbOpener.getWritableDatabase();
		}
	}
	
	public void close() {
		if (_database != null)
			_database.close();
		_database = null;
	}
	
	public boolean addUser(User user) {
		// Make sure we're all set up
		open();
		
		// Make sure the inputs aren't null
		if (user.getName().equals("") || user.getUsername().equals("") || 
			user.getEmail().equals("") || user.getPassword().equals("")) {
			close();
			return false;
		}
		
		ContentValues values = new ContentValues();
		values.put(TYPE_FIELD, user.getType().toString());
		values.put(NAME_FIELD, user.getName());
		values.put(EMAIL_FIELD, user.getEmail());
		values.put(USERNAME_FIELD, user.getUsername());
		values.put(PASSWORD_FIELD, user.getPassword());
		values.put(PRODUCTS_FIELD, user.getProducts());
		
		long result = _database.insert(DbOpenHelper.USERS_TABLE_NAME, null, values);
		
		if (result != -1) {
			user.setPassword("");
			_currentUser = user;
		}
		
		close();
		
		return true;
	}
	
	public boolean doesUserExist(String username) {
		// Make sure we're all set up
		open();
		
		boolean result = false;
		
		// Make sure input isn't null
		if (username.equals("")) {
			result = false;
		} else {
			// Query the database
			String[] fields = { USERNAME_FIELD };
			String condition = USERNAME_FIELD + "='" + username + "'";
			Cursor cursor = _database.query(DbOpenHelper.USERS_TABLE_NAME, fields, condition, null, null, null, null);
			
			// Does it exist?
			if (cursor == null)
				result = false;
			else if (cursor.getCount() == 0)
				result = false;
			else
				result = true;
		}
		
		close();
		return result;
	}
	
	public User getUser(String username) {
		// Make sure we're all set up
		open();
		
		User result = null;
		
		// Make sure inputs aren't null
		if (username.equals("")) {
			result = null;
		} else {
			// Query the database
			String[] fields = { TYPE_FIELD, NAME_FIELD, EMAIL_FIELD, USERNAME_FIELD, PASSWORD_FIELD, PRODUCTS_FIELD };
			String condition = USERNAME_FIELD + "='" + username + "'";
			Cursor cursor = _database.query(DbOpenHelper.USERS_TABLE_NAME, fields, condition, null, null, null, null);
			
			// Does it exist?
			if (cursor == null)
				result = null;
			else if (cursor.getCount() == 0)
				result = null;
			else {
				// Get the first record
				cursor.moveToFirst();
				
				// Fill in the user data
				User dbUser = new User();
				dbUser.setType(cursor.getString(cursor.getColumnIndex(TYPE_FIELD)));
				dbUser.setName(cursor.getString(cursor.getColumnIndex(NAME_FIELD)));
				dbUser.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_FIELD)));
				dbUser.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME_FIELD)));
				dbUser.setProducts(cursor.getString(cursor.getColumnIndex(PRODUCTS_FIELD)));
				result = dbUser;
			}
		}
		close();
		return result;
	}
	
	public User authUser(String username, String password) {
		// Make sure we're all set up
		open();
		
		User result = null;
		
		// Make sure inputs aren't null
		if (username.equals("") || password.equals("")) {
			result = null;
		} else {
			// Query the database
			String[] fields = { TYPE_FIELD, NAME_FIELD, EMAIL_FIELD, USERNAME_FIELD, PASSWORD_FIELD, PRODUCTS_FIELD };
			String condition = USERNAME_FIELD + "='" + username + "'";
			Cursor cursor = _database.query(DbOpenHelper.USERS_TABLE_NAME, fields, condition, null, null, null, null);
			
			// Does it exist?
			if (cursor == null)
				result = null;
			else if (cursor.getCount() == 0)
				result = null;
			else {
				// Get the first record
				cursor.moveToFirst();
				
				// Check the password
				String dbPassword = cursor.getString(cursor.getColumnIndex(PASSWORD_FIELD));
				if (dbPassword.equals(password)) {
					User dbUser = new User();
					dbUser.setType(cursor.getString(cursor.getColumnIndex(TYPE_FIELD)));
					dbUser.setName(cursor.getString(cursor.getColumnIndex(NAME_FIELD)));
					dbUser.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_FIELD)));
					dbUser.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME_FIELD)));
					dbUser.setProducts(cursor.getString(cursor.getColumnIndex(PRODUCTS_FIELD)));
					_currentUser = dbUser;
					result = dbUser;
				} else
					result = null;
			}
		}
		close();
		return result;
	}
	
	public ArrayList<User> getFarmerUsers(String searchTerm) {
		// Make sure we're all set up
		open();
		
		ArrayList<User> resultList = new ArrayList<User>();
		
		// Query the database
		String[] fields = { TYPE_FIELD, NAME_FIELD, EMAIL_FIELD, USERNAME_FIELD, PASSWORD_FIELD, PRODUCTS_FIELD };
		String condition = TYPE_FIELD + "='" + UserType.Producer.toString() + "' AND "
			+ NAME_FIELD + " LIKE '%" + searchTerm + "%'";
		Cursor cursor = _database.query(DbOpenHelper.USERS_TABLE_NAME, fields, condition, null, null, null, null,
				(searchTerm.equals("") ? "5" : null));
		
		// Does it exist?
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				// Go to the first record
				cursor.moveToFirst();
				
				// Get all the results
				do {
					User dbUser = new User();
					dbUser.setType(cursor.getString(cursor.getColumnIndex(TYPE_FIELD)));
					dbUser.setName(cursor.getString(cursor.getColumnIndex(NAME_FIELD)));
					dbUser.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_FIELD)));
					dbUser.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME_FIELD)));
					dbUser.setProducts(cursor.getString(cursor.getColumnIndex(PRODUCTS_FIELD)));
					
					resultList.add(dbUser);
				} while (cursor.moveToNext());
			}
		}
		
		// Return what was found
		close();
		return resultList;
	}
	
	public ArrayList<User> getFarmerUsers() {
		return getFarmerUsers("");
	}
}
