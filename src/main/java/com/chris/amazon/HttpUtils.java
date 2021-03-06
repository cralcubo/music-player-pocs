package com.chris.amazon;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;

public class HttpUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

	private static final String PARAMETERS_REGEX = "(?<==)('.+?'|[^&]+)(?=&)";

	public static String doGet(String url) throws IOException {
		final String encodedUrl = encodeParameters(url);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			logDebug(LOGGER, () -> "Sending request to=" + encodedUrl);
			
			HttpGet get = new HttpGet(encodedUrl);
			logDebug(LOGGER, () -> "Excecuting request" + get.getRequestLine());
			
			String response = httpClient.execute(get, new MyResponseHandler());
			logDebug(LOGGER, () -> "Response:" + response);
			
			return response;
		} finally {
			httpClient.close();
		}
	}

	private static class MyResponseHandler implements ResponseHandler<String> {
		private static final Logger LOGGER = LoggerFactory.getLogger(MyResponseHandler.class);
		@Override
		public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			int status = response.getStatusLine().getStatusCode();
			logDebug(LOGGER, () -> "Status from server="  + status);
			
			if(status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : "";
			}
			else {
				throw new ClientProtocolException("Unexpected status=" + status);
			}
		}
	}
	
	public static String encodeParameters(String url) {
		Matcher m = Pattern.compile(PARAMETERS_REGEX).matcher(url + '&');
		// UTF-8 encoding chartset
		String utf8 = StandardCharsets.UTF_8.name();
		while(m.find()) {
			try {
				String param = m.group();
				String encParam = URLEncoder.encode(param, utf8)
						.replace("+", "%20")
		                .replace("*", "%2A")
		                .replace("%7E", "~");
				System.out.println(String.format("Encoding param: [%s] to [%s]", param, encParam));
				url = url.replace(param, encParam);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(String.format("Totally unexpected, %s is supposed to be an accepted character encoding.", utf8));
			}
		}
		
		return url;
	}
	
	private static void logDebug(Logger log, Supplier<String> msg) {
		if(log.isDebugEnabled()) {
			log.debug(msg.get());
		}
	}

}