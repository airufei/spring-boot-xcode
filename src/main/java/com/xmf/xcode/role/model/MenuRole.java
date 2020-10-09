package com.xmf.xcode.role.model;

import com.xmf.xcode.common.BaseEntitys;
import lombok.Data;

/**
 * 角色菜单关系Entity
 *
 * @author rufei.cn
 * @version 2018-12-19
 */
@Data
public class MenuRole extends BaseEntitys {

    private static final long serialVersionUID = 1L;
    private Long roleId;        // 角色ID
    private String roleCode;        // 角色Code

    private Long menuId;        // 菜单ID
    private String  updateTimestr;        // 创建时间

}