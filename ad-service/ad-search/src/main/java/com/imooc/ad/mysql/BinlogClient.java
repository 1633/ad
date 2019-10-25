package com.imooc.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.imooc.ad.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Seven
 * @date 2019/10/26 0:07
 * @description Binlog的启动类
 */
@Slf4j
@Component
public class BinlogClient {
    /**
     * 客户端
     */
    private BinaryLogClient client;
    private final BinlogConfig config;
    private final AggregationListener listener;

    @Autowired
    public BinlogClient(BinlogConfig config, AggregationListener listener) {
        this.config = config;
        this.listener = listener;
    }

    /**
     * 启动线程，实现监听 且在程序启动就监听
     */
    public void connect() {
        new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(), config.getPort(), config.getUsername(), config.getPassword());
            if (!StringUtils.isEmpty(config.getBinlogName()) && !config.getPosition().equals(-1)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }

            client.registerEventListener(listener);

            try {
                log.info("connect mysql for binlog start............");
                client.connect();
                log.info("connect mysql for binlog done............");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 关闭binlog监听
     */
    public void close() {
        try {
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
