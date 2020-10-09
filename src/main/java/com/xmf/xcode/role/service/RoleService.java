package com.xmf.xcode.role.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.role.dao.RoleDao;
import com.xmf.xcode.role.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Service(角色数据)
 *
 * @author rufei.cn
 * @version 2018-12-19
 */
@Service
@SuppressWarnings("all")
public class RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleHelperService roleHelperService;
    private static Logger logger = LoggerFactory.getLogger(RoleService.class);

    /**
     * getList(获取角色数据带分页数据-服务)
     *
     * @param json
     * @return
     * @author rufei.cn
     */
    public Partion getList(@RequestBody JSONObject json) {
        logger.info("getList(获取角色数据带分页数据-服务) 开始 json={}", json);
        if (json == null || json.size() < 1) {
            return null;
        }
        Partion pt = null;
        int totalcount = roleHelperService.getTotalCount(json);
        List<Role> list = null;
        if (totalcount > 0) {
            list = roleDao.getList(json);
        }
        pt = new Partion(json, list, totalcount);

        logger.info("getList(获取角色数据带分页数据-服务) 结束 ");
        return pt;
    }

    /**
     * getRoleList(获取角色数据 不带分页数据-服务)
     *
     * @param role
     * @return
     * @author rufei.cn
     */
    public List<Role> getRoleList(@RequestBody Role role) {
        String parms = JSON.toJSONString(role);
        List<Role> list = null;
        logger.info("getRoleList(获取角色数据 不带分页数据-服务) 开始 parms={}", parms);
        if (role == null) {
            return list;
        }
        list = roleDao.getRoleList(role);
        logger.info("getRoleList(获取角色数据 不带分页数据-服务) 结束");
        return list;
    }


    /**
     * save (保存角色数据 数据-服务)
     *
     * @param role
     * @return
     * @author rufei.cn
     */
    public Role save(@RequestBody Role role) throws Exception {
        String parms = JSON.toJSONString(role);
        logger.info("save (保存角色数据 数据-服务) 开始 parms={}", parms);
        Role ret = null;
        if (role == null) {
            return ret;
        }
        ret = roleHelperService.save(role);
        logger.info("save (保存角色数据 数据-服务) 结束");
        return ret;
    }

    /**
     * getRole(获取角色数据单条数据-服务)
     *
     * @param role
     * @return
     * @author rufei.cn
     */
    public Role getRole(@RequestBody Role role) {
        Role ret = null;
        String parms = JSON.toJSONString(role);
        List<Role> list = null;
        logger.info("getRole(获取角色数据单条数据-服务) 开始 parms={}", parms);
        if (role == null) {
            return ret;
        }
        ret = roleHelperService.getSignleRole(role);
        logger.info("getRole(获取角色数据单条数据-服务) 结束 ");
        return ret;
    }

    /**
     * delete(逻辑删除角色数据数据-服务)
     *
     * @param id
     * @return
     * @author rufei.cn
     */
    public boolean delete(Long id) {
        logger.info("delete(逻辑删除角色数据数据-服务) 开始 id={}", id);
        boolean isSuccess = false;
        if (id < 1) {
            return isSuccess;
        }
        Role dt = roleHelperService.getRoleById(id);
        if (dt == null) {
            return isSuccess;
        }
        roleDao.delete(id);
        isSuccess = true;
        logger.info("delete(逻辑删除角色数据数据-服务)结束 id={},isSuccess={}", id, isSuccess);
        return isSuccess;
    }
}