package com.example.smartpass;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.passwordLib.PasswordFile;
import com.passwordLib.SendPasswordTask;

public class NewLogin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Typeface sintRegFont = Typeface.createFromAsset(getAssets(),
	            "fonts/SintonyRegular.ttf");
		
		ActionBar bar = getActionBar();
		bar.setTitle("Create a New Account");
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(90, 100, 102)));
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_login);
		// Show the Up button in the action bar.
		setupActionBar();

		final Button createNewLoginButton = (Button) findViewById(R.id.createNewLoginButton);
		final Button cancelNewLoginButton = (Button) findViewById(R.id.newLoginCancelButton);
		final EditText usernameEditText = (EditText) findViewById(R.id.newLoginUsernameEditText);
		final EditText passwordEditText = (EditText) findViewById(R.id.newLoginPasswordEditText);

		final Context context = this;

		createNewLoginButton.setTypeface(sintRegFont);
		cancelNewLoginButton.setTypeface(sintRegFont);
		usernameEditText.setTypeface(sintRegFont);
		passwordEditText.setTypeface(sintRegFont);
		
		createNewLoginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				final String username = usernameEditText.getText().toString();
				final String password = passwordEditText.getText().toString();

				Object[] args = new Object[3];
				args[0] = (context);
				args[1] = (username);
				args[2] = (password);

				PasswordFile.initPasswordFile(username, password, context);

				PasswordFile.saveFile();
				new SendPasswordTask().execute(args);
				((Activity) context).finish();
			}
		});
		
		cancelNewLoginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((Activity) context).finish();
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
