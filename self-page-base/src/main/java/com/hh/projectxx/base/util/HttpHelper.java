package com.hh.projectxx.base.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class HttpHelper {
	private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);
	
	private HttpClient client;

	@PostConstruct
	public void init() {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setDefaultMaxConnectionsPerHost(100);
		params.setConnectionTimeout(5000);
		params.setSoTimeout(5000);
		
		this.client = new HttpClient(connectionManager);
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
	}
	
	public String sendRequest(HttpMethod method) throws IOException {
		try {
			int status = client.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				String resp = method.getResponseBodyAsString();
				return resp;
			}
			return null;
		} catch (IOException e) {
			logger.error("error occurred while post");
			throw e;
		} finally {
			method.releaseConnection();
		}
	}
}
