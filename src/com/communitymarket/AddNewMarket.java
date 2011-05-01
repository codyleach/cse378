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

public class AddNewMarket extends Activity {
	private MarketDbAdapter marketDB;
	private Market _market;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_market);
        
        // Save Button
        final Button saveButton = (Button) findViewById(R.id.add_new_market_save_button);
        if (saveButton != null) {
        	saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	// Get the new market name
                	EditText nameEditText = (EditText) findViewById(R.id.add_new_market_name);
                	createMarket();
                	
                    // Perform action on click
                	Intent intent = new Intent();
                	intent.putExtra("saved", true);
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
    
    private void createMarket() {
    	String sName;
    	String sAddress;
    	String sStartDate;
    	String sEndDate;
    	String sStartTime;
    	String sEndTime;
    	String sNumStalls;
    	
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
    	
    	marketDB.addMarket(sName, sAddress, sStartDate, sEndDate, sStartTime, sEndTime, sNumStalls);
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
        	Intent data = new Intent();
        	data.putExtra("gohome", true);
        	setResult(RESULT_OK, data);
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (intent != null) {
			boolean goHome = intent.getBooleanExtra("gohome", false);
			if (goHome)
				leave();
		}
    }
    
    private void leave() {
    	Intent data = new Intent();
    	data.putExtra("gohome", true);
    	setResult(RESULT_OK, data);
        finish();
    }
}
