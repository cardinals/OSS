package com.nobitastudio.oss.util;

import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.model.dto.GetParam;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/19 16:44
 * @description
 */
public class OkHttpUtil {

    // 服务调用失败.各种异常情况的处理器
    public interface FailureHandler {
        void handle(Call call, IOException e);
    }

    // 服务调用成功处理器
    public interface SuccessHandler {
        void handle(Call call, Response response);
    }

    public static OkHttpClient mOkHttpClient; // 请求client

    public static final String OSS_SERVER_ADDRESS = ConstantContainer.OSS_SERVER_LOCAL; // 请求的服务地址

    // 默认最长的建立连接时长
    public static final int DEFAULT_CONNECT_TIME = 10;
    // 默认最长的写时长
    public static final int DEFAULT_WRITE_TIME = 10;
    // 默认最长的读时长
    public static final int DEFAULT_READ_TIME = 20;

    // 按照默认值初始化client
    public synchronized static void initClient() {
        if (mOkHttpClient != null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)
                    .build();
        }
    }

    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            initClient();
        }
        return mOkHttpClient;
    }

    // url ->  server_ip + restfulParam + getParam 返回 /xx/xx
    public static String generateRestParam(List<String> restParams) {
        String restParamFormatted = "";
        if (restParams != null && restParams.size() != 0) {
            for (String restParam : restParams) {
                restParamFormatted = restParamFormatted + "/" + restParam;
            }
        }
        return restParamFormatted;
    }

    // url ->  server_ip + restfulParam + getParam 返回 /?key=value
    public static String generateGetParam(List<GetParam> getParams) {
        String getParamFormatted = "";
        if (getParams != null && getParams.size() != 0) {
            getParamFormatted = getParamFormatted + "/?";
            for (GetParam getParam : getParams) {
                getParamFormatted = getParam.getKey() + "=" + getParam.getValue() + "&";
            }
            getParamFormatted = getParamFormatted.substring(0, getParamFormatted.length() - 1); //去掉末尾的/
        }
        return getParamFormatted;
    }

    // ============== 异步 ==============

    // get请求
    public static Call get(List<String> restParams, List<GetParam> getParams, FailureHandler failureHandler, SuccessHandler successHandler) {
        Request request = new Request.Builder().url(OSS_SERVER_ADDRESS + generateRestParam(restParams) + generateGetParam(getParams)).get().build();
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failureHandler.handle(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                successHandler.handle(call, response);
            }
        });
        return call;  //返回后,用于取消网络请求
    }

    // post 请求

    // delete 请求

    // put请求


    // ============== 同步 ==============  暂时不需要
}
