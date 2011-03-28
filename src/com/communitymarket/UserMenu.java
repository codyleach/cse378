package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserMenu extends Activity {
	private UserType userType;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        // Get the type of user
        userType = (UserType) getIntent().getExtras().getSerializable("usertype");
        
        // Show administrative options?
        if (userType == UserType.Coordinator) {
        	// Get the manage markets button
        	Button manageButton = (Button) findViewById(R.id.manage_button);
        	if (manageButton != null) {
        		manageButton.setVisibility(View.VISIBLE);
        	}
        }
        
        // Producer Button
        final Button farmersButton = (Button) findViewById(R.id.farmers_button);
        if (farmersButton != null) {
        	farmersButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Intent intent = new Intent(UserMenu.this, farmersList.class);
    				startActivityForResult(intent, 0);
                }
            });
        }
    }
}
