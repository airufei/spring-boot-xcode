/**
 * Project Name:CooxinPro
 * File Name:Pantion.java
 * Package Name:com.cn.cooxin.pojo
 * Date:2016年7月6日下午1:51:37
 * Copyright (c) 2016, hukailee@163.com All Rights Reserved.
 */

package com.xmf.xcode.common;

import com.xmf.xcode.util.StringUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName:Partion Date: 2016年7月6日 下午1:51:37
 *
 * @author rufei.cn
 * @Version 1.0
 * @see
 */
@Data
@SuppressWarnings("all")
public class Partion<T> implements java.io.Serializable {

    private int totalCount;// 总的记录数
    private List<T> list;// 查询的数据集
    private int currentCount = 1;// 当前页页码
    private int pageSize = 10;// 每页记录数
    private int pageCount = 0;// 总页数
    private int nevPage = 0;// 上一页页码
    private int nextPage = 0;// 下一页页码
    private int isNext = -1;// 是否显示下一页按钮(-1显示  1不显示)
    private int isNev = 1;// 是否显示下一页按钮(-1显示  1不显示)

    public Partion() {

    }

    /**
     * 构造函数
     * @param map 查询参数
     * @param list 数据集
     * @param totalcount 总记录数
     */
    public Partion(Map map, List<T> list, int totalcount) {
        if (map != null) {
            currentCount = StringUtil.objToInt(map.get("currentCount"));
            pageSize = StringUtil.objToInt(map.get("pageSize"));
        }
        if (list == null) {
            list = new ArrayList();
        }
        if (totalcount > 0) {
            int pageCount = (totalcount + pageSize - 1) / pageSize;
            this.setPageCount(pageCount);
            nextPage = currentCount + 1;
            nevPage = currentCount - 1;
            if (nevPage < 1) {
                nevPage = 1;
            }
            this.setNextPage(nextPage);
            this.setNevPage(nevPage);
        }
        if (nextPage > pageCount) {
            this.setIsNext(1);
            nextPage = pageCount;
        }
        if (currentCount > 1) {
            this.setIsNev(-1);
        }
        this.setList(list);
        this.setTotalCount(totalcount);
    }

}
