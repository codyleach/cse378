package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class marketInformation extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_info_page); 
        
      //Search Button
//        final Button searchButton = (Button) findViewById(R.id.search_button);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Perform action on click
//            	Intent intent = new Intent(marketInformation.this, marketSearchResults.class);
//            	intent.putExtra("usertype", UserType.Consumer);
//				startActivityForResult(intent, 0);
//            	
//            }
//        });
        
    }
}
