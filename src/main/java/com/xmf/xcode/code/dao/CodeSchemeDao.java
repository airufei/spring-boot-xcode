package com.xmf.xcode.code.dao;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.model.CodeScheme;

import java.util.List;
import java.util.Map;

/**
 * 代码生成方案DAO接口
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@SuppressWarnings("all")
public interface CodeSchemeDao {

    /**
     * 删除数据（逻辑删除）
     *
     * @param question
     */
    public void delete(long id);

    /**
     * 单条数据增加
     *
     * @param codeScheme
     */
    public void add(CodeScheme codeScheme);

    /**
     * 批量数据增加
     *
     * @param codeScheme
     */
    public void addTrainRecordBatch(List<CodeScheme> list);

    /**
     * 根据ID获取单条数据
     *
     * @param id
     */
    public CodeScheme getCodeSchemeById(long id);

    /**
     * 修改单条数据
     *
     * @param id
     */
    public void updateById(CodeScheme codeScheme);

    /**
     * 获取分页数据
     *
     * @param map
     */
    public List<CodeScheme> getList(JSONObject map);


    /**
     * 获取集合数据，不带分页
     *
     * @param map
     */
    public List<CodeScheme> getCodeSchemeList(CodeScheme codeScheme);

    /**
     * 获取单条数据
     *
     * @param map
     */
    public CodeScheme getSignleCodeScheme(CodeScheme codeScheme);

    /**
     * 获取分页记录总数
     *
     * @param map
     */
    public Integer getTotalCount(Map map);

}