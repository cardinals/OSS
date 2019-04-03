package com.nobitastudio.oss.model.dto;

import com.nobitastudio.oss.model.entity.OSSOrder;
import com.nobitastudio.oss.model.entity.RegistrationRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/13 00:43
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmOrCancelRegisterDTO implements Serializable {

    private static final long serialVersionUID = 1046437035255760058L;

    private OSSOrder ossOrder;
    private RegistrationRecord registrationRecord;
}
