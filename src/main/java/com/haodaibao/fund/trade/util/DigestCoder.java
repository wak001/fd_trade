package com.haodaibao.fund.trade.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class DigestCoder {
	
	/**
	 * MD5 16/32摘要处理
	 * @param plainText 处理数据 
	 * @param model
	 */
	public static String md5s(byte[] data,int model) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data);
			byte b[] = md.digest();

			String buf = Convert.byte2Hex(b);
			return model == 32 ? buf.toString() :  buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * MD5 32位摘要运算
	 * @param plainText
	 */
	public static String md5s(byte[] data){
		return md5s(data,32);
	}
	
	/**
	 * SHA1摘要算法
	 *<p>Description:</p>
	 * @Title: SHA1s 
	 * @param data
	 * @return
	 * @throws Exception
	 * @author suxx
	 */
	public static String SHA1s(byte[] data) throws Exception
	{
		MessageDigest msg = MessageDigest.getInstance("SHA");
		byte[] 	b = msg.digest(data);
		return Convert.byte2Hex(b);
	}
	
	

	public static void main(String[] args) throws Exception {
		System.out.println(md5s("123".getBytes(),16));
		System.out.println(md5s("123".getBytes(),32));
	}
}
