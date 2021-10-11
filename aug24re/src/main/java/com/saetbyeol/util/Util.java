package com.saetbyeol.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class Util {
	public boolean str2Int(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public int str2Int2(String str) {
		int result = 0;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}
	
	public String getUserIp(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");
		if(ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if(ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
