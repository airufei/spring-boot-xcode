package com.xmf.xcode.code.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.dao.CodeSchemeDao;
import com.xmf.xcode.code.model.CodeScheme;
import com.xmf.xcode.code.model.CodeTable;
import com.xmf.xcode.code.model.CodeTableColumn;
import com.xmf.xcode.code.model.GenTemplate;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.config.GenConfig;
import com.xmf.xcode.util.GenUtils;
import com.xmf.xcode.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service(代码生成方案)
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@Service
@SuppressWarnings("all")
public class CodeSchemeService {

    @Autowired
    private CodeSchemeDao codeSchemeDao;
    @Autowired
    private CodeSchemeHelperService codeSchemeHelperService;
    @Autowired
    private CodeTableHelperService codeTableHelperService;
    @Autowired
    private CodeTableColumnHelperService codeTableColumnHelperService;


    private static Logger logger = LoggerFactory.getLogger(CodeSchemeService.class);

    /**
     * getList(获取代码生成方案带分页数据-服务)
     *
     * @param json
     * @author rufei.cn
     */
    public Partion getList(JSONObject json) {
        logger.info("getList(获取代码生成方案带分页数据-服务) 开始 json={}", json);
        if (json == null || json.size() < 1) {
            return null;
        }
        Partion pt = null;

        int totalcount = codeSchemeHelperService.getTotalCount(json);
        List<CodeScheme> list = null;
        if (totalcount > 0) {
            list = codeSchemeDao.getList(json);
        }
        pt = new Partion(json, list, totalcount);

        logger.info("getList(获取代码生成方案带分页数据-服务) 结束 ");
        return pt;
    }

    /**
     * getCodeSchemeList(获取代码生成方案 不带分页数据-服务)
     *
     * @param codeScheme
     * @author rufei.cn
     */
    public List<CodeScheme> getCodeSchemeList(CodeScheme codeScheme) {
        String parms = JSON.toJSONString(codeScheme);
        List<CodeScheme> list = null;
        logger.info("getCodeSchemeList(获取代码生成方案 不带分页数据-服务) 开始 parms={}", parms);
        if (codeScheme == null) {
            return list;
        }

        list = codeSchemeDao.getCodeSchemeList(codeScheme);

        logger.info("getCodeSchemeList(获取代码生成方案 不带分页数据-服务) 结束");
        return list;
    }


    /**
     * save (保存代码生成方案 数据-服务)
     *
     * @param codeScheme
     * @author rufei.cn
     */
    public CodeScheme save(CodeScheme codeScheme) {
        String parms = JSON.toJSONString(codeScheme);
        logger.info("save (保存代码生成方案 数据-服务) 开始 parms={}", parms);
        if (codeScheme == null) {
            return codeScheme;
        }
        codeScheme = codeSchemeHelperService.save(codeScheme);
        String gen = null;
        if (codeScheme != null) {
            gen = generateGen(codeScheme);//开始生成文件
        }
        if (StringUtil.isBlank(gen)) {
            codeScheme = null;
        }
        logger.info("save (保存代码生成方案 数据-服务) 结束");
        return codeScheme;
    }


    public String generateGen(CodeScheme codeScheme) {

        StringBuilder result = new StringBuilder();
        String functionNameSimple = codeScheme.getFunctionNameSimple();
        String functionName = codeScheme.getFunctionName();
        if (StringUtil.isBlank(functionNameSimple)) {
            codeScheme.setFunctionNameSimple(functionName);
        }
        String tableName = codeScheme.getTableName();
        // 查询主表及字段列
        CodeTable GenTable = codeTableHelperService.getOneCodeTable(tableName);
        if (GenTable == null) {
            return null;
        }
        CodeTableColumn col = new CodeTableColumn();
        col.setTableName(GenTable.getName());
        List<CodeTableColumn> list = codeTableColumnHelperService.getTableColumnList(tableName);
        if (list == null) {
            return null;
        }
        int queryFieldCount = codeTableColumnHelperService.getQueryFieldCount(col);
        int editFieldCount = codeTableColumnHelperService.getEditFieldCount(col);
        // 获取所有代码模板
        GenConfig config = GenUtils.getConfig();

        // 获取模板列表
        List<GenTemplate> templateList = GenUtils.getTemplateList(config, codeScheme.getCategory(), false);
        List<GenTemplate> childTableTemplateList = GenUtils.getTemplateList(config, codeScheme.getCategory(), true);
        if (templateList == null) {
            return null;
        }
        // 生成主表模板代码
        codeScheme.setTableName(GenTable.getName());//getIsNotBaseField 这个字段在GenTableColumn类中
        Map<String, Object> model = GenUtils.getDataModel(codeScheme, GenTable, list, queryFieldCount, editFieldCount);
        for (GenTemplate tpl : templateList) {
            logger.info("tpl================" + tpl.getName());
            List<String> li = tpl.getCategoryList();
            for (String st : li) {
                logger.info("st================" + st);
            }
            boolean res = true;
            String path = codeScheme.getPath();
            path = GenUtils.getProjectPath(path);
            String moduleName = StringUtil.lowerCase(codeScheme.getModuleName());
            String str = GenUtils.generateToFile(tpl, model, res, path, moduleName);
            result.append(str);
        }
        return result.toString();
    }

    /**
     * getCodeScheme(获取代码生成方案单条数据-服务)
     *
     * @param codeScheme
     * @author rufei.cn
     */
    public CodeScheme getCodeScheme(CodeScheme codeScheme) {
        CodeScheme ret = null;
        String parms = JSON.toJSONString(codeScheme);
        List<CodeScheme> list = null;
        logger.info("getCodeScheme(获取代码生成方案单条数据-服务) 开始 parms={}", parms);
        if (codeScheme == null) {
            return ret;
        }
        ret = codeSchemeHelperService.getSignleCodeScheme(codeScheme);
        logger.info("getCodeScheme(获取代码生成方案单条数据-服务) 结束 ");
        return ret;
    }

    /**
     * delete(逻辑删除代码生成方案数据-服务)
     *
     * @param id
     * @author rufei.cn
     */
    public boolean delete(Long id) {
        logger.info("delete(逻辑删除代码生成方案数据-服务) 开始 id={}", id);
        boolean isSuccess = false;
        if (id < 1) {
            return isSuccess;
        }
        CodeScheme dt = codeSchemeHelperService.getCodeSchemeById(id);
        if (dt == null) {
            return isSuccess;
        }
        codeSchemeDao.delete(id);
        isSuccess = true;
        logger.info("delete(逻辑删除代码生成方案数据-服务)结束 id={},isSuccess={}", id, isSuccess);
        return isSuccess;
    }
}