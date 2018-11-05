package com.taiji.pubsec.ajqlc.httpinterface.action.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taiji.pubsec.ajqlc.httpinterface.action.InOutStorageService;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.AjustmentFormItemBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.AuthBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.FormItemBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.ResultBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.util.PropertyReader;

public class InOutStorageImpl extends AbstractClientImpl implements InOutStorageService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(InOutStorageImpl.class);
	
	@Override
	public ResultBean acquireBackStorageForm(AuthBean auth, String formCode) {
		String host = PropertyReader.getHostAddress();
		CloseableHttpResponse response = null;
		ResultBean result = null;

		try {
			if (!hasLogin(auth)) {
				ResultBean loginResult = login(auth);
				if (!loginResult.isSuccess()) {
					result = loginResult;
					return result;
				}
			}

			HttpPost httpPost = new HttpPost(host + "/interface/acquireBackStorageForm.action");
			putSessionInPostRequest(httpPost, auth.getSessionId());
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("formCode", formCode));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			response = httpclient.execute(httpPost);
			result = parseResponse(response);
		} catch(Exception e){
			logger.error("发生异常", e);
			result = createResultBean(RESULT_ERROR, "请求执行异常");
		} finally{
			closeResponse(response);
		}
		return result;
	}

	@Override
	public ResultBean acquireInStorageForm(AuthBean auth, String formCode) {
		String host = PropertyReader.getHostAddress();
		CloseableHttpResponse response = null;
		ResultBean result = null;

		try {
			if (!hasLogin(auth)) {
				ResultBean loginResult = login(auth);
				if (!loginResult.isSuccess()) {
					result = loginResult;
					return result;
				}
			}

			HttpPost httpPost = new HttpPost(host + "/interface/acquireInStorageForm.action");
			putSessionInPostRequest(httpPost, auth.getSessionId());
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("formCode", formCode));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			response = httpclient.execute(httpPost);
			result = parseResponse(response);
		} catch(Exception e){
			logger.error("发生异常", e);
			result = createResultBean(RESULT_ERROR, "请求执行异常");
		} finally{
			closeResponse(response);
		}
		return result;
	}

	@Override
	public ResultBean acquireOutStorageForm(AuthBean auth, String formCode){
		String host = PropertyReader.getHostAddress();
		CloseableHttpResponse response = null;
		ResultBean result = null;

		try {
			if (!hasLogin(auth)) {
				ResultBean loginResult = login(auth);
				if (!loginResult.isSuccess()) {
					result = loginResult;
					return result;
				}
			}

			HttpPost httpPost = new HttpPost(host + "/interface/acquireOutStorageForm.action");
			putSessionInPostRequest(httpPost, auth.getSessionId());
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("formCode", formCode));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			response = httpclient.execute(httpPost);
			result = parseResponse(response);
		} catch(Exception e){
			logger.error("发生异常", e);
			result = createResultBean(RESULT_ERROR, "请求执行异常");
		} finally{
			closeResponse(response);
		}
		return result;
	}

	@Override
	public ResultBean acquireInvolvedArticle(AuthBean auth, String articleCode) {
		String host = PropertyReader.getHostAddress();
		CloseableHttpResponse response = null;
		ResultBean result = null;

		try {
			if (!hasLogin(auth)) {
				ResultBean loginResult = login(auth);
				if (!loginResult.isSuccess()) {
					result = loginResult;
					return result;
				}
			}

			HttpPost httpPost = new HttpPost(host + "/interface/acquireInvolvedArticle.action");
			putSessionInPostRequest(httpPost, auth.getSessionId());
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("articleCode", articleCode));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			response = httpclient.execute(httpPost);
			result = parseResponse(response);
		} catch(Exception e){
			logger.error("发生异常", e);
			result = createResultBean(RESULT_ERROR, "请求执行异常");
		} finally{
			closeResponse(response);
		}
		return result;
	}

	@Override
	public ResultBean putAjustmentStorage(AuthBean auth, List<AjustmentFormItemBean> items, String formCode) {
		String host = PropertyReader.getHostAddress();
		CloseableHttpResponse response = null;
		ResultBean result = null;

		//TODO
		
		return result;
	}

	@Override
	public String putBackStorage(List<FormItemBean> inStorageItems, String formCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String putInStorage(List<FormItemBean> inStorageItems, String formCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String putOutStorage(List<FormItemBean> items, String formCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBean acquireAdjustmentForm(AuthBean auth, String formCode) {
		String host = PropertyReader.getHostAddress();
		CloseableHttpResponse response = null;
		ResultBean result = null;

		try {
			if (!hasLogin(auth)) {
				ResultBean loginResult = login(auth);
				if (!loginResult.isSuccess()) {
					result = loginResult;
					return result;
				}
			}

			HttpPost httpPost = new HttpPost(host + "/interface/acquireAdjustmentForm.action");
			putSessionInPostRequest(httpPost, auth.getSessionId());
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("formCode", formCode));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			response = httpclient.execute(httpPost);
			result = parseResponse(response);
		} catch(Exception e){
			logger.error("发生异常", e);
			result = createResultBean(RESULT_ERROR, "请求执行异常");
		} finally{
			closeResponse(response);
		}
		return result;
	}

}
