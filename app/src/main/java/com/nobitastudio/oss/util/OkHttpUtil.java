package com.nobitastudio.oss.util;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.dto.GetParam;
import com.nobitastudio.oss.model.entity.Bind;
import com.nobitastudio.oss.model.entity.User;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/19 16:44
 * @description T :返回结果
 */
public class OkHttpUtil {

    // 请求方式  get.post.put.delete
    public enum METHOD {
        GET, POST, PUT, DELETE
    }

    // 连接失败.超时.未得到结果  一般采取默认处理
    public interface ConnectFailHandler {
        void handle(Call call, IOException e);
    }

    // 服务调用成功处理器
    public interface SuccessHandler<T> {
        void handle(T t);
    }

    // 服务异常.各种异常情况的处理器
    public interface FailHandler<T> {
        void handle(ServiceResult<T> serviceResult);
    }

    // 错错误
    public interface ErrorHandler {
        void handle(Call call, Response response);
    }

    public static final String OSS_SERVER_ADDRESS = ConstantContainer.OSS_SERVER_LOCAL; // 请求的服务地址

    // 默认最长的建立连接时长
    public static final int DEFAULT_CONNECT_TIME = 10;
    // 默认最长的写时长
    public static final int DEFAULT_WRITE_TIME = 10;
    // 默认最长的读时长
    public static final int DEFAULT_READ_TIME = 20;
    // 请求的传递类型.默认Json
    public static final MediaType mMediaType = MediaType.parse("application/json; charset=utf-8");

    public static OkHttpClient mOkHttpClient; // 请求client

    // 获取当前处于哪一个activity
    public static Activity getSelectedActivity() {
        return (Activity) NormalContainer.container.get(NormalContainer.SELECTED_ACTIVITY);
    }

    // 跑在UI线程上
    public static void runOnUiThread(Runnable runnable) {
        if (getSelectedActivity() != null) {
            getSelectedActivity().runOnUiThread(runnable);
        }
    }

    // 按照默认值初始化client
    public synchronized static void initClient() {
        if (mOkHttpClient == null) {
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
    public static <T> Call get(List<String> restParams, List<GetParam> getParams, Class<T> tClass,
                               ConnectFailHandler connectFailHandler, SuccessHandler<T> successHandler, FailHandler<T> failureHandler, ErrorHandler errorHandler) {
        Request request = new Request.Builder().url(OSS_SERVER_ADDRESS + generateRestParam(restParams) + generateGetParam(getParams)).get().build();
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 服务器未返回.未联网.超时等
                runOnUiThread(() -> connectFailHandler.handle(call, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 服务器返回了数据
                if (response.isSuccessful()) {
                    // 请求返回码 200 - 300 即返回成功
                    if (response.code() == 200) {
                        ServiceResult<T> result = JSON.parseObject(response.body().string(), ServiceResult.class);
                        if (result.getResult() instanceof JSONObject) {
                            T t = ((JSONObject) result.getResult()).toJavaObject(tClass);
                            result.setResult(t);
                        } else {
                            return;
                        }
                        if (result.getState() == 0) {
                            // 成功
                            runOnUiThread(() -> successHandler.handle(result.getResult()));
                        } else {
                            // 失败
                            runOnUiThread(() -> failureHandler.handle(result));
                        }
                    }
                } else {
                    // 未授权.500 404 等
                    runOnUiThread(() -> errorHandler.handle(call, response));
                }
            }
        });
        return call;  //返回,用于取消网络请求
    }

    // get请求  type = new TypeReference<List<String>>() {}.getType()
    public static <T> Call get(List<String> restParams, List<GetParam> getParams, TypeReference<T> typeReference,
                               ConnectFailHandler connectFailHandler, SuccessHandler<T> successHandler, FailHandler<T> failureHandler, ErrorHandler errorHandler) {
        Request request = new Request.Builder().url(OSS_SERVER_ADDRESS + generateRestParam(restParams) + generateGetParam(getParams)).get().build();
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 服务器未返回.未联网.超时等
                runOnUiThread(() -> connectFailHandler.handle(call, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 服务器返回了数据
                if (response.isSuccessful()) {
                    // 请求返回码 200 - 300 即返回成功
                    if (response.code() == 200) {
                        ServiceResult<T> result = JSON.parseObject(response.body().string(), ServiceResult.class);
                        if (result.getResult() instanceof JSONObject) {
                            T t = ((JSONObject) result.getResult()).toJavaObject(typeReference);
                            result.setResult(t);
                        } else {
                            return;
                        }
                        if (result.getState() == 0) {
                            // 成功
                            runOnUiThread(() -> successHandler.handle(result.getResult()));
                        } else {
                            // 失败
                            runOnUiThread(() -> failureHandler.handle(result));
                        }
                    }
                } else {
                    // 未授权.500 404 等
                    runOnUiThread(() -> errorHandler.handle(call, response));
                }
            }
        });
        return call;  //返回,用于取消网络请求
    }
//    public Call post(Object requestBody, List<String> restParams, List<GetParam> getParams,
//                     ConnectFailHandler connectFailHandler, SuccessHandler<T> successHandler, FailHandler<T> failureHandler, ErrorHandler errorHandler) {
//
//        Request request = new Request.Builder().url(OSS_SERVER_ADDRESS + generateRestParam(restParams) + generateGetParam(getParams)).get().build();
//        Call call = getOkHttpClient().newCall(request);
//
//
//        return call;
//    }

    // post 请求

    // delete 请求

    // put请求


    // ============== 同步 ==============  暂时不需要
}
