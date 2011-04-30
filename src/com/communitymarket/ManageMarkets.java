package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ManageMarkets extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_markets);
        
        // Add New Market Button
        final Button addNewButton = (Button) findViewById(R.id.manage_add_new_button);
        if (addNewButton != null) {
        	addNewButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Intent intent = new Intent(ManageMarkets.this, AddNewMarket.class);
    				startActivityForResult(intent, 0);
                }
            });
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
    	MenuItem item = menu.findItem(R.id.log_out);
    	if (item != null) {
    		// Is the user logged in?
    		if (LoginDbAdapter.getCurrentUser() == null)
    			item.setEnabled(false);
    		else
    			item.setEnabled(true);
    	}
    	
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.go_home:
            finish();
            return true;
        case R.id.log_out:
        	LoginDbAdapter.logout();
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (resultCode == Activity.RESULT_OK) {
    		if (data.getBooleanExtra("saved", false)) {
    			// Get the new market name
    			String newName = data.getStringExtra("name");
    			
    			if (!newName.trim().equals("")) {
    				// Get the new button
    				Button newButton = (Button) findViewById(R.id.recently_added_button);
    				if (newButton != null) {
    					newButton.setText(newName);
    					newButton.setVisibility(View.VISIBLE);
    				}
    			}
    		}
    	}
    }
}
