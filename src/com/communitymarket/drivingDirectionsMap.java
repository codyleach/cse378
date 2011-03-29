package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class drivingDirectionsMap extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driving_directions_map); 
        
//      //Get Info Button
//        final Button infoButton = (Button) findViewById(R.id.getInfoButton);
//        infoButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Perform action on click
//            	Intent intent = new Intent(marketSearchResults.this, marketInformation.class);
//            	intent.putExtra("usertype", UserType.Consumer);
//				startActivityForResult(intent, 0);
//            	
//            }
//        });
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.go_home:
            Intent intent = new Intent(drivingDirectionsMap.this, UserMenu.class);
            intent.putExtra("usertype", UserType.Consumer);
            startActivityForResult(intent, 0);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
