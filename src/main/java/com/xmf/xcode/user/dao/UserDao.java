package com.xmf.xcode.user.dao;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.user.model.User;

import java.util.List;
import java.util.Map;

/**
 * 调度系统用户DAO接口
 * @author rufei.cn
 * @version 2018-09-18
 */
@SuppressWarnings("all")
public interface UserDao {
	
	/**
	 * 删除数据（逻辑删除）
	 * @param question
	 * @return
	 */
	public void delete(long id);
    /**
	 * 单条数据增加
	 * @param user
	 * @return
	 */
	public void add(User user);

    /**
	 * 批量数据增加
	 * @param jobUser
	 * @return
	 */
	 public void addTrainRecordBatch(List<User> list);

     /**
	 * 根据ID获取单条数据
	 * @param id
	 * @return
	 */
	 public User getUserById(long id);

      /**
	 * 修改单条数据
	 * @param id
	 * @return
	 */
	  public void updateById(User user);

	  /**
	   * 获取分页数据
	   * @param map
	   * @return
	   */
	   public List<User>  getList(JSONObject map);
	   
	   
	   /**
	   * 获取集合数据，不带分页
	   * @param map
	   * @return
	   */
	   public List<User>  getUserList(User user);

	   /**
	   * 获取单条数据
	   * @param map
	   * @return
	   */
	   public User getSignleUser(User user);

	  /**
	   * 获取分页记录总数
	   * @param map
	   * @return
	   */
	   public Integer  getTotalCount(Map map);
	   
}