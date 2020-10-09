package com.xmf.xcode.menu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.common.ResultCodeMessage;
import com.xmf.xcode.menu.model.Menu;
import com.xmf.xcode.menu.model.MenuNode;
import com.xmf.xcode.menu.service.MenuService;
import com.xmf.xcode.role.model.Role;
import com.xmf.xcode.role.service.RoleService;
import com.xmf.xcode.user.model.User;
import com.xmf.xcode.common.ReturnT;
import com.xmf.xcode.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * MenuController(job-菜单)
 *
 * @author rufei.cn
 * @version 2018-10-17
 */
@Controller
@RequestMapping("/menu")
@SuppressWarnings("all")
public class MenuController {

    private static Logger logger = LoggerFactory.getLogger(MenuService.class);
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;


    @RequestMapping
    public String index(HttpServletRequest request, Model model) {
        Menu menu = new Menu();
        menu.setLevel(1);
        List<Menu> menuList = menuService.getMenuList(menu);
        if (menuList == null) {
            menuList = new ArrayList<>();
        }
        List<Menu> list = new ArrayList<>();
        menu.setName("无上级");
        long id = -1;
        menu.setId(id);
        list.add(0, menu);
        for (Menu item : menuList) {
            list.add(item);
        }
        model.addAttribute("menuList", list);
        return "menu/menu-index";
    }


    /**
     * 获取参数树
     *
     * @param uId
     * @return
     */
    @RequestMapping("getTreeList")
    @ResponseBody
    public List<MenuNode> getTreeList(HttpServletRequest request) {
        List<MenuNode> list = null;
        HttpSession session = request.getSession();
        logger.info("1==========================================================1");
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return list;
        }
        String roleCode = user.getRoleCode();
        if (StringUtil.isBlank(roleCode)) {
            return list;
        }
        String pageType = request.getParameter("pageType");
        long selecdRoleId = StringUtil.stringToLong(request.getParameter("roleId"));
        if (!"left_menu".equals(pageType)) {
            roleCode = "admin_role";
        }
        Role role = new Role();
        role.setRoleCode(roleCode);
        Role jobRole = roleService.getRole(role);
        if (jobRole == null) {
            return list;
        }
        Long roleId = jobRole.getId();
        int level = 1;//默认从根节点开始查询
        list = menuService.getTreeList(1, null, roleId, pageType, selecdRoleId);
        return list;
    }

    /**
     * getList:(获取job-菜单分页查询接口)
     *
     * @param request
     * @return
     * @author rufei.cn
     */
    @RequestMapping("pageList")
    @ResponseBody
    public JSONObject getList(HttpServletRequest request) {
        JSONObject retJon = new JSONObject();
        JSONObject param = null;
        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String fid = request.getParameter("fid");
        String level = request.getParameter("level");
        if ("-1".equals(fid)) {
            fid = null;
        }
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
        param = StringUtil.getPageJSONObject(pageNo, pageSize);
        logger.info("getList:(获取job-菜单分页查询接口) 开始  param={}", param);
        param.put("name", name);
        param.put("fid", fid);
        param.put("level", level);
        Partion pt = menuService.getList(param);
        List<Menu> list = null;
        int totalCount = 0;
        if (pt != null) {
            list = (List<Menu>) pt.getList();
            totalCount = pt.getTotalCount();
        }
        retJon.put("data", list);
        retJon.put("recordsTotal", totalCount);
        retJon.put("recordsFiltered", totalCount);
        logger.info("getList:(获取job-菜单分页查询接口) 结束");
        return retJon;
    }

    /**
     * delete:(逻辑删除job-菜单数据接口)
     *
     * @param request
     * @param parms
     * @return
     * @author rufei.cn
     */
    @RequestMapping("delete")
    @ResponseBody
    public ReturnT<String> delete(HttpServletRequest request) {
        ReturnT<String> retData = new ReturnT<>(ResultCodeMessage.FAILURE, "删除失败");
        String ids = null;

        ids = request.getParameter("id");
        int id = StringUtil.stringToInt(ids);
        logger.info("delete 开始============>" + id);
        if (id <= 0) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        long newId = id;
        boolean ret = menuService.delete(newId);
        if (ret) {
            retData.setCode(ResultCodeMessage.SUCCESS);
            retData.setMsg(ResultCodeMessage.SUCCESS_MESSAGE);
        }
        logger.info("delete 结束============>" + JSON.toJSONString(retData));
        return retData;
    }

    /**
     * save:(保存job-菜单数据接口)
     *
     * @param request
     * @param parms
     * @return
     * @author rufei.cn
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public ReturnT<String> save(Menu menu, HttpServletRequest request) {
        ReturnT<String> retData = new ReturnT<>(ResultCodeMessage.FAILURE, "保存数据失败");
        String parms = null;

        Map<String, String[]> map = request.getParameterMap();
        logger.info(JSON.toJSONString(map));
        parms = JSON.toJSONString(menu);
        logger.info("save:(保存job-菜单数据接口) 开始  parms={}", parms);
        if (menu == null) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        if (menu == null) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        String url = menu.getUrl();
        Integer level = menu.getLevel();
        if (level == null) {
            retData.setMsg("菜单级别level不能为空");
            return retData;
        }
        if (level == 1) {
            menu.setUrl(null);
        }
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        Menu ret = menuService.save(menu);
        if (ret == null) {
            retData.setMsg("保存数据失败");
            return retData;
        }
        retData.setCode(ResultCodeMessage.SUCCESS);
        retData.setMsg(ResultCodeMessage.SUCCESS_MESSAGE);
        logger.info("save 结束============>" + JSON.toJSONString(retData));
        return retData;
    }

}