package com.example.smartpass;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.passwordLib.PasswordFile;
import com.passwordLib.PasswordLib;

public class NewAccount extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActionBar bar = getActionBar();
		bar.setTitle("Add Account");
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(90, 100, 102)));

		Typeface sintRegFont = Typeface.createFromAsset(getAssets(),
				"fonts/SintonyRegular.ttf");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_account);
		// Show the Up button in the action bar.
		setupActionBar();

		final Button newAccountCancelButton = (Button) findViewById(R.id.newAccountCancelButton);
		final Button newAccountCreateButton = (Button) findViewById(R.id.newAccountCreateButton);

		final EditText websiteNameEditText = (EditText) findViewById(R.id.websiteNameEditText);
		final EditText usernameEditText = (EditText) findViewById(R.id.newAcctUsernameEditText);

		final EditText newAccountLengthEditText = (EditText) findViewById(R.id.newAccountLengthEditText);

		newAccountCancelButton.setTypeface(sintRegFont);
		newAccountCreateButton.setTypeface(sintRegFont);
		websiteNameEditText.setTypeface(sintRegFont);
		usernameEditText.setTypeface(sintRegFont);
		newAccountLengthEditText.setTypeface(sintRegFont);
		
		final Context context = this;

		final CheckBox lower = ((CheckBox) findViewById(R.id.lowerCheckBox));
		final CheckBox upper = ((CheckBox) findViewById(R.id.upperCheckBox));
		final CheckBox number = ((CheckBox) findViewById(R.id.numberCheckBox));
		final CheckBox special = ((CheckBox) findViewById(R.id.specialCheckBox));

		final Spinner folderSpinner = (Spinner) findViewById(R.id.folderSpinner);
		
		Set<String> folderSet = PasswordFile.getFolders();
		String[] folders = new String[folderSet.size()];
		int i = 0;
		for (String folder : folderSet) {
			folders[i++] = folder;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.offblack_spinner, folders);

		folderSpinner.setAdapter(adapter);

		newAccountCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, PasswordList.class);
				context.startActivity(intent);
				
				((Activity) context).finish();
			}
		});

		newAccountCreateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int len = 24;
				try {
					len = Integer.parseInt(newAccountLengthEditText.getText()
							.toString());
				} catch (Exception e) {
					// pass
				}

				len = (len >= 1) ? len : 1;

				String newPassword = PasswordLib.createPassword(len,
						lower.isChecked(), upper.isChecked(),
						number.isChecked(), special.isChecked());

				try {
					System.out.println("adding password: " + newPassword + " "
							+ usernameEditText.getText().toString() + " "
							+ folderSpinner.getSelectedItem().toString() + " "
							+ websiteNameEditText.getText().toString());

					PasswordFile.addPassword(newPassword, usernameEditText
							.getText().toString(), folderSpinner
							.getSelectedItem().toString(), websiteNameEditText
							.getText().toString());

				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				}

				Intent intent = new Intent(context, PasswordList.class);
				context.startActivity(intent);

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
		getMenuInflater().inflate(R.menu.new_account, menu);
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
