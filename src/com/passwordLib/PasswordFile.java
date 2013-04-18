package com.passwordLib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.content.Context;

import com.example.smartpass.UserAccount;

public final class PasswordFile {
	public static String username;
	public static String saltedPassword;
	public static String password;
	private static String salt;
	public static String fileName;

	public static String selectedPassword = "";
	public static String selectedUsername = "";

	private static HashMap<String, UserAccount> encryptedPasswordFile;

	private static Set<String> folderList;

	static Context context;

	@SuppressWarnings("unchecked")
	public static void initPasswordFile(String _username, String _password,
			Context _context) {
		username = _username;
		salt = _username + "L6M9A6O926C9A6T936S9W6A9G659U69";
		saltedPassword = _password + salt;
		password = _password;
		context = _context;
		fileName = username + ".pass";

		// download the password file
		// System.out.println("Downloading password file");

		/* read from disk */
		FileInputStream f_in;
		ObjectInputStream obj_in;
		try {
			f_in = new FileInputStream(context.getFilesDir() + "/" + fileName);
			obj_in = new ObjectInputStream(f_in);

			Object obj = obj_in.readObject();

			if (obj instanceof HashMap<?, ?>) {
				encryptedPasswordFile = (HashMap<String, UserAccount>) obj;
			} else {
				encryptedPasswordFile = new HashMap<String, UserAccount>();
			}
			System.out.println(encryptedPasswordFile.toString());
			System.out.println("loaded from file");

			f_in.close();
			obj_in.close();

			PasswordFile.folderList = new TreeSet<String>();
			for (String folder : encryptedPasswordFile.keySet()) {
				String folderName = folder.split(":")[0];
				PasswordFile.folderList.add(folderName);
			}

		} catch (FileNotFoundException e) {
			encryptedPasswordFile = new HashMap<String, UserAccount>();
		} catch (IOException e) {
			encryptedPasswordFile = new HashMap<String, UserAccount>();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public static void saveFile() {
		try {
			FileOutputStream f_out = new FileOutputStream(context.getFilesDir()
					+ "/" + fileName);
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
			obj_out.writeObject(encryptedPasswordFile);
			f_out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Mistake");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Mistake");
			e.printStackTrace();
		}
	}

	public static void addPassword(String newPassPlainText, String newUsername,
			String folder, String passwordName) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		String fullName = folder + ":" + passwordName;

		String encryptedPassword = "";
		try {
			encryptedPassword = SimpleCrypto.encrypt(newPassPlainText,
					saltedPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			SimpleCrypto.decrypt(encryptedPassword, saltedPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		UserAccount ua = new UserAccount(newUsername, encryptedPassword,
				folder, passwordName);

		encryptedPasswordFile.put(fullName, ua);
		Object args[] = new Object[] { context, username, saltedPassword };
		new SendPasswordTask().execute(args);
	}

	public static Set<String> getFolders() {
		return PasswordFile.folderList;
	}

	public static ArrayList<String> getAccountsInFolder(String folder) {
		ArrayList<String> accounts = new ArrayList<String>();
		for (String account : encryptedPasswordFile.keySet()) {
			String currentFolder = account.split(":")[0];
			if (currentFolder.equals(folder)) {
				accounts.add(account.split(":")[1]);
			}
		}
		return accounts;
	}

	public static void addFolder(String folderName) {
		PasswordFile.folderList.add(folderName);
	}

	public static String getPassword(String folder, String accoutnname) {
		String fullname = folder + ":" + accoutnname;
		String encryptedPass = PasswordFile.encryptedPasswordFile.get(fullname).encryptedPassword;
		return PasswordLib.decode(encryptedPass, saltedPassword);
	}

	public static String getUsername(String folder, String accoutnname) {
		String fullname = folder + ":" + accoutnname;
		String username = encryptedPasswordFile.get(fullname).username;

		// return PasswordLib.decode(encryptedPass, password);
		return username;
	}

	public static UserAccount getUserAccount(String folder, String accountName) {
		String fullname = folder + ":" + accountName;
		return encryptedPasswordFile.get(fullname);
	}

	public static void deletePassword(String folder, String accountName) {
		String fullname = folder + ":" + accountName;
		encryptedPasswordFile.remove(fullname);

	}

}
