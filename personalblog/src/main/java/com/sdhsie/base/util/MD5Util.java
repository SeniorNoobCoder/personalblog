package com.sdhsie.base.util;

import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
public class MD5Util {  
    private final static char[] hexDigits = { '0', '1', '2', '3', '4', '5',  
            '6', '7', '8', '9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A', 'B', 'C', 'D', 'E', 'F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };  
    private static String bytesToHex(byte[] bytes) {  
        StringBuffer sb = new StringBuffer();  
        int t;  
        for (int i = 0; i < 16; i++) {  
            t = bytes[i];  
            if (t < 0)  
                t += 256;  
            sb.append(hexDigits[(t >>> 4)]);  
            sb.append(hexDigits[(t % 16)]);  
        }  
        return sb.toString();  
    }  
    public static String MD5(String input) throws Exception {  
        return code(input, 32);  
    }  
    
    public static String md516(String input) throws Exception {  
        return code(input, 16);  
    }  
    
    public static String code(String input, int bit) throws Exception {  
        try {  
            MessageDigest md = MessageDigest.getInstance(System.getProperty(  
                    "MD5.algorithm", "MD5"));  
            if (bit == 16)  
                return bytesToHex(md.digest(input.getBytes("utf-8")))  
                        .substring(8, 24);  
            return bytesToHex(md.digest(input.getBytes("utf-8")));  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            throw new Exception("Could not found MD5 algorithm.", e);  
        }  
    }  
    public static String md5_3(String b) throws Exception{  
        MessageDigest md = MessageDigest.getInstance(System.getProperty(  
                "MD5.algorithm", "MD5"));  
        byte[] a = md.digest(b.getBytes());  
        a = md.digest(a);  
        a = md.digest(a);  
          
        return bytesToHex(a);  
    }  
    
}  