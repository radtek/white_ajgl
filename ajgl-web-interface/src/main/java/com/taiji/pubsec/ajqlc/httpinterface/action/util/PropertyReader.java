package com.taiji.pubsec.ajqlc.httpinterface.action.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyReader {
	private static final Logger logger = LoggerFactory
			.getLogger(PropertyReader.class);

	private static Properties props;

	static {
		props = new Properties();
		try {
			InputStream in = PropertyReader.class
					.getResourceAsStream("/config.properties");
			props.load(in);
		} catch (IOException e) {
			logger.error("读取配置文件失败", e);
		}
	}

	public static String getHostAddress() {
		return (String) props.get("service.hostaddress");
	}

	public static void main(String[] args) {
		logger.debug(PropertyReader.getHostAddress());
	}

}
