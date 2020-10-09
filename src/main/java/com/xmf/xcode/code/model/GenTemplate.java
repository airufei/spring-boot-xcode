/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.xmf.xcode.code.model;

import com.google.common.collect.Lists;
import com.xmf.xcode.common.BaseEntitys;
import com.xmf.xcode.util.StringUtil;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;


/**
 * 生成方案Entity
 *
 * @version 2013-10-15
 */
@XmlRootElement(name="template")
@Data
public class GenTemplate extends BaseEntitys {
	
	private static final long serialVersionUID = 1L;
	private String name; 	// 名称
	private String category;		// 分类
	private String filePath;		// 生成文件路径
	private String fileName;		// 文件名
	private String content;		// 内容
	
	@XmlTransient
	public List<String> getCategoryList() {
		if (category == null){
			return Lists.newArrayList();
		}else{
			return Lists.newArrayList(StringUtil.split(category, ","));
		}
	}

	public void setCategoryList(List<String> categoryList) {
		if (categoryList == null){
			this.category = "";
		}else{
			this.category = ","+StringUtil.join(categoryList, ",") + ",";
		}
	}
	
}


