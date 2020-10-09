package com.xmf.xcode.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.common.ResultCodeMessage;
import com.xmf.xcode.user.model.User;
import com.xmf.xcode.user.service.UserService;
import com.xmf.xcode.common.ReturnT;
import com.xmf.xcode.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController(调度系统用户)
 *
 * @author rufei.cn
 * @version 2018-09-18
 */
@Controller
@RequestMapping("/user")
@SuppressWarnings("all")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @RequestMapping
    public String index() {
        return "user/user-index";
    }

    /**
     * getList:(获取调度系统用户分页查询接口)
     *
     * @param request
     * @param request
     * @return
     * @author rufei.cn
     */
    @RequestMapping("getList")
    @ResponseBody
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", 0);        // 总记录数
        maps.put("recordsFiltered", 0);    // 过滤后的总记录数
        maps.put("data", null);                    // 分页列表
        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int pageSize = 10;
        int pageNo = 1;
        int start = 0;
        if (StringUtil.isNotBlank(startStr)) {
            start = StringUtil.stringToInt(startStr);
        }
        if (StringUtil.isNotBlank(length)) {
            pageSize = StringUtil.stringToInt(length);
        }
        if (start > 0) {
            pageNo = (start / pageSize) + 1;
        }
        JSONObject object = StringUtil.getPageJSONObject(pageNo, pageSize);
        object.put("username", username);
        object.put("email", email);
        object.put("phone", phone);
        Partion pt = userService.getList(object);
        List<User> list = null;
        int totalCount = 0;
        if (pt != null) {
            list = (List<User>) pt.getList();
            totalCount = pt.getPageCount();
        }
        maps.put("recordsTotal", totalCount);        // 总记录数
        maps.put("recordsFiltered", totalCount);    // 过滤后的总记录数
        maps.put("data", list);                    // 分页列表
        logger.info("getList:(获取调度系统用户分页查询接口) 结束");
        return maps;
    }

    /**
     * delete:(逻辑删除调度系统用户数据接口)
     *
     * @param request
     * @param parms
     * @return
     * @author rufei.cn
     */
    @RequestMapping("delete")
    @ResponseBody
    public ReturnT<String> delete(HttpServletRequest request, String parms) {
        ReturnT<String> retData = new ReturnT<>(ResultCodeMessage.FAILURE, ResultCodeMessage.FAILURE_MESSAGE);
        logger.info("delete:(逻辑删除调度系统用户数据接口) 开始  parms={}", parms);
        if (StringUtil.isBlank(parms)) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        JSONObject json = JSONObject.parseObject(parms);
        if (json == null) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        Long id = json.getLong("id");
        if (id != null && id > 0) {
            userService.delete(id);
            retData.setMsg("删除成功");
            retData.setCode(ResultCodeMessage.SUCCESS);
        } else {
            retData.setMsg("请选择需要删除的数据");
        }
        logger.info("delete:(逻辑删除调度系统用户数据接口) 结束  parms={}", parms);
        return retData;
    }

    /**
     * save:(保存调度系统用户数据接口)
     *
     * @param request
     * @param user
     * @return
     * @author rufei.cn
     */
    @RequestMapping("save")
    @ResponseBody
    public ReturnT<String> save(HttpServletRequest request, User user) {
        ReturnT<String> retData = new ReturnT<String>();
        // 无保存内容
        if (user == null) {
            retData.setMsg("无保存内容");
            return retData;
        }
        String username = user.getUsername();
        String phone = user.getPhone();
        if (StringUtil.isBlank(username)) {
            retData.setMsg("用户名不能为空");
            return retData;
        }
        if (StringUtil.isBlank(phone)) {
            retData.setMsg("手机号不能为空");
            return retData;
        }
        if (!StringUtil.isMobilePhone(phone)) {
            retData.setMsg("手机号格式不正确");
            return retData;
        }
        Long id = user.getId();
        String password = user.getPassword();
        if (id != null && id >= 0) {
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
        } else if (StringUtil.isBlank(password)) {
            retData.setMsg("密码不能为空");
            return retData;
        }
        int length = -1;
        if (StringUtil.isNotBlank(password)) {
            length = password.length();
            password = StringUtil.getEncryptPassword(password);
            user.setPassword(password);
        }
        if (length != -1 && (length < 6 || length > 20)) {
            retData.setMsg("密码长度6-20位");
            return retData;
        }
        // 保存数据库
        User ret = userService.save(user);
        if (ret != null) {
            retData.setCode(ResultCodeMessage.SUCCESS);
            retData.setMsg("保存成功");
        }
        logger.info("save:(保存调度系统用户数据接口) 结束");
        return retData;
    }


}