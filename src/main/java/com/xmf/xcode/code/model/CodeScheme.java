package com.xmf.xcode.code.model;

import com.xmf.xcode.common.BaseEntitys;
import lombok.Data;

/**
 * 代码生成方案Entity
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@Data
public class CodeScheme extends BaseEntitys {

    private static final long serialVersionUID = 1L;
    private String name;        // 名称

    private String category;        // 分类

    private String packageName;        // 生成包路径

    private String moduleName;        // 生成模块名

    private String subModuleName;        // 生成子模块名

    private String functionName;        // 生成功能名

    private String functionNameSimple;        // 生成功能名（简写）

    private String functionAuthor;        // 生成功能作者

    private String tableName;        // 表名

    private Integer tableId;        // 生成表编号

    private String modulePageName;        // 页面模块

    private String subPageName;        // 子模块

    private String updateTimestr;        // 时间

    private  String path;

}