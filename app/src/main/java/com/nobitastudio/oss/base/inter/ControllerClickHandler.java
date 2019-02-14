package com.nobitastudio.oss.base.inter;

import com.nobitastudio.oss.fragment.BaseFragment;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/14 16:46
 * @description
 */
public interface ControllerClickHandler {

    /**
     * 打开fragment
     * @param fragment
     */
    void startFragment(BaseFragment fragment);

    /**
     * 打开fragment并关闭
     * @param fragment
     */
    void startFragmentAndDestroyCurrent(BaseFragment fragment);
}
