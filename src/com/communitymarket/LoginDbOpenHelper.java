package com.communitymarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDbOpenHelper extends SQLiteOpenHelper {
	private static final int 	DB_VERSION 	 = 3;
	private static final String DB_NAME		 = "communitymarket";
    public  static final String TABLE_NAME 	 = "users";
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME 
                + " (" + LoginDbAdapter.TYPE_FIELD + " TEXT, "
                + LoginDbAdapter.NAME_FIELD + " TEXT, "
                + LoginDbAdapter.EMAIL_FIELD + " TEXT, "
                + LoginDbAdapter.USERNAME_FIELD + " TEXT, "
                + LoginDbAdapter.PASSWORD_FIELD + " TEXT, "
                + LoginDbAdapter.PRODUCTS_FIELD + " TEXT);";

    LoginDbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        
        // Create some fake users
        ContentValues values = new ContentValues();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Producer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "John Johnston");
		values.put(LoginDbAdapter.EMAIL_FIELD, "john@johnstonfamily.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "jjohnston");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "hello1234");
		values.put(LoginDbAdapter.PRODUCTS_FIELD, "Corn, Wheat");
        db.insert(TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Producer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Liz Lemon");
		values.put(LoginDbAdapter.EMAIL_FIELD, "liz@lizlemon.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "llemon");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "hello1234");
		values.put(LoginDbAdapter.PRODUCTS_FIELD, "Lemons, Carrots");
        db.insert(TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Producer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Paul Newman");
		values.put(LoginDbAdapter.EMAIL_FIELD, "paul@newmanfarms.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "pnewman");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "hello1234");
		values.put(LoginDbAdapter.PRODUCTS_FIELD, "Salad Dressing");
        db.insert(TABLE_NAME, null, values);
        
        values.clear();
		values.put(LoginDbAdapter.TYPE_FIELD, UserType.Producer.toString());
		values.put(LoginDbAdapter.NAME_FIELD, "Barry Griffen");
		values.put(LoginDbAdapter.EMAIL_FIELD, "barry@griffenunited.com");
		values.put(LoginDbAdapter.USERNAME_FIELD, "bgriffen");
		values.put(LoginDbAdapter.PASSWORD_FIELD, "hello1234");
		values.put(LoginDbAdapter.PRODUCTS_FIELD, "Tomatoes, Grapes, Wine");
        db.insert(TABLE_NAME, null, values);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2) {
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
