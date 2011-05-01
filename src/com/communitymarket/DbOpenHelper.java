package com.communitymarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {
	private static final int 	DB_VERSION 	 = 3;
	private static final String DB_NAME		 = "communitymarket";
    public  static final String USERS_TABLE_NAME 	 = "users";
    private static final String USERS_TABLE_CREATE = "CREATE TABLE " + USERS_TABLE_NAME 
                + " (" + LoginDbAdapter.TYPE_FIELD + " TEXT, "
                + LoginDbAdapter.NAME_FIELD + " TEXT, "
                + LoginDbAdapter.EMAIL_FIELD + " TEXT, "
                + LoginDbAdapter.USERNAME_FIELD + " TEXT, "
                + LoginDbAdapter.PASSWORD_FIELD + " TEXT, "
                + LoginDbAdapter.PRODUCTS_FIELD + " TEXT);";
    public  static final String RATINGS_TABLE_NAME 	 = "ratings";
    private static final String RATINGS_TABLE_CREATE = "CREATE TABLE " + RATINGS_TABLE_NAME 
                + " (" + RatingDbAdapter.USERNAME_FIELD + " TEXT, "
                + RatingDbAdapter.RATING_FIELD + " INT, "
                + RatingDbAdapter.FARMER_ID_FIELD + " TEXT);";
    
    private static DbOpenHelper _instance;
    
    protected DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    
    public static DbOpenHelper getInstance(Context context) {
		if (_instance == null) {
			_instance = new DbOpenHelper(context);
		}
		return _instance;
	}
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(RATINGS_TABLE_CREATE);
        
        // Create some fake users
        ContentValues values = new ContentValues();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Producer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "John Johnston");
		values.put(LoginDbAdapter.EMAIL_FIELD, "john@johnstonfamily.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "jjohnston");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "hello1234");
		values.put(LoginDbAdapter.PRODUCTS_FIELD, "Corn, Wheat");
        db.insert(USERS_TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Producer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Liz Lemon");
		values.put(LoginDbAdapter.EMAIL_FIELD, "liz@lizlemon.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "llemon");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "hello1234");
		values.put(LoginDbAdapter.PRODUCTS_FIELD, "Lemons, Carrots");
        db.insert(USERS_TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Producer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Paul Newman");
		values.put(LoginDbAdapter.EMAIL_FIELD, "paul@newmanfarms.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "pnewman");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "hello1234");
		values.put(LoginDbAdapter.PRODUCTS_FIELD, "Salad Dressing");
        db.insert(USERS_TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Producer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Barry Griffen");
		values.put(LoginDbAdapter.EMAIL_FIELD, "barry@griffenunited.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "bgriffen");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "hello1234");
		values.put(LoginDbAdapter.PRODUCTS_FIELD, "Tomatoes, Grapes, Wine");
        db.insert(USERS_TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Producer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Sandra Williams");
		values.put(LoginDbAdapter.EMAIL_FIELD, "sandra@williamsfarms.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "swilliams");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "hello1234");
		values.put(LoginDbAdapter.PRODUCTS_FIELD, "Kumquats, Birdhouses");
        db.insert(USERS_TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Producer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Phineas Applebee");
		values.put(LoginDbAdapter.EMAIL_FIELD, "phinster@applebees.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "papplebee");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "hello1234");
		values.put(LoginDbAdapter.PRODUCTS_FIELD, "Quince");
        db.insert(USERS_TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Producer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Jerry Atric");
		values.put(LoginDbAdapter.EMAIL_FIELD, "jerry@atricinternational.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "jatric");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "hello1234");
		values.put(LoginDbAdapter.PRODUCTS_FIELD, "Squash, Cucumber, Red Onions");
        db.insert(USERS_TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Coordinator.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Cody Leach");
		values.put(LoginDbAdapter.EMAIL_FIELD, "codyleach@gmail.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "codyleach");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "Hello");
        db.insert(USERS_TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Consumer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Diego Moreno");
		values.put(LoginDbAdapter.EMAIL_FIELD, "d@g.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "dmorenoh");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "Diego");
        db.insert(USERS_TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Consumer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Daniel Sveen");
		values.put(LoginDbAdapter.EMAIL_FIELD, "dsveen@gmail.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "Daniel");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "Daniel");
        db.insert(USERS_TABLE_NAME, null, values);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2) {
    	db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
    	db.execSQL("DROP TABLE IF EXISTS " + RATINGS_TABLE_NAME);
        onCreate(db);
    }
}
