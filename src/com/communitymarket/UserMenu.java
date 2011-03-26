package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserMenu extends Activity {
	private UserType userType;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        
        // Get the type of user
        userType = (UserType) getIntent().getExtras().getSerializable("usertype");
        
        // Producer Button
        final Button farmersButton = (Button) findViewById(R.id.farmers_button);
        farmersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent intent = new Intent(UserMenu.this, farmersList.class);
				startActivityForResult(intent, 0);
            	
            }
        });
    }
}
