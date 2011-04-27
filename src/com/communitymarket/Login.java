package com.communitymarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	// Variables
	private UserType _userType;
	private Class<?> _nextClass;
	private Spinner	 _spinner;
	private TextView _errorText;
	private TextView _title;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	// Save some intent data
    	try {
    		_userType 	= (UserType) getIntent().getExtras().getSerializable("usertype");
    	} catch (Exception ex) {
    		_userType = null;
    	}
    	try {
    		_nextClass  = (Class<?>) getIntent().getExtras().getSerializable("nextactivity");
    	} catch (Exception ex) {
    		_nextClass = null;
    	}
    	
    	// Is the user already logged in?
    	User currentUser = LoginDbAdapter.getCurrentUser();
    	if (currentUser != null) {
    		if (_userType != null) {
    			if (_userType == currentUser.getType()) {
    				loginSuccessful();
    				return;
    			}
    		} else {
    			// User is already logged in, so let's go!
    			loginSuccessful();
				return;
    		}
    	}
    	
    	// Set up the UI
    	setContentView(R.layout.login);
    	((EditText)findViewById(R.id.login_password))
    		.setTransformationMethod(new PasswordTransformationMethod());
    	((EditText)findViewById(R.id.login_register_password))
			.setTransformationMethod(new PasswordTransformationMethod());
    	((EditText)findViewById(R.id.login_register_confirm_password))
			.setTransformationMethod(new PasswordTransformationMethod());
    	_title = (TextView) findViewById(R.id.login_header);
    	_errorText = (TextView) findViewById(R.id.login_error_message);
    	
    	// Populate the type spinner
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
				R.array.register_usertype_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_spinner = (Spinner) findViewById(R.id.login_register_usertype);
		_spinner.setAdapter(adapter);
    	
		
    	Button signInButton = (Button) findViewById(R.id.login_signin_button);
    	signInButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Get the values from the form
            	String username = ((EditText) findViewById(R.id.login_username)).getText().toString();
            	String password = ((EditText) findViewById(R.id.login_password)).getText().toString();
            	
            	// Make sure the values are valid
            	if (username.equals("") || password.equals("")) {
            		setError("Incorrect username or password.");
            		return;
            	}
            	
            	// Log me in, log me in
            	LoginDbAdapter loginAdapter = LoginDbAdapter.getInstance(v.getContext());
            	User returnedUser = loginAdapter.authUser(username, password);
            	if (returnedUser == null) {
            		setError("Incorrect username or password.");
            		return;
            	}
            	
            	// Let's get this train rollin'
            	loginSuccessful();
			}
		});
    	
    	Button cancelButton = (Button) findViewById(R.id.login_cancel_button);
    	cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				getOut(RESULT_CANCELED);
			}
		});
    	
    	Button registerButton = (Button) findViewById(R.id.login_register_button);
    	registerButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Change the title
				_title.setText(R.string.register);
				_title.setVisibility(View.VISIBLE);
				_errorText.setVisibility(View.GONE);
				
				// Set the spinner value
				if (_userType == UserType.Consumer)
					_spinner.setSelection(0);
				else if (_userType == UserType.Producer)
					_spinner.setSelection(1);
				else
					_spinner.setSelection(2);
				
				// Get the two layouts
				LinearLayout loginLayout = (LinearLayout) findViewById(R.id.login_layout);
				LinearLayout registerLayout = (LinearLayout) findViewById(R.id.login_register_layout);
				
				// Switch up quickly...like a ninja
				loginLayout.setVisibility(View.GONE);
				registerLayout.setVisibility(View.VISIBLE);
			}
		});
    	
    	Button registerSaveButton = (Button) findViewById(R.id.login_register_save_button);
    	registerSaveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Get the values from the form
				String name = ((EditText) findViewById(R.id.login_register_name)).getText().toString();
				String email = ((EditText) findViewById(R.id.login_register_email)).getText().toString(); 
            	String username = ((EditText) findViewById(R.id.login_register_username)).getText().toString();
            	String password = ((EditText) findViewById(R.id.login_register_password)).getText().toString(); 
            	String confirmPassword = ((EditText) findViewById(R.id.login_register_confirm_password))
            		.getText().toString();
            	String products = ((EditText) findViewById(R.id.login_register_products)).getText().toString(); 
            	
            	// Make sure the user has given all the values
            	if (name.equals("")) {
            		setError("Please provide your name.");
            		return;
            	} else if (email.equals("")) {
            		setError("Please provide your e-mail.");
            		return;
            	} else if (username.equals("")) {
            		setError("Please provide a username.");
            		return;
            	} else if (password.equals("")) {
            		setError("Please provide a password.");
            		return;
            	} else if (confirmPassword.equals("")) {
            		setError("Please confirm the password.");
            		return;
            	} else if (_spinner.getSelectedItemPosition() == 1
            			&& products.equals("")) {
            		setError("Please enter your products.");
            		return;
            	}
            	
            	// Make sure the email is valid
            	if (email.startsWith("@") || email.startsWith(".")
            			|| !email.contains("@") || !email.contains(".")) {
            		setError("Invalid e-mail address.");
            		return;
            	}
            	
            	// Make sure the passwords match
            	if (!password.equals(confirmPassword)) {
            		setError("Passwords do not match.");
            		EditText passwordText = (EditText) findViewById(R.id.login_register_password);
            		EditText confirmPasswordText = (EditText) findViewById(R.id.login_register_confirm_password);
            		passwordText.setText("");
            		confirmPasswordText.setText("");
            		passwordText.requestFocus();
            		return;
            	}
            	
            	// Does the username exist?
            	LoginDbAdapter loginAdapter = LoginDbAdapter.getInstance(v.getContext());
            	if (loginAdapter.doesUserExist(username)) {
            		setError("Username already exists.");
            		return;
            	}
            	
            	// Register the user
            	UserType type;
            	if (_spinner.getSelectedItemPosition() == 0) {
            		type = UserType.Consumer;
            		products = "";
            	} else if (_spinner.getSelectedItemPosition() == 1) {
            		type = UserType.Producer;
            	} else {
            		type = UserType.Coordinator;
            		products = "";
            	}
            	
            	User newUser = new User(type, name, email, username, password, products);
            	if (loginAdapter.addUser(newUser)) {
            		loginSuccessful(true);
            	} else
            		getOut(RESULT_CANCELED);
			}
		});
    	
    	Button registerCancelButton = (Button) findViewById(R.id.login_register_cancel_button);
    	registerCancelButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Change the title
				_title.setText(R.string.login);
				_title.setVisibility(View.VISIBLE);
				_errorText.setVisibility(View.GONE);
				
				// Get the two layouts
				LinearLayout loginLayout = (LinearLayout) findViewById(R.id.login_layout);
				LinearLayout registerLayout = (LinearLayout) findViewById(R.id.login_register_layout);
				
				// Switch up quickly...like a ninja
				loginLayout.setVisibility(View.VISIBLE);
				registerLayout.setVisibility(View.GONE);
			}
		});
    	
    	_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    			// Get the products field
    			EditText products = (EditText) findViewById(R.id.login_register_products);
    			
    			// Check the type
    			if (position == 1) {
    				// Producer
    				products.setVisibility(View.VISIBLE);
    			} else {
    				products.setVisibility(View.GONE);
    			}
    		}
    		
    		public void onNothingSelected(AdapterView<?> parent) {
    			// This should never happen
    		}
		});
    }
    
    private void setError(String error) {
    	_errorText.setText(error);
    	_title.setVisibility(View.GONE);
    	_errorText.setVisibility(View.VISIBLE);
    }
    
    private void loginSuccessful() {
    	loginSuccessful(false);
    }
    
    private void loginSuccessful(boolean showRegisteredToast) {
    	// Show some toast?
    	if (showRegisteredToast) {
    		Toast toast = Toast.makeText(getApplicationContext(), 
    				"Registration successful", Toast.LENGTH_SHORT);
    		toast.show();
    	}
    	
    	// Do we have somewhere to go?
    	if (_nextClass == null) {
    		getOut();
    		return;
    	}
    	
    	Intent intent = new Intent(Login.this, _nextClass);
		intent.putExtra("usertype", _userType);
    	startActivityForResult(intent, 0);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	getOut();
    }
    
    private void getOut() {
    	getOut(RESULT_OK);
    }
    
    private void getOut(int result) {
    	setResult(result);
    	finish();
    }
}
