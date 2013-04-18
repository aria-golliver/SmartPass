package com.passwordLib;

import android.annotation.SuppressLint;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

@SuppressLint("DefaultLocale")
public class PasswordLib {

	final static private String lowercase = "abcdefghijklmnopqrstuvwxyz";

	final static private String uppercase = "abcdefghijklmnopqrstuvwxyz"
			.toUpperCase();
	final static private String numbers = "0123456789";
	final static private String specialCharacters = "!@#$%^&*()_+-=[]{};':\",./<>?\\|";

	public static String createPassword(int maxLength, boolean lower,
			boolean upper, boolean number, boolean special) {
		String possibleChars = "";
		if (lower)
			possibleChars += lowercase;
		if (upper)
			possibleChars += uppercase;
		if (number)
			possibleChars += numbers;
		if (special)
			possibleChars += specialCharacters;

		char[] possibleCharsArray = possibleChars.toCharArray();

		char password[] = new char[maxLength];

		for (int i = 0; i < maxLength; i++) {
			int rand = (int) (Math.random() * possibleCharsArray.length);
			password[i] = possibleCharsArray[rand];
		}

		return String.valueOf(password);
	}

	/*
	 * public static String createEncryptedPassword(int maxLength, boolean
	 * lower, boolean upper, boolean number, boolean special, String
	 * masterPassword) { try { return SimpleCrypto.encrypt(masterPassword,
	 * createPassword(maxLength, lower, upper, number, special)); } catch
	 * (InvalidKeyException e) { e.printStackTrace(); } catch
	 * (NoSuchAlgorithmException e) { e.printStackTrace(); } catch
	 * (NoSuchPaddingException e) { e.printStackTrace(); } catch
	 * (IllegalBlockSizeException e) { e.printStackTrace(); } catch
	 * (BadPaddingException e) { e.printStackTrace(); } return null; }
	 */

	public static String encrypt(String input, String masterPassword) {
		try {
			return SimpleCrypto.encrypt(input, masterPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String decode(String encryptedString, String masterPassword) {
		try {
			return (String) SimpleCrypto.decrypt(encryptedString,
					masterPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
