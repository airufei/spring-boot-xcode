package com.xmf.xcode.menu.service;

import com.alibaba.fastjson.JSONObject;
import com.xmf.xcode.menu.dao.MenuDao;
import com.xmf.xcode.menu.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service(job-菜单)
 *
 * @author rufei.cn
 * @version 2018-10-10
 */
@Service
@SuppressWarnings("all")
public class MenuHelperService {

    @Autowired
    private MenuDao menuDao;

    private static Logger logger = LoggerFactory.getLogger(MenuService.class);

    /**
     * 获取分页总记录数
     *
     * @param map
     * @return
     */
    public int getTotalCount(JSONObject map) {
        int resCount = 0;
        Integer totalCount = menuDao.getTotalCount(map);
        if (totalCount != null) {
            resCount = totalCount;
        }
        return resCount;
    }


    /**
     * save(保存job-菜单)
     * @param menu
     * @author rufei.cn
     * @date 2018/1/30 14:59
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Menu save(Menu menu) {
        if (menu == null) {
            return menu;
        }
        if (menu.getId() != null && menu.getId() > 0) {
            updateById(menu);
        } else {
            menuDao.add(menu);
        }
        return menu;
    }

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public Menu getMenuById(long id) {
        return menuDao.getMenuById(id);
    }

    /**
     * 获取单条数据
     *
     * @param menu
     * @return
     * @author rufei.cn
     */
    public Menu getSignleMenu(Menu menu) {
        return menuDao.getSignleMenu(menu);
    }

    /**
     * 修改单条数据
     *
     * @param id
     * @return
     */
    public void updateById(Menu menu) {
        menuDao.updateById(menu);
    }



}