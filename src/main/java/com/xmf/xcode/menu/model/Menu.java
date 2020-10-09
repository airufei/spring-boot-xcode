package com.xmf.xcode.menu.model;

import com.xmf.xcode.common.BaseEntitys;
import lombok.Data;

/**
 * job-菜单Entity
 *
 * @author rufei.cn
 * @version 2018-10-10
 */
@Data
public class Menu extends BaseEntitys {

    private static final long serialVersionUID = 1L;
    private String name;        // 菜单名称

    private String url;        // 菜单地址

    private Integer isbutton;        // 是否button按钮 0不是 1是

    private Long fid;        // 父级菜单ID

    private Integer level;        // 菜单等级

    private String  updateTimestr;        // 创建时间
   }