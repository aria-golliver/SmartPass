package com.passwordLib;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.crypto.spec.*;

public class SimpleCrypto {
	private static final String ALGORITHME = "Blowfish";
	private static final String TRANSFORMATION = "Blowfish/ECB/PKCS5Padding";
	private static final String CHARSET = "ISO-8859-1";

	public static String encrypt(String plaintext, String SECRET)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE,
				new SecretKeySpec(SECRET.getBytes(CHARSET), ALGORITHME));
		return new String(cipher.doFinal(plaintext.getBytes()), CHARSET);
	}

	public static String decrypt(String ciphertext, String SECRET)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SECRET.getBytes(),
				ALGORITHME));
		return new String(cipher.doFinal(ciphertext.getBytes(CHARSET)), CHARSET);
	}
}