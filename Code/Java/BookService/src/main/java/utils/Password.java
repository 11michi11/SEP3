package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.UUID;

public class Password {
	
	private static SecureRandom random = new SecureRandom();
	private static final String dic = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int PWD_LEN = 8;

	public static String generatePasswordWithLength(int len) {
		String result = "";
		for (int i = 0; i < len; i++) {
			int index = random.nextInt(dic.length());
			result += dic.charAt(index);
		}
		return result;
	}

	public static String encryptPwd(String pwd) {
		MessageDigest dig;
		String encrypted = "";
		try {
			dig = MessageDigest.getInstance("SHA-256");
			dig.update(pwd.getBytes());
			encrypted = toHex(dig.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encrypted;
	}

	private static String toHex(byte[] byteData) {
		StringBuilder hexString = new StringBuilder();
		for (byte aByteData : byteData) {
			String hex = Integer.toHexString(0xff & aByteData);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter pwd to encrypt");
		System.out.println(Password.encryptPwd(in.nextLine()));

		System.out.println(UUID.randomUUID().toString());
	}

}
