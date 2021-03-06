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
        
     
        
      /// Markets Button
        final Button marketsButton = (Button) findViewById(R.id.markets_button);
        if (marketsButton != null) {
        	marketsButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Intent intent = new Intent(UserMenu.this, findMarkets.class);
    				startActivityForResult(intent, 0);
                }
            });
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
        
     // Products Button
        final Button productsButton = (Button) findViewById(R.id.products_button);
        if (productsButton != null) {
        	productsButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Intent intent = new Intent(UserMenu.this, findProducts.class);
    				startActivityForResult(intent, 0);
                }
            });
        }
        
     // Recipes Button
        final Button recipesButton = (Button) findViewById(R.id.recipes_button);
        if (recipesButton != null) {
        	recipesButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Intent intent = new Intent(UserMenu.this, findRecipes.class);
    				startActivityForResult(intent, 0);
                }
            });
        }
        
        // Manage Markets Button
        final Button manageButton = (Button) findViewById(R.id.manage_button);
        if (manageButton != null) {
        	manageButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Intent intent = new Intent(UserMenu.this, ManageMarkets.class);
    				startActivityForResult(intent, 0);
                }
            });
        }
        
        // Different User Button
        final Button differentUserButton = (Button) findViewById(R.id.different_user_button);
        if (differentUserButton != null) {
        	differentUserButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    leave();
                }
            });
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (intent != null) {
			// Is the user still logged in?
			if (LoginDbAdapter.getCurrentUser() == null) {
				// Coordinator?
				if (userType == UserType.Coordinator)
					leave();
			}
		}
    }
    
    private void leave() {
    	Intent data = new Intent();
    	data.putExtra("gohome", true);
    	setResult(RESULT_OK, data);
        finish();
    }
}
