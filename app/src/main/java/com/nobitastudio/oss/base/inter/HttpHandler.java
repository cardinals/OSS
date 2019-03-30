package com.nobitastudio.oss.base.inter;

import com.nobitastudio.oss.util.OkHttpUtil;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/29 16:01
 * @description 使用工具http util异常情况时的处理器
 */
public interface HttpHandler {

    OkHttpUtil.NetworkUnavailableHandler getNetworkUnavailableHandler();

    OkHttpUtil.ConnectFailHandler getConnectFailHandler();

    OkHttpUtil.ErrorHandler getErrorHandler();
}
