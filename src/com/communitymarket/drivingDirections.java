package com.communitymarket;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class drivingDirections extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driving_directions); 
        
        final Button drivingDirectionsButton = (Button) findViewById(R.id.search_location_button);
        drivingDirectionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	String mSearchMarket = ((EditText) findViewById(R.id.location_search_box)).getText().toString();
            	Uri uri = Uri.parse("http://maps.google.com/maps?saddr=\""+mSearchMarket +"\"&daddr=\"8th and P lincoln, NE\"");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.go_home:
            Intent intent = new Intent(drivingDirections.this, UserMenu.class);
            intent.putExtra("usertype", UserType.Consumer);
            startActivityForResult(intent, 0);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
