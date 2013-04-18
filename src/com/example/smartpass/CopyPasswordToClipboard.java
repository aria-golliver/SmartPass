package com.example.smartpass;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.Menu;

import com.passwordLib.PasswordFile;

public class CopyPasswordToClipboard extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_copy_password_to_clipboard);

		ClipData cd = ClipData.newPlainText("password: ",
				PasswordFile.selectedPassword);

		ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		cm.setPrimaryClip(cd);
		((Activity) this).finishAffinity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.copy_to_clipboard, menu);
		return true;
	}

}
