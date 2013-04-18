package com.example.smartpass;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.smartpass.Adapter.PasswordListAdapter;
import com.example.smartpass.Classes.ExpandListChild;
import com.example.smartpass.Classes.ExpandListGroup;
import com.passwordLib.PasswordFile;

public class PasswordList extends Activity {
	/** Called when the activity is first created. */
	private PasswordListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;
	private ExpandableListView ExpandList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_list);
		ActionBar bar = getActionBar();
		bar.setTitle("Password List");
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(90, 100, 102)));

		ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
		ExpListItems = SetStandardGroups();
		ExpAdapter = new PasswordListAdapter(PasswordList.this, ExpListItems);
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

		ExpandList
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						System.out.println(groupPosition + " " + childPosition
								+ " " + id);

						String username = childList[groupPosition][childPosition].username;
						String folder = childList[groupPosition][childPosition].folder;
						String websiteName = childList[groupPosition][childPosition].websitename;

						String decryptedPassword = PasswordFile.getPassword(
								folder, websiteName);

						PasswordFile.selectedPassword = decryptedPassword;
						PasswordFile.selectedUsername = username;

						launchNotifications();

						return false;
					}
				});

		final Button newAccountButton = (Button) findViewById(R.id.newAccountButton);
		final Button newFolderButton = (Button) findViewById(R.id.newFolderButton);
		final Button deletePasswordListButton = (Button) findViewById(R.id.deletePasswordListButton);

		final Context context = this;

		newAccountButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewAccount.class);
				startActivity(intent);
				((Activity) context).finish();
			}
		});

		deletePasswordListButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, DeletePassword.class);
				startActivity(intent);
				((Activity) context).finish();
			}
		});

		newFolderButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewFolder.class);
				startActivity(intent);
				((Activity) context).finish();
			}
		});
	}

	public ArrayList<ExpandListGroup> SetStandardGroups() {
		ArrayList<ExpandListGroup> folders = new ArrayList<ExpandListGroup>();
		System.out.println(PasswordFile.getFolders());
		Log.e(null, PasswordFile.getFolders().toString());
		for (String folder : PasswordFile.getFolders()) {
			ExpandListGroup folderGroup = new ExpandListGroup();
			folderGroup.setName(" " + folder);
			ArrayList<ExpandListChild> children = new ArrayList<ExpandListChild>();
			for (String childName : PasswordFile.getAccountsInFolder(folder)) {
				ExpandListChild child = new ExpandListChild();
				child.setName("      " + childName);
				children.add(child);
			}
			folderGroup.setItems(children);
			folders.add(folderGroup);
		}

		return folders;
	}

	private void launchNotifications() {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager notificationManager = (NotificationManager) getSystemService(ns);

		int icon = R.drawable.ic_launcher;

		Context context = getApplicationContext();
		CharSequence contentUsernameTitle = "Username";
		CharSequence contentUsernameText = "press to copy to clipboard";

		CharSequence contentPasswordTitle = "Password";
		CharSequence contentPasswordText = "press to copy to clipboard";

		CharSequence contentClearTitle = "Clear";
		CharSequence contentClearText = "erase everything from the clipboard";

		Intent usernameNotificationIntent = new Intent(this,
				CopyUsernameToClipboard.class);

		PendingIntent usernameIntent = PendingIntent.getActivity(this, 0,
				usernameNotificationIntent, 0);

		Notification usernameNotification = new Notification.Builder(context)
				.setContentTitle(contentUsernameTitle)
				.setContentText(contentUsernameText).setSmallIcon(icon)
				.setContentIntent(usernameIntent).build();

		Intent passwordNotificationIntent = new Intent(this,
				CopyPasswordToClipboard.class);

		PendingIntent passwordIntent = PendingIntent.getActivity(this, 0,
				passwordNotificationIntent, 0);

		Notification passwordNotification = new Notification.Builder(context)
				.setContentTitle(contentPasswordTitle)
				.setContentText(contentPasswordText).setSmallIcon(icon)
				.setContentIntent(passwordIntent).build();

		Intent clearNotificationIntent = new Intent(this, ClearClipboard.class);

		PendingIntent clearIntent = PendingIntent.getActivity(this, 0,
				clearNotificationIntent, 0);
		
		Notification clearNotification = new Notification.Builder(context)
				.setContentTitle(contentClearTitle)
				.setContentText(contentClearText).setSmallIcon(icon)
				.setContentIntent(clearIntent).build();

		notificationManager.notify(200002, passwordNotification);
		notificationManager.notify(300003, usernameNotification);
		notificationManager.notify(100001, clearNotification);
	}
}
