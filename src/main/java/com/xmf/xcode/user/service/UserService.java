package com.xmf.xcode.user.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.user.dao.UserDao;
import com.xmf.xcode.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Service(调度系统用户)
 *
 * @author rufei.cn
 * @version 2018-09-18
 */
@Service
@SuppressWarnings("all")
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserHelperService userHelperService;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * getList(获取调度系统用户带分页数据-服务)
     *
     * @param json
     * @return
     * @author rufei.cn
     */
    public Partion getList(@RequestBody JSONObject json) {
        logger.info("getList(获取调度系统用户带分页数据-服务) 开始 json={}", json);
        if (json == null || json.size() < 1) {
            return null;
        }
        Partion pt = null;
        int totalcount = userHelperService.getTotalCount(json);
        List<User> list = null;
        if (totalcount > 0) {
            list = userDao.getList(json);
        }
        pt = new Partion(json, list, totalcount);
        logger.info("getList(获取调度系统用户带分页数据-服务) 结束 ");
        return pt;
    }

    /**
     * getUserList(获取调度系统用户 不带分页数据-服务)
     *
     * @param user
     * @return
     * @author rufei.cn
     */
    public List<User> getUserList(@RequestBody User user) {
        String parms = JSON.toJSONString(user);
        List<User> list = null;
        logger.info("getUserList(获取调度系统用户 不带分页数据-服务) 开始 parms={}", parms);
        if (user == null) {
            return list;
        }
        list = userDao.getUserList(user);
        logger.info("getUserList(获取调度系统用户 不带分页数据-服务) 结束");
        return list;
    }


    /**
     * save (保存调度系统用户 数据-服务)
     *
     * @param user
     * @return
     * @author rufei.cn
     */
    public User save(@RequestBody User user) {
        String parms = JSON.toJSONString(user);
        logger.info("save (保存调度系统用户 数据-服务) 开始 parms={}", parms);
        if (user == null) {
            return user;
        }
        user = userHelperService.save(user);
        logger.info("save (保存调度系统用户 数据-服务) 结束");
        return user;
    }


    /**
     * getUser(获取调度系统用户单条数据-服务)
     *
     * @param user
     * @return
     * @author rufei.cn
     */
    public User getUser(@RequestBody User user) {
        User ret = null;
        String parms = JSON.toJSONString(user);
        List<User> list = null;
        logger.info("getUser(获取调度系统用户单条数据-服务) 开始 parms={}", parms);
        if (user == null) {
            return ret;
        }
        ret = userHelperService.getSignleUser(user);
        logger.info("getUser(获取调度系统用户单条数据-服务) 结束 ");
        return ret;
    }


    /**
     * delete(逻辑删除调度系统用户数据-服务)
     *
     * @param id
     * @return
     * @author rufei.cn
     */
    public boolean delete(Long id) {
        logger.info("delete(逻辑删除调度系统用户数据-服务) 开始 id={}", id);
        boolean isSuccess = false;
        if (id < 1) {
            return isSuccess;
        }
        User dt = userHelperService.getUserById(id);
        if (dt == null) {
            return isSuccess;
        }
        userDao.delete(id);
        isSuccess = true;
        logger.info("delete(逻辑删除调度系统用户数据-服务)结束 id={},isSuccess={}", id, isSuccess);
        return isSuccess;
    }
}