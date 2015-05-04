package com.haodaibao.fund.trade.util;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;


public class Base64Coder {
	private final static String ENCODING = "UTF-8";
	
	public static String base64Encode(byte[] data) throws IOException{
		return Base64.encodeBase64String(data);
	}
	
	public static String base64Encode(String data) throws IOException{
		return base64Encode(data,ENCODING);
	}
	
	public static String base64Encode(String data,String charset) throws IOException{
		byte[] b =Base64.encodeBase64URLSafe(data.getBytes(charset));
		return new String(b,charset);
	}
	
	
	public static String base64EncodeSafe(String data) throws IOException
	{
		return base64EncodeSafe(data,ENCODING);
	}
	
	
	public static String base64EncodeSafe(String data,String charset) throws IOException
	{
		byte[] b =Base64.encodeBase64URLSafe(data.getBytes(charset));
		return new String(b,charset);
	}
	
	
	public static String decode(String data) throws IOException
	{
		return decode(data,ENCODING);
	}
	
	public static String decode(String data,String charset) throws IOException
	{
		byte[] b =Base64.decodeBase64(data.getBytes(charset));
		return new String(b,charset);
	}
}
