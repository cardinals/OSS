package com.nobitastudio.oss.model.dto;

import com.nobitastudio.oss.model.entity.CheckItem;
import com.nobitastudio.oss.model.entity.Drug;
import com.nobitastudio.oss.model.entity.ElectronicCase;
import com.nobitastudio.oss.model.entity.OperationItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/25 17:01
 * @description 通过诊疗卡查询全部的电子病历信息时的信息封装对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCaseDTO {

    // 电子病历实体信息
    private ElectronicCase electronicCase;

    // 电子病历中的药品
    private List<Drug> drugs;
    // 每一项药品对应的数量
    private List<Integer> drugCount;

    // 电子病历中的检查项
    private List<CheckItem> checkItems;
    // 各个检查项对应的数量
    private List<Integer> checkItemCount;

    // 电子病历中的手术项
    private List<OperationItem> operationItems;
    // 各个手术项对应的数量
    private List<Integer> operationItemCount;

    // 挂号单全部信息
    private RegistrationAll registrationAll;
}
