package com.communitymarket;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UserMenu extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        // Get the menu items
        String[] items = getResources().getStringArray(R.array.user_menu_array);
        
        // Set the list
        ListView lv = (ListView) findViewById(R.id.menu_listview);
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.menu_item, items));
    }
}
