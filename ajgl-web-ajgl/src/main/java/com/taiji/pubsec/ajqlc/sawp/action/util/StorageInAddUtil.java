package com.taiji.pubsec.ajqlc.sawp.action.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.taiji.pubsec.ajqlc.sawp.bean.InStorageFormItemServiceBean;

/**
 * 涉案物品入库新增获取数量和单位
 * @author lenovo
 *
 */
public class StorageInAddUtil {
	
   /**
    * 获取量词
    * @param number
    * @return
    */
	//TODO
	public static String measurementTransition(String number) {
		if(number==null){
			return "";
		}
	    String[] measurement = {"元人民币","千克","小包","大包","毫升","ml","ML","公斤",
	    		"元","件","个","盘","张","瓶","本","把","部","克","斤","包","份","串",
	    		"枚","只","支","颗","台","升","箱","副","辆","条","根","双","捆"};
	    for (int i = 0; i < measurement.length; i++) {
		    if (number.indexOf( measurement[i]) != -1 ) {
			    return measurement[i];
		    }
	    }
	    return number.substring(number.length()-1);
	}
	
	/**
	 * 获取数量
	 * @param number
	 * @return
	 */
	//TODO
	public static Double numTransition(String number) {
		if(null == number){
			return 0.0;
		}
		InStorageFormItemServiceBean isfisb = new InStorageFormItemServiceBean();
		
		Double in = isfisb.getNumberRequested();
		
		try {
			in= (Double) w(number);
			if(in == 0){
				 String regEx = "[0-9]";  
				 String str = matchResult(Pattern.compile(regEx), number);  
				 in = (double) Integer.valueOf(str).intValue();
			}
		} catch (Exception e) {
			
		}
		return in == null?0.0:in;
	}
	
	  /**
	   * @see 转万及以下的
	   * @param str
	   * @return
	   */
	  public static double w(String str){
	      double sum=0;
	      int index=str.indexOf("万");
	      if (index>0){
	    	  String temp=str.substring(0,index );
	    	  str=str.substring(index+1);
	    	  sum=sum+str2num(temp)*10000;
	      }
	      index=str.indexOf("仟");
	      if (index>0){
	          String temp=str.substring(0,index );
	          str=str.substring(index+1);
	          sum=sum+str2num(temp)*1000;
	      }
	      index=str.indexOf("千");
	      if (index>0){
	    	  String temp=str.substring(0,index );
	    	  str=str.substring(index+1);
	    	  sum=sum+str2num(temp)*1000;
	      }
	      index=str.indexOf("佰");
	      if (index>0){
	          String temp=str.substring(0,index );
	          str=str.substring(index+1);
	          sum=sum+str2num(temp)*100;

	      }
	      index=str.indexOf("百");
	      if (index>0){
	    	  String temp=str.substring(0,index );
	    	  str=str.substring(index+1);
	    	  sum=sum+str2num(temp)*100;
	    	  
	      }
	      index=str.indexOf("拾");
	      if (index>=0){
	          String temp=str.substring(0,index );
	          str=str.substring(index+1);
	          if(index == 0){
	        	  sum = 10; 
	          }else{
	        	  sum=sum+str2num(temp)*10;
	          }
	      }
	      index=str.indexOf("十");
	      if (index>=0){
	    	  String temp=str.substring(0,index );
	    	  str=str.substring(index+1);
	    	  if(index == 0){
	        	  sum = 10; 
	          }else{
	        	  sum=sum+str2num(temp)*10;
	          }
	      }
	      index=str.indexOf("点");
	      if (str.length()>0) {
	    	  if (index>0){
	    		  String temp=str.substring(0,index );
		    	  str=str.substring(index);
		    	  if(null != temp){
		    		  sum=sum+str2num(temp);
		    	  }
	    	  }else{
	    		  sum=sum+str2num(str) ;
	    	  }
	      }
	      index=str.indexOf("点");
	      if (index==0){
	    	  String temp=str.substring(index+1,index+2);
	    	  str=str.substring(index+2);
	    	  sum=sum+str2num(temp)*0.1;
	    	  if(str.length()>0){
	    		  sum=sum+str2num(str)*0.01;
	    	  }
	      }
	      
	      return sum;
	  }

	 /**
	   * 大写转小写
	   * @param str
	   * @return
	   */
	  public static int str2num(String str){
	      String[] daxie={"壹","贰","叁","肆","伍","陆","柒","捌","玖",
	    		  		  "一","二","三","四","五","六","七","八","九","零"};
	      int[] num={1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,0};
	      for (int i = 0; i < daxie.length; i++) {
	            if ( str.equals( daxie[i] ) ) {
	                 return num[i];
	            }
	          }
	      return 0;
	  }
	
	  public static String matchResult(Pattern p,String str)  
	    {  
	        StringBuilder sb = new StringBuilder();  
	        Matcher m = p.matcher(str);  
	        while (m.find())  
	        for (int i = 0; i <= m.groupCount(); i++)   
	        {  
	            sb.append(m.group());     
	        }  
	        return sb.toString();  
	    }  
}
