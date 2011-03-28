package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class drivingDirections extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driving_directions); 
        
      //View Map Button
        final Button viewMapButton = (Button) findViewById(R.id.view_directions_map_button);
        viewMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent intent = new Intent(drivingDirections.this, drivingDirectionsMap.class);
            	intent.putExtra("usertype", UserType.Consumer);
				startActivityForResult(intent, 0);
            	
            }
        });
        
      //Change Origin Button
        final Button changeOriginButton = (Button) findViewById(R.id.change_origin_button);
        changeOriginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent intent = new Intent(drivingDirections.this, changeOrigin.class);
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
