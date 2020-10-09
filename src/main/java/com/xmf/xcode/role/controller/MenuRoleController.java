package com.xmf.xcode.role.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.common.ResultCodeMessage;
import com.xmf.xcode.util.StringUtil;
import com.xmf.xcode.common.ReturnT;
import com.xmf.xcode.role.model.MenuRole;
import com.xmf.xcode.role.service.MenuRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * MenuRoleController(角色菜单关系)
 *
 * @author rufei.cn
 * @version 2018-12-19
 */
@Controller
@RequestMapping("/menuRole")
@SuppressWarnings("all")
public class MenuRoleController {

    private static Logger logger = LoggerFactory.getLogger(MenuRoleService.class);
    @Autowired
    private MenuRoleService menuRoleService;

    @RequestMapping
    public String index() {
        return "role/jobMenuRole-index";
    }

    /**
     * getList:(获取角色菜单关系分页查询接口)
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
        String roleId = request.getParameter("roleId");
        String menuId = request.getParameter("menuId");
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
        logger.info("getList:(获取角色菜单关系分页查询接口) 开始  param={}", param);

        param.put("roleId", roleId);
        param.put("menuId", menuId);
        Partion pt = menuRoleService.getList(param);
        List<MenuRole> list = null;
        int totalCount = 0;
        if (pt != null) {
            list = (List<MenuRole>) pt.getList();
            totalCount = pt.getTotalCount();
        }
        retJon.put("data", list);
        retJon.put("recordsTotal", totalCount);
        retJon.put("recordsFiltered", totalCount);
        logger.info("getList:(获取角色菜单关系分页查询接口) 结束");
        return retJon;
    }

    /**
     * delete:(逻辑删除角色菜单关系数据接口)
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
        logger.info("delete 开始============>id={}", id);
        if (id <= 0) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        long newId = id;
        boolean ret = menuRoleService.delete(newId);
        if (ret) {
            retData.setCode(ResultCodeMessage.SUCCESS);
            retData.setMsg(ResultCodeMessage.SUCCESS_MESSAGE);
        }
        return retData;
    }

    /**
     * save:(保存角色菜单关系数据接口)
     *
     * @param request
     * @param parms
     * @return
     * @author rufei.cn
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public ReturnT<String> save(MenuRole menuRole) throws Exception {
        ReturnT<String> retData = new ReturnT<>(ResultCodeMessage.FAILURE, "保存数据失败");
        String parms = null;
        parms = JSON.toJSONString(menuRole);
        logger.info("save:(保存角色菜单关系数据接口) 开始  parms={}", parms);
        if (menuRole == null) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        if (menuRole == null) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        MenuRole ret = menuRoleService.save(menuRole);
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