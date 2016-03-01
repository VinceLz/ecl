package Util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;

/*
 * md5加密
 */
public class Md5Util {
	
	

		public static String createMd5(String password){
			
		        MessageDigest md5 = null;
		        try {
		            md5 = MessageDigest.getInstance("MD5");
		        } catch (Exception e) {
		            System.out.println(e.toString());
		            e.printStackTrace();
		            return "";
		        }

		        byte[] byteArray=null;
				try {
					byteArray = password.getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				}
		        byte[] md5Bytes = md5.digest(byteArray);
		        StringBuffer hexValue = new StringBuffer();
		        for (int i = 0; i < md5Bytes.length; i++) {
		            int val = ((int) md5Bytes[i]) & 0xff;
		            if (val < 16) {
		                hexValue.append("0");
		            }
		            hexValue.append(Integer.toHexString(val));
		        }
		        return hexValue.toString();
			}
		public static void main(String[] args) {
			System.out.print(createMd5("admin"));
		}
}

