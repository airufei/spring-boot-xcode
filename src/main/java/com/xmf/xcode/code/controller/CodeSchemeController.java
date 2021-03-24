package com.xmf.xcode.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.model.CodeScheme;
import com.xmf.xcode.code.model.GenCategory;
import com.xmf.xcode.code.service.CodeSchemeService;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.common.ResultCodeMessage;
import com.xmf.xcode.common.ReturnT;
import com.xmf.xcode.config.GenConfig;
import com.xmf.xcode.util.FileUtil;
import com.xmf.xcode.util.GenUtils;
import com.xmf.xcode.util.StringUtil;
import com.xmf.xcode.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * CodeSchemeController(代码生成方案)
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@Controller
@RequestMapping("/codeScheme")
@SuppressWarnings("all")
public class CodeSchemeController {

    private static Logger logger = LoggerFactory.getLogger(CodeSchemeService.class);
    @Autowired
    private CodeSchemeService codeSchemeService;

    @RequestMapping
    public String index() {
        return "code/codeScheme-index";
    }

    /**
     * getList:(获取代码生成方案分页查询接口)
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
        String tableName = request.getParameter("tableName");
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
        logger.info("getList:(获取代码生成方案分页查询接口) 开始  param={}", param);
        param.put("name", name);
        param.put("tableName", tableName);
        Partion pt = codeSchemeService.getList(param);
        List<CodeScheme> list = null;
        int totalCount = 0;
        if (pt != null) {
            list = (List<CodeScheme>) pt.getList();
            totalCount = pt.getTotalCount();
        }
        retJon.put("data", list);
        retJon.put("recordsTotal", totalCount);
        retJon.put("recordsFiltered", totalCount);

        logger.info("getList:(获取代码生成方案分页查询接口) 结束");
        return retJon;
    }

    /**
     * delete:(逻辑删除代码生成方案数据接口)
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
        boolean ret = codeSchemeService.delete(newId);
        if (ret) {
            retData.setCode(ResultCodeMessage.SUCCESS);
            retData.setMsg(ResultCodeMessage.SUCCESS_MESSAGE);
        }
        logger.info("delete 结束============>" + JSON.toJSONString(retData));
        return retData;
    }

    /**
     * delete:(逻辑删除代码生成方案数据接口)
     *
     * @param request
     * @param parms
     * @author rufei.cn
     */
    @RequestMapping("downloadCode")
    public void downloadCode(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("id");
        long newId = StringUtil.stringToLong(ids);
        logger.info("download 开始============>" + newId);
        if (newId <= 0) {
            return;
        }
        CodeScheme codeScheme = new CodeScheme();
        codeScheme.setId(newId);
        CodeScheme scheme = codeSchemeService.getCodeScheme(codeScheme);
        if (scheme == null) {
            return;
        }

        //获取文件路径
        String filePath = scheme.getPath();
        String os = System.getProperties().getProperty("os.name");
        String zipPath = "/opt/hiscene/down/";
        logger.info("filePath={},zipPath={}============>", filePath, zipPath);
        List<File> zipList = null;
        try {
            zipList = FileUtil.getFileNewList(zipPath);
        } catch (Exception e) {
            logger.info("删除原有的zip包===========>{}", e.getMessage());
        }
        if (zipList != null) {
            for (File file : zipList) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        zipList = null;
        List<File> list = FileUtil.getFileNewList(filePath);
        if (list != null) {
            for (File file : list) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        list = null;
        String generateGen = codeSchemeService.generateGen(scheme);
        if (generateGen == null) {
            return;
        }
        if (StringUtil.isNotBlank(os) && os.contains("Windows")) {
            filePath = FileUtil.getCurrentDriveName() + ":" + filePath;
            zipPath = FileUtil.getCurrentDriveName() + ":" + zipPath;
        }
        list = FileUtil.getFileNewList(filePath);
        if (list == null || list.size() <= 0) {
            logger.info("获取文件列表失败");
            return;
        }
        ZipUtil.batchDownloadFiles(list, zipPath, filePath, response);
        logger.info("download 结束============>");
    }

    /**
     * save:(保存代码生成方案数据接口)
     *
     * @param request
     * @param parms
     * @author rufei.cn
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public ReturnT<String> save(CodeScheme codeScheme) {
        ReturnT<String> retData = new ReturnT<>(ResultCodeMessage.FAILURE, "保存数据失败");
        String parms = null;

        parms = JSON.toJSONString(codeScheme);
        logger.info("save:(保存代码生成方案数据接口) 开始  parms={}", parms);
        if (codeScheme == null) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        if (codeScheme == null) {
            retData.setMsg(ResultCodeMessage.PARMS_ERROR_MESSAGE);
            return retData;
        }
        CodeScheme ret = codeSchemeService.save(codeScheme);
        if (ret == null) {
            retData.setMsg("保存或者生成代码失败");
            return retData;
        }
        retData.setCode(ResultCodeMessage.SUCCESS);
        retData.setMsg(ResultCodeMessage.SUCCESS_MESSAGE);
        logger.info("save 结束============>" + JSON.toJSONString(retData));
        return retData;
    }

    /**
     * 获取生成模型
     *
     * @param dbName
     * @param tableName
     */
    @RequestMapping(value = "getCodeModelList")
    @ResponseBody
    public List<GenCategory> getCodeModelList() {
        GenConfig con = GenUtils.getConfig();
        List<GenCategory> list = con.getCategoryList();
        return list;
    }
}