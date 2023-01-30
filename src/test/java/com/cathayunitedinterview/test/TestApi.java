package com.cathayunitedinterview.test;

import java.net.URISyntaxException;

import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import com.cathayunited.interview.model.Currency;
import com.cathayunited.interview.utils.Helper;
import javax.swing.*;

public class TestApi {
	public static final String apiBaseUrl = "http://127.0.0.1:8080/api";
	JFrame jFrame = new JFrame();

	// 1. 測試呼叫查詢幣別對應表資料 API,並顯示其內容。
	@Test
	public void showAllCurrency() throws Exception {
		String path = "/currency";
		URIBuilder builder = new URIBuilder(String.format("%s%s", apiBaseUrl, path));
		HttpGet req = new HttpGet(builder.toString());
        System.out.println(Helper.callApi(req));
		
	}

	// 2. 測試呼叫新增幣別對應表資料 API。
	@Test
	public void createCurrency() throws Exception {
		String path = "/newCurrency";
		URIBuilder builder = new URIBuilder(String.format("%s%s", apiBaseUrl, path));

		String code = JOptionPane.showInputDialog(jFrame, "請輸入您想要新增的幣別代號");
		String description = JOptionPane.showInputDialog(jFrame, "請輸入您想要新增的幣別中文名稱");
		HttpPost post = new HttpPost(builder.toString());
		post.setEntity(EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON)
				.setText(Helper.toJSON(new Currency(code, description))).build());
		 System.out.println(Helper.callApi(post));

	}

	// 3. 測試呼叫更新幣別對應表資料 API,並顯示其內容。
	@Test
	public void updateCurrencyById() throws Exception {
		String id = JOptionPane.showInputDialog(jFrame, "請輸入您想要修改的幣別ID");
		String path = "/updateCurrency/" + id;
		URIBuilder builder = new URIBuilder(String.format("%s%s", apiBaseUrl, path));
		String code = JOptionPane.showInputDialog(jFrame, "請輸入您想要修改的幣別代號");
		String description = JOptionPane.showInputDialog(jFrame, "請輸入您想要修改的幣別中文名稱");

		HttpPut put = new HttpPut(builder.toString());
		put.setEntity(EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON)
				.setText(Helper.toJSON(new Currency(code, description))).build());
		 System.out.println(Helper.callApi(put));
	}

	// 4. 測試呼叫刪除幣別對應表資料 API。
	@Test
	public void deleteTutorialById() throws Exception {
		String id = JOptionPane.showInputDialog(jFrame, "請輸入您想要刪除的幣別ID");
		String path = "/deleteCurrency/" + id;
		URIBuilder builder = new URIBuilder(String.format("%s%s", apiBaseUrl, path));
		HttpDelete delete = new HttpDelete(builder.toString());
		System.out.println(Helper.callApi(delete));
	}

	// 5. 測試呼叫 coindesk API,並顯示其內容。
	@Test
	public void callCoindeskAPI() throws Exception {
		String path = "/showCoindeskAPI";
		URIBuilder builder = new URIBuilder(String.format("%s%s", apiBaseUrl, path));
		HttpGet get = new HttpGet(builder.toString());
		 System.out.println(Helper.callApi(get));
	}

	// 6. 測試呼叫資料轉換的 API,並顯示其內容。
	@Test
	public void callNewCoindeskAPI() throws Exception {
		String path = "/showNewCoindeskAPI";
		URIBuilder builder = new URIBuilder(String.format("%s%s", apiBaseUrl, path));
		HttpGet get = new HttpGet(builder.toString());
		System.out.println(Helper.callApi(get));
	}

}
