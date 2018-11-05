package com.taiji.pubsec.ajqlc.util;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author wangfx
 *
 */
public class ParamMapUtil {
	
	@SuppressWarnings("rawtypes")
	public static boolean isNotBlank(Object o) {
		if (o instanceof String) {
			return StringUtils.isNotBlank((String)o);
		}
		if ((o instanceof Long)||(o instanceof Integer)||(o instanceof Date)) {
			if (!o.equals(null)) {
				return true;
			}
		}
		if (o instanceof Collection) {
			if (!((Collection)o).isEmpty()) {
				return true;
			}
		}
		return false;
	}

}
