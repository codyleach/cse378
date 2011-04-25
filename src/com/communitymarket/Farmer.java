package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class Farmer extends Activity {
	private static final int LOGIN_REQUEST = 0;
	private RatingBar _ratingBar = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer);
        
        //Coordinator Button
        _ratingBar = (RatingBar) findViewById(R.id.user_rating);
        _ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// Log the user in
				if (fromUser) {
					// Is the user already logged in?
					if (LoginDbAdapter.getCurrentUser() == null) {
						Intent loginIntent = new Intent(Farmer.this, Login.class);
						startActivityForResult(loginIntent, 0);
					} else {
						saveUserRating();
					}
				}
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	// Was the login successful?
    	if (requestCode == LOGIN_REQUEST) {
    		if (resultCode != RESULT_OK) {
    			// Failure
    			_ratingBar.setRating(0);
    		} else {
    			saveUserRating();
    		}
    	}
    }
    
    private void saveUserRating() {
    	// Get the current user
    	User currentUser = LoginDbAdapter.getCurrentUser();
    	
    	/*
    	 * DIEGO, DO YA THANG, YO! 
    	 */
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
            Intent intent = new Intent(Farmer.this, UserMenu.class);
            intent.putExtra("usertype", UserType.Consumer);
            startActivityForResult(intent, 0);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
