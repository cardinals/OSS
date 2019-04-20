package com.nobitastudio.oss.model.vo;

import com.nobitastudio.oss.model.dto.DoctorAndDepartment;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.model.entity.MedicalCard;
import com.nobitastudio.oss.model.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/16 12:23
 * @description 用户登录成功后,返回结果的集合
 */
public class UserLoginResult implements Serializable {

    private static final long serialVersionUID = 7668766782546903694L;

    private User user;

    private List<MedicalCard> medicalCards;

    private List<DoctorAndDepartment> doctorAndDepartments;

    private List<HealthArticle> healthArticles;
}
