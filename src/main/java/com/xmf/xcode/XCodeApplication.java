package com.xmf.xcode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 任务处理
 *
 * @author rufei
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement// 开启事务
@MapperScan(basePackages = "com.xmf.xcode.*.dao")// 扫面mybatis Mapper包
public class XCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(XCodeApplication.class, args);
    }
}
