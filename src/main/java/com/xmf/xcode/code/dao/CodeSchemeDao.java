package com.xmf.xcode.code.dao;

import com.xmf.xcode.code.model.*;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.model.CodeScheme;

/**
 * 代码生成方案DAO接口
 * @author rufei.cn
 * @version 2018-12-10
 */
@SuppressWarnings("all")
public interface CodeSchemeDao {
	
	/**
	 * 删除数据（逻辑删除）
	 * @param question
	 * @return
	 */
	public void delete(long id);
    /**
	 * 单条数据增加
	 * @param codeScheme
	 * @return
	 */
	public void add(CodeScheme codeScheme);

    /**
	 * 批量数据增加
	 * @param codeScheme
	 * @return
	 */
	 public void addTrainRecordBatch(List<CodeScheme> list);

     /**
	 * 根据ID获取单条数据
	 * @param id
	 * @return
	 */
	 public CodeScheme getCodeSchemeById(long id);

      /**
	 * 修改单条数据
	 * @param id
	 * @return
	 */
	  public void updateById(CodeScheme codeScheme);

	  /**
	   * 获取分页数据
	   * @param map
	   * @return
	   */
	   public List<CodeScheme>  getList(JSONObject map);
	   
	   
	   /**
	   * 获取集合数据，不带分页
	   * @param map
	   * @return
	   */
	   public List<CodeScheme>  getCodeSchemeList(CodeScheme codeScheme);

	   /**
	   * 获取单条数据
	   * @param map
	   * @return
	   */
	   public CodeScheme  getSignleCodeScheme(CodeScheme codeScheme);

	  /**
	   * 获取分页记录总数
	   * @param map
	   * @return
	   */
	   public Integer  getTotalCount(Map map);
	   
}