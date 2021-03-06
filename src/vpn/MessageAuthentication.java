package vpn;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;

import javax.crypto.Cipher;

public class MessageAuthentication {
	
	
	public static PrivateKey generateMAC() throws Exception{
		
		// Generate new key
		KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		
		return privateKey;
	}
	
	
	public static byte[] computeMAC(String plaintext) throws Exception{
		
		// Generate new key
		KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		
		// Compute signature
		Signature instance = Signature.getInstance("SHA1withRSA");
		instance.initSign(privateKey);
		instance.update((plaintext).getBytes());
		byte[] signature = instance.sign();
		
		// Compute digest
		MessageDigest sha1 = MessageDigest.getInstance("SHA1");
		byte[] digest = sha1.digest((plaintext).getBytes());
		
		// Encrypt digest
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] cipherText = cipher.doFinal(digest);
		
		// Display results
		System.out.println("Input data: " + plaintext);
		System.out.println("Digest: " + bytes2String(digest));
		System.out.println("Cipher text: " + bytes2String(cipherText));
		System.out.println("Signature: " + bytes2String(signature));

		return cipherText;
	}
	
	
	
	//test
	
	public static void main(String[] args) throws Exception {
		
	
		String plaintext = "This is the message being signed";

		byte[] cipherText = computeMAC(plaintext);

		// Display results
		System.out.println("Input data: " + plaintext);
		//System.out.println("Digest: " + bytes2String(digest));
		System.out.println("Cipher text: " + bytes2String(cipherText));
		//System.out.println("Signature: " + bytes2String(signature));
	}
	
	
	private static String bytes2String(byte[] bytes) {
	    StringBuilder string = new StringBuilder();
	    for (byte b : bytes) {
	        String hexString = Integer.toHexString(0x00FF & b);
	        string.append(hexString.length() == 1 ? "0" + hexString : hexString);
	    }
	    return string.toString();
	}
}