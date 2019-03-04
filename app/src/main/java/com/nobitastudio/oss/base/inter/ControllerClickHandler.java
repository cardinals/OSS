package com.nobitastudio.oss.base.inter;

import com.nobitastudio.oss.base.fragment.BaseFragment;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/14 16:46
 * @description
 */
public interface ControllerClickHandler {

    /**
     * 打开fragment
     * @param targetFragment
     */
    void startFragment(BaseFragment targetFragment);

    /**
     * 打开fragment并关闭
     * @param targetFragment
     */
    void startFragmentAndDestroyCurrent(BaseFragment targetFragment);
}
