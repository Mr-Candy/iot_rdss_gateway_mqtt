package com.rd.iot_rdss_gateway.util;

public class StringByteConvertor {
	
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if(src == null || src.length <= 0){
			return null;
		}
		 for(int i=0; i<src.length; i++){
			 int v = src[i] & 0xFF;
			 String hv = Integer.toHexString(v);
			 if(hv.length() < 2){
				 stringBuilder.append(0);
			 }
			 stringBuilder.append(hv);
		 }
		 return stringBuilder.toString();
	}
	
	public static byte[] hexStringToBytes(String hexString) {
		if(hexString == null || hexString.trim().equals("")){
			return null;
		}
		
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		
		if(length == 0){
			byte[] d = new byte[1];
			d[0] = charToByte(hexChars[0]);
			return d;
		}

		byte[] d = new byte[length];
		
		for(int i=0; i<length; i++){
			int pos = i * 2;
			d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos+1]));
		}
		
		return d;
	}
	
	private static byte charToByte(char c) {
		return (byte)"0123456789ABCDEF".indexOf(c);
	}
}
