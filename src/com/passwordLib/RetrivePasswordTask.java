package com.passwordLib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.smartpass.LoginScreen;
import com.example.smartpass.PasswordList;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class RetrivePasswordTask extends AsyncTask<Object, Void, Boolean> {

	Context context;
	String username;
	String password;

	@Override
	protected Boolean doInBackground(Object... params) {
		System.out.println("get args " + params[0]);
		context = (Context) (params[0]);
		System.out.println("get args " + params[1]);
		username = (String) (params[1]);
		System.out.println("get args " + params[2]);
		password = (String) (params[2]);
		JSch jsch = new JSch();
		Session session = null;

		final String SERVER = "app.smartfile.com";
		final String USERNAME = PasswordFile.username;
		final String PASSWORD = PasswordFile.password;

		try {
			session = jsch.getSession(USERNAME, SERVER, 22);

			session.setConfig("StrictHostKeyChecking", "no");

			session.setPassword(PASSWORD);

			session.connect();

			Channel channel = session.openChannel("sftp");

			channel.connect();

			ChannelSftp sftpChannel = (ChannelSftp) channel;

			String fileName = PasswordFile.fileName;

			sftpChannel.get(fileName, context.getFilesDir() + "/" + fileName);

			sftpChannel.exit();

			BufferedReader br = null;

			try {

				String sCurrentLine;

				br = new BufferedReader(new FileReader(context.getFilesDir()
						+ "/" + fileName));

				while ((sCurrentLine = br.readLine()) != null) {
					System.out.println(sCurrentLine);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		} catch (JSchException e) {
			e.printStackTrace();
			return false;
		} catch (SftpException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			System.out.println("retrieve error");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {

			PasswordFile.initPasswordFile(username, password, context);
			Intent intent = new Intent(context, PasswordList.class);
			context.startActivity(intent);

		} else {
			System.out.println("error logging in: " + username + "|" + password);

			Intent intent = new Intent(context, LoginScreen.class);
			context.startActivity(intent);

			CharSequence errorMessage = "Could not connect to SmartFile. Either your username/password was invalid, or the server is offline.";
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(context, errorMessage, duration);
			toast.show();
		}
	}

}
