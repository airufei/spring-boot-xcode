/**
 * Project Name:CooxinPro
 * File Name:FileUtil.java
 * Package Name:com.cn.cooxin.util
 * Date:2016年7月13日上午11:12:10
 * Copyright (c) 2016, hukailee@163.com All Rights Reserved.
 */

package com.xmf.xcode.util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName:FileUtil(文件处理类)
 *
 * @author rufei
 * @Version 1.0
 * @see
 */
public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);

    /**
     * CreateDirectory:(根据路径创建)
     *
     * @param path
     * @author rufei
     */
    public static void CreateDirectory(String path) {
        if (StringUtil.isNotBlank(path)) {
            File file = new File(path);
            // 如果文件夹不存在则创建
            if (!file.isDirectory()) {
                file.mkdirs();
            }

        }

    }

    /**
     * getFileList:(获取文件夹下面的所有文件)
     *
     * @author rufei
     * @param strPath
     * @return
     */
    static List<File> filelist = new ArrayList<File>();

    public static List<File> getFileList(String strPath) {
        if (strPath == null) {
            return filelist;
        }
        try {
            File dir = new File(strPath);
            // 该文件目录下文件全部放入数组
            File[] files = dir.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    // 判断是文件还是文件夹
                    if (files[i].isDirectory()) {
                        // 获取文件绝对路径
                        getFileList(files[i].getAbsolutePath());
                    } else {
                        filelist.add(files[i]);
                    }
                }

            }
        } catch (Exception e) {
            logger.error("getFileList============ error====" + e);

        }
        return filelist;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        if (StringUtil.isBlank(fileName)) {
            return false;
        }
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.debug("删除文件 " + fileName + " 成功!");
                return true;
            } else {
                logger.debug("删除文件 " + fileName + " 失败!");
                return false;
            }
        } else {
            logger.debug(fileName + " 文件不存在!");
            return true;
        }
    }

    /**
     * 创建单个文件
     *
     * @param descFileName 文件名，包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createFile(String descFileName) {
        File file = new File(descFileName);
        if (file.exists()) {
            logger.debug("文件 " + descFileName + " 已存在!");
            return false;
        }
        if (descFileName.endsWith(File.separator)) {
            logger.debug(descFileName + " 为目录，不能创建目录!");
            return false;
        }
        if (!file.getParentFile().exists()) {
            // 如果文件所在的目录不存在，则创建目录
            if (!file.getParentFile().mkdirs()) {
                logger.debug("创建文件所在的目录失败!");
                return false;
            }
        }

        // 创建文件
        try {
            if (file.createNewFile()) {
                logger.debug(descFileName + " 文件创建成功!");
                return true;
            } else {
                logger.debug(descFileName + " 文件创建失败!");
                return false;
            }
        } catch (Exception e) {

            logger.debug(descFileName + " 文件创建失败!");
            return false;
        }

    }

    /**
     * 获取当前项目的盘符
     *
     * @return
     */
    public static String getCurrentDriveName() {
        String property = System.getProperty("user.dir");
        String[] split = null;
        String driveName = null;
        if (StringUtil.isNotBlank(property)) {
            split = property.split(":");
        }
        if (split != null) {
            driveName = split[0];
        }
        return driveName;
    }

    /**
     * 写入文件
     *
     * @param fileName 要写入的文件
     */
    public static void writeToFile(String fileName, String content, boolean append) {
        try {
            FileUtils.write(new File(fileName), content, "utf-8", append);
            logger.debug("文件 " + fileName + " 写入成功!");
        } catch (IOException e) {
            logger.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

    /**
     * 写入文件
     *
     * @param fileName 要写入的文件
     */
    public static void writeToFile(String fileName, String content, String encoding, boolean append) {
        try {
            FileUtils.write(new File(fileName), content, encoding, append);
            logger.debug("文件 " + fileName + " 写入成功!");
        } catch (IOException e) {
            logger.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String driveName = getCurrentDriveName();
        logger.info("oo:" + driveName);
    }
}
