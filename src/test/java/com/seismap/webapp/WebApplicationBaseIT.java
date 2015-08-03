package com.seismap.webapp;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.InputStream;


public abstract class WebApplicationBaseIT {

	private static final String PORT_STRING = System.getenv("SEISMAP_PORT");
	private static final int PORT = (PORT_STRING == null ? 7000 : Integer
			.parseInt(PORT_STRING));
	private static final String CONTEXT = null;

	public static class Response {

		private int code;
		private String body;

		public Response(int code, String body) {
			this.code = code;
			this.body = body;
		}

		public int getCode() {

			return code;
		}

		public String getBody() {
			return body;
		}

	}

	protected String getBaseUrl() {
		int port = getPort();
		String context = getContext();
		return "http://localhost" + (port == 80 ? "" : ":" + port)
				+ (context == null ? "" : "/" + context) + "/";
	}

	protected String getAbsoluteUri(String uri) {
		if (uri.startsWith("http://") || uri.startsWith("https://")) {
			return uri;
		} else {
			if (uri.length() > 0 && uri.charAt(0) == '/') {
				return getBaseUrl() + uri.substring(1);
			} else {
				return getBaseUrl() + uri;
			}
		}
	}

	protected Response executeGet(String uri) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet method = new HttpGet(uri);
		HttpResponse httpResponse = client.execute(method);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		InputStream body = httpResponse.getEntity().getContent();
		return new Response(statusCode, body.toString());
	}

	protected int getPort() {
		return PORT;
	}

	protected String getContext() {
		return CONTEXT;
	}
}
