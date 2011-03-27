package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserMenu extends Activity {
	//private UserType userType;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        // Get the type of user
        //userType = (UserType) getIntent().getExtras().getSerializable("usertype");
        
     
        
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
        
     // Different User Button
        final Button differentUserButton = (Button) findViewById(R.id.different_user_button);
        if (differentUserButton != null) {
        	differentUserButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Intent intent = new Intent(UserMenu.this, home.class);
    				startActivityForResult(intent, 0);
                }
            });
        }
        
        
    }
}
