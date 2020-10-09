package com.xmf.xcode.home;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.common.PermissionInterceptor;
import com.xmf.xcode.common.ResultCodeMessage;
import com.xmf.xcode.common.RetData;
import com.xmf.xcode.common.ReturnT;
import com.xmf.xcode.user.model.User;
import com.xmf.xcode.user.service.UserHelperService;
import com.xmf.xcode.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * index controller
 *
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserHelperService userHelperService;

    @RequestMapping("/")
    public String index(Model model) {
        Map<String, Object> dashboardMap = new HashMap<>();
        model.addAllAttributes(dashboardMap);
        return "user/user-index";
    }

    @RequestMapping("/chartInfo")
    @ResponseBody
    public ReturnT<JSONObject> chartInfo(Date startDate, Date endDate) {
        ReturnT<JSONObject> retData = null;
        logger.info("retData = " + retData);
        return retData;
    }

    @RequestMapping("/toLogin")
    public String toLogin(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "toLogin";
    }

    @RequestMapping(value = "login")
    @ResponseBody
    public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String phone, String password, String ifRemember) {
        // param
        if (StringUtil.isBlank(phone) || StringUtil.isBlank(password)) {
            return new ReturnT<String>(500, "密码不能为空");
        }
        HttpSession session = request.getSession();
        Object us = session.getAttribute("user");
        if (us != null) {
            return ReturnT.SUCCESS;
        }
        // do login
        RetData retData = null;
        try {
            retData = userHelperService.login(phone, password);
        } catch (Exception e) {
            logger.error(StringUtil.getExceptionMsg(e));

        }
        int code = retData.getCode();
        String message = retData.getMessage();
        Object data = retData.getData();
        if (code == ResultCodeMessage.FAILURE || code == ResultCodeMessage.PARMS_ERROR) {
            return new ReturnT<String>(500, message);
        }
        User user = (User) data;
        if (user == null) {
            return new ReturnT<String>(500, "用户信息不存在");
        }
        boolean ifRem = StringUtil.isNotBlank(ifRemember) && "on".equals(ifRemember);
        PermissionInterceptor.login(response, ifRem);
        logger.info("-----------------------------------5");
        request.getSession().setAttribute("user", user);
        return ReturnT.SUCCESS;
    }

    @RequestMapping(value = "logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        PermissionInterceptor.logout(request, response);
        return "login";
    }

    @RequestMapping("/help")
    public String help() {
        return "help";
    }

    @RequestMapping("/sysError")
    public String error(String errorMsg, Model model) {
        model.addAttribute("errorMsg", errorMsg);
        return "common/common-error";
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
