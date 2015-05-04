package com.haodaibao.fund.trade.util;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class DesCoder {
	public static final String KEY_ALGORITHM = "DES";
	public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
	
	/**
	 * 生成秘钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(56);
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
	
	/**
	 * 还原秘钥
	 * @param key 秘钥字节
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception{
		DESKeySpec keySpec = new DESKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		SecretKey secretKey = keyfactory.generateSecret(keySpec);
		return secretKey;
	}
	
	/**
	 * des解密
	 * @param data 密文字节
	 * @param key  秘钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	
	/**
	 * des加密
	 * @param data 数据源
	 * @param key  秘钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,byte[] key)throws Exception{
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE,k);
		return cipher.doFinal(data);
	}
	
	/**
	 * des加密
	 * @param data
	 * @param key
	 * @param Charset
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data,String key,String charset) throws Exception{
		byte[] msg = encrypt(data.getBytes(charset),key.getBytes(charset));
		return Convert.byte2Hex(msg);
	}
	
	/**
	 * des解密
	 * @param data
	 * @param key
	 * @param Charset
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data,String key,String charset) throws Exception{
		byte[] msg = decrypt(Convert.hex2Byte(data),key.getBytes(charset));
		return new String(msg,charset);
	}
	
	public static InputStream getEncryptStream(InputStream in,byte[] key) throws Exception{
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE,k);
		CipherInputStream cin = new CipherInputStream(in, cipher);
		return cin;
	}
	
	public static InputStream getDecryptStream(InputStream in,byte[] key)  throws Exception{
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE,k);
		CipherInputStream cin = new CipherInputStream(in, cipher);
		return cin;
	}
	
	/**
	 * des文件加密
	 *<p>Description:</p>
	 * @Title: encryptFile 
	 * @param in
	 * @param key
	 * @throws Exception
	 * @author suxx
	 */
	public static void encryptFile(InputStream in,byte[] key,String outPutPath) throws Exception{
		InputStream cin = getEncryptStream(in, key);
		FileOutputStream os = new FileOutputStream(outPutPath);
		int i = -1;
		while((i = cin.read())!= -1){
			os.write(i);
		}
		os.flush();
		cin.close();
		os.close();
	}
	
	/**
	 * des文件解密
	 *<p>Description:</p>
	 * @Title: decryptFile 
	 * @param in
	 * @param key
	 * @throws Exception
	 * @author suxx
	 */
	public static void decryptFile(InputStream in,byte[] key,String outPutPath) throws Exception{
		InputStream cin = getDecryptStream(in, key);
		FileOutputStream os = new FileOutputStream(outPutPath);
		int i = -1;
		while((i = cin.read())!= -1){
			os.write(i);
		}
		os.flush();
		cin.close();
		os.close();
	}

	
	public static void main(String[] args) throws Exception {
		//String str = "abc农行0123456789";
//		byte[] by = initKey();
//		byte[] yb = DesCoder.encrypt(str.getBytes(), by);
//		byte[] rs = DesCoder.decrypt(yb, by);
//		System.out.println(Base64Coder.base64Encode(yb, false));
//		System.out.println(new String(rs).toString());
//		String yb = DesCoder.encrypt(str, "12345678","GB18030");
//		System.out.println(yb);
		String str = "hello world!";
		String key = "qwertyuioplkjhgfdsazxcvb";
		//DesCoder.encryptFile(new ByteArrayInputStream(str.getBytes()),key.getBytes(),"C:\\encrypt.txt");
		DesCoder.decryptFile(new FileInputStream("C:\\Users\\Administrator\\Desktop\\123456123456_20121206_purchase.txt"),key.getBytes(),"C:\\decrypt.txt");
	}
}
