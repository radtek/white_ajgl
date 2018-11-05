package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestTest {
	
	public static boolean checkRegular(String iniString) {
        // write code here
		
		return !iniString.matches(".*(.)(.*\\1).*");
    }
	
	public static void main(String[] args){
		String str = "this    is    text";
		System.out.println(str.matches("this\\s+is\\s+text"));
		
		System.out.println("--------------------------------");
		
		String content = "I am noob from runoob.com.";
		String pattern = ".*runoob.*";
		
		boolean isMatch = Pattern.matches(pattern, content);
		System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
		
		
		System.out.println("--------------------------------");
		
		// 按指定模式在字符串查找
	      String line = "This order was placed for QT3000! OK?";
	      String pattern1 = "(\\D*)(\\d+)(.*)";
	 
	      // 创建 Pattern 对象
	      Pattern r = Pattern.compile(pattern1);
	 
	      // 现在创建 matcher 对象
	      Matcher m = r.matcher(line);
	      if (m.find( )) {
	         System.out.println("Found value: " + m.group(0) );
	         System.out.println("Found value: " + m.group(1) );
	         System.out.println("Found value: " + m.group(2) );
	         System.out.println("Found value: " + m.group(3) ); 
	      } else {
	         System.out.println("NO MATCH");
	      }
	}
}
