package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class editMarket extends Activity {
	private static final int LOGIN_REQUEST = 0;
	private MarketDbAdapter marketDB;
	private Market _market;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_market);
        
        // Get the market
        final int marketID = getIntent().getExtras().getInt("market");
        
        // If no farmer given, then get out
        if (marketID < 0)
        	finish();
        
        // Get the farmer data
        _market = MarketDbAdapter.getInstance(this.getApplicationContext()).getMarket(marketID);
        
        if (_market == null)
        	finish();
        
        // Populate the farmer data
        populateMarketData();
        
        
        // Save Button
        final Button saveButton = (Button) findViewById(R.id.add_new_market_save_button);
        if (saveButton != null) {
        	saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                	// Get the new market name
//                	EditText nameEditText = (EditText) findViewById(R.id.add_new_market_name);
//                	String newName = "";
//                	if (nameEditText != null)
//                		newName = nameEditText.getText().toString();
//                	
                	//update market data
                    updateMarketData(marketID);
                    
                    // Perform action on click
                	Intent intent = new Intent();
                	intent.putExtra("saved", true);
//                	intent.putExtra("name", newName.trim());
    				setResult(Activity.RESULT_OK, intent);
    				finish();
                }
            });
        }
        
        // Cancel Button
        final Button cancelButton = (Button) findViewById(R.id.add_new_market_cancel_button);
        if (cancelButton != null) {
        	cancelButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Intent intent = new Intent();
                	intent.putExtra("saved", false);
    				setResult(Activity.RESULT_OK, intent);
    				finish();
                }
            });
        }
    }
    
    
    private void populateMarketData() {
    	if (_market == null)
    		return;
    	
    	EditText name = (EditText) findViewById(R.id.add_new_market_name);
    	name.setText(_market.getName());
    	
    	EditText address = (EditText) findViewById(R.id.add_new_address);
    	address.setText(_market.getAddress());
    	
    	EditText startDate = (EditText) findViewById(R.id.add_new_start_date);
    	startDate.setText(_market.getStartDate());
    	
    	EditText endDate = (EditText) findViewById(R.id.add_new_end_date);
    	endDate.setText(_market.getEndDate());
    	
    	EditText startTime = (EditText) findViewById(R.id.add_new_start_time);
    	startTime.setText(_market.getStartTime());
    	
    	EditText endTime = (EditText) findViewById(R.id.add_new_end_time);
    	endTime.setText(_market.getEndTime());
    	
    	EditText numStalls = (EditText) findViewById(R.id.add_new_number_stalls);
    	numStalls.setText(_market.getNumberOfStalls());
    	
   
    }
    
    private void updateMarketData(int marketID) {
    	String sName;
    	String sAddress;
    	String sStartDate;
    	String sEndDate;
    	String sStartTime;
    	String sEndTime;
    	String sNumStalls;
    	String sID = Integer.toString(marketID);
    	String query;
    	
    	EditText name = (EditText) findViewById(R.id.add_new_market_name);
    	sName = name.getText().toString();
    	
    	EditText address = (EditText) findViewById(R.id.add_new_address);
    	sAddress = address.getText().toString();
    	
    	EditText startDate = (EditText) findViewById(R.id.add_new_start_date);
    	sStartDate = startDate.getText().toString();
    	
    	EditText endDate = (EditText) findViewById(R.id.add_new_end_date);
    	sEndDate = endDate.getText().toString();
    	
    	EditText startTime = (EditText) findViewById(R.id.add_new_start_time);
    	sStartTime = startTime.getText().toString();
    	
    	EditText endTime = (EditText) findViewById(R.id.add_new_end_time);
    	sEndTime = endTime.getText().toString();
    	
    	EditText numStalls = (EditText) findViewById(R.id.add_new_number_stalls);
    	sNumStalls = numStalls.getText().toString();
    	
    	query = "UPDATE " + MarketDbOpenHelper.TABLE_NAME + " SET " 
    		+ MarketDbAdapter.MARKET_NAME_FIELD + " = '" + sName
    		+ "', " + MarketDbAdapter.ADDRESS_FIELD + " = '" + sAddress
    		+ "', " + MarketDbAdapter.START_DATE_FIELD + " = '" + sStartDate
    		+ "', " + MarketDbAdapter.END_DATE_FIELD + " = '" + sEndDate
    		+ "', " + MarketDbAdapter.START_TIME_FIELD + " = '" + sStartTime
    		+ "', " + MarketDbAdapter.END_TIME_FIELD + " = '" + sEndTime
    		+ "', " + MarketDbAdapter.NUM_STALLS_FIELD + " = '" + sNumStalls
    		+ "' WHERE " + MarketDbAdapter.MARKET_ID_FIELD + " = '" + sID + "'";
    	
    	marketDB.updateMarket(query);
    }
    
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.go_home:
            Intent intent = new Intent(editMarket.this, UserMenu.class);
            intent.putExtra("usertype", UserType.Consumer);
            startActivityForResult(intent, 0);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
