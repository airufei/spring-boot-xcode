package com.xmf.xcode.user.service;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.common.ResultCodeMessage;
import com.xmf.xcode.common.RetData;
import com.xmf.xcode.user.model.User;
import com.xmf.xcode.util.StringUtil;
import com.xmf.xcode.user.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service(调度系统用户)
 *
 * @author rufei.cn
 * @version 2018-09-18
 */
@Service
@SuppressWarnings("all")
public class UserHelperService {

    @Autowired
    private UserDao userDao;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 获取分页总记录数
     *
     * @param map
     * @return
     */
    public int getTotalCount(JSONObject map) {
        int resCount = 0;
        Integer totalCount = userDao.getTotalCount(map);
        if (totalCount != null) {
            resCount = totalCount;
        }
        return resCount;
    }


    /**
     * save(保存调度系统用户)
     * @param user
     * @author rufei.cn
     * @date 2018/1/30 14:59
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User save(User user) {
        if (user == null) {
            return user;
        }
        if (user.getId() != null && user.getId() > 0) {
            updateById(user);
        } else {
            userDao.add(user);
        }
        return user;
    }

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    /**
     * 获取单条数据
     *
     * @param user
     * @return
     * @author rufei.cn
     */
    public User getSignleUser(User user) {
        return userDao.getSignleUser(user);
    }

    /**
     * 修改单条数据
     *
     * @param id
     * @return
     */
    public void updateById(User user) {
        userDao.updateById(user);
    }


    /**
     * login(登录)
     *
     * @param username
     * @param password
     * @return
     */
    public RetData login(String phone, String password) {
        RetData retData = new RetData();
        if (StringUtil.isBlank(phone)) {
            retData.setMessage("手机号不能为空");
            return retData;
        }
        if (StringUtil.isBlank(password)) {
            retData.setMessage("密码不能为空");
            return retData;
        }
        if (!StringUtil.isMobilePhone(phone)) {
            retData.setMessage("手机号不正确");
            return retData;
        }
        int length = password.length();
        if (length != -1 && (length < 6 || length > 20)) {
            retData.setMessage("密码长度6-20位");
            return retData;
        }
        try {
            password = StringUtil.getEncryptPassword(password);
            User user = new User();
            user.setPhone(phone);
            user.setPassword(password);
            User u = getSignleUser(user);
            if (u != null && u.getId() > 0) {
                retData.setCode(ResultCodeMessage.SUCCESS);
                retData.setData(u);
                retData.setMessage(ResultCodeMessage.SUCCESS_MESSAGE);
            } else {
                retData.setCode(ResultCodeMessage.FAILURE);
                retData.setMessage("账户或者密码不正确");
            }
        } catch (Exception e) {
            String msg = "login(登录) 异常====>" + StringUtil.getExceptionMsg(e);
            logger.error(msg);

        }
        return retData;
    }

}