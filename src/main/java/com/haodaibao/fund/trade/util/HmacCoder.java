package com.haodaibao.fund.trade.util;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HmacCoder {
	public static byte[] initHmacMD5Key() throws Exception
	{
		KeyGenerator keygenerator = KeyGenerator.getInstance("HmacMD5");
		SecretKey secretKey = keygenerator.generateKey();
		return secretKey.getEncoded();
	}
	
	public static byte[] encodeHmacMD5(byte[] data, byte[] key) throws Exception{
		SecretKey secretKey = new SecretKeySpec(key,"HmacMD5");
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
	
	public static byte[] initHamcSHAKey() throws Exception{
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA1");
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}
	
	public static byte[] encodeHmacSHA(byte[] data,byte[] key) throws Exception{
		SecretKey secretKey = new SecretKeySpec(key,"HmacSHA1");
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
}
