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
import android.widget.TextView;

public class AddNewMarket extends Activity {
	private MarketDbAdapter marketDB;
	private Market _market;
	private int _marketId = -1;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_market);
        marketDB = new MarketDbAdapter(this.getApplicationContext());
        
        // Is this an edit or an add?
        if (getIntent().getExtras() != null) {
	        _marketId = getIntent().getExtras().getInt("market", -1);
	        if (_marketId >= 0) {
	        	_market = marketDB.getMarket(_marketId);
	        	fillData();
	        }
        }
        
        // Save Button
        final Button saveButton = (Button) findViewById(R.id.add_new_market_save_button);
        if (saveButton != null) {
        	saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	// Get the new market name
                	EditText nameEditText = (EditText) findViewById(R.id.add_new_market_name);
                	if (!nameEditText.getText().toString().equals(""))
                		saveMarket();
                	
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
    
    private void fillData() {
    	// Change the title
    	TextView title = (TextView) findViewById(R.id.add_new_market_title);
    	title.setText("Update Market");
    	
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
    	numStalls.setText(_market.getNumberOfStalls() + "");
    }
    
    private void saveMarket() {
    	Market newMarket = new Market();
    	
    	if (_marketId >= 0)
    		newMarket.setMarketID(_marketId);
    	
    	EditText name = (EditText) findViewById(R.id.add_new_market_name);
    	newMarket.setName(name.getText().toString());
    	
    	EditText address = (EditText) findViewById(R.id.add_new_address);
    	newMarket.setAddress(address.getText().toString());
    	
    	EditText startDate = (EditText) findViewById(R.id.add_new_start_date);
    	newMarket.setStartDate(startDate.getText().toString());
    	
    	EditText endDate = (EditText) findViewById(R.id.add_new_end_date);
    	newMarket.setEndDate(endDate.getText().toString());
    	
    	EditText startTime = (EditText) findViewById(R.id.add_new_start_time);
    	newMarket.setStartTime(startTime.getText().toString());
    	
    	EditText endTime = (EditText) findViewById(R.id.add_new_end_time);
    	newMarket.setEndTime(endTime.getText().toString());
    	
    	EditText numStalls = (EditText) findViewById(R.id.add_new_number_stalls);
    	newMarket.setNumberOfStalls(Integer.parseInt(numStalls.getText().toString()));
    	
    	marketDB.addMarket(newMarket);
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
        	leave();
            return true;
        case R.id.log_out:
        	LoginDbAdapter.logout();
        	leave();
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
