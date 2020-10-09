package com.xmf.xcode.role.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.role.dao.MenuRoleDao;
import com.xmf.xcode.role.model.MenuRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Service(角色菜单关系)
 *
 * @author rufei.cn
 * @version 2018-12-19
 */
@Service
@SuppressWarnings("all")
public class MenuRoleService {

    @Autowired
    private MenuRoleDao menuRoleDao;
    @Autowired
    private MenuRoleHelperService menuRoleHelperService;
    private static Logger logger = LoggerFactory.getLogger(MenuRoleService.class);

    /**
     * getList(获取角色菜单关系带分页数据-服务)
     *
     * @param json
     * @return
     * @author rufei.cn
     */
    public Partion getList(@RequestBody JSONObject json) {
        logger.info("getList(获取角色菜单关系带分页数据-服务) 开始 json={}", json);
        if (json == null || json.size() < 1) {
            return null;
        }
        Partion pt = null;
        int totalcount = menuRoleHelperService.getTotalCount(json);
        List<MenuRole> list = null;
        if (totalcount > 0) {
            list = menuRoleDao.getList(json);
        }
        pt = new Partion(json, list, totalcount);
        logger.info("getList(获取角色菜单关系带分页数据-服务) 结束 ");
        return pt;
    }

    /**
     * getMenuRoleList(获取角色菜单关系 不带分页数据-服务)
     *
     * @param menuRole
     * @return
     * @author rufei.cn
     */
    public List<MenuRole> getMenuRoleList(@RequestBody MenuRole menuRole) {
        String parms = JSON.toJSONString(menuRole);
        List<MenuRole> list = null;
        logger.info("getMenuRoleList(获取角色菜单关系 不带分页数据-服务) 开始 parms={}", parms);
        if (menuRole == null) {
            return list;
        }
        list = menuRoleDao.getMenuRoleList(menuRole);
        logger.info("getMenuRoleList(获取角色菜单关系 不带分页数据-服务) 结束");
        return list;
    }


    /**
     * save (保存角色菜单关系 数据-服务)
     *
     * @param menuRole
     * @return
     * @author rufei.cn
     */
    public MenuRole save(@RequestBody MenuRole menuRole) throws Exception {
        String parms = JSON.toJSONString(menuRole);
        logger.info("save (保存角色菜单关系 数据-服务) 开始 parms={}", parms);
        MenuRole ret = null;
        if (menuRole == null) {
            return ret;
        }
        ret = menuRoleHelperService.save(menuRole);
        logger.info("save (保存角色菜单关系 数据-服务) 结束");
        return ret;
    }

    /**
     * getMenuRole(获取角色菜单关系单条数据-服务)
     *
     * @param menuRole
     * @return
     * @author rufei.cn
     */
    public MenuRole getMenuRole(@RequestBody MenuRole menuRole) {
        MenuRole ret = null;
        String parms = JSON.toJSONString(menuRole);
        List<MenuRole> list = null;
        logger.info("getMenuRole(获取角色菜单关系单条数据-服务) 开始 parms={}", parms);
        if (menuRole == null) {
            return ret;
        }
        ret = menuRoleHelperService.getSignleMenuRole(menuRole);
        logger.info("getMenuRole(获取角色菜单关系单条数据-服务) 结束 ");
        return ret;
    }

    /**
     * delete(逻辑删除角色菜单关系数据-服务)
     *
     * @param id
     * @return
     * @author rufei.cn
     */
    public boolean delete(Long id) {
        logger.info("delete(逻辑删除角色菜单关系数据-服务) 开始 id={}", id);
        boolean isSuccess = false;
        menuRoleDao.delete(id);
        isSuccess = true;
        logger.info("delete(逻辑删除角色菜单关系数据-服务)结束 id={},isSuccess={}", id, isSuccess);
        return isSuccess;
    }
}