package com.example.smartpass;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.passwordLib.PasswordFile;
import com.passwordLib.RetrivePasswordTask;

public class LoginScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(0xFFFF0000));
		
	//	Drawable d = getResources().getDrawable(R.drawable.d);
		bar.hide();
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);

		final Button loginButton = (Button) findViewById(R.id.loginButton);
		final Button newLoginButton = (Button) findViewById(R.id.newLoginButton);
		final EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
		final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

		final Context context = this;

		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CharSequence toastMessage = "Connecting to SmartFile";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, toastMessage, duration);
				toast.show();
				
				final String username = usernameEditText.getText().toString();
				final String password = passwordEditText.getText().toString();

				Object[] args = new Object[3];
				args[0] = (context);
				args[1] = (username);
				args[2] = (password);
				PasswordFile.initPasswordFile(username, password, context);
				new RetrivePasswordTask().execute(args);
			}
		});

		newLoginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewLogin.class);
				context.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_screen, menu);
		return true;
	}

}