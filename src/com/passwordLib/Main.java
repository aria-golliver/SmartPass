package com.passwordLib;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Main {
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
//		PasswordFile.initPasswordFile("Golliver+Earl", "bitcoin1toor");
//		String password = PasswordLib.createPassword(24, true, true, true, true);
//		PasswordFile.addPassword(password, "email", "gmail");
//		password = PasswordLib.createPassword(24, true, true, true, true);
//		PasswordFile.addPassword(password, "email", "yahoo");
//		password = PasswordLib.createPassword(24, true, true, true, true);
//		PasswordFile.addPassword(password, "email", "hotmail");
//		
//
//		password = PasswordLib.createPassword(24, true, true, true, true);
//		PasswordFile.addPassword(password, "sports", "espn");
//		password = PasswordLib.createPassword(24, true, true, true, true);
//		PasswordFile.addPassword(password, "sports", "fifa");
//		password = PasswordLib.createPassword(24, true, true, true, true);
//		PasswordFile.addPassword(password, "tech", "ieee");
		
		System.out.println(PasswordFile.getFolders());
		for(String folder : PasswordFile.getFolders())
			System.out.println(folder + ": " + PasswordFile.getAccountsInFolder(folder));
	}
}
