package com.xmf.xcode.role.service;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.role.dao.RoleDao;
import com.xmf.xcode.role.model.MenuRole;
import com.xmf.xcode.role.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service(角色数据)
 *
 * @author rufei.cn
 * @version 2018-12-19
 */
@Service
@SuppressWarnings("all")
public class RoleHelperService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private MenuRoleHelperService menuRoleHelperService;

    private static Logger logger = LoggerFactory.getLogger(RoleService.class);

    /**
     * 获取分页总记录数
     *
     * @param map
     * @return
     */
    public int getTotalCount(JSONObject map) {
        int resCount = 0;
        Integer totalCount = roleDao.getTotalCount(map);
        if (totalCount != null) {
            resCount = totalCount;
        }
        return resCount;
    }


    /**
     * save(保存角色数据)
     * @param role
     * @author rufei.cn
     * @date 2018/1/30 14:59
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Role save(Role role) throws Exception {
        Role ret = null;
        if (role == null) {
            return ret;
        }
        if (role.getId() != null && role.getId() > 0) {
            updateById(role);
            ret = role;
        } else {
            role.setId(null);
            roleDao.add(role);
            ret = role;
        }
        boolean b = saveRoleAndMenu(ret);
        if (!b) {
            ret = null;
        }
        return ret;
    }

    /**
     * 保持角色和菜单关系
     *
     * @param role
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveRoleAndMenu(Role role) throws Exception {
        boolean ret = false;
        Long roleId = role.getId();
        List<String> list = role.getList();
        if (roleId == null || roleId <= 0) {
            return ret;
        }
        if (list == null || list.size() <= 0) {
            return ret;
        }
        int len = 0;
        len = list.size();
        List<MenuRole> menuRoleList = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            String s = list.get(i).replace("[", "").replace("]", "");
            JSONObject jsonObject = JSONObject.parseObject(s);
            if (jsonObject == null) {
                continue;
            }
            MenuRole menuRole = new MenuRole();
            long id = jsonObject.getLongValue("id");
            menuRole.setMenuId(id);
            menuRole.setRoleId(roleId);
            menuRole.setRoleCode(role.getRoleCode());
            menuRoleList.add(menuRole);
        }
        if (menuRoleList.size() > 0) {
            menuRoleHelperService.addTrainRecordBatch(menuRoleList);
            ret = true;
        }
        return ret;
    }

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public Role getRoleById(long id) {
        return roleDao.getRoleById(id);
    }

    /**
     * 获取单条数据
     *
     * @param role
     * @return
     * @author rufei.cn
     */
    public Role getSignleRole(Role role) {
        return roleDao.getSignleRole(role);
    }

    /**
     * 修改单条数据
     *
     * @param id
     * @return
     */
    public void updateById(Role role) {
        roleDao.updateById(role);
    }

}