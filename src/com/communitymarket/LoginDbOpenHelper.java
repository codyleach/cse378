package com.communitymarket;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDbOpenHelper extends SQLiteOpenHelper {
	private static final int 	DB_VERSION 	 = 1;
	private static final String DB_NAME		 = "communitymarket";
    public static final String TABLE_NAME 	 = "users";
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME 
                + " (" + LoginDbAdapter.USERNAME_FIELD + " TEXT, "
                + LoginDbAdapter.EMAIL_FIELD + " TEXT, "
                + LoginDbAdapter.PASSWORD_FIELD + " TEXT);";

    LoginDbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2) {
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
