package com.nobitastudio.oss.util;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.dto.GetParam;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
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

    public interface NetworkUnavailableHandler {
        void handle();
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
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    public static OkHttpClient mOkHttpClient; // 请求client

    public static Callback mCallback; // 默认callBack.不做任何操作

    // 获取当前处于哪一个activity
    public static Activity getSelectedActivity() {
        return NormalContainer.get(NormalContainer.SELECTED_ACTIVITY);
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

    public synchronized static void initCallback() {
        if (mCallback == null) {
            mCallback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            };
        }
    }

    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            initClient();
        }
        return mOkHttpClient;
    }

    public static Callback getCallback() {
        if (mCallback == null) {
            initCallback();
        }
        return mCallback;
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

    // 获取请求
    public static Request generateRequest(METHOD method, List<String> restParams, List<GetParam> getParams, Object requestBody) {
        Request.Builder mBuilder = new Request.Builder().url(OSS_SERVER_ADDRESS + generateRestParam(restParams) + generateGetParam(getParams));
        switch (method) {
            case GET:
                return mBuilder.get().build();
            case DELETE:
                return mBuilder.delete().build();
            case POST:
                if (requestBody != null) {
                    mBuilder.post(RequestBody.create(MEDIA_TYPE, JSON.toJSONString(requestBody)));
                }
                return mBuilder.build();
            case PUT:
                if (requestBody != null) {
                    mBuilder.put(RequestBody.create(MEDIA_TYPE, JSON.toJSONString(requestBody)));
                }
                return mBuilder.build();
            default:
                return mBuilder.get().build();
        }
    }
    // ============== 异步 ==============

    public static <T> Call asyn(METHOD method,
                                Boolean needHandle, List<String> restParams, List<GetParam> getParams, Object requestBody,
                                ReflectStrategy<T> reflectStrategy,
                                NetworkUnavailableHandler networkUnavailableHandler, ConnectFailHandler connectFailHandler, SuccessHandler<T> successHandler,
                                FailHandler<T> failureHandler, ErrorHandler errorHandler) {
        if (!NetworkUtils.isConnected() || (!NetworkUtils.getMobileDataEnabled() && !NetworkUtils.isWifiConnected())) {
            // 网络未开启  NetworkUtils.isAvailableByPing() 阻塞线程 不可用
            runOnUiThread(networkUnavailableHandler::handle);
            return null;
        } else {
            Request request = generateRequest(method, restParams, getParams, requestBody);
            Call call = getOkHttpClient().newCall(request);
            call.enqueue(needHandle ? new Callback() {
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
                            if (result.getState() == 0) {
                                // 成功
                                if (result.getResult() instanceof JSONObject) {
                                    T t;
                                    if (reflectStrategy.isClass()) {
                                        t = ((JSONObject) result.getResult()).toJavaObject(reflectStrategy.getTClass());
                                    } else {
                                        t = ((JSONObject) result.getResult()).toJavaObject(reflectStrategy.getTypeReference());
                                    }
                                    result.setResult(t);
                                } else {
                                    return;
                                }
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
            } : getCallback());
            return call;  //返回,用于取消网络请求
        }
    }

    // ============== 同步 ==============  暂时不需要
    public static <T> Call sync(METHOD method,
                                Boolean needHandle, List<String> restParams, List<GetParam> getParams, Object requestBody,
                                ReflectStrategy<T> reflectStrategy,
                                ConnectFailHandler connectFailHandler, SuccessHandler<T> successHandler,
                                FailHandler<T> failureHandler, ErrorHandler errorHandler) {
        return null;
    }
}
