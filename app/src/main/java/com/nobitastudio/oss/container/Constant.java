package com.nobitastudio.oss.container;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.nobitastudio.oss.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/28 09:56
 * @description 常量工具类
 */
public class Constant {

    public static final String LOCAL_HOST_IP = "10.0.2.2";  // Android 虚拟机内置访问本地服务地址 -> localhost
    public static final String OSS_SERVER_ONLINE = "http://www.nobitastudio.cn";  // 线上地址
    public static final String OSS_SERVER_LOCAL = "http://10.0.2.2";  // 本地服务

    // 科室图片相关
    public static Map<String, Integer> DEPARTMENT_MIPMAP;

    public static Map<String, Integer> getDepartmentMipmap() {
        return DEPARTMENT_MIPMAP == null ? initDepartmentMipmap() : DEPARTMENT_MIPMAP;
    }

    // 初始化保存信息
    private static Map<String, Integer> initDepartmentMipmap() {
        DEPARTMENT_MIPMAP = new HashMap<>();
        DEPARTMENT_MIPMAP.put("全科", R.mipmap.ic_general);
        DEPARTMENT_MIPMAP.put("美容科", R.mipmap.ic_beauty);
        DEPARTMENT_MIPMAP.put("麻醉科", R.mipmap.ic_anesthesia);
        DEPARTMENT_MIPMAP.put("营养科", R.mipmap.ic_nutrition);
        DEPARTMENT_MIPMAP.put("眼科", R.mipmap.ic_eye);
        DEPARTMENT_MIPMAP.put("化验科", R.mipmap.ic_assay);
        DEPARTMENT_MIPMAP.put("皮肤科", R.mipmap.ic_skin);
        DEPARTMENT_MIPMAP.put("整形科", R.mipmap.ic_plastic);
        DEPARTMENT_MIPMAP.put("中医科", R.mipmap.ic_tcm);
        DEPARTMENT_MIPMAP.put("生殖科", R.mipmap.ic_reproduction);
        DEPARTMENT_MIPMAP.put("外科", R.mipmap.ic_operation_big);
        DEPARTMENT_MIPMAP.put("内科", R.mipmap.ic_internal_medicine);
        DEPARTMENT_MIPMAP.put("泌尿科", R.mipmap.ic_urinary);
        DEPARTMENT_MIPMAP.put("妇科", R.mipmap.ic_woman);
        DEPARTMENT_MIPMAP.put("儿科", R.mipmap.ic_child);
        DEPARTMENT_MIPMAP.put("肿瘤科", R.mipmap.ic_tumor);
        DEPARTMENT_MIPMAP.put("脑科", R.mipmap.ic_brain);
        DEPARTMENT_MIPMAP.put("内分泌科", R.mipmap.ic_endocrine);
        DEPARTMENT_MIPMAP.put("骨科", R.mipmap.ic_bone);
        DEPARTMENT_MIPMAP.put("耳鼻喉科", R.mipmap.ic_ent);
        DEPARTMENT_MIPMAP.put("男科", R.mipmap.ic_man);
        DEPARTMENT_MIPMAP.put("口腔科", R.mipmap.ic_oral);
        DEPARTMENT_MIPMAP.put("病理科", R.mipmap.ic_pathology);
        DEPARTMENT_MIPMAP.put("影像科", R.mipmap.ic_image);
        DEPARTMENT_MIPMAP.put("传染科", R.mipmap.ic_infect);
        DEPARTMENT_MIPMAP.put("康复科", R.mipmap.ic_rehabilitation);
        return DEPARTMENT_MIPMAP;
    }

    // 高德地图导航
    // 石河子大学附属医院坐标：经度：86.059641,纬度：44.299419,注意构造函数是 LatLng（纬度，经度）
    public static final LatLng SHZ_UNIVERSITY_FIRST_AFFILIATED_HOSPITAL_LATLNG = new LatLng(44.299419, 86.059641);

    // 驾车
    public static final AmapNaviParams AMAP_NAVI_DRIVER_PARAM = new AmapNaviParams(null, null,
            new Poi("石河子大学医学院第一附属医院", SHZ_UNIVERSITY_FIRST_AFFILIATED_HOSPITAL_LATLNG, ""), AmapNaviType.DRIVER);
    // 骑行
    public static final AmapNaviParams AMAP_NAVI_RIDE_PARAM = new AmapNaviParams(null, null,
            new Poi("石河子大学医学院第一附属医院", SHZ_UNIVERSITY_FIRST_AFFILIATED_HOSPITAL_LATLNG, ""), AmapNaviType.RIDE);
    // 走路
    public static final AmapNaviParams AMAP_NAVI_WALK_PARAM = new AmapNaviParams(null, null,
            new Poi("石河子大学医学院第一附属医院", SHZ_UNIVERSITY_FIRST_AFFILIATED_HOSPITAL_LATLNG, ""), AmapNaviType.WALK);

    public static AmapNaviParams getAmapNaviParamByIndex(int index) {
        switch (index) {
            case 0:
                return AMAP_NAVI_DRIVER_PARAM;
            case 1:
                return AMAP_NAVI_RIDE_PARAM;
            case 2:
                return AMAP_NAVI_WALK_PARAM;
            default:
                return AMAP_NAVI_DRIVER_PARAM;
        }
    }
}
