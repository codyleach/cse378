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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class home extends Activity {
    /** Called when the activity is first created. */
	private PopupWindow pw;
	private View layout;
	
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
                layout = inflater.inflate(R.layout.popup_login, (ViewGroup) findViewById(R.id.popup_menu_root));
                
                // Create the popup
                pw = new PopupWindow(layout, 375, 425, true);
                
                // Set actions to the popup
                Button button1 = (Button)layout.findViewById(R.id.popup_menu_cancel_button);
                button1.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // Close the popup
                        pw.dismiss();
                    }
                });
            
                Button button3 = (Button)layout.findViewById(R.id.popup_menu_signin_button);
                button3.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                    	// Get the values from the form
                    	String username = ((EditText) layout.findViewById(R.id.popup_login_username)).getText().toString();
                    	String password = ((EditText) layout.findViewById(R.id.popup_login_password)).getText().toString(); 
                    	
                    	// Make sure the values are valid
                    	if (username.equals("") || password.equals(""))
                    		return;
                    	
                    	// Log me in, log me in
                    	LoginDbAdapter loginAdapter = new LoginDbAdapter(v.getContext());
                    	if (!loginAdapter.authUser(username, password))
                    		return;
                    	
                    	// Perform action on click
                    	pw.dismiss();
                    	Intent intent = new Intent(home.this, UserMenu.class);
                    	intent.putExtra("usertype", UserType.Coordinator);
        				startActivityForResult(intent, 0);
                    }
                });
                
                Button regSaveButton = (Button) layout.findViewById(R.id.popup_register_save_button);
                regSaveButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                    	// Get the values from the form
                    	String username = ((EditText) layout.findViewById(R.id.popup_register_username)).getText().toString();
                    	String email = ((EditText) layout.findViewById(R.id.popup_register_email)).getText().toString(); 
                    	String password = ((EditText) layout.findViewById(R.id.popup_register_password)).getText().toString(); 
                    	
                    	// Make sure the values are valid
                    	if (username.equals("") || email.equals("") || password.equals(""))
                    		return;
                    	
                    	// Register the user
                    	LoginDbAdapter loginAdapter = new LoginDbAdapter(v.getContext());
                    	if (!loginAdapter.addUser(username, email, password))
                    		return;
                    	
                        // Close the popup
                    	pw.dismiss();
                    	Intent intent = new Intent(home.this, UserMenu.class);
                    	intent.putExtra("usertype", UserType.Coordinator);
        				startActivityForResult(intent, 0);
                    }
                });
                
                Button regCancelButton = (Button) layout.findViewById(R.id.popup_register_cancel_button);
                regCancelButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // Close the popup
                    	LinearLayout loginLayout = (LinearLayout) layout.findViewById(R.id.popup_login_layout);
                    	loginLayout.setVisibility(View.VISIBLE);
                    	
                    	LinearLayout regLayout = (LinearLayout) layout.findViewById(R.id.popup_login_register_layout);
                    	regLayout.setVisibility(View.GONE);
                    }
                });
                
                Button button2 = (Button)layout.findViewById(R.id.popup_menu_register_button);
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                    	// Perform action on click
                    	LinearLayout loginLayout = (LinearLayout) layout.findViewById(R.id.popup_login_layout);
                    	loginLayout.setVisibility(View.GONE);
                    	
                    	LinearLayout regLayout = (LinearLayout) layout.findViewById(R.id.popup_login_register_layout);
                    	regLayout.setVisibility(View.VISIBLE);
                    }
                });
                
                // finally show the popup in the center of the window
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });
    }
}