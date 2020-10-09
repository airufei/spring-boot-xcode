/**
 * Project Name:CooxinPro
 * File Name:BaseEntity.java
 * Package Name:com.cn.cooxin.pojo
 * Date:2017年1月15日下午3:37:22
 * Copyright (c) 2017, hukailee@163.com All Rights Reserved.
 */

package com.xmf.xcode.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:BaseEntity (实体基类)
 * Date:     2017年1月15日 下午3:37:22
 *
 * @author rufei.cn
 * @Version 1.0
 * @see
 */
@Data
@SuppressWarnings("all")
public class BaseEntitys implements Serializable {


    private static final long serialVersionUID = 1L;
    private Long id;//数据主键Id
    private Date createTime = new Date();//创建时间
    private Date updateTime = new Date();//修改时间
    private Integer flag = 1;// 删除标记 1正常 -1删除
    private String remark;// 备注

}

