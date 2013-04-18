package com.example.smartpass;

import java.util.ArrayList;

import com.example.smartpass.Adapter.PasswordListAdapter;
import com.example.smartpass.Classes.ExpandListChild;
import com.example.smartpass.Classes.ExpandListGroup;
import com.passwordLib.PasswordFile;
import com.passwordLib.SendPasswordTask;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

public class DeletePassword extends Activity {

	private ExpandableListView ExpandList;

	private PasswordListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Typeface sintRegFont = Typeface.createFromAsset(getAssets(),
				"fonts/SintonyRegular.ttf");

		ActionBar bar = getActionBar();
		bar.setTitle("Delete a Password");
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(90, 100, 102)));

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_password);

		ExpandList = (ExpandableListView) findViewById(R.id.ExpListDelete);

		ExpListItems = SetStandardGroups();
		ExpAdapter = new PasswordListAdapter(DeletePassword.this, ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
		
		final UserAccount[][] childList = new UserAccount[PasswordFile
				.getFolders().size()][];

		int i = 0;
		for (String folder : PasswordFile.getFolders()) {
			childList[i] = new UserAccount[PasswordFile.getAccountsInFolder(
					folder).size()];
			int j = 0;
			for (String accountName : PasswordFile.getAccountsInFolder(folder)) {
				UserAccount acct = PasswordFile.getUserAccount(folder,
						accountName);
				childList[i][j] = acct;
				j++;
			}

			i++;
		}

		final Context context = this;

		ExpandList
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						String folder = childList[groupPosition][childPosition].folder;
						String websiteName = childList[groupPosition][childPosition].websitename;
						PasswordFile.deletePassword(folder, websiteName);

						PasswordFile.saveFile();

						final String username = "";
						final String password = "";

						Object[] args = new Object[3];
						args[0] = (context);
						args[1] = (username);
						args[2] = (password);

						new SendPasswordTask().execute(args);

						Intent intent = new Intent(context, PasswordList.class);

						startActivity(intent);
						((Activity) context).finish();

						return false;
					}
				});

		Button cancelButton = (Button) findViewById(R.id.deletePasswordCancel);
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, PasswordList.class);

				startActivity(intent);
				((Activity) context).finish();
			}
		});
		cancelButton.setTypeface(sintRegFont);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_password, menu);
		return true;
	}

	public ArrayList<ExpandListGroup> SetStandardGroups() {
		ArrayList<ExpandListGroup> folders = new ArrayList<ExpandListGroup>();
		Log.e(null, PasswordFile.getFolders().toString());
		for (String folder : PasswordFile.getFolders()) {
			ExpandListGroup folderGroup = new ExpandListGroup();
			folderGroup.setName(" " + folder);
			ArrayList<ExpandListChild> children = new ArrayList<ExpandListChild>();
			for (String childName : PasswordFile.getAccountsInFolder(folder)) {
				ExpandListChild child = new ExpandListChild();
				child.setName("      " + childName);
				child.ua = PasswordFile.getUserAccount(folder, childName);
				children.add(child);
			}
			folderGroup.setItems(children);
			folders.add(folderGroup);
		}

		return folders;
	}
}
