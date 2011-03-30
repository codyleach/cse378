package com.communitymarket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class Farmer extends Activity {
	private PopupWindow pw;
	private View layout;
	private boolean loggedIn;
	private boolean loginJustShown;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer);
        loggedIn = false;
        loginJustShown = false;
        
        // get the instance of the LayoutInflater
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        //Coordinator Button
        final RatingBar userRatingBar = (RatingBar) findViewById(R.id.user_rating);
        userRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				if (loggedIn || loginJustShown) {
					loginJustShown = false;
					return;
				}
				
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
                        loginJustShown = true;
                        userRatingBar.setRating(0);
                    }
                });
            
                Button button3 = (Button)layout.findViewById(R.id.popup_menu_signin_button);
                button3.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                    	// Perform action on click
                    	pw.dismiss();
                    	loggedIn = true;
                    }
                });
                
                Button regSaveButton = (Button) layout.findViewById(R.id.popup_register_save_button);
                regSaveButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // Close the popup
                    	pw.dismiss();
                    	loggedIn = true;
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
