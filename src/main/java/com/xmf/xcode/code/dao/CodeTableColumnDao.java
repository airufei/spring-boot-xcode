package com.xmf.xcode.code.dao;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.model.CodeTableColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 表字段信息DAO接口
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@SuppressWarnings("all")
public interface CodeTableColumnDao {

    /**
     * 删除数据（逻辑删除）
     *
     * @param question
     */
    public void delete(long id);

    /**
     * 单条数据增加
     *
     * @param codeTableColumn
     */
    public void add(CodeTableColumn codeTableColumn);

    /**
     * 批量数据增加
     *
     * @param codeTableColumn
     */
    public void addTrainRecordBatch(List<CodeTableColumn> list);

    /**
     * 根据ID获取单条数据
     *
     * @param id
     */
    public CodeTableColumn getCodeTableColumnById(long id);

    /**
     * 修改单条数据
     *
     * @param id
     */
    public void updateById(CodeTableColumn codeTableColumn);

    /**
     * 获取分页数据
     *
     * @param map
     */
    public List<CodeTableColumn> getList(JSONObject map);


    /**
     * 获取集合数据，不带分页
     *
     * @param map
     */
    public List<CodeTableColumn> getCodeTableColumnList(CodeTableColumn codeTableColumn);

    /**
     * 获取单条数据
     *
     * @param map
     */
    public CodeTableColumn getSignleCodeTableColumn(CodeTableColumn codeTableColumn);

    /**
     * 获取分页记录总数
     *
     * @param map
     */
    public Integer getTotalCount(Map map);

    /**
     * deleteTable(物理删除表字段信息数据-服务)
     *
     * @param tableName
     * @author rufei.cn
     */
    void deleteTable(String tableName);

    /**
     * 获取表字段信息 mysql
     *
     * @param tableName
     */
    public List<CodeTableColumn> getDBTableColumnList(@Param("tableName") String tableName);

    /**
     * getQueryFieldCount:(查询字段的个数)
     *
     * @param col
     * @author airufei
     */
    public int getQueryFieldCount(CodeTableColumn col);

    /**
     * getQueryFieldCount:(编辑页字段的个数)
     *
     * @param col
     * @author airufei
     */
    public int getEditFieldCount(CodeTableColumn col);
}