/**
 * 
 */
package com.taiji.pubsec.ajqlc.login;

import java.util.List;

/**
 * @author huangda
 *
 */
public interface UrlAfterLoginService {

	public String getJumpUrl(String userid, List<String> roles);
}
