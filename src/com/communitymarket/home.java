package com.communitymarket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

public class home extends Activity {
    /** Called when the activity is first created. */
	private PopupWindow pw;
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
            	intent.putExtra("usertype", UserType.Consumer);
				startActivityForResult(intent, 0);
            	
            }
        });
        
        //Producer Button
        final Button producerButton = (Button) findViewById(R.id.producer);
        producerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Intent intent = new Intent(home.this, UserMenu.class);
            	intent.putExtra("usertype", UserType.Producer);
				startActivityForResult(intent, 0);
            	
            }
        });
        
     // get the instance of the LayoutInflater
        final LayoutInflater inflater = (LayoutInflater) home.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      


        //Coordinator Button
        final Button coordinatorButton = (Button) findViewById(R.id.marketCoordinator);
        coordinatorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	  // inflate our view from the corresponding XML file
                View layout = inflater.inflate(R.layout.popup_login, (ViewGroup) findViewById(R.id.popup_menu_root));
                
                
                // create a 100px width and 200px height popup window
                pw = new PopupWindow(layout, 300, 300, true);
                // set actions to buttons we have in our popup
                Button button1 = (Button)layout.findViewById(R.id.popup_menu_button1);
                button1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View vv) {
                        // close the popup
                        pw.dismiss();
                    }
                });
            
                Button button3 = (Button)layout.findViewById(R.id.popup_menu_button3);
                button3.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View vv) {
                    	// Perform action on click
                    	 pw.dismiss();
                    	Intent intent = new Intent(home.this, UserMenu.class);
                    	intent.putExtra("usertype", UserType.Coordinator);
        				startActivityForResult(intent, 0);
                    	
                    }
                });
                
                Button button2 = (Button)layout.findViewById(R.id.popup_menu_button2);
                button2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View vv) {
                    	// Perform action on click
                    	 pw.dismiss();
                    	Intent intent = new Intent(home.this, UserRegistration.class);
                    	intent.putExtra("usertype", UserType.Coordinator);
        				startActivityForResult(intent, 0);
                    	
                    	//View layoutRegister = inflater.inflate(R.layout.popup_register, (ViewGroup) findViewById(R.id.popup_menu_root));	
                    	//new PopupWindow(layoutRegister, 300, 300, true);
                    	
                    }
                });
                
                // finally show the popup in the center of the window
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            	
            	
            }
        });
    }
}