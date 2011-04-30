package com.communitymarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MarketDbOpenHelper extends SQLiteOpenHelper {
	private static final int 	DB_VERSION 	 = 2;
	private static final String DB_NAME		 = "communitymarket";
    public static final String TABLE_NAME 	 = "markets";
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME 
                + " (" + MarketDbAdapter.MARKET_NAME_FIELD + " TEXT, "
                + MarketDbAdapter.ADDRESS_FIELD + " TEXT, "
                + MarketDbAdapter.START_DATE_FIELD + " TEXT, "
                + MarketDbAdapter.END_DATE_FIELD + " TEXT, "
                + MarketDbAdapter.START_TIME_FIELD + " TEXT, "
                + MarketDbAdapter.END_TIME_FIELD + " TEXT, "
                + MarketDbAdapter.MARKET_ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MarketDbAdapter.NUM_STALLS_FIELD + " TEXT);";

    MarketDbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        
     // Create some fake markets
        ContentValues values = new ContentValues();
		values.put(MarketDbAdapter.MARKET_NAME_FIELD, "Lincoln Haymarket");
		values.put(MarketDbAdapter.START_DATE_FIELD, "May 7");
		values.put(MarketDbAdapter.END_DATE_FIELD, "October 15");
		values.put(MarketDbAdapter.START_TIME_FIELD, "8:00 AM");
		values.put(MarketDbAdapter.END_TIME_FIELD, "12:00 PM");
		values.put(MarketDbAdapter.ADDRESS_FIELD, "8th and P Street, Lincoln, NE, 68508");
        db.insert(TABLE_NAME, null, values);
        
        values.clear();
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2) {
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
