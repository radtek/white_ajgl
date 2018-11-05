package com.taiji.pubsec.ajqlc.httpinterface.action.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taiji.pubsec.ajqlc.httpinterface.action.bean.AuthBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.ResultBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.util.PropertyReader;

import net.sf.json.JSONObject;

public abstract class AbstractClientImpl {
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractClientImpl.class);

	protected static final boolean RESULT_ERROR = false;
	protected static final boolean RESULT_SUCCESS = true;
	private static final String RESULT_SUCCESS_STRING = "success";
	public static final String RESULT_JSON_VALUE_KEY = "json";

	protected static CloseableHttpClient httpclient = HttpClients
			.createDefault();

	public ResultBean login(AuthBean auth) {
		String host = PropertyReader.getHostAddress();
		ResultBean result = null;
		CloseableHttpResponse response = null;

		HttpGet homePageGett = new HttpGet(host + "/index.jsp");
		try {
			response = httpclient.execute(homePageGett);
			printResponse(response);
			if (response.getStatusLine().getStatusCode() == 200) { // 首页访问成功
				HttpPost loginPost = new HttpPost(host + "/login");
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("userName", auth.getUsername()));
				nvps.add(new BasicNameValuePair("password", auth.getPassword()));
				nvps.add(new BasicNameValuePair("passEncrypt", "no"));
				loginPost.setEntity(new UrlEncodedFormEntity(nvps, Charset
						.forName("UTF-8")));
				try {
					response = httpclient.execute(loginPost);
					printResponse(response);
					if (isLoginSuccess(response)) {
						result = createResultBean(RESULT_SUCCESS, "验证成功");
						auth.setSessionId(getSessionId(response));
					} else {
						result = createResultBean(RESULT_ERROR, "验证失败");
					}
				} catch (IOException e) {
					logger.error("发生异常", e);
					result = createResultBean(RESULT_ERROR, "请求执行异常");
				} finally {
					closeResponse(response);
				}
			} else {
				result = createResultBean(RESULT_ERROR, "首页访问错误");
			}
		} catch (Exception e) {
			logger.error("发生异常", e);
			result = createResultBean(RESULT_ERROR, "请求执行异常");
		} finally {
			closeResponse(response);
		}
		return result;
	}

//	protected boolean isExcuteSuccess(HttpResponse response)
//			throws IllegalStateException, IOException {
//		boolean result = false;
//		if (response.getStatusLine().getStatusCode() == 200) {
//			String content = EntityUtils.toString(response.getEntity());
//			logger.debug("content returnd: {}", content);
//			JSONObject jsonObject = JSONObject.fromObject(content);
//			Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(
//					jsonObject, Map.class);
//			if (map.get("result").equals(RESULT_SUCCESS_STRING)) {
//				result = true;
//			}
//		}
//		return result;
//
//	}

	protected ResultBean parseResponse(HttpResponse response)
			throws IllegalStateException, IOException {
		ResultBean result = null;
		if (response.getStatusLine().getStatusCode() == 200) {

			String content = EntityUtils.toString(response.getEntity());
			logger.debug("content returned: {}", content);
			JSONObject jsonObject = JSONObject.fromObject(content);
			Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(
					jsonObject, Map.class);
//			if (map.get("result").equals(RESULT_SUCCESS_STRING)) {
//				result = createResultBean(RESULT_SUCCESS, "请求执行成功");
//				result.getResults().put(RESULT_JSON_VALUE_KEY, jsonObject);
//			}
			result = createResultBean(RESULT_SUCCESS, "请求执行成功");
			result.getResults().put(RESULT_JSON_VALUE_KEY, jsonObject);
		} else {
			result = createResultBean(RESULT_ERROR, "请求执行失败");
		}
		return result;
	}

	// private String convertStreamToString(InputStream is) {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// try {
	// while ((line = reader.readLine()) != null) {
	// sb.append(line);
	// }
	// } catch (IOException e) {
	// logger.error("发生异常", e);
	// }
	// } finally {
	// try {
	// is.close();
	// } catch (IOException e) {
	// logger.error("发生异常", e);
	// }
	// }
	// return sb.toString();
	// }

	protected void closeResponse(HttpResponse response) {
		try {
			EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
			logger.error("发生异常", e);
		}
	}

	protected ResultBean createResultBean(boolean isSuccess, String message) {
		ResultBean result = new ResultBean();
		result.setSuccess(isSuccess);
		result.setMessage(message);
		return result;
	}

	private boolean isLoginSuccess(HttpResponse response) {
		boolean result = false;
		if (response.getStatusLine().getStatusCode() == 302) {
			Header[] hds = response.getHeaders("Location");
			HeaderElement[] hes = hds[0].getElements();
			String redirectUrl = hes[0].getName();
			if (redirectUrl.endsWith("showAskRoomListPage.action")) {
				result = true;
			}
		}
		return result;
	}

	private String getSessionId(HttpResponse httpResponse) {
		Header[] hds = httpResponse.getHeaders("Set-Cookie");
		HeaderElement[] hes = hds[0].getElements();
		String sid = hes[0].getValue();
		return sid;
	}

	protected boolean hasLogin(AuthBean auth) {
		return (auth.getSessionId() != null);
	}

	protected void putSessionInPostRequest(HttpPost postRequest,
			String sessionId) {
		postRequest.setHeader("Set-Cookie", "JSESSIONID=" + sessionId);
		logger.debug("session id put in request: {}", sessionId);
	}

	protected void putSessionInGetRequest(HttpGet getRequest, String sessionId) {
		getRequest.setHeader("Set-Cookie", "JSESSIONID=" + sessionId);
		logger.debug("session id put in request: {}", sessionId);
	}

	protected static void printResponse(HttpResponse response)
			throws IllegalStateException, IOException {
		for (Header header : response.getAllHeaders()) {
			logger.debug(header.getName() + ":" + header.getValue());
		}
		logger.debug(response.getStatusLine().toString());
		logger.debug("Content Type: {}", response.getEntity().getContentType());
		logger.debug("Content length: {}", response.getEntity()
				.getContentLength());
		logger.debug(EntityUtils.toString(response.getEntity()));
	}

	private static String getMD5Str(String s) throws Exception {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
