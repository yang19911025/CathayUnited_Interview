package com.cathayunitedinterview.test;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.junit.Test;

import com.cathayunited.interview.model.Currency;
import com.cathayunited.interview.utils.Helper;
import javax.swing.*;

public class TestApi {
	public static final String apiBaseUrl = "http://127.0.0.1:8080/api";
	public static final String codeRex = "^[A-Za-z]{0,4}$";
	public static final String descriptionRex = "^[\\u4e00-\\u9fa5]{0,5}$";
	public static final String idRex = "^[1-9]*$";
	private JsonParser parser = new JsonParser() ;
    private String currencyList;
	private Set<String>idList = new HashSet<String>();
	static JFrame jFrame = new JFrame();

	// 1. 測試呼叫查詢幣別對應表資料 API,並顯示其內容。
	@Test
	public void showAllCurrency() throws Exception {
		String path = "/currency";
		URIBuilder builder = new URIBuilder(String.format("%s%s", apiBaseUrl, path));
		HttpGet req = new HttpGet(builder.toString());

		JSONArray jsonArray = new JSONArray(Helper.callApi(req));
		StringBuilder sb = new StringBuilder();


		for(int i = 0;i<jsonArray.length();i++){
			sb.append(parser.parse(jsonArray.getJSONObject(i).toString())+"\n");

			idList.add(jsonArray.getJSONObject(i).getString("id"));
		}
		currencyList = sb.toString();

		System.out.println(currencyList);

	}


	// 2. 測試呼叫新增幣別對應表資料 API。
	@Test
	public void createCurrency() throws Exception {
		String path = "/newCurrency";
		URIBuilder builder = new URIBuilder(String.format("%s%s", apiBaseUrl, path));


		String code = toVerifyInput("請輸入您想要新增的幣別代號,例如:USD",codeRex);
		String description = toVerifyInput("請輸入您想要新增的幣別中文名稱,例如:美金",descriptionRex);



		HttpPost post = new HttpPost(builder.toString());
		post.setEntity(EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON)
				.setText(Helper.toJSON(new Currency(code, description))).build());
		 System.out.println(Helper.callApi(post));

	}

	// 3. 測試呼叫更新幣別對應表資料 API,並顯示其內容。
	@Test
	public void updateCurrencyById() throws Exception {
		showAllCurrency();
		String id = toVerifyInput(currencyList+"請輸入您想要修改的幣別ID",idRex,idList);

		String path = "/updateCurrency/" + id;
		URIBuilder builder = new URIBuilder(String.format("%s%s", apiBaseUrl, path));
		String code = toVerifyInput("請輸入您想要修改的幣別代號,例如:USD",codeRex);
		String description = toVerifyInput("請輸入您想要修改的幣別中文名稱,例如:美金",descriptionRex);

		HttpPut put = new HttpPut(builder.toString());
		put.setEntity(EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON)
				.setText(Helper.toJSON(new Currency(code, description))).build());
		 System.out.println(Helper.callApi(put));
	}

	// 4. 測試呼叫刪除幣別對應表資料 API。
	@Test
	public void deleteTutorialById() throws Exception {
		showAllCurrency();
		if(idList.size() == 1){
			JOptionPane.showMessageDialog(jFrame,"只剩一種幣別不得刪除");
			return;
		}

		String id = toVerifyInput(currencyList+"請輸入您想要刪除的幣別ID",idRex,idList);
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

private static String toVerifyInput(String message,String rex){
		String data = null;
	while (true){
		data = JOptionPane.showInputDialog(jFrame, message);

		if(data.matches(rex))
			break;
		JOptionPane.showMessageDialog(jFrame,"輸入有誤,請重新輸入");
	}
	return data;
}
	private static String toVerifyInput(String message,String rex,Set idList){
		String data = null;
		while (true){
			data = JOptionPane.showInputDialog(jFrame, message);

			if(data.matches(rex)&&idList.contains(data))
				break;
			JOptionPane.showMessageDialog(jFrame,"輸入有誤,請重新輸入");
		}
		return data;
	}

}
