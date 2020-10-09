package com.xmf.xcode.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class StringUtil extends StringUtils {
    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
    private static final char SEPARATOR = '_';

    public static boolean isBlank(final CharSequence cs) {
        if ("null".equals(StringUtils.trim(String.valueOf(cs)))) {
            return true;
        }
        return StringUtils.isBlank(cs);
    }

    public static boolean isNotBlank(final CharSequence cs) {
        if ("null".equals(StringUtils.trim(String.valueOf(cs)))) {
            return false;
        }

        return StringUtils.isNotBlank(cs);
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        if ("null".equals(StringUtils.trim(String.valueOf(cs)))) {
            return false;
        }
        return StringUtils.isNotEmpty(cs);
    }

    /**
     * getUUID:(获取UUID)
     *
     * @param
     * @return
     * @author rufei.cn
     * @Date 2017/11/23 15:19
     **/
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    /**
     * stringToFloat:(String转float 默认值0)
     *
     * @param
     * @return
     * @author rufei.cn
     * @Date 2017/11/23 15:19
     **/
    public static float stringToFloat(String s) {
        float result = 0L;
        if (isBlank(s)) {
            return result;
        }
        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            String exceptionMsg = StringUtil.getExceptionMsg(e);
            logger.error(exceptionMsg);
        }
        return result;
    }

    /**
     * stringToDouble:(String 转double 默认值0)
     *
     * @param
     * @return
     * @author rufei.cn
     * @Date 2017/11/23 15:20
     **/
    public static double stringToDouble(String s) {
        double result = 0L;
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            String exceptionMsg = StringUtil.getExceptionMsg(e);
            logger.error(exceptionMsg);
        }
        return result;
    }

    /**
     * stringToInt:(String 转int 默认值0)
     *
     * @param
     * @return
     * @author rufei.cn
     * @Date 2017/11/23 15:21
     **/
    public static int stringToInt(String strId) {
        int id = 0;
        try {
            if (isBlank(strId)) {
                return id;
            }
            id = Integer.parseInt(strId);
        } catch (Exception e) {
            String exceptionMsg = StringUtil.getExceptionMsg(e);
            logger.error(exceptionMsg);
        }
        return id;
    }

    /**
     * objectToInt:(Object 转int 默认值0)
     *
     * @param
     * @return
     * @author rufei.cn
     * @Date 2017/11/23 15:21
     **/
    public static int objectToInt(Object obj) {
        int id = -1;
        try {
            if (obj == null) {
                return id;
            }
            if (isBlank(obj.toString())) {
                return id;
            }
            id = Integer.parseInt(obj.toString());
        } catch (Exception e) {
            String exceptionMsg = StringUtil.getExceptionMsg(e);
            logger.error(exceptionMsg);
        }
        return id;
    }

    /**
     * stringToLong:(String转long，默认值0)
     *
     * @param
     * @return
     * @author rufei.cn
     * @Date 2017/11/23 15:21
     **/
    public static long stringToLong(String strId) {
        long id = 0;
        try {
            if (isBlank(strId)) {
                return id;
            }
            id = Long.parseLong(strId);
        } catch (Exception e) {
            String exceptionMsg = StringUtil.getExceptionMsg(e);
            logger.error(exceptionMsg);
        }
        return id;
    }

    /**
     * obectToString:(Object 转字符串 默认"")
     *
     * @param
     * @return
     * @author rufei.cn
     * @Date 2017/11/23 15:25
     **/
    public static String obectToString(Object obj) {
        String str = "";
        if (obj != null) {
            str = obj.toString();
        }
        return str;
    }

    /**
     * stringToBoolean:(String转boolean 默认false)
     *
     * @param
     * @return
     * @author rufei.cn
     * @Date 2017/11/23 15:30
     **/
    public static boolean stringToBoolean(String s) {
        boolean b = false;
        try {
            if (isBlank(s)) {
                return b;
            }
            b = Boolean.parseBoolean(s);
        } catch (Exception e) {
            String exceptionMsg = StringUtil.getExceptionMsg(e);
            logger.error(exceptionMsg);
        }
        return b;
    }

    /**
     * 字符串转数字
     *
     * @param str 数字字符串
     * @return
     */
    public static int objToInt(Object obj) {
        int result = 0;
        try {
            if (obj != null && obj.toString().length() > 0) {
                result = Integer.parseInt(obj.toString());
            }
        } catch (NumberFormatException e) {
            String exceptionMsg = StringUtil.getExceptionMsg(e);
            logger.error(exceptionMsg);
        }
        return result;

    }


    /**
     * isNumber:(判断字符是否为数字)
     *
     * @param
     * @return
     * @author rufei.cn
     * @Date 2017/11/23 15:31
     **/
    public static boolean isNumber(String str) {
        if (str.matches("\\d*")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * isMobilePhone :(判断字符是否为手机号)
     *
     * @param
     * @return
     * @author rufei.cn
     * @Date 2017/11/23 15:32
     **/
    public static boolean isMobilePhone(String s) {
        boolean result = false;
        if (StringUtil.isBlank(s)) {
            return result;
        }
        String mobile = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        try {
            Pattern pattern = Pattern.compile(mobile);
            Matcher matcher = pattern.matcher(s);
            result = matcher.matches();
        } catch (Exception e) {
            result = false;
            logger.error(StringUtil.getExceptionMsg(e));
        }
        return result;
    }

    /**
     * 是否中文名称
     *
     * @param name
     * @return
     */
    public static boolean isChinese(String str) {
        boolean ret = false;
        if (isBlank(str)) {
            return ret;
        }
        return Pattern.matches("[\\u4e00-\\u9fa5]+", str);
    }

    /**
     * 过滤特殊字符
     *
     * @param str 带特殊字符的字符串
     * @return 过滤之后的字符串（只剩字母、数字、下划线）
     */
    public static String stringFilter(String str) {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * getURLDecoderString:(URL解码)
     *
     * @param url
     * @param ENCODE
     * @return
     * @author rufei.cn
     */
    public static String getURLDecoderString(String url, String encode) {
        String result = "";
        if (null == url) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(url, encode);
        } catch (UnsupportedEncodingException e) {
            String exceptionMsg = StringUtil.getExceptionMsg(e);
            logger.error(exceptionMsg);
        }
        return result;
    }


    /**
     * getURLEncoderString:(URL编码)
     *
     * @param url
     * @param ENCODE
     * @return
     * @author rufei.cn
     */
    public static String getURLEncoderString(String url, String encode) {
        String result = "";
        if (null == url) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(url, encode);
        } catch (UnsupportedEncodingException e) {
            logger.error(StringUtil.getExceptionMsg(e));
        }
        return result;
    }

    /**
     * StrToDouble:(字符转Double)
     *
     * @param amount
     * @return
     * @author rufei.cn
     */
    public static double StrToDouble(String amount) {
        double result = 0.00;
        try {
            if (StringUtil.isBlank(amount)) {
                return result;
            }
            result = Double.parseDouble(amount);
        } catch (Exception e) {
            logger.error(StringUtil.getExceptionMsg(e));
        }
        return result;
    }

    /**
     * StrToBoolean:(字符转 boolean)
     *
     * @param amount
     * @return
     * @author rufei.cn
     */
    public static boolean strToBoolean(String str) {
        boolean result = false;
        try {
            if (isBlank(str)) {
                return result;
            }
            result = Boolean.parseBoolean(str);
        } catch (Exception e) {
            String exceptionMsg = StringUtil.getExceptionMsg(e);
            logger.error(exceptionMsg);
        }
        return result;
    }


    /**
     * redirect:(重定向)
     *
     * @param response
     * @param url
     * @author rufei.cn
     */
    public static void redirect(HttpServletResponse response, String url) {
        try {
            logger.info("重定向到：url={}", url);
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }

    /**
     * getPageJSONObject:(当前页转化为MYSQL所需要的分页参数)
     *
     * @param pageStr
     * @return
     * @author rufei.cn
     */

    public static JSONObject getPageJSONObject(int currentCount, int pageSize) {
        JSONObject map = new JSONObject();
        if (pageSize < 1) {
            pageSize = 10;
        }
        int startIndex = 0;
        if (currentCount <= 1) {
            currentCount = 1;
            startIndex = 0;
        } else {
            startIndex = (currentCount - 1) * pageSize;
        }
        map.put("startIndex", startIndex);
        map.put("pageSize", pageSize);
        map.put("currentCount", currentCount);
        map.put("sortWay", "desc");
        map.put("flag", 1);
        return map;
    }


    /**
     * 输入流转化成字符串
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    /**
     * getExceptionMsg:(根据异常获取关键异常信息字符串)
     *
     * @param throwable
     * @return
     * @author rufei.cn
     */
    public static String getExceptionMsg(Throwable throwable) {
        StringBuilder stackMessage = new StringBuilder();
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        if (stackTrace == null || stackTrace.length < 1) {
            return null;
        }
        int len = stackTrace.length;
        for (int i = 0; i < len; i++) {
            if (i > 6) {
                break;
            }
            String moduleName = moduleName = stackTrace[i].getClassName();
            String methodName = stackTrace[i].getMethodName();
            Class<?> aClass = null;
            try {
                aClass = Class.forName(moduleName);
            } catch (ClassNotFoundException e) {

            }
            int lineNumber = stackTrace[i].getLineNumber();
            String simpleName = null;
            stackMessage.append(moduleName).append(" ").append(methodName).append(simpleName).append("\n\n");
        }
        return stackMessage.toString();
    }



    /**
     * getRandNum:(生成随机数)
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     * @Author rufei.cn
     */
    public static int getRandNum(int min, int max) {
        Random random = new Random();
        int num = random.nextInt(max);
        if (min > num) {
            num = max - min;
        }
        return num;
    }

    /**
     * 当前线程sleep
     *
     * @param randNum
     */
    public static void threadSleep(long randNum) {
        try {
            Thread.sleep(randNum);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 获取访问者IP
     * <p>
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * <p>
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 获取系统名称
     *
     * @return
     */
    public static String getSubSysName(String sysName,String profiles) {
        String sysSubSysName = sysName+"("+profiles+")";
        return sysSubSysName;
    }

    /**
     * getSystemUrl:(获取访问服务器的端口号IP)
     * http://localhost:8081/lcApp 或者http://localhost:8081/lcApp
     *
     * @param request
     * @param isHttps 是否https 链接
     * @return
     * @author rufei.cn
     */
    public static String getSystemUrl(HttpServletRequest request) {
        // 用于获取服务器IP 端口号 项目名
        int localPort = request.getServerPort();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        logger.info("ip/domname:" + scheme + "://" + serverName + ":" + localPort + request.getContextPath());
        String url = "";
        if (localPort == 80) {
            url = scheme + "://" + serverName + request.getContextPath();
        } else {
            url = scheme + "://" + serverName + ":" + localPort
                    + request.getContextPath();
        }
        return url;
    }

    /**
     * 密码加密
     *
     * @param password
     * @return
     * @throws Exception
     */
    public static String getEncryptPassword(String password) {
        String ret = null;
        try {
            password = M5Util.getMd5(password);
            ret = new StringBuilder(password).reverse().toString();//反转
        } catch (Exception e) {
            ret = null;
            String exceptionMsg = StringUtil.getExceptionMsg(e);
            logger.error(exceptionMsg);
        }
        return ret;
    }

    /**
     * getUuId:(获取唯一标识)
     *
     * @return
     * @author rufei.cn
     */
    public static String getUuId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    public static void main(String[] args) {
        String password = getEncryptPassword("abc123");
        System.out.println(password);
        System.out.println(isChinese("张三"));
        System.out.println(isChinese("三奥术大师大所多无"));
    }

}