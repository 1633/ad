package com.imooc.ad.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Seven
 * @date 2019/10/25 23:58
 * @description 配置binlog监听的配置类 @ConfigurationProperties指定配置文件中的前缀
 */
@Configuration
@ConfigurationProperties(prefix = "adconf.mysql")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinlogConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String binlogName;
    private Long position;

}
