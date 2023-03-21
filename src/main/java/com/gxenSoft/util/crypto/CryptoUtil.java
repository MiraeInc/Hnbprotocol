package com.gxenSoft.util.crypto;


import org.apache.commons.codec.binary.Base64;

import com.gxenSoft.sqlMap.SqlMap;

import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {
	
	private static byte[] keyValue = new byte[] { 'M', 'A', 'N', 'D', 'O', 'M', '0', 'A', '4', 'A', '5', 'C', '6', 'D', '0', 'A'};
	
	public static String encrypt(String input) {
        byte[] crypted = null;
        try {

            SecretKeySpec skey = new SecretKeySpec(keyValue, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            return input;
        }

        return Base64.encodeBase64String(crypted);
    }


    public static String decrypt(String input) {
        byte[] output = null;
        try {

            SecretKeySpec skey = new SecretKeySpec(keyValue, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decodeBase64(input));

        } catch (Exception e) {
            return input;
        }
        return new String(output);
    }
    
    public static SqlMap decryptMapColumn(SqlMap map, String columnNameList) {
    	if(map == null) return null;
    	if(columnNameList == null || "".equalsIgnoreCase(columnNameList)) return null;
        String[] colList = columnNameList.split(",");
        if(colList == null || colList.length == 0) return null;
    	
        for(String col : colList) {
    		String columnName = col.trim();
    		if(map.get(columnName) != null && !"".equalsIgnoreCase(map.get(columnName).toString())) {
        		map.put(columnName, CryptoUtil.decrypt(map.get(columnName).toString()));
        	}
        }
        return map;
    }
    
    public static List<SqlMap> decryptList(List<SqlMap> list, String columnNameList) {
    	if(list == null || list.size() == 0) return list;
    	if(columnNameList == null || "".equalsIgnoreCase(columnNameList)) return list;
        String[] colList = columnNameList.split(",");
        if(colList == null || colList.length == 0) return list;

        for(String col : colList) {
    		String columnName = col.trim();
        	for(int i = 0; i < list.size(); i++) {
        		SqlMap obj = list.get(i);
        		
    			if(obj.get(columnName) != null && !"".equalsIgnoreCase(obj.get(columnName).toString())) {
    				obj.put(columnName, CryptoUtil.decrypt(obj.get(columnName).toString()));
    			}
    		}
        }
        
    	return list;
    }
    
    public static void main(String[] args)throws Exception {
        System.out.println("양방향암호화=="+CryptoUtil.encrypt("ABCDEFG~!@#$%^&*()"));
        System.out.println("양방향복호화=="+CryptoUtil.decrypt("xAts6TAo02A8VsMDEm6Cm7dJXKD6m7bmemR7W+9sp70="));
	}
}




