package com.xmf.xcode.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    private static Logger logger = LoggerFactory.getLogger(ZipUtil.class);
    /**
     * 批量打包
     *
     * @param list     json格式字符串数据
     * @param downPath 项目根目录
     *                 zip文件保存绝对路径
     */
    public static String createZipAndReturnPath(List<File> list, String downPath,String filePath) {
        ZipOutputStream out = null;
        try {
            //生成打包下载后的zip文件:Papers.zip
            String papersZipName = "x-code.zip";
            File files = new File(downPath);
            if (!files.exists()) {
                files.mkdirs();
            }
            //zip文件保存路径
            String zipPath = downPath + papersZipName;
            logger.info("zipPath={}", zipPath);
            out = new ZipOutputStream(new FileOutputStream(zipPath));

            //遍历jsonArray列表获取所有JSONObject对象
            for (int i = 0; i < list.size(); i++) {
                File file = list.get(i);

                //获得文件相对路径
                String relativePath = file.getPath();
                String packag="";
                if(relativePath!=null){
                    packag = relativePath.replace(filePath,"");
                }
                //获得下载文件完整路径
                String downloadPath = relativePath;
                //以论文标题为每个文件命名
                FileInputStream fis = new FileInputStream(downloadPath);
                out.putNextEntry(new ZipEntry(packag));

                //写入压缩包
                int len;
                byte[] buffer = new byte[1024];
                while ((len = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.closeEntry();
                fis.close();
            }
            out.close();
            out.flush();
            return zipPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }

    /**
     * 批量下载
     *
     * @param list     文件路径
     * @param downPath 下载zip文件路径
     * @param response 返回
     */
    public static void batchDownloadFiles(List<File> list, String downPath,String filePath, HttpServletResponse response) {
        //创建zip文件并返回zip文件路径
        String zipPath = ZipUtil.createZipAndReturnPath(list, downPath,filePath);
        if (zipPath == null) {
            return;
        }
        BufferedOutputStream out = null;
        BufferedInputStream is = null;
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/zip;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=x-code.zip");
            System.out.println(response.getHeader("Content-Disposition"));

            //
            logger.info("开始下载 downPath ={}", downPath);
            is = new BufferedInputStream(new FileInputStream(new File(zipPath)));
            out = new BufferedOutputStream(response.getOutputStream());

            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = is.read(buff, 0, buff.length)) != -1) {
                out.write(buff, 0, len);
            }
            logger.info("下载结束 len ={}", len);
            out.close();
            out.flush();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out.flush();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
