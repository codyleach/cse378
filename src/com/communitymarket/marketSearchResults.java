package com.communitymarket;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class marketSearchResults extends Activity {
	
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_search_results); 
        
        
    	
        
      //Get Info Button
        final Button infoButton = (Button) findViewById(R.id.getInfoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent intent = new Intent(marketSearchResults.this, marketInformation.class);
            	intent.putExtra("usertype", UserType.Consumer);
				startActivityForResult(intent, 0);
            }
        });
        
      //Driving Directions Button
        final Button drivingDirectionsButton = (Button) findViewById(R.id.drivingDirectionsButton);
        drivingDirectionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Bundle extras = getIntent().getExtras();
            	String marketSearch = extras.getString("searchResult");
                // Perform action on click
            	/*Intent intent = new Intent(marketSearchResults.this, drivingDirections.class);
            	intent.putExtra("usertype", UserType.Consumer);
				startActivityForResult(intent, 0); */
            	Uri uri = Uri.parse("http://maps.google.com/maps?saddr=\""+marketSearch +"\"&daddr=\"8th and P lincoln, NE\"");
            	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            	try {
            		startActivity(intent);
            	} catch (ActivityNotFoundException e) {
            	    /*AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            	    dialog.setTitle("Location");
            	    dialog.setMessage("40.8");
            	    dialog.setPositiveButton("OK", null);
            	    dialog.show();*/
            	}
            }
        });
        
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
}
