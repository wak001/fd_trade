package com.haodaibao.fund.trade.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;


public class TripleDesCoder {
	public static final String KEY_ALGORITHM = "DESede";
	public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
	
	/**
	 * 还原秘钥
	 *<p>Description:</p>
	 * @Title: toKey 
	 * @param key
	 * @return
	 * @throws Exception
	 * @author suxx
	 */
	private static Key toKey(byte[] key) throws Exception{
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		return keyFactory.generateSecret(dks);
	}
	
	/**
	 * 解密
	 *<p>Description:</p>
	 * @Title: decrypt 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 * @author suxx
	 */
	public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE,k);
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密
	 *<p>Description:</p>
	 * @Title: encrypt 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 * @author suxx
	 */
	public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE,k);
		return cipher.doFinal(data);
	}
	
	/**
	 * 生成秘钥
	 *<p>Description:</p>
	 * @Title: initKey 
	 * @return
	 * @throws Exception
	 * @author suxx
	 */
	public static byte[] initKey() throws Exception{
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(168);
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
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
		String key = "qwertyuioplkjhgfdsazxcvb";
		byte[] data = TripleDesCoder.encrypt("aaaa".getBytes("gbk"), key.getBytes());
		byte[] dedata = TripleDesCoder.decrypt(data, key.getBytes());
		String endata = new String(data,"gbk");
		String dedatastr = new String(dedata,"gbk");
		System.out.println(endata);
		System.out.println(dedatastr);
//		TripleDesCoder.decrypt("2鮕?".getBytes(), key.getBytes());
	}
	
}
