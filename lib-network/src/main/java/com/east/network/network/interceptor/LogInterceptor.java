package com.east.network.network.interceptor;

import com.east.network.utils.JsonUtil;
import com.east.network.utils.LogUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;


public class LogInterceptor implements Interceptor {

    public static String TAG = "EVIL_HTTP_LOG==>";

    public static int httpId = 0;

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        httpId = createRandomNumber(10000,99999);

        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtil.INSTANCE.d(TAG,httpId+"----------Start----------------");
        LogUtil.INSTANCE.d(TAG,httpId+"==URL=>"+request.url());
        String method=request.method();
        LogUtil.INSTANCE.d(TAG,httpId+"==METHOD=>"+method);
        RequestBody b = request.body();
        if(b != null){
            LogUtil.INSTANCE.d(TAG,httpId+"==REQUEST=>"+b.toString());
        }

        Set<String> headers = request.headers().names();
        if(headers != null && !headers.isEmpty()){
            Iterator<String> iter = headers.iterator();
            Map<String,String> map = new HashMap<>();
            String key = null;
            while(iter.hasNext()){
                key = iter.next();
                map.put(key,request.header(key));
            }
            LogUtil.INSTANCE.e(TAG,httpId+"==HEADERS=>"+ JsonUtil.Companion.getInstance().getGson().toJson(map));
        }
        if(request.method().equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                LogUtil.INSTANCE.e(TAG, httpId+"==PARAMS=>"+sb.toString());
            }
        }
        LogUtil.INSTANCE.e(TAG,httpId+"==REPONSE=>" + content);
        LogUtil.INSTANCE.e(TAG,httpId+"----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }

    private static int createRandomNumber(int min,int max){
        return (int)(min+Math.random()*(max));
    }
}
