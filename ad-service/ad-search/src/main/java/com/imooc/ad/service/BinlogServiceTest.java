package com.imooc.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

/**
 * @author Seven
 * @date 2019/10/24 0:08
 * @description 测试 MySQL的Binlog 启动后可以监听数据库的binlog信息(数据库的增删改操作)
 * 新增信息：WriteRowsEventData{tableId=348, includedColumns={0, 1, 2}, rows=[[18, 3, OPPO]]}
 * 新增信息：WriteRowsEventData{tableId=89, includedColumns={0, 1, 2, 3, 4, 5, 6, 7}, rows=[
 *     [5, 1, middle-plan, 1, Wed Oct 30 20:00:00 CST 2019, Sat Nov 30 20:00:00 CST 2019, Sat Oct 26 20:00:00 CST 2019, Sat Oct 26 20:00:00 CST 2019]
 * ]}
 * 更新信息：UpdateRowsEventData{tableId=94, includedColumnsBeforeUpdate={0, 1, 2}, includedColumns={0, 1, 2}, rows=[
 *          {before=[18, 3, OPPO], after=[18, 3, FindOne]}]}
 */
public class BinlogServiceTest {

    public static void main(String[] args) throws Exception {
        BinaryLogClient client = new BinaryLogClient("192.168.169.129", 3306, "root", "yijialu7");

        // 可以指定文件与文件位置
//        client.setBinlogFilename("");
//        client.setBinlogPosition(66666L);

        // 通过注册监听事件获取结果 判断结果类型，输出数据
        client.registerEventListener(event -> {
            EventData data = event.getData();

            if (data instanceof UpdateRowsEventData) {
                System.out.println("update--------------------");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("write--------------------");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("delete--------------------");
                System.out.println(data.toString());
            }
        });

        client.connect();

    }

}
