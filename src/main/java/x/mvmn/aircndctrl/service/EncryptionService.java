package x.mvmn.aircndctrl.service;

public interface EncryptionService {

	String decryptBase64ToStr(String messageBase64);

	String encryptStrToBase64(String message);

	String decryptBase64ToStr(String messageBase64, byte[] encryptionKey);

	String encryptStrToBase64(String message, byte[] encryptionKey);

	String decryptBase64ToStr(String messageBase64, String encryptionKey);

	String encryptStrToBase64(String message, String encryptionKey);
}