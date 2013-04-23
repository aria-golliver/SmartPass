package com.example.smartpass;

import java.io.Serializable;

public class UserAccount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3508430212625390708L;

	public UserAccount(String newUsername, String encryptedPassword,
			String folder, String websitename) {
		this.username = newUsername;
		this.encryptedPassword = encryptedPassword;
		this.folder = folder;
		this.websitename = websitename;
	}

	public final String username;
	public final String websitename;
	public final String encryptedPassword;
	public final String folder;

	@Override
	public String toString() {
		return "username: " + username;
	}
}
