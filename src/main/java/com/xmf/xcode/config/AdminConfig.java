package com.xmf.xcode.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */
@Configuration
public class AdminConfig implements InitializingBean {


    private String i18n="";

    private static AdminConfig adminConfig = null;

    public static AdminConfig getAdminConfig() {
        return adminConfig;
    }

    @Override
    public void afterPropertiesSet() {
        adminConfig = this;
    }


    public String getI18n() {
        return i18n;
    }

}
