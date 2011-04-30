package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class marketInformation extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.market_info_page); 

		//Subscribe Button
		final Button subscribeButton = (Button) findViewById(R.id.subscribe_button);
		subscribeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				AlertDialog.Builder alert = new AlertDialog.Builder(marketInformation.this);

				alert.setMessage("You've been subscribed to notifications for this market.");
				alert.setIcon(R.drawable.icon);
				alert.setPositiveButton("Okay",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();

					}
				});

				alert.show();

			}
		});
		
		//Driving Directions Button
        final Button drivingDirectionsButton = (Button) findViewById(R.id.view_driving_directions_button);
        drivingDirectionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent intent = new Intent(marketInformation.this, drivingDirections.class);
            	intent.putExtra("usertype", UserType.Consumer);
				startActivityForResult(intent, 0);
            	
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