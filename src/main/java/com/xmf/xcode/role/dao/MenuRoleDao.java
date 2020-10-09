package com.xmf.xcode.role.dao;

import com.xmf.xcode.role.model.*;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.role.model.MenuRole;

/**
 * 角色菜单关系DAO接口
 * @author rufei.cn
 * @version 2018-12-19
 */
@SuppressWarnings("all")
public interface MenuRoleDao {
	
	/**
	 * 删除数据（逻辑删除）
	 * @param question
	 * @return
	 */
	public void delete(long roleId);
    /**
	 * 单条数据增加
	 * @param menuRole
	 * @return
	 */
	public void add(MenuRole menuRole);

    /**
	 * 批量数据增加
	 * @param jobMenuRole
	 * @return
	 */
	 public void addTrainRecordBatch(List<MenuRole> list);

     /**
	 * 根据ID获取单条数据
	 * @param id
	 * @return
	 */
	 public MenuRole getMenuRoleById(long id);

      /**
	 * 修改单条数据
	 * @param id
	 * @return
	 */
	  public void updateById(MenuRole menuRole);

	  /**
	   * 获取分页数据
	   * @param map
	   * @return
	   */
	   public List<MenuRole>  getList(JSONObject map);
	   
	   
	   /**
	   * 获取集合数据，不带分页
	   * @param map
	   * @return
	   */
	   public List<MenuRole>  getMenuRoleList(MenuRole menuRole);

	   /**
	   * 获取单条数据
	   * @param map
	   * @return
	   */
	   public MenuRole getSignleMenuRole(MenuRole menuRole);

	  /**
	   * 获取分页记录总数
	   * @param map
	   * @return
	   */
	   public Integer  getTotalCount(Map map);
	   
}