package com.xmf.xcode.code.model;

import com.xmf.xcode.common.BaseEntitys;
import com.xmf.xcode.util.StringUtil;

/**
 * 表字段信息Entity
 *
 * @author rufei.cn
 * @version 2018-12-10
 */

public class CodeTableColumn extends BaseEntitys {
    private static final long serialVersionUID = 1L;
    private String tableId="";        // 归属表编号

    private String name="";       // 名称

    private String comments="";       // 描述

    private String jdbcType="";        // 列的数据类型的字节长度

    private String javaType="";       // JAVA类型

    private String javaField="";        // JAVA字段名

    private String isPk = "0";        // 是否主键

    private String isNull = "0";        // 是否可为空

    private String isInsert = "1";        // 是否为插入字段

    private String isEdit = "1";        // 是否编辑字段

    private String isList = "1";        // 是否列表字段

    private String isQuery = "0";        // 是否查询字段

    private String queryType = "=";        // 查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）

    private String showType="";       // 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）

    private String dictType="";        // 字典类型

    private String isSort = "0";        // 排序字段

    private String settings="";        // 其它设置（扩展字段JSON）

    private String sort = "0";        // 排序（升序）

    private String tableName="";        // 表名称

    private String isEditpage = "0";        // 编辑字段

    private String isinsertrequiredfield = "0";        // 插入必须字段 1 非必须0

    private String isupdaterequiredfield = "0";        // 插入必须字段 1 非必须0

    private String updateTimestr="";       // 时间

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaField() {
        return javaField;
    }

    public void setJavaField(String javaField) {
        this.javaField = javaField;
    }

    public String getIsPk() {
        return isPk;
    }

    public void setIsPk(String isPk) {
        this.isPk = isPk;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getIsInsert() {
        return isInsert;
    }

    public void setIsInsert(String isInsert) {
        this.isInsert = isInsert;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public String getIsList() {
        return isList;
    }

    public void setIsList(String isList) {
        this.isList = isList;
    }

    public String getIsQuery() {
        return isQuery;
    }

    public void setIsQuery(String isQuery) {
        this.isQuery = isQuery;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getIsSort() {
        return isSort;
    }

    public void setIsSort(String isSort) {
        this.isSort = isSort;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIsEditpage() {
        return isEditpage;
    }

    public void setIsEditpage(String isEditpage) {
        this.isEditpage = isEditpage;
    }

    public String getIsinsertrequiredfield() {
        return isinsertrequiredfield;
    }

    public void setIsinsertrequiredfield(String isinsertrequiredfield) {
        this.isinsertrequiredfield = isinsertrequiredfield;
    }

    public String getIsupdaterequiredfield() {
        return isupdaterequiredfield;
    }

    public void setIsupdaterequiredfield(String isupdaterequiredfield) {
        this.isupdaterequiredfield = isupdaterequiredfield;
    }

    public String getUpdateTimestr() {
        return updateTimestr;
    }

    public void setUpdateTimestr(String updateTimestr) {
        this.updateTimestr = updateTimestr;
    }

    /**
     * 获取列名和说明
     *
     * @return
     */
    public String getNameAndComments() {
        return getName() + (comments == null ? "" : "  :  " + comments);
    }

    /**
     * 获取字符串长度
     *
     * @return
     */
    public String getDataLength() {
        String[] ss = StringUtil.split(
                StringUtil.substringBetween(getJdbcType(), "(", ")"), ",");
        if (ss != null && ss.length == 1) {// &&
            // "String".equals(getJavaType())){
            return ss[0];
        }
        return "0";
    }

    /**
     * 获取简写Java类型
     *
     * @return
     */
    public String getSimpleJavaType() {
        String simpleJavaType=StringUtil.indexOf(getJavaType(), ".") != -1 ? StringUtil
                .substringAfterLast(getJavaType(), ".") : getJavaType();
        String javaField = getJavaField();
        if(javaField!=null&&javaField.equals("id"))
        {
            simpleJavaType="Long";
        }
        return simpleJavaType;
    }

    /**
     * 获取简写Java字段
     *
     * @return
     */
    public String getSimpleJavaField() {
        return StringUtil.substringBefore(getJavaField(), ".");
    }

    /**
     * 获取Java字段，如果是对象，则获取对象.附加属性1
     *
     * @return
     */
    public String getJavaFieldId() {
        return StringUtil.substringBefore(getJavaField(), "|");
    }

    /**
     * 获取Java字段，如果是对象，则获取对象.附加属性2
     *
     * @return
     */
    public String getJavaFieldName() {
        String[][] ss = getJavaFieldAttrs();
        return ss.length > 0 ? getSimpleJavaField() + "." + ss[0][0] : "";
    }

    /**
     * 获取Java字段，所有属性名
     *
     * @return
     */
    public String[][] getJavaFieldAttrs() {
        String[] ss = StringUtil.split(
                StringUtil.substringAfter(getJavaField(), "|"), "|");
        String[][] sss = new String[ss.length][2];
        if (ss != null) {
            for (int i = 0; i < ss.length; i++) {
                sss[i][0] = ss[i];
                sss[i][1] = StringUtil.toCamelCase(ss[i]);
            }
        }
        return sss;
    }


    /**
     * 是否是基类字段
     *
     * @return
     */
    public Boolean getIsNotBaseField() {
        return !StringUtil.equals(getSimpleJavaField(), "id")
                && !StringUtil.equals(getSimpleJavaField(), "remark")
                && !StringUtil.equals(getSimpleJavaField(), "createtime")
                && !StringUtil.equals(getSimpleJavaField(), "updatetime")
                && !StringUtil.equals(getSimpleJavaField(), "updateTime")
                && !StringUtil.equals(getSimpleJavaField(), "createTime")
                && !StringUtil.equals(getSimpleJavaField(), "createdBy")
                && !StringUtil.equals(getSimpleJavaField(), "updatedTime")
                && !StringUtil.equals(getSimpleJavaField(), "updatedBy")
                && !StringUtil.equals(getSimpleJavaField(), "status")
                && !StringUtil.equals(getSimpleJavaField(), "createdTime")
                && !StringUtil.equals(getSimpleJavaField(), "operatorName")
                && !StringUtil.equals(getSimpleJavaField(), "operatorId")
                && !StringUtil.equals(getSimpleJavaField(), "roleCode")
                && !StringUtil.equals(getSimpleJavaField(), "flag");

    }

    /**
     * 是否Bo字段
     *
     * @return
     */
    public Boolean getIsNotBoField() {
        return
                 !StringUtil.equals(getSimpleJavaField(), "createtime")
                && !StringUtil.equals(getSimpleJavaField(), "updatetime")
                && !StringUtil.equals(getSimpleJavaField(), "updateTime")
                && !StringUtil.equals(getSimpleJavaField(), "createTime")
                && !StringUtil.equals(getSimpleJavaField(), "createdBy")
                && !StringUtil.equals(getSimpleJavaField(), "updatedTime")
                && !StringUtil.equals(getSimpleJavaField(), "updatedBy")
                && !StringUtil.equals(getSimpleJavaField(), "createdTime");
    }

}