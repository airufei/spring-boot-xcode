package com.xmf.xcode.code.model;


import com.xmf.xcode.common.BaseEntitys;

import javax.xml.bind.annotation.XmlAttribute;


/**
 * 字典数据Entity
 *
 * @author airufei
 * @version 2017-02-26
 */
public class Dict extends BaseEntitys {

    private static final long serialVersionUID = 1L;
    private String dictKey; // 值
    private String dictValue; // 名称
    private Long fid; // 父级ID
    private String sort; // 排序
    private String fvalue; // 父级字典值
    private String fname; // 父级名称
    private String keyWords;//分类类型 关键词
    private String type;//分类类型 关键词

    public Dict() {

    }

    @XmlAttribute
    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    @XmlAttribute
    public String getDictValue() {
        return dictValue;
    }

}