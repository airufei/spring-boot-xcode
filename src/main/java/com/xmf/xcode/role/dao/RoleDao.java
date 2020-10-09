package com.xmf.xcode.role.dao;

import com.xmf.xcode.role.model.*;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.role.model.Role;

/**
 * 角色数据DAO接口
 * @author rufei.cn
 * @version 2018-12-19
 */
@SuppressWarnings("all")
public interface RoleDao {
	
	/**
	 * 删除数据（逻辑删除）
	 * @param question
	 * @return
	 */
	public void delete(long id);
    /**
	 * 单条数据增加
	 * @param role
	 * @return
	 */
	public void add(Role role);

    /**
	 * 批量数据增加
	 * @param jobRole
	 * @return
	 */
	 public void addTrainRecordBatch(List<Role> list);

     /**
	 * 根据ID获取单条数据
	 * @param id
	 * @return
	 */
	 public Role getRoleById(long id);

      /**
	 * 修改单条数据
	 * @param id
	 * @return
	 */
	  public void updateById(Role role);

	  /**
	   * 获取分页数据
	   * @param map
	   * @return
	   */
	   public List<Role>  getList(JSONObject map);
	   
	   
	   /**
	   * 获取集合数据，不带分页
	   * @param map
	   * @return
	   */
	   public List<Role>  getRoleList(Role role);

	   /**
	   * 获取单条数据
	   * @param map
	   * @return
	   */
	   public Role getSignleRole(Role role);

	  /**
	   * 获取分页记录总数
	   * @param map
	   * @return
	   */
	   public Integer  getTotalCount(Map map);
	   
}