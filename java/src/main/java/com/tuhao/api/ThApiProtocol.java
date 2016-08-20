package com.tuhao.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public class ThApiProtocol {
	public static final String APP_ID_KEY = "app_id";
    public static final String METHOD_KEY = "method";
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String FORMAT_KEY = "format";
    public static final String VERSION_KEY = "v";
    public static final String SIGN_KEY = "sign";
    public static final String SIGN_METHOD_KEY = "sign_method";

    public static final int ALLOWED_DEVIATE_SECONDS = 600;

    public static final int ERR_SYSTEM = -1;
    public static final int ERR_INVALID_APP_ID = 40001;
    public static final int ERR_INVALID_APP = 40002;
    public static final int ERR_INVALID_TIMESTAMP = 40003;
    public static final int ERR_EMPTY_SIGNATURE = 40004;
    public static final int ERR_INVALID_SIGNATURE = 40005;
    public static final int ERR_INVALID_METHOD_NAME = 40006;
    public static final int ERR_INVALID_METHOD = 40007;
    public static final int ERR_INVALID_TEAM = 40008;
    public static final int ERR_PARAMETER = 41000;
    public static final int ERR_LOGIC = 50000;

    public static String sign(String appSecret, HashMap<String,String> parames){
        Object[] keyArray = parames.keySet().toArray();
        List<String> keyList = new ArrayList<String>();
        for(int i = 0; i < keyArray.length; i++){
            keyList.add((String)keyArray[i]);
        }
        Collections.sort(keyList);
        String signContent = appSecret;

        for (String key : keyList) {
            signContent += (key + parames.get(key));
        }
        signContent += appSecret;
        return hash(signContent);
    }

    private static String hash(String signContent){
    	System.out.println(signContent);
    	String hashResult = DigestUtils.md5Hex(signContent);

        return hashResult;
    }
}
