package com.xmf.xcode.menu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.menu.dao.MenuDao;
import com.xmf.xcode.menu.model.Menu;
import com.xmf.xcode.menu.model.MenuNode;
import com.xmf.xcode.role.service.MenuRoleService;
import com.xmf.xcode.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service(job-菜单)
 *
 * @author rufei.cn
 * @version 2018-10-10
 */
@Service
@SuppressWarnings("all")
public class MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private MenuHelperService menuHelperService;
    @Autowired
    private MenuRoleService menuRoleService;

    private static Logger logger = LoggerFactory.getLogger(MenuService.class);

    /**
     * getList(获取job-菜单带分页数据-服务)
     *
     * @param json
     * @return
     * @author rufei.cn
     */
    public Partion getList(JSONObject json) {
        logger.info("getList(获取job-菜单带分页数据-服务) 开始 json={}", json);
        if (json == null || json.size() < 1) {
            return null;
        }
        Partion pt = null;

        int totalcount = menuHelperService.getTotalCount(json);
        List<Menu> list = null;
        if (totalcount > 0) {
            list = menuDao.getList(json);
        }
        pt = new Partion(json, list, totalcount);
        logger.info("getList(获取job-菜单带分页数据-服务) 结束 ");
        return pt;
    }

    /**
     * getMenuList(获取job-菜单 不带分页数据-服务)
     *
     * @param menu
     * @return
     * @author rufei.cn
     */
    public List<Menu> getMenuList(Menu menu) {
        String parms = JSON.toJSONString(menu);
        List<Menu> list = null;
        logger.info("getMenuList(获取job-菜单 不带分页数据-服务) 开始 parms={}", parms);
        if (menu == null) {
            return list;
        }

        list = menuDao.getMenuList(menu);
        logger.info("getMenuList(获取job-菜单 不带分页数据-服务) 结束");
        return list;
    }


    /**
     * save (保存job-菜单 数据-服务)
     *
     * @param menu
     * @return
     * @author rufei.cn
     */
    public Menu save(Menu menu) {
        String parms = JSON.toJSONString(menu);
        logger.info("save (保存job-菜单 数据-服务) 开始 parms={}", parms);
        if (menu == null) {
            return menu;
        }

        menu = menuHelperService.save(menu);
        logger.info("save (保存job-菜单 数据-服务) 结束");
        return menu;
    }

    /**
     * getMenu(获取job-菜单单条数据-服务)
     *
     * @param menu
     * @return
     * @author rufei.cn
     */
    public Menu getMenu(Menu menu) {
        Menu ret = null;
        String parms = JSON.toJSONString(menu);
        List<Menu> list = null;
        logger.info("getMenu(获取job-菜单单条数据-服务) 开始 parms={}", parms);
        if (menu == null) {
            return ret;
        }

        ret = menuHelperService.getSignleMenu(menu);
        logger.info("getMenu(获取job-菜单单条数据-服务) 结束 ");
        return ret;
    }

    /**
     * delete(逻辑删除job-菜单数据-服务)
     *
     * @param id
     * @return
     * @author rufei.cn
     */
    public boolean delete(Long id) {
        logger.info("delete(逻辑删除job-菜单数据-服务) 开始 id={}", id);
        boolean isSuccess = false;
        if (id < 1) {
            return isSuccess;
        }
        Menu dt = menuHelperService.getMenuById(id);
        if (dt == null) {
            return isSuccess;
        }
        menuDao.delete(id);
        isSuccess = true;
        logger.info("delete(逻辑删除job-菜单数据-服务)结束 id={},isSuccess={}", id, isSuccess);
        return isSuccess;
    }

    /**
     * 获取参数树
     *
     * @param level        菜单等级
     * @param fid          父级ID
     * @param roleId       角色ID 用于查询所具备的权限菜单信息
     * @param pageType     当前查询的页面
     * @param selecdRoleId //角色ID  用于查询这个角色下的需要选中的菜单
     * @return
     */
    public List<MenuNode> getTreeList(int level, Long fid, long roleId, String pageType, long selecdRoleId) {
        List<MenuNode> list = null;
        List<Menu> menuList = null;
        if ("left_menu".equals(pageType)) {
            Map parms = new HashMap();
            parms.put("flag", 1);
            parms.put("level", level);
            parms.put("roleId", roleId);
            if (fid != null && fid > 0) {
                parms.put("fid", fid);
            }
            menuList = menuDao.getRoleMenuList(parms);
        } else {
            Menu job = new Menu();
            job.setLevel(level);
            if (fid != null && fid > 0) {
                job.setFid(fid);
            }
            menuList = menuDao.getMenuList(job);
        }
        if (menuList == null || menuList.size() <= 0) {
            return list;
        }
        int size = menuList.size();
        list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Menu menu = menuList.get(i);
            if (menu == null) {
                continue;
            }
            Long id = menu.getId();
            level = menu.getLevel() + 1;
            List<MenuNode> nodeList = getTreeList(level, id, roleId, pageType, selecdRoleId);
            MenuNode node = new MenuNode();
            if (nodeList != null && nodeList.size() > 0) {
                node.setNodes(nodeList);
            }
            boolean isChecked = isNodeChecked(selecdRoleId, id);
            JSONObject state = new JSONObject();
            state.put("checked", isChecked);
            node.setText(menu.getName());
            node.setState(state);
            if (i % 2 == 0) {
                node.setIcon("fa fa-circle-o text-red");
            } else {
                node.setIcon("fa fa-circle-o text-aqua");
            }
            if ("left_menu".equals(pageType)) {
                node.setHref(menu.getUrl());
            }
            node.setSelectable(false);
            node.setNodeid(menu.getId());
            list.add(node);
        }
        return list;
    }

    /**
     * 判断当前节点是否需要选中
     *
     * @param roleId
     * @param menuId
     * @return
     */
    public boolean isNodeChecked(long roleId, Long menuId) {
        boolean isChecked = false;
        if (roleId <= 0) {
            return isChecked;
        }
        List<Menu> roleList = getMenuRoles(roleId, null);
        if (roleList == null || roleList.size() <= 0) {
            return isChecked;
        }
        int size = roleList.size();
        for (int i = 0; i < size; i++) {
            Menu menuRole = roleList.get(i);
            Long roleMenuId = menuRole.getId();
            if (roleMenuId == null) {
                continue;
            }
            if (roleMenuId.equals(menuId)) {
                isChecked = true;
                break;
            }
        }
        return isChecked;
    }

    /**
     * 根据角色ID获取需要选中的菜单数据
     *
     * @param roleId
     * @return
     */
    public List<Menu> getMenuRoles(long roleId, String roleCode) {
        List<Menu> list = null;
        Map parms = new HashMap();
        parms.put("flag", 1);
        if (roleId > 0) {
            parms.put("roleId", roleId);
        }
        if (StringUtil.isNotBlank(roleCode)) {
            parms.put("roleCode", roleCode);
        }
        list = menuDao.getRoleMenuList(parms);
        return list;
    }
}