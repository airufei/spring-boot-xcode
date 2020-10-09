package com.xmf.xcode.role.service;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.role.dao.MenuRoleDao;
import com.xmf.xcode.role.model.MenuRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service(角色菜单关系)
 *
 * @author rufei.cn
 * @version 2018-12-19
 */
@Service
@SuppressWarnings("all")
public class MenuRoleHelperService {

    @Autowired
    private MenuRoleDao menuRoleDao;
    private static Logger logger = LoggerFactory.getLogger(MenuRoleService.class);

    /**
     * 获取分页总记录数
     *
     * @param map
     * @return
     */
    public int getTotalCount(JSONObject map) {
        int resCount = 0;
        Integer totalCount = menuRoleDao.getTotalCount(map);
        if (totalCount != null) {
            resCount = totalCount;
        }
        return resCount;
    }


    /**
     * save(保存角色菜单关系)
     * @param menuRole
     * @author rufei.cn
     * @date 2018/1/30 14:59
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MenuRole save(MenuRole menuRole) throws  Exception {
        MenuRole ret = null;
        if (menuRole == null) {
            return ret;
        }
        Long roleId = menuRole.getId();
        long id=0;
        if(roleId!=null)
        {
            id=roleId.longValue();
        }
        if (id > 0) {
            updateById(menuRole);
            ret = menuRole;
        } else {
            menuRole.setId(null);
            menuRoleDao.add(menuRole);

            ret = menuRole;
        }
        return ret;
    }

    /**
     * addTrainRecordBatch(批量新增角色菜单关系数据)
     * @param jobMenuRole
     * @author rufei.cn
     * @date 2018/1/30 14:59
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addTrainRecordBatch(List<MenuRole> list) throws  Exception
    {
        menuRoleDao.addTrainRecordBatch(list);
    }

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public MenuRole getMenuRoleById(long id) {
        return menuRoleDao.getMenuRoleById(id);
    }

    /**
     * 获取单条数据
     *
     * @param menuRole
     * @return
     * @author rufei.cn
     */
    public MenuRole getSignleMenuRole(MenuRole menuRole) {
        return menuRoleDao.getSignleMenuRole(menuRole);
    }

    /**
     * 修改单条数据
     *
     * @param id
     * @return
     */
    public void updateById(MenuRole menuRole) {
        menuRoleDao.updateById(menuRole);
    }

}