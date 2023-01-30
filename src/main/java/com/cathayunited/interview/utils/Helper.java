package com.cathayunited.interview.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Helper {
	
	private static final CloseableHttpClient httpClient = Helper.createClient();

	
	public static String getDateStringFromISOTimeStamp(String timestamp) {
		DateTimeFormatter dtf1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss+ss:ss");
		DateTime dt = dtf1.parseDateTime(timestamp);
		DateTimeFormatter dtf2 = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
		return dt.toString(dtf2);
	}
	
	public static String callApi(HttpRequestBase methods) throws Exception {
		CloseableHttpResponse response = httpClient.execute(methods);
		String raw = EntityUtils.toString(response.getEntity());
		return raw;
	}
	
	private static CloseableHttpClient createClient() {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSL");

			// set up a TrustManager that trusts everything
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
				public void checkClientTrusted(X509Certificate[] certs, String authType) { }
				
				public void checkServerTrusted(X509Certificate[] certs, String authType) { }
			} }, new SecureRandom());
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
		
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			    .register("http", PlainConnectionSocketFactory.getSocketFactory())
			    .register("https", sslsf)
			    .build();
		
		// PoolingHttpClientConnectionManager
		PoolingHttpClientConnectionManager pm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		pm.setMaxTotal(1200);
		pm.setDefaultMaxPerRoute(300);
		
		RequestConfig.Builder builder = RequestConfig.custom();
		builder.setSocketTimeout(60000)
			.setConnectTimeout(60000)
			.setConnectionRequestTimeout(60000);

		RequestConfig defaultRequestConfig = builder.build();
		
		return HttpClients.custom().setConnectionManager(pm).setDefaultRequestConfig(defaultRequestConfig).build();
		
	}

	
	public static <T> String toJSON(T instance) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(instance);
    }
	
	public static List<String> getCurrencyList(){
		File currencyList = new File("currencyList.txt");
		
		List<String> list = new ArrayList<String>();
		
		InputStream is = null;
		BufferedReader br = null;
		String  str = null;
			try {
				is = Helper.class.getClassLoader().getResourceAsStream("currencyList.txt");
				br = new BufferedReader(new InputStreamReader(is));
				while ((str = br.readLine()) != null) {
			           list.add(str);
			         }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(br != null)
					br.close();
					if(is != null)
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		return list;
	}
}
