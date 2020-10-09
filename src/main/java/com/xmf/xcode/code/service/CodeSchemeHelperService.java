package com.xmf.xcode.code.service;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.code.dao.CodeSchemeDao;
import com.xmf.xcode.code.model.CodeScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service(代码生成方案)
 *
 * @author rufei.cn
 * @version 2018-12-10
 */
@Service
@SuppressWarnings("all")
public class CodeSchemeHelperService {

    @Autowired
    private CodeSchemeDao codeSchemeDao;

    private static Logger logger = LoggerFactory.getLogger(CodeSchemeService.class);

    /**
     * 获取分页总记录数
     *
     * @param map
     * @return
     */
    public int getTotalCount(JSONObject map) {
        int resCount = 0;
        Integer totalCount = codeSchemeDao.getTotalCount(map);
        if (totalCount != null) {
            resCount = totalCount;
        }
        return resCount;
    }


    /**
     * save(保存代码生成方案)
     *
     * @param codeScheme
     * @author rufei.cn
     * @date 2018/1/30 14:59
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CodeScheme save(CodeScheme codeScheme) {
        if (codeScheme == null) {
            return codeScheme;
        }
        String tableName = codeScheme.getTableName();
        CodeScheme scheme = getSignleCodeSchemeByTableName(tableName);
        if (scheme != null) {
            codeScheme.setId(scheme.getId());
        }
        if (codeScheme.getId() != null && codeScheme.getId() > 0) {
            updateById(codeScheme);
        } else {
            codeSchemeDao.add(codeScheme);
        }
        return codeScheme;
    }

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public CodeScheme getCodeSchemeById(long id) {
        return codeSchemeDao.getCodeSchemeById(id);
    }

    /**
     * 获取单条数据
     *
     * @param codeScheme
     * @return
     * @author rufei.cn
     */
    public CodeScheme getSignleCodeScheme(CodeScheme codeScheme) {
        return codeSchemeDao.getSignleCodeScheme(codeScheme);
    }

    /**
     * 获取单条数据
     *
     * @param codeScheme
     * @return
     * @author rufei.cn
     */
    public CodeScheme getSignleCodeSchemeByTableName(String tableName) {
        CodeScheme codeScheme = new CodeScheme();
        codeScheme.setTableName(tableName);
        return getSignleCodeScheme(codeScheme);
    }

    /**
     * 修改单条数据
     *
     * @param id
     * @return
     */
    public void updateById(CodeScheme codeScheme) {
        codeSchemeDao.updateById(codeScheme);
    }

}