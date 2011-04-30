package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ManageMarkets extends Activity {
	private LayoutInflater _inflater;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_markets);
        
        _inflater = this.getLayoutInflater();
        
        // Add New Market Button
        final Button addNewButton = (Button) findViewById(R.id.manage_add_new_button);
        if (addNewButton != null) {
        	addNewButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Intent intent = new Intent(ManageMarkets.this, AddNewMarket.class);
    				startActivityForResult(intent, 0);
                }
            });
        }
        
        // Populate the list
        ListView lview = (ListView) findViewById(R.id.manage_markets_listview);
        lview.setAdapter(new ArrayAdapter<Market>(this, R.layout.manage_markets_item, 
        		MarketDbAdapter.getInstance(this.getApplicationContext()).getMarkets()){
        	@Override
        	public View getView(int position, View convert, ViewGroup parent) {
        		// Get the farmer
        		Market currentMarket = getItem(position);
        		
        		// Create the item
        		View newItem = _inflater.inflate(R.layout.manage_markets_item, null);
        		
        		// Get all of the item elements
        		com.communitymarket.MenuButtonItem button = (com.communitymarket.MenuButtonItem) newItem.findViewById(R.id.manage_markets_button);
        		
        		// Fill in the data
        		button.setText(currentMarket.getName());
        		
        		
        		
        		// Done
        		return newItem;
        	}
        });
        
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				// Get the item
				Market selectedMarket = (Market) adapterView.getItemAtPosition(position);
				
				// Launch the farmer page
				Intent intent = new Intent(ManageMarkets.this, Market.class);
				intent.putExtra("usertype", UserType.Consumer);
				intent.putExtra("market", selectedMarket.getMarketID());
				startActivityForResult(intent, 0);
			}
		});
        
        
        /*
         *  // Populate the list
        ListView lview = (ListView) findViewById(R.id.manage_markets_listview);
        lview.setAdapter(new ArrayAdapter<User>(this, R.layout.manage_markets_item, 
        		LoginDbAdapter.getInstance(this.getApplicationContext()).getFarmerUsers()){
        	@Override
        	public View getView(int position, View convert, ViewGroup parent) {
        		// Get the farmer
        		User farmer = getItem(position);
        		
        		// Create the item
        		View newItem = _inflater.inflate(R.layout.manage_markets_item, null);
        		
        		// Get all of the item elements
        		TextView name = (TextView) newItem.findViewById(R.id.manage_markets_button);
        		
        		// Fill in the data
        		name.setText(farmer.getName());
        		
        		// Done
        		return newItem;
        	}
        });
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
            Intent intent = new Intent(ManageMarkets.this, UserMenu.class);
            intent.putExtra("usertype", UserType.Consumer);
            startActivityForResult(intent, 0);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//    	super.onActivityResult(requestCode, resultCode, data);
//    	if (resultCode == Activity.RESULT_OK) {
//    		if (data.getBooleanExtra("saved", false)) {
//    			// Get the new market name
//    			String newName = data.getStringExtra("name");
//    			
//    			if (!newName.trim().equals("")) {
//    				// Get the new button
//    				Button newButton = (Button) findViewById(R.id.recently_added_button);
//    				if (newButton != null) {
//    					newButton.setText(newName);
//    					newButton.setVisibility(View.VISIBLE);
//    				}
//    			}
//    		}
//    	}
//    }
}
