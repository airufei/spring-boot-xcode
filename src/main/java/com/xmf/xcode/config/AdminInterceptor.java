package com.xmf.xcode.config;

import com.xmf.xcode.user.model.User;
import com.xmf.xcode.util.FtlUtil;
import com.xmf.xcode.util.I18nUtil;
import com.xmf.xcode.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * push cookies to model as cookieMap
 *
 * @author xuxueli 2015-12-12 18:09:04
 */

public class AdminInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);


    @Value("${xmf.xcode.login.webRoot}")
    private  String webroot;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("I18nUtil", FtlUtil.generateStaticModel(I18nUtil.class.getName()));
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String webRoot = getWebRootUrl();
        if (StringUtil.isBlank(webRoot)) {
            throw new Exception("未获取服务域名/IP");
        }
        String loginUrl = webRoot + "/toLogin";
        String strUrl = request.getRequestURI();
        logger.info("请求地址："+strUrl);
        if ((strUrl.contains("/toLogin") || strUrl.contains("/login") || strUrl.contains("/api") || strUrl.contains("/logout")|| strUrl.contains("/error"))) {
            return true;
        }
        if ((strUrl.contains("/plugins/")||strUrl.contains("/adminlte/")||strUrl.contains("/js/")||strUrl.contains("/treeview/")||strUrl.contains("/fonts/")||strUrl.endsWith(".css") || strUrl.endsWith(".js")|| strUrl.endsWith(".jpg")|| strUrl.endsWith(".gif"))) {
            return true;
        }
        //logger.info("登录地址============================" + loginUrl);
        HttpSession session = null;
        try {
            session = request.getSession();
        } catch (Exception e) {
        }
        if (session == null) {
            StringUtil.redirect(response, loginUrl);
            return false;
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getId() == null || user.getId() <= 0) {
            StringUtil.redirect(response, loginUrl);
            return false;
        }
        String roleCode = user.getRoleCode();
        boolean interceptUrl = true; //isInterceptUrl(roleCode, strUrl);//检查权限
        if (!interceptUrl) {
            logger.info("请求地址============================" + strUrl);
            String msg = "权限不足";
            msg = URLEncoder.encode(msg, "utf-8");
            String errorUrl = webRoot + "/sysError?errorMsg=" + msg;
            StringUtil.redirect(response, errorUrl);
            return false;
        }
        return true;
    }

    /**
     * 获取当前运行的系统名称
     *
     * @return
     */
    public String getWebRootUrl() {
        if (webroot==null) {
            return "http://localhost:8093";
        }
        return webroot;
    }

}
