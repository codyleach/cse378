package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class changeOrigin extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_origin); 
        
      //Go Button
        final Button goButton = (Button) findViewById(R.id.change_origin_button);
        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent intent = new Intent(changeOrigin.this, drivingDirections.class);
            	intent.putExtra("usertype", UserType.Consumer);
				startActivityForResult(intent, 0);
            	
            }
        });
//        
//      //Driving Directions Button
//        final Button drivingDirectionsButton = (Button) findViewById(R.id.drivingDirectionsButton);
//        infoButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Perform action on click
//            	Intent intent = new Intent(marketSearchResults.this, drivingDirections.class);
//            	intent.putExtra("usertype", UserType.Consumer);
//				startActivityForResult(intent, 0);
//            	
//            }
//        });
        
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
        	Intent data = new Intent();
        	data.putExtra("gohome", true);
        	setResult(RESULT_OK, data);
            finish();
            return true;
        case R.id.log_out:
        	LoginDbAdapter.logout();
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
