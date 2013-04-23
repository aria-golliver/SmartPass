package com.example.smartpass;

import com.passwordLib.PasswordFile;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

public class NewFolder extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActionBar bar = getActionBar();
		bar.setTitle("Add Folder");
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(90, 100, 102)));

		Typeface sintRegFont = Typeface.createFromAsset(getAssets(),
				"fonts/SintonyRegular.ttf");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_folder);
		// Show the Up button in the action bar.
		setupActionBar();

		final Button newFolderCancelButton = (Button) findViewById(R.id.newFolderCancelButton);
		final Button newFolderCreateButton = (Button) findViewById(R.id.newFolderCreateButton);
		newFolderCancelButton.setTypeface(sintRegFont);
		newFolderCreateButton.setTypeface(sintRegFont);
		final Context context = this;

		newFolderCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, PasswordList.class);
				context.startActivity(intent);
				((Activity) context).finish();
			}
		});

		newFolderCreateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String folderName = ((EditText) findViewById(R.id.websiteNameEditText))
						.getText().toString();

				PasswordFile.addFolder(folderName);
				Intent intent = new Intent(context, PasswordList.class);
				context.startActivity(intent);
				// ((Activity)context).finish();
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
		getMenuInflater().inflate(R.menu.new_folder, menu);
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
