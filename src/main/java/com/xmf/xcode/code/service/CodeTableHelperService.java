package com.xmf.xcode.code.service;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.dao.CodeTableDao;
import com.xmf.xcode.code.model.CodeTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service(数据表信息)
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@Service
@SuppressWarnings("all")
public class CodeTableHelperService {

    @Autowired
    private CodeTableDao codeTableDao;
    private static Logger logger = LoggerFactory.getLogger(CodeTableService.class);

    /**
     * 获取分页总记录数
     *
     * @param map
     */
    public int getTotalCount(JSONObject map) {
        int resCount = 0;
        Integer totalCount = codeTableDao.getTotalCount(map);
        if (totalCount != null) {
            resCount = totalCount;
        }
        return resCount;
    }


    /**
     * save(保存数据表信息)
     *
     * @param codeTable
     * @author rufei.cn
     * @date 2018/1/30 14:59
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CodeTable save(CodeTable codeTable) {
        if (codeTable == null) {
            return codeTable;
        }
        if (codeTable.getId() != null && codeTable.getId() > 0) {
            updateById(codeTable);
        } else {
            codeTableDao.add(codeTable);
        }
        return codeTable;
    }

    /**
     * 获取单条数据
     *
     * @param id
     */
    public CodeTable getCodeTableById(long id) {
        return codeTableDao.getCodeTableById(id);
    }

    /**
     * 获取单条数据
     *
     * @param codeTable
     * @author rufei.cn
     */
    public CodeTable getSignleCodeTable(CodeTable codeTable) {
        return codeTableDao.getSignleCodeTable(codeTable);
    }

    /**
     * getCodeTable(获取数据表信息单条数据-服务)
     *
     * @param codeTable
     * @author rufei.cn
     */
    public CodeTable getOneCodeTable(String tableName) {
        CodeTable ret = null;
        List<CodeTable> list = null;
        if (tableName == null) {
            return ret;
        }
        CodeTable codeTable = new CodeTable();
        codeTable.setName(tableName);
        ret = getSignleCodeTable(codeTable);
        logger.info("getCodeTable(获取数据表信息单条数据-服务) 结束 ");
        return ret;
    }

    /**
     * 修改单条数据
     *
     * @param id
     */
    public void updateById(CodeTable codeTable) {
        codeTableDao.updateById(codeTable);
    }

}