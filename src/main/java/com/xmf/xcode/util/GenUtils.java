/**
 * Project Name:CooxinPro
 * File Name:GenUtils.java
 * Package Name:com.cn.cooxin.util
 * Date:2017年1月21日下午10:14:37
 * Copyright (c) 2017, hukailee@163.com All Rights Reserved.
 *
*/

package com.xmf.xcode.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmf.xcode.code.model.*;
import com.xmf.xcode.code.model.*;
import com.xmf.xcode.config.GenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.List;
import java.util.Map;


/**
 * ClassName:GenUtils (用一句话描述这个变量表示什么)
 * Date:     2017年1月21日 下午10:14:37
 * @Author   airufei
 * @Version  1.0
 * @see 	 
 */
public class GenUtils {

	private static Logger logger = LoggerFactory.getLogger(GenUtils.class);

	
	/**
	 * 获取模板路径
	 * @return
	 */
	public static String getTemplatePath(){
		try{
			File file = new DefaultResourceLoader().getResource("").getFile();
			if(file != null){
				return file.getAbsolutePath() + File.separator + StringUtil.replaceEach(GenUtils.class.getName(),
						new String[]{"util."+GenUtils.class.getSimpleName(), "."}, new String[]{"template", File.separator});
			}			
		}catch(Exception e){
			logger.error("{}", e);
		}

		return "";
	}
	
	/**
	 * XML文件转换为对象
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fileToObject(String fileName, Class<?> clazz){
		try {
			String pathName = "/model/" + fileName;
			Resource resource = new ClassPathResource(pathName); 
			InputStream is = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder sb = new StringBuilder();  
			while (true) {
				String line = br.readLine();
				if (line == null){
					break;
				}
				sb.append(line).append("\r\n");
			}
			if (is != null) {
				is.close();
			}
			if (br != null) {
				br.close();
			}
			return (T) JaxbMapper.fromXml(sb.toString(), clazz);
		} catch (IOException e) {
			logger.warn("Error file convert: {}", e.getMessage());
		} catch (JAXBException e) {
			logger.warn("Error file convert: {}", e.getMessage());
			
		}

		return null;
	}
	
	/**
	 * 获取代码生成配置对象
	 * @return
	 */
	public static GenConfig getConfig(){
		return fileToObject("config.xml", GenConfig.class);
	}

	/**
	 * 根据分类获取模板列表
	 * @param config
	 * @param category
	 * @param isChildTable 是否是子表
	 * @return
	 */
	public static List<GenTemplate> getTemplateList(GenConfig config, String category, boolean isChildTable){
		List<GenTemplate> templateList = Lists.newArrayList();
		if (config !=null && config.getCategoryList() != null && category !=  null){
			for (GenCategory e : config.getCategoryList()){
				if (category.equals(e.getDictKey())){
					List<String> list = null;
					if (!isChildTable){
						list = e.getTemplate();
					}else{
						list = e.getChildTableTemplate();
					}
					if (list != null){
						for (String s : list){
							if (StringUtil.startsWith(s, GenCategory.CATEGORY_REF)){
								templateList.addAll(getTemplateList(config, StringUtil.replace(s, GenCategory.CATEGORY_REF, ""), false));
							}else{
								GenTemplate template = fileToObject(s, GenTemplate.class);
								if (template != null){
									templateList.add(template);
								}
							}
						}
					}
					break;
				}
			}
		}
		return templateList;
	}
	
	/**
	 * 获取数据模型
	 * @param genScheme
	 * @param GenTable
	 * @return
	 */
	public static Map<String, Object> getDataModel(CodeScheme genScheme, CodeTable GenTable, List<CodeTableColumn> list, int queryCount, int editCount){
		Map<String, Object> model = Maps.newHashMap();
		String moduleName=StringUtil.lowerCase(genScheme.getModuleName());
		model.put("packageName", StringUtil.lowerCase(genScheme.getPackageName()));
		model.put("lastPackageName", StringUtil.substringAfterLast((String)model.get("packageName"),"."));
		model.put("javaModuleName", moduleName);
		model.put("modulePageName", StringUtil.lowerCase(genScheme.getModulePageName()));
		model.put("subPackageName", StringUtil.lowerCase(genScheme.getSubPageName()));
		model.put("subModuleName", StringUtil.lowerCase(genScheme.getSubModuleName()));
		model.put("className", StringUtil.uncapitalize(GenTable.getClassName()));
		model.put("ClassName", StringUtil.capitalize(GenTable.getClassName()));
		model.put("moduleName", moduleName);
		model.put("functionName", genScheme.getFunctionName());
		model.put("functionNameSimple", genScheme.getFunctionNameSimple());
		model.put("functionAuthor", StringUtil.isNotBlank(genScheme.getFunctionAuthor())?genScheme.getFunctionAuthor():"rufei");
		model.put("functionVersion", DateUtils.getDate());
		model.put("dbType", "mysql");
		model.put("editCount", editCount);
		model.put("queryCount", queryCount);
		model.put("table", GenTable);
		model.put("colList", list);
		return model;
	}
	

	
	/**
	 * 生成到文件
	 * @param tpl
	 * @param model
	 * @param isReplaceFile
	 * @return
	 */
	public static String generateToFile(GenTemplate tpl, Map<String, Object> model, boolean isReplaceFile,String path,String moduleName){
		String msg=null;
		try {
			model.put("moduleName", moduleName);
			FileUtil.CreateDirectory(path);
			// 获取生成文件
			String fileName = path + File.separator + StringUtil.replaceEach(FreeMarkers.renderString(tpl.getFilePath() + "/", model), new String[]{"//", "/", "."}, new String[]{File.separator, File.separator, File.separator})+ FreeMarkers.renderString(tpl.getFileName(), model);
			if(fileName!=null)
			{
				fileName=fileName.replace(",", "").replace("index_1", "index");
			}
			logger.info(" moduleName === " + moduleName +" ============fileName:" + fileName);
			// 获取生成文件内容
			String content = FreeMarkers.renderString(StringUtil.trimToEmpty(tpl.getContent()), model);
			logger.debug(" content === \r\n" + content);
			
			// 如果选择替换文件，则删除原文件
			if (isReplaceFile){
				FileUtil.deleteFile(fileName);
			}
			// 创建并写入文件
			if (FileUtil.createFile(fileName)){
				FileUtil.writeToFile(fileName, content, true);
				logger.debug(" file create === " + fileName);
				msg= "生成成功："+fileName+"<br/>";
			}else{
				logger.debug(" file extents === " + fileName);
				msg ="文件已存在："+fileName+"<br/>";
			}
		} catch (Exception e) {
			logger.warn("Error file convert: {}", e.getMessage());
		}
		catch (Error e) {
			logger.warn("Error=========================>"+e);
		}
		return msg;
	}

	/**
	 * 获取工程路径
	 * @return
	 */
	public static String getProjectPath(String projectPath){
		// 如果配置了工程路径，则直接返回，否则自动获取。
		if (StringUtil.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {

		}
		return projectPath;
	}

	public static void main(String[] args) {
		try {
			GenConfig config = getConfig();
			logger.warn(JaxbMapper.toXml(config));
		} catch (Exception e) {

		}
	}
	
}

