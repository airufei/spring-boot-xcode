package com.xmf.xcode.role.model;

import com.xmf.xcode.common.BaseEntitys;
import lombok.Data;

import java.util.List;

/**
 * 角色数据Entity
 *
 * @author rufei.cn
 * @version 2018-12-19
 */
@Data
public class Role extends BaseEntitys {

    private static final long serialVersionUID = 1L;
    private String name;        // 角色名称
    private String roleCode;        // 角色代码
    private List<String> list;//菜单数据
    private String  updateTimestr;        // 创建时间
}