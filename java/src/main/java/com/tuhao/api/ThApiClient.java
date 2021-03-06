package com.tuhao.api;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.tuhao.api.exception.TuhaoException;
import com.tuhao.api.utils.Preconditions;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

public class ThApiClient {
    private static final String Version = "1.0";

    private static final String apiEntry = "https://open.tuhaoliuliang.cn/api/entry?";

    private static final String format = "json";

    private static final String signMethod = "md5";

    private static final String DefaultUserAgent = "ThApiSdk Client v0.1";

    private String appId;
    private String appSecret;

    public ThApiClient(String appId, String appSecret) throws Exception {
        if (!Preconditions.isNotBlank(appId,appSecret)) {
            throw new TuhaoException("appId 和 appSecret 不能为空");
        }

        this.appId = appId;
        this.appSecret = appSecret;
    }

    public HttpResponse get(String method, HashMap<String, String> parames) throws Exception {
        String url = apiEntry + getParamStr(method, parames);

        HttpClient client = new SSLClient();
        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent", DefaultUserAgent);

        HttpResponse response = client.execute(request);
        return response;
    }

    public HttpResponse get(String method) throws Exception {
        String url = apiEntry + getParamStr(method, new HashMap<String, String>());

        HttpClient client = new SSLClient();
        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent", DefaultUserAgent);

        HttpResponse response = client.execute(request);
        return response;
    }

    public HttpResponse post(String method, HashMap<String, String> parames) throws Exception {
        String url = apiEntry + getParamStr(method, parames);

        HttpClient client = new SSLClient();
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("User-Agent", DefaultUserAgent);

        HttpResponse response = client.execute(httppost);
        return response;
    }

    public String getParamStr(String method, HashMap<String, String> parames) {
        String str = "";
        try {
            str = URLEncoder.encode(buildParamStr(buildCompleteParams(method, parames)), "UTF-8")
                    .replace("%3A", ":")
                    .replace("%2F", "/")
                    .replace("%26", "&")
                    .replace("%3D", "=")
                    .replace("%3F", "?");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    private String buildParamStr(HashMap<String, String> param) {
        String paramStr = "";
        Object[] keyArray = param.keySet().toArray();
        if (Preconditions.isNotBlank(keyArray)) {
            for (int i = 0; i < keyArray.length; i++) {
                String key = (String) keyArray[i];

                if (0 == i) {
                    paramStr += (key + "=" + param.get(key));
                } else {
                    paramStr += ("&" + key + "=" + param.get(key));
                }
            }
        }

        return paramStr;
    }


    private HashMap<String, String> buildCompleteParams(String method, HashMap<String, String> parames) throws Exception {
        HashMap<String, String> commonParams = getCommonParams(method);

        if (Preconditions.isNotBlank(parames)) {
            for (String key : parames.keySet()) {
                if (commonParams.containsKey(key)) {
                    throw new Exception("参数名冲突");
                }

                commonParams.put(key, parames.get(key));
            }
        }

        commonParams.put(ThApiProtocol.SIGN_KEY, ThApiProtocol.sign(appSecret, commonParams));
        return commonParams;
    }

    private HashMap<String, String> getCommonParams(String method) {
        HashMap<String, String> parames = new HashMap<String, String>();
        parames.put(ThApiProtocol.APP_ID_KEY, appId);
        parames.put(ThApiProtocol.METHOD_KEY, method);
        parames.put(ThApiProtocol.TIMESTAMP_KEY, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        parames.put(ThApiProtocol.FORMAT_KEY, format);
        parames.put(ThApiProtocol.SIGN_METHOD_KEY, signMethod);
        parames.put(ThApiProtocol.VERSION_KEY, Version);
        return parames;
    }
}
