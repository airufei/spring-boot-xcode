package com.xmf.xcode.user.model;


import com.xmf.xcode.common.BaseEntitys;
import lombok.Data;

/**
 * 调度系统用户Entity
 *
 * @author rufei.cn
 * @version 2018-09-18
 */
@Data
public class User extends BaseEntitys {

    private static final long serialVersionUID = 1L;
    private String username;        // 名字

    private String phone;        // 手机/账户

    private String password;        // 密码

    private Integer age;        // 年龄

    private String email;        // 邮箱

    private String address;        // 地址

    private String qq;        // QQ

    private String wechart;        // 微信号

    private String  updateTimestr;        // 创建时间

    private String roleCode;//角色ID

}