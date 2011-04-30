package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewMarket extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_market);
        
        // Save Button
        final Button saveButton = (Button) findViewById(R.id.add_new_market_save_button);
        if (saveButton != null) {
        	saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	// Get the new market name
                	EditText nameEditText = (EditText) findViewById(R.id.add_new_market_name);
                	String newName = "";
                	if (nameEditText != null)
                		newName = nameEditText.getText().toString();
                	
                    // Perform action on click
                	Intent intent = new Intent();
                	intent.putExtra("saved", true);
                	intent.putExtra("name", newName.trim());
    				setResult(Activity.RESULT_OK, intent);
    				finish();
                }
            });
        }
        
        // Cancel Button
        final Button cancelButton = (Button) findViewById(R.id.add_new_market_cancel_button);
        if (cancelButton != null) {
        	cancelButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Intent intent = new Intent();
                	intent.putExtra("saved", false);
    				setResult(Activity.RESULT_OK, intent);
    				finish();
                }
            });
        }
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
        	Intent data = new Intent();
        	data.putExtra("gohome", true);
        	setResult(RESULT_OK, data);
            finish();
            return true;
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
    }
    
    private void leave() {
    	Intent data = new Intent();
    	data.putExtra("gohome", true);
    	setResult(RESULT_OK, data);
        finish();
    }
}
