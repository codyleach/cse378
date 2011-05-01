package com.communitymarket;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.ViewGroup;

public class farmersList extends Activity {
	private LayoutInflater  _inflater;
	private RatingDbAdapter _ratingDb;
	private Context 		_context;
	private ListView		_listView;
	private TextView		_searchTextView;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmers_list);
        
        _context  = this.getApplicationContext();
        _inflater = this.getLayoutInflater();
        _ratingDb = new RatingDbAdapter(this);
        
        // Populate the list
        _searchTextView = (TextView) findViewById(R.id.farmers_search_text);
        _listView = (ListView) findViewById(R.id.farmers_list_listview);
        updateListView();
        
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				// Get the item
				User selectedUser = (User) adapterView.getItemAtPosition(position);
				
				// Launch the farmer page
				Intent intent = new Intent(farmersList.this, Farmer.class);
				intent.putExtra("usertype", UserType.Consumer);
				intent.putExtra("farmer", selectedUser.getUsername());
				startActivityForResult(intent, 0);
			}
		});
        
        // Search Button
        Button searchButton = (Button) findViewById(R.id.farmers_search_button);
        if (searchButton != null) {
        	searchButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Search 'em up
                	updateListView();
                }
            });
        }
    }
    
    private void updateListView() {
    	updateListView(LoginDbAdapter.getInstance(_context)
    			.getFarmerUsers(_searchTextView.getText().toString()));
    }
    
    private void updateListView(ArrayList<User> farmers) {
    	_listView.setAdapter(new ArrayAdapter<User>(this, 
    			R.layout.farmer_list_item, farmers){
        	@Override
        	public View getView(int position, View convert, ViewGroup parent) {
        		// Get the farmer
        		User farmer = getItem(position);
        		
        		// Create the item
        		View newItem = _inflater.inflate(R.layout.farmer_list_item, null);
        		
        		// Get all of the item elements
        		TextView name = (TextView) newItem.findViewById(R.id.farmer_list_item_name);
        		TextView products = (TextView) newItem.findViewById(R.id.farmer_list_item_products);
        		RatingBar ratingBar = (RatingBar) newItem.findViewById(R.id.farmer_list_item_ratingbar);
        		ImageView image = (ImageView) newItem.findViewById(R.id.farmer_list_item_image);
        		
        		// Fill in the data
        		name.setText(farmer.getName());
        		products.setText(farmer.getProducts());
        		
        		float[] ratingInfo = _ratingDb.getAvgRating(farmer.getUsername());
        		ratingBar.setRating(ratingInfo[1]);
        		
        		// Set the image
        		String username = farmer.getUsername();
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
        		
        		// Done
        		return newItem;
        	}
        });
    }
    
    public void myClickHandler(View v) {
    	Intent intent = new Intent(farmersList.this, Farmer.class);
		startActivityForResult(intent, 0);
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
        	Intent data = new Intent();
        	data.putExtra("gohome", true);
        	setResult(RESULT_OK, data);
            finish();
            return true;
        case R.id.log_out:
        	LoginDbAdapter.logout();
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (intent != null) {
			boolean goHome = intent.getBooleanExtra("gohome", false);
			if (goHome)
				leave();
		}
		
		updateListView();
    }
    
    private void leave() {
    	Intent data = new Intent();
    	data.putExtra("gohome", true);
    	setResult(RESULT_OK, data);
        finish();
    }
}
