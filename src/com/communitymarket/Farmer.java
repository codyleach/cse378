package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class Farmer extends Activity {
	private static final int LOGIN_REQUEST = 0;
	private RatingBar _ratingBar = null;
	private RatingBar _avgRatingBar = null;
	private TextView _avgRatingNum = null;
	private TextView _avgRatingNumUsers = null;
	private RatingDbAdapter _ratingDb;
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
        _ratingDb = new RatingDbAdapter(this);
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
    	} else {
    		if (intent != null) {
    			boolean goHome = intent.getBooleanExtra("gohome", false);
    			if (goHome)
    				leave();
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
		else if (username.equals("jatric"))
			image.setImageResource(R.drawable.jerry_atric);
		
		// Set the header data
		TextView ratingHeader = (TextView) findViewById(R.id.farmer_rate_header_text);
		ratingHeader.setText("Rate " + _farmer.getName());
		TextView productsHeader = (TextView) findViewById(R.id.farmer_products_header_text);
		productsHeader.setText("Products " + _farmer.getName() + " Sells");
		
		// Get the rating data
		updateAvgRating();
    	
    	if(LoginDbAdapter.getCurrentUser() != null) {
        	int farmerRating = _ratingDb.getRating(_farmer.getUsername(), LoginDbAdapter.getCurrentUser().getUsername());
        	_ratingBar.setRating(farmerRating);
        }
    }
    
    private void updateAvgRating() {
    	if (_avgRatingNum == null)
    		_avgRatingNum = (TextView) findViewById(R.id.farmer_avg_rating_number);
    	if (_avgRatingNumUsers == null)
    		_avgRatingNumUsers = (TextView) findViewById(R.id.farmer_avg_rating_num_users);
    	if (_avgRatingBar == null)
    		_avgRatingBar = (RatingBar) findViewById(R.id.farmer_avg_rating);
    	
    	float[] ratingInfo = _ratingDb.getAvgRating(_farmer.getUsername());
    	_avgRatingNumUsers.setText("" + (int)ratingInfo[0]);
    	_avgRatingNum.setText(String.format("%.2g", ratingInfo[1]));
    	_avgRatingBar.setRating(ratingInfo[1]);
    }
    
    private void saveUserRating() {
    	// Get the current user
    	User currentUser = LoginDbAdapter.getCurrentUser();

    	// Save it!
    	_ratingDb.open();
    	_ratingDb.addRating(_farmer.getUsername(), (int) _ratingBar.getRating(), currentUser.getUsername());
    	
    	updateAvgRating();
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
        	leave();
            return true;
        case R.id.log_out:
        	LoginDbAdapter.logout();
        	_ratingBar.setRating(0);
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    private void leave() {
    	Intent data = new Intent();
    	data.putExtra("gohome", true);
    	setResult(RESULT_OK, data);
        finish();
    }
}
