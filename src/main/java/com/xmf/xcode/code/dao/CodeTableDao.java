package com.xmf.xcode.code.dao;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.model.CodeTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据表信息DAO接口
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@SuppressWarnings("all")
public interface CodeTableDao {

    /**
     * 删除数据（逻辑删除）
     *
     * @param question
     * 
     */
    public void delete(long id);

    /**
     * 单条数据增加
     *
     * @param codeTable
     * 
     */
    public void add(CodeTable codeTable);

    /**
     * 批量数据增加
     *
     * @param codeTable
     * 
     */
    public void addTrainRecordBatch(List<CodeTable> list);

    /**
     * 根据ID获取单条数据
     *
     * @param id
     * 
     */
    public CodeTable getCodeTableById(long id);

    /**
     * 修改单条数据
     *
     * @param id
     * 
     */
    public void updateById(CodeTable codeTable);

    /**
     * 获取分页数据
     *
     * @param map
     * 
     */
    public List<CodeTable> getList(JSONObject map);


    /**
     * 获取集合数据，不带分页
     *
     * @param map
     * 
     */
    public List<CodeTable> getCodeTableList(CodeTable codeTable);

    /**
     * 获取单条数据
     *
     * @param map
     * 
     */
    public CodeTable getSignleCodeTable(CodeTable codeTable);

    /**
     * 获取分页记录总数
     *
     * @param map
     * 
     */
    public Integer getTotalCount(Map map);

    /**
     * 获取当前数据库的所有表信息（不包含系统表）
     * @param dbName
     * @param tableName
     * 
     */
    public List<CodeTable> getTableList(@Param("dbName") String dbName, @Param("tableName") String tableName);


}