package com.nobitastudio.oss.fragment.home;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.TypeReference;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.fragment.BaseFragment;
import com.nobitastudio.oss.base.helper.TipDialogHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.base.inter.HttpHandler;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.controller.home.HomeController;
import com.nobitastudio.oss.controller.home.InfoController;
import com.nobitastudio.oss.controller.home.MineController;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.dto.GetParam;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.entity.MedicalCard;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class HomeFragment extends BaseFragment implements HttpHandler {

    /**
     * 初始化有哪些 pager
     */
    enum Pager {
        HOME, INFO, MINE;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return HOME;
                case 1:
                    return INFO;
                case 2:
                    return MINE;
                default:
                    return HOME;
            }
        }
    }

    @BindView(R.id.fragment_pager)
    ViewPager mViewPager;
    @BindView(R.id.fragment_tabs)
    QMUITabSegment mTabSegment;

    private HashMap<Pager, View> mPages;
    private PagerAdapter mPagerAdapter = new PagerAdapter() {

        private int mChildCount = 0;

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return mPages.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            View page = mPages.get(Pager.getPagerFromPosition(position));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(page, params);
            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount == 0) {
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }
    };
    TipDialogHelper mTipDialogHelper;

    /**
     * 初始化pagers  分别为 医院主页,消息,我的
     */
    private void initPagers() {
        mPages = new HashMap<>();
        ControllerClickHandler mHandler = new ControllerClickHandler() {
            @Override
            public void startFragment(BaseFragment target) {
                HomeFragment.this.startFragment(target);
            }

            @Override
            public void startFragmentAndDestroyCurrent(BaseFragment targetFragment) {
                HomeFragment.this.startFragmentAndDestroyCurrent(targetFragment);
            }
        };
        mPages.put(Pager.HOME, new HomeController(getContext(), mHandler));
        mPages.put(Pager.INFO, new InfoController(getContext(), mHandler));
        mPages.put(Pager.MINE, new MineController(getContext(), mHandler));
        mViewPager.setAdapter(mPagerAdapter);
        mTabSegment.setupWithViewPager(mViewPager, false);
    }

    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_blue);
        mTabSegment.setDefaultNormalColor(normalColor);
        mTabSegment.setDefaultSelectedColor(selectColor);
        QMUITabSegment.Tab home = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_home),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_home_selected),
                getResources().getString(R.string.home), false
        );

        QMUITabSegment.Tab info = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_notification),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_notification_selected),
                getResources().getString(R.string.msg), false
        );
        QMUITabSegment.Tab mine = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_mine),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_mine_selected),
                getResources().getString(R.string.mine), false
        );
        mTabSegment.addTab(home)
                .addTab(info)
                .addTab(mine);
//        mTabSegment.setDefaultTabIconPosition(QMUITabSegment.ICON_POSITION_BOTTOM);
//        // 如果你的 icon 显示大小和实际大小不吻合:
//        // 1. 设置icon 的 bounds
//        // 2. Tab 使用拥有5个参数的构造器
//        // 3. 最后一个参数（setIntrinsicSize）设置为false
//        int iconShowSize = QMUIDisplayHelper.dp2px(getContext(), 20);
//        Drawable normalDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_component);
//        normalDrawable.setBounds(0, 0, iconShowSize, iconShowSize);
//        Drawable selectDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_component_selected);
//
//        selectDrawable.setBounds(0, 0, iconShowSize, iconShowSize);
//
//        QMUITabSegment.Tab home = new QMUITabSegment.Tab(
//                normalDrawable,
//                normalDrawable,
//                "Components", false, false
//        );
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected void refresh(Boolean isCancelPull) {
        ((HomeController) mPages.get(Pager.HOME)).refresh(false);  // HomeController 没有生命周期.先初始化再刷新数据.
    }

    @Override
    protected void init() {
        initUserData();
        initTabs();
        initPagers();
        refresh(Boolean.FALSE);
    }

    // 初始化用户基础数据 收藏的医生
    private void initUserData() {

        mTipDialogHelper = new TipDialogHelper(getContext());

        // 绑定的诊疗卡
        getAsyn(Arrays.asList("medical-card", ((User) NormalContainer.get(NormalContainer.USER)).getId().toString(), "medical-cards"), null,
                new ReflectStrategy<>(new TypeReference<List<MedicalCard>>() {
                }), new OkHttpUtil.SuccessHandler<List<MedicalCard>>() {
                    @Override
                    public void handle(List<MedicalCard> medicalCards) {
                        NormalContainer.put(NormalContainer.BIND_MEDICAL_CARD, medicalCards);
                    }
                }, new OkHttpUtil.FailHandler<List<MedicalCard>>() {
                    @Override
                    public void handle(ServiceResult<List<MedicalCard>> serviceResult) {
                        mTipDialogHelper.showInfoTipDialog("获取绑定的诊疗卡失败.请进入'诊疗卡'重新获取", mViewPager);
                    }
                });

        // 收藏的医生
        getAsyn(Arrays.asList("doctor", ((User) NormalContainer.get(NormalContainer.USER)).getId().toString(), "collect-doctor"), null,
                new ReflectStrategy<>(new TypeReference<List<Doctor>>() {
                }), new OkHttpUtil.SuccessHandler<List<Doctor>>() {
                    @Override
                    public void handle(List<Doctor> doctors) {
                        NormalContainer.put(NormalContainer.COLLECT_DOCTOR, doctors);
                    }
                }, new OkHttpUtil.FailHandler<List<Doctor>>() {
                    @Override
                    public void handle(ServiceResult<List<Doctor>> serviceResult) {
                        mTipDialogHelper.showInfoTipDialog("获取收藏的医生失败.请至'我的'收藏中重新获取", mViewPager);
                    }
                });

        // 设置属性:推送等待
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public OkHttpUtil.NetworkUnavailableHandler getNetworkUnavailableHandler() {
        return () -> mTipDialogHelper.showInfoTipDialog("当前网络不可用", mViewPager);
    }

    // ===================== okHttpUtil
    // 获取默认的连接失败处理器.默认显示 服务器开小差了  如果需要改变展示方式，重写该方法
    @Override
    public OkHttpUtil.ConnectFailHandler getConnectFailHandler() {
        return (call, e) -> mTipDialogHelper.showInfoTipDialog("服务器开小差了,请稍后再试", mViewPager);
    }

    // 服务器发生错误. 如果需要改变展示方式，重写该方法
    @Override
    public OkHttpUtil.ErrorHandler getErrorHandler() {
        return (call, response) -> {
            Log.e("网络错误,错误码:", String.valueOf(response.code()));
            mTipDialogHelper.showErrorTipDialog("服务器发生错误,请联系系统管理员", mViewPager);
        };
    }


    public <T> Call getAsyn(List<String> restParams, List<GetParam> getParams, ReflectStrategy<T> reflectStrategy,
                            OkHttpUtil.SuccessHandler<T> successHandler, OkHttpUtil.FailHandler<T> failureHandler) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.GET,
                Boolean.TRUE, restParams, getParams, null, reflectStrategy,
                getNetworkUnavailableHandler(), getConnectFailHandler(), successHandler, failureHandler, getErrorHandler());
    }

    // 只调用 .不需要处理返回结果
    public Call getAsyn(List<String> restParams, List<GetParam> getParams) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.GET,
                Boolean.FALSE, restParams, getParams, null, null,
                getNetworkUnavailableHandler(), null, null, null, null);
    }

    // delete请求
    public <T> Call deleteAsyn(List<String> restParams, List<GetParam> getParams, ReflectStrategy<T> reflectStrategy,
                               OkHttpUtil.SuccessHandler<T> successHandler, OkHttpUtil.FailHandler<T> failureHandler) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.DELETE,
                Boolean.TRUE, restParams, getParams, null, reflectStrategy,
                getNetworkUnavailableHandler(), getConnectFailHandler(), successHandler, failureHandler, getErrorHandler());
    }

    // 只调用 .不需要处理返回结果
    public Call deleteAsyn(List<String> restParams, List<GetParam> getParams) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.DELETE,
                Boolean.FALSE, restParams, getParams, null, null,
                getNetworkUnavailableHandler(), null, null, null, null);
    }

    // post 请求
    public <T> Call postAsyn(List<String> restParams, List<GetParam> getParams, Object requestBody, ReflectStrategy<T> reflectStrategy,
                             OkHttpUtil.SuccessHandler<T> successHandler, OkHttpUtil.FailHandler<T> failureHandler) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.POST,
                Boolean.TRUE, restParams, getParams, requestBody, reflectStrategy,
                getNetworkUnavailableHandler(), getConnectFailHandler(), successHandler, failureHandler, getErrorHandler());
    }

    // post 请求 不处理返回结果
    public <T> Call postAsyn(List<String> restParams, List<GetParam> getParams, Object requestBody) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.POST,
                Boolean.FALSE, restParams, getParams, requestBody, null,
                getNetworkUnavailableHandler(), null, null, null, null);
    }

    // put 请求
    public <T> Call putAsyn(List<String> restParams, List<GetParam> getParams, Object requestBody, ReflectStrategy<T> reflectStrategy,
                            OkHttpUtil.SuccessHandler<T> successHandler, OkHttpUtil.FailHandler<T> failureHandler) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.PUT,
                Boolean.TRUE, restParams, getParams, requestBody, reflectStrategy,
                getNetworkUnavailableHandler(), getConnectFailHandler(), successHandler, failureHandler, getErrorHandler());
    }

    // put 请求 不处理返回结果
    public <T> Call putAsyn(List<String> restParams, List<GetParam> getParams, Object requestBody) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.PUT,
                Boolean.FALSE, restParams, getParams, requestBody, null,
                getNetworkUnavailableHandler(), null, null, null, null);
    }

}
