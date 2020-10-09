package com.xmf.xcode.menu.dao;

import com.xmf.xcode.menu.model.*;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.menu.model.Menu;

/**
 * job-菜单DAO接口
 *
 * @author rufei.cn
 * @version 2018-10-10
 */
@SuppressWarnings("all")
public interface MenuDao {

    /**
     * 删除数据（逻辑删除）
     *
     * @param question
     * @return
     */
    public void delete(long id);

    /**
     * 单条数据增加
     *
     * @param menu
     * @return
     */
    public void add(Menu menu);

    /**
     * 批量数据增加
     *
     * @param jobMenu
     * @return
     */
    public void addTrainRecordBatch(List<Menu> list);

    /**
     * 根据ID获取单条数据
     *
     * @param id
     * @return
     */
    public Menu getMenuById(long id);

    /**
     * 修改单条数据
     *
     * @param id
     * @return
     */
    public void updateById(Menu menu);

    /**
     * 获取分页数据
     *
     * @param map
     * @return
     */
    public List<Menu> getList(JSONObject map);


    /**
     * 获取集合数据，不带分页
     *
     * @param map
     * @return
     */
    public List<Menu> getMenuList(Menu menu);

    /**
     * 获取单条数据
     *
     * @param map
     * @return
     */
    public Menu getSignleMenu(Menu menu);

    /**
     * 获取分页记录总数
     *
     * @param map
     * @return
     */
    public Integer getTotalCount(Map map);

    /**
     * 根据角色查询用户菜单
     *
     * @param json
     * @return
     */
    public List<Menu> getRoleMenuList(Map json);


}