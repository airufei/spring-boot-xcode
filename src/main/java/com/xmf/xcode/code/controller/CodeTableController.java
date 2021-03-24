package com.xmf.xcode.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.model.CodeTable;
import com.xmf.xcode.code.service.CodeTableService;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.common.ResultCodeMessage;
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
 * CodeTableController(数据表信息)
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@Controller
@RequestMapping("/codeTable")
@SuppressWarnings("all")

public class CodeTableController {

    private static Logger logger = LoggerFactory.getLogger(CodeTableController.class);
    @Autowired
    private CodeTableService codeTableService;

    @RequestMapping
    public String index() {
        return "code/codeTable-index";
    }

    /**
     * getList:(获取数据表信息分页查询接口)
     *
     * @param request
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
        logger.info("getList:(获取数据表信息分页查询接口) 开始  param={}", param);
        param.put("name", name);
        Partion pt = codeTableService.getList(param);
        List<CodeTable> list = null;
        int totalCount = 0;
        if (pt != null) {
            list = (List<CodeTable>) pt.getList();
            totalCount = pt.getTotalCount();
        }
        retJon.put("data", list);
        retJon.put("recordsTotal", totalCount);
        retJon.put("recordsFiltered", totalCount);
        logger.info("getList:(获取数据表信息分页查询接口) 结束");
        return retJon;
    }

    /**
     * delete:(逻辑删除数据表信息数据接口)
     *
     * @param request
     * @param parms
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
        boolean ret = codeTableService.delete(newId);
        if (ret) {
            retData.setCode(ResultCodeMessage.SUCCESS);
            retData.setMsg(ResultCodeMessage.SUCCESS_MESSAGE);
        }
        logger.info("delete 结束============>" + JSON.toJSONString(retData));
        return retData;
    }

    /**
     * 获取当前数据库的所有表信息（不包含系统表）
     *
     * @param dbName
     * @param tableName
     */
    @RequestMapping(value = "getTableList")
    @ResponseBody
    public List<CodeTable> getTableList() {
        List<CodeTable> tableList = null;
        String tableName = null;
        tableList = codeTableService.getTableList(tableName);
        return tableList;
    }

    /**
     * 已经配置的数据表
     *
     * @param dbName
     * @param tableName
     */
    @RequestMapping(value = "getHasConfigTableList")
    @ResponseBody
    public List<CodeTable> getHasConfigTableList() {
        List<CodeTable> tableList = null;
        CodeTable codeTable = new CodeTable();
        tableList = codeTableService.getCodeTableList(codeTable);
        return tableList;
    }

    /**
     * 获取单个表的数据
     *
     * @param tableName
     */
    @RequestMapping(value = "getOneTable")
    @ResponseBody
    public CodeTable getOneTable(String tableName) {
        if (StringUtil.isBlank(tableName)) {
            return null;
        }
        CodeTable table = codeTableService.getOneCodeTable(tableName);
        if (table != null) {
            return table;
        }
        List<CodeTable> tableList = codeTableService.getTableList(tableName);
        if (tableList == null || tableList.size() <= 0) {
            return table;
        }
        table = tableList.get(0);
        if (table != null && StringUtil.isBlank(table.getClassName())) {
            String capitalize = StringUtil.capitalize(StringUtil.toCamelCase(table.getName()));
            table.setClassName(capitalize);
        }
        return table;
    }

    /**
     * save:(保存数据表信息数据接口)
     *
     * @param request
     * @param parms
     * @author rufei.cn
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public ReturnT<String> save(CodeTable codeTable) {
        ReturnT<String> retData = new ReturnT<>(ResultCodeMessage.FAILURE, "保存数据失败");
        String parms = null;

        parms = JSON.toJSONString(codeTable);
        logger.info("save:(保存数据表信息数据接口) 开始  parms={}", parms);
        if (codeTable == null) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        if (codeTable == null) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        CodeTable ret = codeTableService.save(codeTable);
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