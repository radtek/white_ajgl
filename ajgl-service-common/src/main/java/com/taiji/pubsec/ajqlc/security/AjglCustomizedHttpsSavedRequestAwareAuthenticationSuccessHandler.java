package com.taiji.pubsec.ajqlc.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class AjglCustomizedHttpsSavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
    protected final Log logger = LogFactory.getLog(this.getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();
    
    private String qinqingdianhuaUrl ;
    
    private boolean useReferer = false;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
       
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();

        if(request.getParameter("qinqingdianhua")!=null){
        	targetUrl = this.qinqingdianhuaUrl ;
        }

        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
    
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        if (isAlwaysUseDefaultTargetUrl()) {
            return getDefaultTargetUrl();
        }
        
        // Check for the parameter and use that if available
        String targetUrl = null;
        
        if(request.getParameter("qinqingdianhua")!=null){
        	return qinqingdianhuaUrl ;
        }

        if (getTargetUrlParameter() != null  ) {
            targetUrl = request.getParameter(getTargetUrlParameter());

            if (StringUtils.hasText(targetUrl)) {
                logger.debug("Found targetUrlParameter in request: " + targetUrl);

                return targetUrl;
            }
        }

        if (useReferer && !StringUtils.hasLength(targetUrl)) {
            targetUrl = request.getHeader("Referer");
            logger.debug("Using Referer header: " + targetUrl);
        }

        if (!StringUtils.hasText(targetUrl)) {
            targetUrl = getDefaultTargetUrl();
            logger.debug("Using default Url: " + targetUrl);
        }

        return targetUrl;
    }
    
    /**
     * If set to {@code true} the {@code Referer} header will be used (if available). Defaults to {@code false}.
     */
    @Override
    public void setUseReferer(boolean useReferer) {
        this.useReferer = useReferer;
    }

	public void setQinqingdianhuaUrl(String qinqingdianhuaUrl) {
		this.qinqingdianhuaUrl = qinqingdianhuaUrl;
	}
    
    

}
