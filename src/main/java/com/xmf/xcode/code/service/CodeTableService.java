package com.xmf.xcode.code.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.dao.CodeTableDao;
import com.xmf.xcode.code.model.CodeTable;
import com.xmf.xcode.common.Partion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service(数据表信息)
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@Service
@SuppressWarnings("all")
public class CodeTableService {

    private static Logger logger = LoggerFactory.getLogger(CodeTableService.class);

    @Autowired
    private CodeTableDao codeTableDao;
    @Autowired
    private CodeTableHelperService codeTableHelperService;
    private String dbName= "xcode";

    /**
     * getList(获取数据表信息带分页数据-服务)
     *
     * @param json
     * @return
     * @author rufei.cn
     */
    public Partion getList(JSONObject json) {
        logger.info("getList(获取数据表信息带分页数据-服务) 开始 json={}", json);
        if (json == null || json.size() < 1) {
            return null;
        }
        Partion pt = null;

        int totalcount = codeTableHelperService.getTotalCount(json);
        List<CodeTable> list = null;
        if (totalcount > 0) {
            list = codeTableDao.getList(json);
        }
        pt = new Partion(json, list, totalcount);
        logger.info("getList(获取数据表信息带分页数据-服务) 结束 ");
        return pt;
    }

    /**
     * getCodeTableList(获取数据表信息 不带分页数据-服务)
     *
     * @param codeTable
     * @return
     * @author rufei.cn
     */
    public List<CodeTable> getCodeTableList(CodeTable codeTable) {
        String parms = JSON.toJSONString(codeTable);
        List<CodeTable> list = null;
        logger.info("getCodeTableList(获取数据表信息 不带分页数据-服务) 开始 parms={}", parms);
        if (codeTable == null) {
            return list;
        }
        list = codeTableDao.getCodeTableList(codeTable);
        logger.info("getCodeTableList(获取数据表信息 不带分页数据-服务) 结束");
        return list;
    }


    /**
     * save (保存数据表信息 数据-服务)
     *
     * @param codeTable
     * @return
     * @author rufei.cn
     */
    public CodeTable save(CodeTable codeTable) {
        String parms = JSON.toJSONString(codeTable);
        logger.info("save (保存数据表信息 数据-服务) 开始 parms={}", parms);
        if (codeTable == null) {
            return codeTable;
        }

        codeTable = codeTableHelperService.save(codeTable);
        logger.info("save (保存数据表信息 数据-服务) 结束");
        return codeTable;
    }

    /**
     * getCodeTable(获取数据表信息单条数据-服务)
     *
     * @param codeTable
     * @return
     * @author rufei.cn
     */
    public CodeTable getCodeTable(CodeTable codeTable) {
        CodeTable ret = null;
        String parms = JSON.toJSONString(codeTable);
        List<CodeTable> list = null;
        logger.info("getCodeTable(获取数据表信息单条数据-服务) 开始 parms={}", parms);
        if (codeTable == null) {
            return ret;
        }
        ret = codeTableHelperService.getSignleCodeTable(codeTable);
        logger.info("getCodeTable(获取数据表信息单条数据-服务) 结束 ");
        return ret;
    }

    /**
     * getCodeTable(获取数据表信息单条数据-服务)
     *
     * @param codeTable
     * @return
     * @author rufei.cn
     */
    public CodeTable getOneCodeTable(String tableName) {
        CodeTable ret = null;
        List<CodeTable> list = null;
        if (tableName == null) {
            return ret;
        }
        CodeTable codeTable=new CodeTable();
        codeTable.setName(tableName);
        ret = codeTableHelperService.getSignleCodeTable(codeTable);
        logger.info("getCodeTable(获取数据表信息单条数据-服务) 结束 ");
        return ret;
    }

    /**
     * 获取当前数据库的所有表信息（不包含系统表）
     *
     * @param dbName
     * @param tableName
     * @return
     */
    public List<CodeTable> getTableList(String tableName) {
        List<CodeTable> tableList = codeTableDao.getTableList(dbName, tableName);
        return tableList;
    }

    /**
     * delete(逻辑删除数据表信息数据-服务)
     *
     * @param id
     * @return
     * @author rufei.cn
     */
    public boolean delete(Long id) {
        logger.info("delete(逻辑删除数据表信息数据-服务) 开始 id={}", id);
        boolean isSuccess = false;
        if (id < 1) {
            return isSuccess;
        }
        CodeTable dt = codeTableHelperService.getCodeTableById(id);
        if (dt == null) {
            return isSuccess;
        }
        codeTableDao.delete(id);
        isSuccess = true;
        logger.info("delete(逻辑删除数据表信息数据-服务)结束 id={},isSuccess={}", id, isSuccess);
        return isSuccess;
    }
}