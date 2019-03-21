package com.nobitastudio.oss.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/10 17:34
 * @description 用户实例
 */
@Data
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -2738521069482377465L;

    public interface UserIdView {
    } // 用户id

    public interface UserPasswordView {
    } // 用户密码

    public interface UserMobileView {
    } // 用户联系方式

    public interface UserNameView {
    } // 用户姓名

    public interface UserIdCardView {
    } // 用户身份证

    public interface UserCreateView extends UserMobileView, UserPasswordView, UserNameView, UserIdCardView {
    }  // 用户注册  mobile.password,name,idcard

    public interface UserModifyView extends UserIdView, UserNameView, UserIdCardView {
    }  // 用户更改基本信息  id name idCard

    public interface UserFindPasswordView extends UserIdView,UserPasswordView {} // 用户找回密码视图

    public interface UserModifyMobileView extends UserIdView,UserMobileView {} // 用户更改绑定的手机号

    public interface UserLoginView extends UserMobileView,UserPasswordView {} // 用户登录时的调用

    // todo 测试
    public User(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    //todo 测试
    public User(String mobile,String password,String name,String idCard) {
        this.mobile = mobile;
        this.password = password;
        this.name = name;
        this.idCard = idCard;
    }

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "last_change_password")
    private LocalDateTime lastChangePassword;

    @Column(name = "unlocked")
    private Boolean unlocked;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "id_card")
    private String idCard;

    /**
     * 用户更新 仅更新用户名以及身份证号
     *
     * @param user
     * @return
     */
    public User update(User user) {
        if (user.getName() != null) {
            this.setName(user.getName());
        }
        if (user.getIdCard() != null) {
            this.setIdCard(user.getIdCard());
        }
        return this;
    }

    public void wipeOffPassword() {
        this.password = null;
    }
}
