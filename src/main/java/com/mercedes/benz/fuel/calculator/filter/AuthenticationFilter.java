package com.mercedes.benz.fuel.calculator.filter;

import java.io.IOException;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.security.sasl.AuthenticationException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.mercedes.benz.fuel.calculator.common.ServiceConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class AuthenticationFilter implements Filter{
	
	@Autowired
	Environment environment;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException, AuthenticationException {
		

		HttpServletRequest req = (HttpServletRequest) request;
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		String secret = environment.getProperty(ServiceConstants.AUTH_SIGNING_KEY_PROP);
		byte[] apiKeySecretBytes = secret.getBytes();
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		
		String bearerToken = req.getHeader("Authorization");
		if(null != bearerToken && bearerToken.contains("Bearer ")) {
			
			String token = bearerToken.split(" ")[1];
			
			try {
				Jwts.parser().setSigningKey(signingKey).parse(token);
			}catch(Exception ex) {
				throw new AuthenticationException("Not a valid token");
			}
			
		}else {
			throw new AuthenticationException("Invalid token");
		}
		
		 chain.doFilter(request, response);
		
	}

}
