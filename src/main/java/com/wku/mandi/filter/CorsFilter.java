package com.wku.mandi.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet filter to enable client-side cross-origin requests.
 * 
 * @see <a href="http://www.w3.org/TR/cors/">Cross-Origin Resource Sharing</a>
 * 
 */
public class CorsFilter implements Filter {
	private static final Logger log = Logger.getLogger(CorsFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * Overridden to insert a response header for CORS.
	 * 
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		// Allow all origins
		// TODO: Correct this setting for prod
		try {
			((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
			((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "*");
			((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,PUT,DELETE");
			chain.doFilter(request, response);
		} catch (Exception e) {
			log.error("Failed to process a request", e);
		}
	}

	public void destroy() {
	}
}
