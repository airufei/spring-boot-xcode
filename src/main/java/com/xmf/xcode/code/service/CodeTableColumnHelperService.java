package com.xmf.xcode.code.service;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.dao.CodeTableColumnDao;
import com.xmf.xcode.code.model.CodeTable;
import com.xmf.xcode.code.model.CodeTableColumn;
import com.xmf.xcode.util.TableColumnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service(表字段信息)
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@Service
@SuppressWarnings("all")
public class CodeTableColumnHelperService {

    @Autowired
    private CodeTableColumnDao codeTableColumnDao;
    @Autowired
    private CodeTableHelperService cdeTableHelperService;
    private static Logger logger = LoggerFactory.getLogger(CodeTableColumnService.class);

    /**
     * 获取分页总记录数
     *
     * @param map
     */
    public int getTotalCount(JSONObject map) {
        int resCount = 0;
        Integer totalCount = codeTableColumnDao.getTotalCount(map);
        if (totalCount != null) {
            resCount = totalCount;
        }
        return resCount;
    }


    /**
     * save(保存表字段信息)
     *
     * @param codeTableColumn
     * @author rufei.cn
     * @date 2018/1/30 14:59
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CodeTableColumn save(CodeTableColumn codeTableColumn) {
        if (codeTableColumn == null) {
            return codeTableColumn;
        }
        if (codeTableColumn.getId() != null && codeTableColumn.getId() > 0) {
            updateById(codeTableColumn);
        } else {
            codeTableColumnDao.add(codeTableColumn);
        }
        return codeTableColumn;
    }

    /**
     * 获取单条数据
     *
     * @param id
     */
    public CodeTableColumn getCodeTableColumnById(long id) {
        return codeTableColumnDao.getCodeTableColumnById(id);
    }

    /**
     * 获取单条数据
     *
     * @param codeTableColumn
     * @author rufei.cn
     */
    public CodeTableColumn getSignleCodeTableColumn(CodeTableColumn codeTableColumn) {
        return codeTableColumnDao.getSignleCodeTableColumn(codeTableColumn);
    }

    /**
     * getQueryFieldCount:(查询字段的个数)
     *
     * @param col
     * @author airufei
     */
    public int getQueryFieldCount(CodeTableColumn col) {
        return codeTableColumnDao.getQueryFieldCount(col);
    }

    /**
     * getQueryFieldCount:(编辑页字段的个数)
     *
     * @param col
     * @author airufei
     */
    public int getEditFieldCount(CodeTableColumn col) {
        return codeTableColumnDao.getEditFieldCount(col);
    }

    /**
     * 获取表字段信息 mysql
     *
     * @param tableName
     */
    public List<CodeTableColumn> getTableColumnList(String tableName) {
        CodeTableColumn column = new CodeTableColumn();
        column.setTableName(tableName);
        List<CodeTableColumn> list = codeTableColumnDao.getCodeTableColumnList(column);
        if (list == null || list.size() <= 0) {
            list = codeTableColumnDao.getDBTableColumnList(tableName);
        }
        if (list == null || list.size() <= 0) {
            return list;
        }
        CodeTable table = cdeTableHelperService.getOneCodeTable(tableName);//根据表名查询表Id
        List<CodeTableColumn> resList = new ArrayList();
        for (CodeTableColumn item : list) {
            TableColumnUtils.initColumnField(item);//初始化表列信息
            if (table != null) {
                item.setTableId(String.valueOf(table.getId()));
                item.setTableName(table.getName());
            }
            String name = item.getName();
            if ("Id".equals(name) || "ID".equals(name) || "id".equals(name)) {
                item.setIsPk("1");
            } else {
                item.setIsPk("0");
            }
            resList.add(item);
        }
        List<CodeTableColumn> newList = new ArrayList();
        for (CodeTableColumn con : resList) {
            String sqlType = con.getJdbcType();
            if (sqlType != null && (sqlType.contains("int"))) {
                con.setJavaType("java.lang.Integer");
            } else if (sqlType != null && sqlType.contains("bigint")) {
                con.setJavaType("java.lang.Long");
            } else if (sqlType != null && (sqlType.contains("datetime") || sqlType.contains("date"))) {
                con.setJavaType("java.util.Date");
            } else if (sqlType != null && (sqlType.contains("decimal"))) {
                con.setJavaType("java.math.BigDecimal");
            } else {
                con.setJavaType("java.lang.String");
            }
            newList.add(con);
        }
        return newList;
    }

    /**
     * 修改单条数据
     *
     * @param id
     */
    public void updateById(CodeTableColumn codeTableColumn) {
        codeTableColumnDao.updateById(codeTableColumn);
    }

}