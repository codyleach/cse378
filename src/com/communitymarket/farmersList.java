package com.communitymarket;

import android.app.Activity;
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
	private LayoutInflater _inflater;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmers_list);
        
        _inflater = this.getLayoutInflater();
        
        // Search Button
        Button searchButton = (Button) findViewById(R.id.search_farmers_button);
        if (searchButton != null) {
        	searchButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Intent intent = new Intent(farmersList.this, Farmer.class);
    				startActivityForResult(intent, 0);
                }
            });
        }
        
        // Populate the list
        ListView lview = (ListView) findViewById(R.id.farmers_list_listview);
        lview.setAdapter(new ArrayAdapter<User>(this, R.layout.farmer_list_item, 
        		LoginDbAdapter.getInstance(this.getApplicationContext()).getFarmerUsers()){
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
        		ratingBar.setRating(3);
        		
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
        		
        		// Done
        		return newItem;
        	}
        });
        
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.go_home:
            Intent intent = new Intent(farmersList.this, UserMenu.class);
            intent.putExtra("usertype", UserType.Consumer);
            startActivityForResult(intent, 0);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
