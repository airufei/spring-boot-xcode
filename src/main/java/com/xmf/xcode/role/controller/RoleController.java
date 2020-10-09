package com.xmf.xcode.role.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.common.ResultCodeMessage;
import com.xmf.xcode.role.model.Role;
import com.xmf.xcode.role.service.MenuRoleService;
import com.xmf.xcode.role.service.RoleService;
import com.xmf.xcode.common.ReturnT;
import com.xmf.xcode.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * RoleController(角色数据)
 *
 * @author rufei.cn
 * @version 2018-12-19
 */
@Controller
@RequestMapping("/role")
@SuppressWarnings("all")
public class RoleController {

    private static Logger logger = LoggerFactory.getLogger(RoleService.class);
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuRoleService menuRoleService;

    @RequestMapping
    public String index() {
        return "role/role-index";
    }

    /**
     * getList:(获取角色数据分页查询接口)
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
        String name = request.getParameter("name");
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
        logger.info("getList:(获取角色数据分页查询接口) 开始  param={}", param);

        param.put("name", name);
        Partion pt = roleService.getList(param);
        List<Role> list = null;
        int totalCount = 0;
        if (pt != null) {
            list = (List<Role>) pt.getList();
            totalCount = pt.getTotalCount();
        }
        retJon.put("data", list);
        retJon.put("recordsTotal", totalCount);
        retJon.put("recordsFiltered", totalCount);
        logger.info("getList:(获取角色数据分页查询接口) 结束");
        return retJon;
    }

    /**
     * delete:(逻辑删除角色数据数据接口)
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
        boolean ret = roleService.delete(newId);
        if (ret) {
            retData.setCode(ResultCodeMessage.SUCCESS);
            retData.setMsg(ResultCodeMessage.SUCCESS_MESSAGE);
        }
        logger.info("delete 结束============>" + JSON.toJSONString(retData));
        return retData;
    }

    /**
     * save:(保存角色数据数据接口)
     *
     * @param request
     * @param parms
     * @return
     * @author rufei.cn
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public ReturnT<String> save(Role role, HttpServletRequest request) throws Exception {
        ReturnT<String> retData = new ReturnT<>(ResultCodeMessage.FAILURE, "保存数据失败");
        String parms = null;
        parms = JSON.toJSONString(role);
        logger.info("save:(保存角色数据数据接口) 开始  parms={}", parms);
        if (role == null) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        if (role == null) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        Long roleId = role.getId();
        boolean isDelete = true;
        if (roleId != null && roleId > 0) {
            isDelete = menuRoleService.delete(roleId);
        }
        if (!isDelete) {
            retData.setMsg("保存数据失败");
            return retData;
        }
        Role ret = roleService.save(role);
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