package com.xmf.xcode.code.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.dao.CodeTableColumnDao;
import com.xmf.xcode.code.model.CodeTableColumn;
import com.xmf.xcode.common.Partion;
import com.xmf.xcode.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service(表字段信息)
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@Service
@SuppressWarnings("all")
public class CodeTableColumnService {

    @Autowired
    private CodeTableColumnDao codeTableColumnDao;
    @Autowired
    private CodeTableColumnHelperService codeTableColumnHelperService;


    private static Logger logger = LoggerFactory.getLogger(CodeTableColumnService.class);

    /**
     * getList(获取表字段信息带分页数据-服务)
     *
     * @param json
     * 
     * @author rufei.cn
     */
    public Partion getList(JSONObject json) {
        logger.info("getList(获取表字段信息带分页数据-服务) 开始 json={}", json);
        if (json == null || json.size() < 1) {
            return null;
        }
        Partion pt = null;

        int totalcount = codeTableColumnHelperService.getTotalCount(json);
        List<CodeTableColumn> list = null;
        if (totalcount > 0) {
            list = codeTableColumnDao.getList(json);
        }
        pt = new Partion(json, list, totalcount);
        logger.info("getList(获取表字段信息带分页数据-服务) 结束 ");
        return pt;
    }

    /**
     * getCodeTableColumnList(获取表字段信息 不带分页数据-服务)
     *
     * @param codeTableColumn
     * 
     * @author rufei.cn
     */
    public List<CodeTableColumn> getCodeTableColumnList(CodeTableColumn codeTableColumn) {
        String parms = JSON.toJSONString(codeTableColumn);
        List<CodeTableColumn> list = null;
        logger.info("getCodeTableColumnList(获取表字段信息 不带分页数据-服务) 开始 parms={}", parms);
        if (codeTableColumn == null) {
            return list;
        }
        list = codeTableColumnDao.getCodeTableColumnList(codeTableColumn);
        logger.info("getCodeTableColumnList(获取表字段信息 不带分页数据-服务) 结束");
        return list;
    }


    /**
     * save (保存表字段信息 数据-服务)
     *
     * @param codeTableColumn
     * 
     * @author rufei.cn
     */
    public CodeTableColumn save(CodeTableColumn codeTableColumn) {
        String parms = JSON.toJSONString(codeTableColumn);
        logger.info("save (保存表字段信息 数据-服务) 开始 parms={}", parms);
        if (codeTableColumn == null) {
            return codeTableColumn;
        }
        codeTableColumn = codeTableColumnHelperService.save(codeTableColumn);
        logger.info("save (保存表字段信息 数据-服务) 结束");
        return codeTableColumn;
    }

    /**
     * getCodeTableColumn(获取表字段信息单条数据-服务)
     *
     * @param codeTableColumn
     * 
     * @author rufei.cn
     */
    public CodeTableColumn getCodeTableColumn(CodeTableColumn codeTableColumn) {
        CodeTableColumn ret = null;
        List<CodeTableColumn> list = null;
        if (codeTableColumn == null) {
            return ret;
        }
        ret = codeTableColumnHelperService.getSignleCodeTableColumn(codeTableColumn);
        return ret;
    }

    /**
     * delete(逻辑删除表字段信息数据-服务)
     *
     * @param id
     * 
     * @author rufei.cn
     */
    public boolean delete(Long id) {
        logger.info("delete(逻辑删除表字段信息数据-服务) 开始 id={}", id);
        boolean isSuccess = false;
        if (id < 1) {
            return isSuccess;
        }
        CodeTableColumn dt = codeTableColumnHelperService.getCodeTableColumnById(id);
        if (dt == null) {
            return isSuccess;
        }
        codeTableColumnDao.delete(id);
        isSuccess = true;
        logger.info("delete(逻辑删除表字段信息数据-服务)结束 id={},isSuccess={}", id, isSuccess);
        return isSuccess;
    }

    /**
     * deleteTable(物理删除表字段信息数据-服务)
     *
     * @param id
     * 
     * @author rufei.cn
     */
    public boolean deleteTable(String tableName) {
        logger.info("deleteTable(物理删除表字段信息数据-服务) 开始 tableName={}", tableName);
        boolean isSuccess = false;
        if (StringUtil.isBlank(tableName)) {
            return isSuccess;
        }
        codeTableColumnDao.deleteTable(tableName);
        isSuccess = true;
        logger.info("delete(物理删除表字段信息数据-服务)结束 tableName={},isSuccess={}", tableName, isSuccess);
        return isSuccess;
    }

    /**
     * deleteTable(物理删除表字段信息数据-服务)
     *
     * @param id
     * 
     * @author rufei.cn
     */
    public boolean addTrainRecordBatch(List<CodeTableColumn> list) {
        logger.info("deleteTable(物理删除表字段信息数据-服务) 开始 list={}", JSON.toJSONString(list));
        boolean isSuccess = false;
        if (list == null || list.size() <= 0) {
            return isSuccess;
        }
        CodeTableColumn column = list.get(0);
        if (column != null) {
            deleteTable(column.getTableName());
        }
        for (CodeTableColumn item : list) {
            codeTableColumnDao.add(item);
        }
        isSuccess = true;
        logger.info("delete(物理删除表字段信息数据-服务)结束isSuccess={}", isSuccess);
        return isSuccess;
    }

    /**
     * 获取表字段信息 mysql
     *
     * @param tableName
     * 
     */
    public List<CodeTableColumn> getTableColumnList(String tableName) {
        return codeTableColumnHelperService.getTableColumnList(tableName);
    }

}