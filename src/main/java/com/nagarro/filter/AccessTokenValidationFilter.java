package com.nagarro.filter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class AccessTokenValidationFilter extends ZuulFilter {

//	// TODO move it to the application.prop
//	private static final String CLIENT_ID = "718776657847-f9is18a4s56t2vt2ngv8j95kkrajv95g.apps.googleusercontent.com";

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		String token = ctx.getRequest().getHeader("access_token");
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("access_token", token);
		// TODO move it to the application.prop
		String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + token;
		try {
			ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
			if (response.getStatusCode() != HttpStatus.OK) {
				setInvalidTokenResponse(ctx, response.getStatusCode(), "invalid_token");
			}
		} catch (HttpClientErrorException e) {
			setInvalidTokenResponse(ctx, HttpStatus.UNAUTHORIZED, "invalid_token");
		}
		return null;
	}

	private void setInvalidTokenResponse(RequestContext ctx, HttpStatus code, String response) {
		ctx.setResponseStatusCode(code.value());
		ctx.setResponseBody(response);
		ctx.setSendZuulResponse(false);
	}

}
