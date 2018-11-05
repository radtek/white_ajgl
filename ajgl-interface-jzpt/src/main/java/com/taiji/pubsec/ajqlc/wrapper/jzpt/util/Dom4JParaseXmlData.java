package com.taiji.pubsec.ajqlc.wrapper.jzpt.util;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.taiji.persistence.dao.Pager;

public class Dom4JParaseXmlData {
	public static Element findElement(Element node){
		Element ele = null;
		if("Value".equals(node.getName()) || null == node ){
			ele = node;
			return node;
		}else{
			List<Element> listElements = node.elements();
			for(Element e : listElements){
				findElement(e);
			}
		}
		return ele;
	}
	/**
	 * 解析<SBMessage>报文
	 * @param xml
	 * @return
	 * @throws ResultReturnException 
	 */
	public static  Pager<HashMap> getAllFieldMaps(String xml)throws DocumentException, ResultReturnException{
		 Pager<HashMap> resultMap = new Pager<HashMap>();
		try {
			 org.dom4j.Document doc = DocumentHelper.parseText(xml);
			 Element root = doc.getRootElement();
		     Element zxbw_bz = root.element("ZXBW_BZ");
		     Element spmessage = zxbw_bz.element("SPmessage");
		     Element fwtg_bz = spmessage.element("FWTG_BZ");
		     Element fwztdm = spmessage.element("FWTGZTDM");
		     if("202".equals(fwztdm.getText())){
		    	 throw new ResultReturnException("内部运行错误,返回状态码202");
		     }
		     Element items = fwtg_bz.element("Items");
		     Element item = items.element("Item");
		     Element value = item.element("Value");
		     List rows = value.elements("Row");
		     List<String> fields = new ArrayList<String>();
		     List<HashMap> hashMapLists =  new ArrayList<HashMap>();
		     for(int i=0; i<rows.size();i++){
		    	 Element row = (Element) rows.get(i);
		    	 if(i == 0){//获取总数
		    		 Element firstElement = row.element("Data");
		    		 if(null == firstElement || null == firstElement.getText() || firstElement.getText().isEmpty()){
		    			 return null;
		    		 }else{
			    		 resultMap.setTotalNum(Integer.parseInt(firstElement.getText().trim()));
		    		 }
		    	 }else if(i == 1){//获取字段定义
		    		 List datas = row.elements("Data");
		    		 for(Iterator<?> itdata = datas.iterator();itdata.hasNext();){
		      		   Element data = (Element) itdata.next();
		      		   if(data != null){
		      			 fields.add(data.getText());
		      		   }
		      	   }
		    		
		    	 }else{//数据
		    		 List datas = row.elements("Data");
		    		 int count = 0;
      			     HashMap hm = new HashMap<String,String>();
		    		 for(int j = 0;j < datas.size(); j++){
		        		   Element data = (Element) datas.get(j);
		        		   Element normal = data.element("Normal");
		        		   if(normal != null){
		        			   hm.put(fields.get(j), normal.getText());
		        		   }
		        	   }
		    		 hashMapLists.add(hm);
		    	 }
		    }
		     resultMap.setTotalNum( hashMapLists.size());
		     resultMap.setPageList(hashMapLists);
		} catch (DocumentException e) {
			System.out.println(xml);
 			e.printStackTrace();
 			
		}
		return resultMap;
	}
	/**
	 * 解析<SinoMessage>报文
	 * @param xml
	 * @return
	 * @throws DocumentException 
	 */
	public static  Pager<HashMap> getSinoAllFieldMaps(String xml) throws DocumentException{
		 Pager<HashMap> resultMap = new Pager<HashMap>();
		 org.dom4j.Document doc = DocumentHelper.parseText(xml);
		 Element root = doc.getRootElement();
		 Element method = root.element("Method");
		 Element items = method.element("Items");
		 Element item = items.element("Item");
		 Element value = item.element("Value");
		 List rows = value.elements("Row");
	     List<String> fields = new ArrayList<String>();
	     List<HashMap> hashMapLists =  new ArrayList<HashMap>();
	     for(int i=0; i<rows.size();i++){
	    	 Element row = (Element) rows.get(i);
	    	 if(i == 0){//获取总数
	    		 List<Element> firstElement = row.elements("Data");
	    		 if(firstElement.get(1).getText().isEmpty()){
	    			 return null;
	    		 }
	    		 resultMap.setTotalNum(Integer.parseInt(firstElement.get(1).getText()));
	    	 }else if(i == 1){//获取字段定义
	    		 List datas = row.elements("Data");
	    		 for(Iterator<?> itdata = datas.iterator();itdata.hasNext();){
	      		   Element data = (Element) itdata.next();
	      		   if(data != null){
	      			 fields.add(data.getText());
	      		   }
	      	   }
	    		
	    	 }else{//数据
	    		 List datas = row.elements("Data");
	    		 int count = 0;
  			     HashMap hm = new HashMap<String,String>();
	    		 for(int j = 0;j < datas.size(); j++){
	        		   Element data = (Element) datas.get(j);
	        		   Element normal = data.element("Normal");
	        		   if(normal != null){
	        			   hm.put(fields.get(j), normal.getText());
	        		   }
	        	   }
	    		 hashMapLists.add(hm);
	    	 }
	    }
	     resultMap.setTotalNum( hashMapLists.size());
	     resultMap.setPageList(hashMapLists);
	
	return resultMap;
	}
}
