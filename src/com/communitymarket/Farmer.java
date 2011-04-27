package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class Farmer extends Activity {
	private static final int LOGIN_REQUEST = 0;
	private RatingBar _ratingBar = null;
	private RatingDbAdapter ratingDb;
	private User _farmer;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer);
        
        // Get the farmer
        String farmer = getIntent().getExtras().getString("farmer");
        
        // If no farmer given, then get out
        if (farmer == null)
        	finish();
        
        // Get the farmer data
        _farmer = LoginDbAdapter.getInstance(this.getApplicationContext()).getUser(farmer);
        
        if (_farmer == null)
        	finish();
        
        // Set up the rating stuff
        ratingDb = new RatingDbAdapter(this);
        _ratingBar = (RatingBar) findViewById(R.id.user_rating);
        
        // Populate the farmer data
        populateFarmerData();
        
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
    
    private void populateFarmerData() {
    	if (_farmer == null)
    		return;
    	
    	TextView name = (TextView) findViewById(R.id.farmer_name);
    	name.setText(_farmer.getName());
    	
    	TextView products = (TextView) findViewById(R.id.farmer_products);
    	products.setText(_farmer.getProducts());
    	
    	// Set the image
    	ImageView image = (ImageView) findViewById(R.id.farmer_image);
		String username = _farmer.getUsername();
		if (username.equals("jjohnston"))
			image.setImageResource(R.drawable.john_johnston);
		else if (username.equals("llemon"))
			image.setImageResource(R.drawable.liz_lemon);
		else if (username.equals("pnewman"))
			image.setImageResource(R.drawable.paul_newman);
		else if (username.equals("bgriffen"))
			image.setImageResource(R.drawable.barry_griffin);
		
		// Set the header data
		TextView ratingHeader = (TextView) findViewById(R.id.farmer_rate_header_text);
		ratingHeader.setText("Rate " + _farmer.getName());
		TextView productsHeader = (TextView) findViewById(R.id.farmer_products_header_text);
		productsHeader.setText("Products " + _farmer.getName() + " Sells");
    	
    	if(LoginDbAdapter.getCurrentUser() != null) {
        	int farmerRating = ratingDb.getRating(_farmer.getUsername(), LoginDbAdapter.getCurrentUser().getUsername());
        	_ratingBar.setRating(farmerRating);
        }
    }
    
    private void saveUserRating() {
    	// Get the current user
    	User currentUser = LoginDbAdapter.getCurrentUser();

    	// Save it!
    	ratingDb.open();
    	ratingDb.addRating(_farmer.getUsername(), (int) _ratingBar.getRating(), currentUser.getUsername());
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
