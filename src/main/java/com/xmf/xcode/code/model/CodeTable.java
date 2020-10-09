package com.xmf.xcode.code.model;

import com.xmf.xcode.common.BaseEntitys;
import lombok.Data;

/**
 * 数据表信息Entity
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@Data
public class CodeTable extends BaseEntitys {

    private static final long serialVersionUID = 1L;
    private String name;        // 名称

    private String comments;        // 描述

    private String className;        // 实体类名称

    private String updateTimestr;        // 时间


}