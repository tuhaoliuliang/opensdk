package com.tuhao.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;

import com.tuhao.api.ThApiClient;

/*
 * 这是个例子
 */
public class THApiTest {
	private static final String APP_ID = ""; //这里换成你的app_id
	private static final String APP_SECRET = ""; //这里换成你的app_secret

	public static void main(String[] args){
		sendCharge();
//		sendBalance();
	}

	/*
	 * 测试单号码充值
	 */
	private static void sendCharge(){
		String method = "tuhao.data.charge";
		HashMap<String, String> params = new HashMap<String, String>();

		params.put("amount", "10"); // 流量包大小
		params.put("area", "0"); // 全国或是省份 0 表示全国流量 1 表示省流量
		params.put("mobile", ""); // 手机号码
		params.put("effecttime", "0"); // 生效时间 0 表示即时生效 1
		params.put("roam", "1"); // 是否是漫游流量 1 表示漫游 0 表示不漫游

		ThApiClient ThApiClient;
		HttpResponse response;

		try {
			ThApiClient = new ThApiClient(APP_ID, APP_SECRET);
			response = ThApiClient.get(method, params);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 测试获取账号余额
	 */
	private static void sendBalance(){
		String method = "tuhao.account.balance";

		ThApiClient ThApiClient;
		HttpResponse response;

		try {
			ThApiClient = new ThApiClient(APP_ID, APP_SECRET);
			response = ThApiClient.get(method);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
