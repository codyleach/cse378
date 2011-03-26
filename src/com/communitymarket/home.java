package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Consumer Button
        final Button consumerButton = (Button) findViewById(R.id.consumer);
        consumerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent intent = new Intent(home.this, UserMenu.class);
				startActivityForResult(intent, 0);
            	
            }
        });
        
        //Producer Button
        final Button producerButton = (Button) findViewById(R.id.producer);
        producerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent intent = new Intent(home.this, UserMenu.class);
				startActivityForResult(intent, 0);
            	
            }
        });
        
        //Coordinator Button
        final Button coordinatorButton = (Button) findViewById(R.id.marketCoordinator);
        coordinatorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent intent = new Intent(home.this, UserMenu.class);
				startActivityForResult(intent, 0);
            	
            }
        });

    }
}