package com.haodaibao.fund.trade.util;

import java.io.UnsupportedEncodingException;
public class Convert {
	/**
	 * 字节转16进制
	 * @param b
	 * @return
	 */
	public static String byte2Hex(byte[] b) {
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	public static byte[] hex2Byte(String hexString){
		if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
		hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}
	
	private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	} 
	
	/**
	 * bcd转码
	 * 
	 * @param asc
	 * @return
	 */
	public static byte[] asc2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	/**
	 * 将字符串转换成二进制数组
	 * @param source  : 16字节
	 * @return
	 */

	public static int[] string2Binary(String source) {
		int len = source.length();
		int[] dest = new int[len * 4];
		char[] arr = source.toCharArray();
		for (int i = 0; i < len; i++) {
			int t = 0;
			try {
				t = getIntByChar(arr[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] str = Integer.toBinaryString(t).split("");
			int k = i * 4 + 3;
			for (int j = str.length - 1; j > 0; j--) {
				dest[k] = Integer.parseInt(str[j]);
				k--;
			}
		}
		return dest;
	}

	/**
	 * 将十六进制A--F转换成对应数
	 * @param ch
	 * @return
	 * @throws Exception
	 */

	public static int getIntByChar(char ch) throws Exception {
		char t = Character.toUpperCase(ch);
		int i = 0;
		switch (t) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			i = Integer.parseInt(Character.toString(t));
			break;
		case 'A':
			i = 10;
			break;
		case 'B':
			i = 11;
			break;
		case 'C':
			i = 12;
			break;
		case 'D':
			i = 13;
			break;
		case 'E':
			i = 14;
			break;
		case 'F':
			i = 15;
			break;
		default:
			throw new Exception("getIntByChar was wrong");
		}
		return i;
	}

	/**
	 * 
	 * 将ASC字符串转16进制
	 * 
	 * @param asc
	 * 
	 * @return
	 */

	public static String asc2hex(String asc) {
		StringBuffer hex = new StringBuffer();
		try {
			byte[] bs = asc.toUpperCase().getBytes("UTF-8");
			for (byte b : bs) {
				hex.append(Integer.toHexString(new Byte(b).intValue()));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hex.toString();
	}

	/**
	 * 将二进制字符串转换成十六进制字符
	 * @param s
	 * @return
	 */

	public static String binary2ASC(String s) {
		String str = "";
		int ii = 0;
		int len = s.length();
		// 不够4bit左补0
		if (len % 4 != 0) {
			while (ii < 4 - len % 4) {
				s = "0" + s;
			}
		}
		for (int i = 0; i < len / 4; i++) {
			str += binary2Hex(s.substring(i * 4, i * 4 + 4));
		}
		return str;
	}

	/**
	 * s位长度的二进制字符串
	 * @param s
	 * @return
	 */

	public static String binary2Hex(String s) {
		int len = s.length();
		int result = 0;
		int k = 0;
		if (len > 4)
			return null;
		for (int i = len; i > 0; i--) {
			result += Integer.parseInt(s.substring(i - 1, i)) * getXY(2, k);
			k++;
		}
		switch (result) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return "" + result;
		case 10:
			return "A";
		case 11:
			return "B";
		case 12:
			return "C";
		case 13:
			return "D";
		case 14:
			return "E";
		case 15:
			return "F";
		default:
			return null;
		}
	}

	/**
	 * 返回x的y次方
	 * @param x
	 * @param y
	 * @return
	 */
	public static int getXY(int x, int y) {
		int temp = x;
		if (y == 0)
			x = 1;
		for (int i = 2; i <= y; i++) {
			x *= temp;
		}
		return x;
	}
}
