package x.mvmn.aircndctrl.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import x.mvmn.aircndctrl.service.EncryptionService;

public class EncryptionServiceImpl implements EncryptionService {

	private static final String CIPHER_TYPE = "AES";
	private static byte[] DEFAULT_KEY = "a3K8Bx%2r8Y7#xDh".getBytes(StandardCharsets.UTF_8);
	private byte[] defaultEncryptionKey;

	public EncryptionServiceImpl(byte[] defaultEncryptionKey) {
		this.defaultEncryptionKey = defaultEncryptionKey;
	}

	public EncryptionServiceImpl() {
		this(DEFAULT_KEY);
	}

	public byte[] decrypt(byte[] message, byte[] encryptionKey) {
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encryptionKey, CIPHER_TYPE));
			return cipher.doFinal(message);
		} catch (Exception e) {
			throw new RuntimeException("Decryption error", e);
		}
	}

	public byte[] encrypt(byte[] message, byte[] encryptionKey) {
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptionKey, CIPHER_TYPE));
			return cipher.doFinal(message);
		} catch (Exception e) {
			throw new RuntimeException("Decryption error", e);
		}
	}

	public String decryptToStr(byte[] message, byte[] encryptionKey) {
		byte[] result = decrypt(message, encryptionKey);
		return new String(result, StandardCharsets.UTF_8);
	}

	public byte[] encryptStr(String message, byte[] encryptionKey) {
		return encrypt(message.getBytes(StandardCharsets.UTF_8), encryptionKey);
	}

	@Override
	public String decryptBase64ToStr(String messageBase64) {
		return decryptToStr(Base64.getDecoder().decode(messageBase64), defaultEncryptionKey);
	}

	@Override
	public String encryptStrToBase64(String message) {
		return Base64.getEncoder().encodeToString(encryptStr(message, defaultEncryptionKey));
	}

	@Override
	public String decryptBase64ToStr(String messageBase64, byte[] encryptionKey) {
		return decryptToStr(Base64.getDecoder().decode(messageBase64), encryptionKey);
	}

	@Override
	public String encryptStrToBase64(String message, byte[] encryptionKey) {
		return Base64.getEncoder().encodeToString(encryptStr(message, encryptionKey));
	}

	@Override
	public String decryptBase64ToStr(String messageBase64, String encryptionKey) {
		return decryptBase64ToStr(messageBase64, encryptionKey.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public String encryptStrToBase64(String message, String encryptionKey) {
		return encryptStrToBase64(message, encryptionKey.getBytes(StandardCharsets.UTF_8));
	}
}
